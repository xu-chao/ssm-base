package com.java.activiti.service.impl;

import com.java.activiti.dao.GoodsDao;
import com.java.activiti.dao.NeedDao;
import com.java.activiti.model.*;
import com.java.activiti.model.erp.Company;
import com.java.activiti.service.*;
import com.java.activiti.service.erp.CompanyService;
import com.java.activiti.util.UserUtil;
import com.java.activiti.util.UuidUtil;
import com.java.activiti.util.aop.Operation;
import org.apache.ibatis.jdbc.Null;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormat;
import org.springframework.security.access.method.P;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service("needService")
public class NeedServiceImpl implements NeedService {

    private static final int ERP_INCREMENT_LENGTH = 12;//流水号长度
    private static final int ERP_DBINDEX = 10;//redisDB缓存区
    private static final String ERP_ID = "ERP_ID";//redisDB缓存区

    @Resource
    private NeedDao needDao;

    @Resource
    private GoodsDao goodsDao;

    @Resource
    private StorageService storageService;

    @Resource
    private NeedGoodsService needGoodsService;

    @Resource
    private CityService cityService;

    @Resource
    private ParkService parkService;

    @Resource
    private SubProjectService subProjectService;

    @Resource
    private CompanyService companyService;

    @Resource
    private RedisService redisService;

    @Override
    public List<Need> needPage(Map<String, Object> map) {
        return needDao.needPage(map);
    }

    @Override
    public int needCount(Map<String, Object> map) {
        return needDao.needCount(map);
    }

    @Override
    public int needCountByMonth() {
        return needDao.needCountByMonth();
    }

    @Override
    public int addNeed(Need need) {
        return needDao.addNeed(need);
    }

    @Override
    public Need findById(String id) {
        return needDao.findById(id);
    }

    @Override
    public Need findNeedById(String id) {
        return needDao.findNeedById(id);
    }

    @Override
    public int updateNeed(Need need) {
        return needDao.updateNeed(need);
    }

    @Override
    public int updateNeedByNId(Need need) {
        return needDao.updateNeedByNId(need);
    }

    @Override
    public Need getNeedByTaskId(String processInstanceId) {
        return needDao.getNeedByTaskId(processInstanceId);
    }

    @Override
    public  List<Need> selectTaskByProcessID(List<String> maps) {
        return needDao.selectTaskByProcessID(maps);
    }

    @Override
    public void export(OutputStream os, Map<String, Object> map) {
        // 获取所有供应商信息
        List<Need> needList = needDao.needPage(map);
        // 1.创建excel工作薄
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.创建一个工作表
        HSSFSheet sheet = wk.createSheet("系统用户");
        // 3.写入表头
        HSSFRow row = sheet.createRow(0);
        // 表头
        String[] headerName = {
                "流水号", "采购单号", "申请日期", "加工类型",
                "提单公司", "专业/类型", "区域", "工厂耗材",
                "工程名称", "公园名称", "项目名称", "子项目名称",
                "系统名称", "申请人", "公司主体", "要求到货时间"
        };
        // 列宽
        int[] columnWidths = {
                6000, 6000, 6000, 6000,
                6000 ,6000, 6000, 6000,
                6000, 6000, 6000, 6000,
                6000, 6000, 6000, 6000
        };
        HSSFCell cell = null;
        for (int i = 0; i < headerName.length; i++) {
            // 创建表头单元格
            cell = row.createCell(i);
            // 向表头单元格写值
            cell.setCellValue(headerName[i]);
            sheet.setColumnWidth(i, columnWidths[i]);
        }
        // 4.向内容单元格写值
        int i = 1;
        for (Need need : needList) {
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(need.getId());// 流水号
            row.createCell(1).setCellValue(need.getNID());// 采购单号
            if (need.getEApplicantData() != null) {
                HSSFCellStyle style_date = wk.createCellStyle();
                DataFormat df = wk.createDataFormat();
                style_date.setDataFormat(df.getFormat("yyyy-MM-dd HH:mm:ss"));
                row.createCell(2).setCellValue(need.getEApplicantData());// 申请日期
                sheet.getRow(i).getCell(2).setCellStyle(style_date);
            }
            row.createCell(3).setCellValue(need.getEPType());// 加工类型
            row.createCell(4).setCellValue(need.getEBillCompany());// 提单公司
            String EType = need.getEType();
            if (EType.equals("0") || EType.equals("机械生产")) {
                row.createCell(5).setCellValue("机械生产");
            }else if (EType.equals("4") || EType.equals("电气生产")) {
                row.createCell(5).setCellValue("电气生产");
            }else if (EType.equals("6") || EType.equals("机械现场")) {
                row.createCell(5).setCellValue("机械现场");
            }else if (EType.equals("7") || EType.equals("电气现场")) {
                row.createCell(5).setCellValue("电气现场");
            }else if (EType.equals("8") || EType.equals("试制研发")) {
                row.createCell(5).setCellValue("试制研发");
            }
            row.createCell(6).setCellValue(need.getEZone());// 区域
            row.createCell(7).setCellValue(need.getEConsumer());// 工厂耗材
            row.createCell(8).setCellValue(need.getSubProject().getCity().getCityName());// 工程名称
            row.createCell(9).setCellValue(need.getSubProject().getPark().getParkName());// 公园名称
            row.createCell(10).setCellValue(need.getSubProject().getSubProjectName());// 项目名称
            row.createCell(11).setCellValue(need.getESubProjectNameElse());// 子项目名称
            row.createCell(12).setCellValue(need.getESysName());// 系统名称
            row.createCell(13).setCellValue(need.getEApplicant());// 申请人
            row.createCell(14).setCellValue((need.getCompany() == null ? "" : need.getCompany().getCOMPANY_NAME()));// 公司主体
            if (need.getEndDate() != null) {
                HSSFCellStyle style_date = wk.createCellStyle();
                DataFormat df = wk.createDataFormat();
                style_date.setDataFormat(df.getFormat("yyyy-MM-dd HH:mm:ss"));
                row.createCell(15).setCellValue(need.getEndDate());// 要求到货时间
                sheet.getRow(i).getCell(15).setCellStyle(style_date);
            }
            i++;
        }
        try {
            // 写入到输出流中
            wk.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭工作簿
                wk.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 数据导入
     */
    @Override
    public List<NeedGoods> goodsImport(InputStream is) throws IOException {
//        XSSFWorkbook wb = null;
        HSSFWorkbook wb = null;
        List<NeedGoods> needGoodsList = new LinkedList<>();
        try {
//            wb = new XSSFWorkbook(is);
            wb = new HSSFWorkbook(is);
//            XSSFSheet sheet = wb.getSheetAt(0);
            HSSFSheet sheet = wb.getSheetAt(0);
            // 读取数据
            // 最后一行的行号
            int lastRow = sheet.getLastRowNum();
//            Need need = new Need();
//            need.setId(UuidUtil.uuidUtil());
//            need.setNID("N" + UuidUtil.uuidUtil());
//            need.setESubID(1);
//            int needResult = needDao.addNeed(need);
            Goods goods = null;
//            Storage storage = null;
            NeedGoods needGoods = null;
            for (int i = 1; i <= lastRow; i++) {
                goods = new Goods();
//                storage = new Storage();
                needGoods = new NeedGoods();
                HSSFCell cell = null;
                // 类型
                cell = sheet.getRow(i).getCell(0);
                String goodsType = "";
                if(cell != null){
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    goodsType = sheet.getRow(i).getCell(0).getStringCellValue();
                }else {
                    goodsType = "其他";
                }
                goods.setGoodsType(goodsType);
                // 名称
                cell = sheet.getRow(i).getCell(1);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                goods.setGoodsName(sheet.getRow(i).getCell(1).getStringCellValue());
                // 图号
                cell = sheet.getRow(i).getCell(2);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                goods.setGoodsCode(sheet.getRow(i).getCell(2).getStringCellValue());
                // 单位
                cell = sheet.getRow(i).getCell(3);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                goods.setGoodsUnit(sheet.getRow(i).getCell(3).getStringCellValue());
                Goods goodsByExcel = goodsDao.findGoodsByExcel(goods);
//                // 数量
//                cell = sheet.getRow(i).getCell(4);
//                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
//                storage.setMount((int)sheet.getRow(i).getCell(4).getNumericCellValue());
//                //  备用
//                cell = sheet.getRow(i).getCell(5);
//                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
//                storage.setMountBack((int)sheet.getRow(i).getCell(5).getNumericCellValue());
//                // 实购
//                cell = sheet.getRow(i).getCell(6);
//                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
//                storage.setMountIn((int)sheet.getRow(i).getCell(6).getNumericCellValue());
//                // 变更
//                cell = sheet.getRow(i).getCell(7);
//                String ETemp = "";
//                if(cell != null){
//                    cell.setCellType(Cell.CELL_TYPE_STRING);
//                    ETemp = sheet.getRow(i).getCell(7).getStringCellValue();
//                }else {
//                    ETemp = "";
//                }
//                storage.setETemp(ETemp);
//                // 计划备注
//                cell = sheet.getRow(i).getCell(8);
//                String EPlan = "";
//                if(cell != null){
//                    cell.setCellType(Cell.CELL_TYPE_STRING);
//                    EPlan = sheet.getRow(i).getCell(8).getStringCellValue();
//                }else {
//                    EPlan = "";
//                }
//                storage.setEPlan(EPlan);
//                // 发货标识
//                cell = sheet.getRow(i).getCell(9);
//                String EIsRun = "";
//                if(cell != null){
//                    cell.setCellType(Cell.CELL_TYPE_STRING);
//                    EIsRun = sheet.getRow(i).getCell(9).getStringCellValue();
//                }else {
//                    EIsRun = "√";
//                }
//                storage.setEIsRun(EIsRun);
//                storage.setStorageId("S" + UuidUtil.uuidUtil());
                if(goodsByExcel == null){
                    goods.setGoodsId("WH-NEW-" + UuidUtil.uuidUtil() + "-00" + i);
                    int goodsResult = goodsDao.addGoods(goods);
                    if(goodsResult == 1){
                        goodsByExcel = goods;
                    }else {
                        throw new IOException();
                    }
                }
//                int storageResult=storageService.addStorage(storage);
//                if(storageResult > 0){
//                    needGoods.setNeed(need);
//                    needGoods.setGoods(goodsByExcel);
//                    needGoods.setStorage(storage);
//                    int needGoodsResult = needGoodsService.addNeedGoods(needGoods);
//                }else {
//                    throw new IOException();
//                }
                needGoods.setGoods(goodsByExcel);
                needGoodsList.add(needGoods);
            }
        } finally {
            if (null != wb) {
                try {
                    wb.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return needGoodsList;
        }
    }

    /**
     * 数据导入
     */
//    @Override
//    public List<NeedGoods> goodsImport(InputStream is) throws IOException {
////        XSSFWorkbook wb = null;
//        HSSFWorkbook wb = null;
//        List<NeedGoods> needGoodsList = new LinkedList<>();
//        try {
////            wb = new XSSFWorkbook(is);
//            wb = new HSSFWorkbook(is);
////            XSSFSheet sheet = wb.getSheetAt(0);
//            HSSFSheet sheet = wb.getSheetAt(0);
//            // 读取数据
//            // 最后一行的行号
//            int lastRow = sheet.getLastRowNum();
//            Need need = new Need();
//            need.setId(UuidUtil.uuidUtil());
//            need.setNID("N" + UuidUtil.uuidUtil());
//            need.setESubID(1);
//            int needResult = needDao.addNeed(need);
//            Goods goods = null;
//            Storage storage = null;
//            NeedGoods needGoods = null;
//            for (int i = 1; i <= lastRow; i++) {
//                // cityID
//                goods = new Goods();
//                storage = new Storage();
//                needGoods = new NeedGoods();
//                HSSFCell cell = null;
//                // 类型
//                cell = sheet.getRow(i).getCell(0);
//                String goodsType = "";
//                if(cell != null){
//                    cell.setCellType(Cell.CELL_TYPE_STRING);
//                    goodsType = sheet.getRow(i).getCell(0).getStringCellValue();
//                }else {
//                    goodsType = "其他";
//                }
//                goods.setGoodsType(goodsType);
//                // 名称
//                cell = sheet.getRow(i).getCell(1);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                goods.setGoodsName(sheet.getRow(i).getCell(1).getStringCellValue());
//                // 图号
//                cell = sheet.getRow(i).getCell(2);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                goods.setGoodsCode(sheet.getRow(i).getCell(2).getStringCellValue());
//                // 单位
//                cell = sheet.getRow(i).getCell(3);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                goods.setGoodsUnit(sheet.getRow(i).getCell(3).getStringCellValue());
//                Goods goodsByExcel = goodsDao.findGoodsByExcel(goods);
//                // 数量
//                cell = sheet.getRow(i).getCell(4);
//                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
//                storage.setMount((int)sheet.getRow(i).getCell(4).getNumericCellValue());
//                //  备用
//                cell = sheet.getRow(i).getCell(5);
//                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
//                storage.setMountBack((int)sheet.getRow(i).getCell(5).getNumericCellValue());
//                // 实购
//                cell = sheet.getRow(i).getCell(6);
//                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
//                storage.setMountIn((int)sheet.getRow(i).getCell(6).getNumericCellValue());
//                // 变更
//                cell = sheet.getRow(i).getCell(7);
//                String ETemp = "";
//                if(cell != null){
//                    cell.setCellType(Cell.CELL_TYPE_STRING);
//                    ETemp = sheet.getRow(i).getCell(7).getStringCellValue();
//                }else {
//                    ETemp = "";
//                }
//                storage.setETemp(ETemp);
//                // 计划备注
//                cell = sheet.getRow(i).getCell(8);
//                String EPlan = "";
//                if(cell != null){
//                    cell.setCellType(Cell.CELL_TYPE_STRING);
//                    EPlan = sheet.getRow(i).getCell(8).getStringCellValue();
//                }else {
//                    EPlan = "";
//                }
//                storage.setEPlan(EPlan);
//                // 发货标识
//                cell = sheet.getRow(i).getCell(9);
//                String EIsRun = "";
//                if(cell != null){
//                    cell.setCellType(Cell.CELL_TYPE_STRING);
//                    EIsRun = sheet.getRow(i).getCell(9).getStringCellValue();
//                }else {
//                    EIsRun = "√";
//                }
//                storage.setEIsRun(EIsRun);
//                storage.setStorageId("S" + UuidUtil.uuidUtil());
//                if(goodsByExcel == null){
//                    goods.setGoodsId("WH-NEW-" + UuidUtil.uuidUtil() + "-00" + i);
//                    int goodsResult = goodsDao.addGoods(goods);
//                    if(goodsResult == 1){
//                        goodsByExcel = goods;
//                    }else {
//                        throw new IOException();
//                    }
//                }
//                int storageResult=storageService.addStorage(storage);
//                if(storageResult > 0){
//                    needGoods.setNeed(need);
//                    needGoods.setGoods(goodsByExcel);
//                    needGoods.setStorage(storage);
//                    int needGoodsResult = needGoodsService.addNeedGoods(needGoods);
//                }else {
//                    throw new IOException();
//                }
//                needGoodsList.add(needGoods);
//            }
//        } finally {
//            if (null != wb) {
//                try {
//                    wb.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            return needGoodsList;
//        }
//    }

    /**
     * 数据导入
     */
    @Override
    public void needImport(InputStream is) throws IOException {
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(is);
            HSSFSheet sheet = wb.getSheetAt(0);
            int lastRow = sheet.getLastRowNum();
            Need need = new Need();
            City city = new City();
            Park park = new Park();
            SubProject subProject = new SubProject();
            Company company = new Company();
            Goods goods = null;
            Storage storage = null;
            NeedGoods needGoods = null;
            for (int i = 1; i <= lastRow; i++) {
                goods = new Goods();
                storage = new Storage();
                needGoods = new NeedGoods();
                HSSFCell cell = null;
                // 采购单号
                if(i ==1){
                    need.setId(redisService.getIncrementNum(ERP_INCREMENT_LENGTH,ERP_ID,ERP_DBINDEX,2));
                    need.setEID(need.getId());
                    need.setEApplicantData(new Date());
                    cell = sheet.getRow(i).getCell(0);
                    String nId = "";
                    if(cell != null){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        nId = sheet.getRow(i).getCell(0).getStringCellValue();
                        if(needDao.findById(nId) != null){ throw new IOException("采购单号已存在:" + nId); }
                    }else {
                        throw new IOException("采购单号出错");
                    }
                    need.setNID(nId);

                    // 加工类型
                    cell = sheet.getRow(i).getCell(1);
                    String EPtype = "";
                    if(cell != null){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        EPtype = sheet.getRow(i).getCell(1).getStringCellValue();
                    }else {
                        throw new IOException("加工类型出错");
                    }
                    need.setEPType(EPtype);

                    // 提单公司
                    cell = sheet.getRow(i).getCell(2);
                    String EBillCompany = "";
                    if(cell != null){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        EBillCompany = sheet.getRow(i).getCell(2).getStringCellValue();
                    }else {
                        EBillCompany = "";
                    }
                    need.setEBillCompany(EBillCompany);

                    // 专业/类型
                    cell = sheet.getRow(i).getCell(3);
                    String EType = "";
                    if(cell != null){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        EType = sheet.getRow(i).getCell(3).getStringCellValue();
                    }else {
                        EType = "";
                    }
                    need.setEType(EType);

                    // 区域
                    cell = sheet.getRow(i).getCell(4);
                    String EZone = "";
                    if(cell != null){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        EZone = sheet.getRow(i).getCell(4).getStringCellValue();
                    }else {
                        EZone = "";
                    }
                    need.setEZone(EZone);

                    // 工厂耗材
                    cell = sheet.getRow(i).getCell(5);
                    String EConsumer = "";
                    if(cell != null){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        EConsumer = sheet.getRow(i).getCell(5).getStringCellValue();
                    }else {
                        EConsumer = "";
                    }
                    need.setEConsumer(EConsumer);

                    // 城市名
                    cell = sheet.getRow(i).getCell(6);
                    String  cityName= "";
                    if(cell != null){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        cityName = sheet.getRow(i).getCell(6).getStringCellValue();
                        city = cityService.findCityByName(cityName);
                        if(city == null) throw new IOException("第"+ i +"行，" + "不存在该城市:" + cityName);
                    }else {
                       throw new IOException("城市名出错");
                    }

                    // 公园名
                    cell = sheet.getRow(i).getCell(7);
                    String  parkName= "";
                    if(cell != null){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        parkName = sheet.getRow(i).getCell(7).getStringCellValue();
                        park = parkService.findParkByCityID(city.getCityID(),parkName);
                        if(park == null) throw new IOException("第"+ i +"行，" + "不存在该公园:" + parkName);
                    }else {
                        throw new IOException("公园名出错");
                    }

                    // 项目名
                    cell = sheet.getRow(i).getCell(8);
                    String  subProjectName= "";
                    if(cell != null){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        subProjectName = sheet.getRow(i).getCell(8).getStringCellValue();
                        subProject = subProjectService.findSubProjectByParkID(park.getParkID(),subProjectName);
                        if(subProject == null) throw new IOException("第"+ i +"行，" + "不存在该项目:" + subProjectName);
                    }else {
                        throw new IOException("项目名出错");
                    }
                    need.setESubID(subProject.getSubProjectID());
                    need.setSubProject(subProject);

                    // 子项目名
                    cell = sheet.getRow(i).getCell(9);
                    String  ESubProjectNameElse= "";
                    if(cell != null){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        ESubProjectNameElse = sheet.getRow(i).getCell(9).getStringCellValue();
                    }else {
                        ESubProjectNameElse = "";
                    }
                    need.setESubProjectNameElse(ESubProjectNameElse);

                    // 系统名称
                    cell = sheet.getRow(i).getCell(10);
                    String  ESysName= "";
                    if(cell != null){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        ESysName = sheet.getRow(i).getCell(10).getStringCellValue();
                    }else {
                        ESysName = "";
                    }
                    need.setESysName(ESysName);

                    // 要求到货时间
                    cell = sheet.getRow(i).getCell(11);
                    Date endDate;
                    if(cell != null){
                        //cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                        endDate = sheet.getRow(i).getCell(11).getDateCellValue();
                    }else {
                        endDate = new Date();
                    }
                    need.setEndDate(endDate);

                    // 公司主体
                    cell = sheet.getRow(i).getCell(12);
                    String companyName = "";
                    if(cell != null){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        companyName = sheet.getRow(i).getCell(12).getStringCellValue();
                        company = companyService.findCompanyByName(companyName);
                        if(company == null) throw new IOException("第"+ i +"行，" + "不存在该公司主体:" + companyName);
                    }else {
                        throw new IOException("公司主体出错");
                    }
                    need.setECompanyId(company.getCOMPANY_ID());
                    need.setCompany(company);

                    need.setUser(UserUtil.getSubjectUser());
                    need.setEApplicant(need.getUser().getId());

                    need.setState("未提交");

                    needDao.addNeed(need);
                }

                // 类型
                cell = sheet.getRow(i).getCell(13);
                String goodsType = "";
                if(cell != null){
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    goodsType = sheet.getRow(i).getCell(13).getStringCellValue();
                }else {
                    goodsType = "其他";
                }
                goods.setGoodsType(goodsType);
                // 名称
                cell = sheet.getRow(i).getCell(14);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                goods.setGoodsName(sheet.getRow(i).getCell(14).getStringCellValue());
                // 图号
                cell = sheet.getRow(i).getCell(15);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                goods.setGoodsCode(sheet.getRow(i).getCell(15).getStringCellValue());
                // 单位
                cell = sheet.getRow(i).getCell(16);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                goods.setGoodsUnit(sheet.getRow(i).getCell(16).getStringCellValue());
                Goods goodsByExcel = goodsDao.findGoodsByExcel(goods);
                // 数量
                cell = sheet.getRow(i).getCell(17);
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                storage.setMount((int)sheet.getRow(i).getCell(17).getNumericCellValue());
                //  备用
                cell = sheet.getRow(i).getCell(18);
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                storage.setMountBack((int)sheet.getRow(i).getCell(18).getNumericCellValue());
                // 实购
                cell = sheet.getRow(i).getCell(19);
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                storage.setMountIn((int)sheet.getRow(i).getCell(19).getNumericCellValue());
                // 变更
                cell = sheet.getRow(i).getCell(20);
                String ETemp = "";
                if(cell != null){
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    ETemp = sheet.getRow(i).getCell(20).getStringCellValue();
                }else {
                    ETemp = "";
                }
                storage.setETemp(ETemp);
                // 计划备注
                cell = sheet.getRow(i).getCell(21);
                String EPlan = "";
                if(cell != null){
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    EPlan = sheet.getRow(i).getCell(21).getStringCellValue();
                }else {
                    EPlan = "";
                }
                storage.setEPlan(EPlan);
                // 发货标识
                cell = sheet.getRow(i).getCell(22);
                String EIsRun = "";
                if(cell != null){
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    EIsRun = sheet.getRow(i).getCell(22).getStringCellValue();
                }else {
                    EIsRun = "√";
                }
                storage.setEIsRun(EIsRun);
                storage.setStorageId("S" + UuidUtil.uuidUtil());
                if(goodsByExcel == null){
                    goods.setGoodsId("WH-NEW-" + UuidUtil.uuidUtil() + "-00" + i);
                    int goodsResult = goodsDao.addGoods(goods);
                    if(goodsResult == 1){
                        goodsByExcel = goods;
                    }else {
                        throw new IOException();
                    }
                }
                int storageResult=storageService.addStorage(storage);
                if(storageResult > 0){
                    needGoods.setNeed(need);
                    needGoods.setGoods(goodsByExcel);
                    needGoods.setStorage(storage);
                    int needGoodsResult = needGoodsService.addNeedGoods(needGoods);
                }else {
                    throw new IOException("系统异常");
                }
            }
        } finally {
            if (null != wb) {
                try {
                    wb.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 判断Need是否已经存在
     *
     * @param need
     * @return
     */
    public List<Need> findNeed(Need need) {
        return needDao.findNeed(need);
    }
}
