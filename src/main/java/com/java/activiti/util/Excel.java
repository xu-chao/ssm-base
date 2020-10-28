package com.java.activiti.util;

import com.java.activiti.dao.question.QuestionDao;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Excel{

    @Resource
    public QuestionDao questionDao;

    private HSSFWorkbook workbook;
    private HSSFSheet sheet;

    /**
     * 创建行元素
     * @param style    样式
     * @param height   行高
     * @param value    行显示的内容
     * @param row1     起始行
     * @param row2     结束行
     * @param col1     起始列
     * @param col2     结束列
     */
    private void createRow(HSSFCellStyle style, int height, String value, int row1, int row2, int col1, int col2){

        sheet.addMergedRegion(new CellRangeAddress(row1, row2, col1, col2));  //设置从第row1行合并到第row2行，第col1列合并到col2列
        HSSFRow rows = sheet.createRow(row1);        //设置第几行
        rows.setHeight((short) height);              //设置行高
        HSSFCell cell = rows.createCell(col1);       //设置内容开始的列
        cell.setCellStyle(style);                    //设置样式
        cell.setCellValue(value);                    //设置该行的值
    }

    /**
     * 创建样式
     * @param fontSize   字体大小
     * @param align  水平位置  左右居中2 居右3 默认居左 垂直均为居中
     * @param bold   是否加粗
     * @return
     */
    private HSSFCellStyle getStyle(int fontSize,int align,boolean bold,boolean border){
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) fontSize);// 字体大小
        if (bold){
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        }
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);                         //设置字体
        style.setAlignment((short) align);          // 左右居中2 居右3 默认居左
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中1
        if (border){
            style.setBorderRight((short) 2);
            style.setBorderLeft((short) 2);
            style.setBorderBottom((short) 2);
            style.setBorderTop((short) 2);
            style.setLocked(true);
        }
        return style;
    }

    /**
     * 根据数据集生成Excel，并返回Excel文件流
     * @param data 数据集
     * @param sheetName Excel中sheet单元名称
     * @param headNames 列表头名称数组
     * @param colKeys 列key,数据集根据该key进行按顺序取值
     * @return
     * @throws IOException
     */
    public InputStream getExcelFile(List<Map> data, String sheetName, String[] headNames,
                                    String[] colKeys, int colWidths[], HttpServletRequest request) throws IOException {
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet(sheetName);
        // 创建表头 startRow代表表体开始的行
        int startRow = createHeadCell( headNames, colWidths,request);

        // 创建表体数据
        HSSFCellStyle cellStyle = getStyle(14,2,false,true); // 建立新的cell样式
        setCellData(data, cellStyle, startRow, colKeys);

        //创建表尾
        createTailCell(data.size()+4,headNames.length);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        byte[] ba = baos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        return bais;
    }

    /**
     * 创建表头
     *
     * @param headNames
     * @param colWidths
     */
    private int createHeadCell( String[] headNames, int colWidths[],HttpServletRequest request) throws IOException {
        // 表头标题
        HSSFCellStyle titleStyle = getStyle(22,2,true,false);//样式
        createRow(titleStyle,0x549,"深圳华强方特智能有限公司",0,0,0,headNames.length-1);
        //      方特标志
        String path = request.getServletContext().getRealPath("/");
        FileInputStream stream = new FileInputStream(path + "/static/images/fangte.png");
        byte[] bytes = new byte[(int) stream.getChannel().size()];
        stream.read(bytes);//读取图片到二进制数组
        int pictureIdx = workbook.addPicture(bytes, HSSFWorkbook.PICTURE_TYPE_JPEG);
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) 0, 0, (short) 1, 1);
        HSSFPicture pict = patriarch.createPicture(anchor, pictureIdx);
//        pict.resize(); //用图片原始大小来显示
        //第二行
        HSSFCellStyle unitStyle = getStyle(12,1,true,false);
        createRow(unitStyle,0x190,"单位: 深圳华强方特智能有限公司",1,1,0,headNames.length-1);

        //第三行左边部分
//        year = Integer.parseInt(bDate.substring(0,4));
//        String month = bDate.substring(4,6);
//        int m = Integer.parseInt(month)-1;
//        Calendar   cal   =   Calendar.getInstance();
//        cal.set(Calendar.YEAR,year);
//        cal.set(Calendar.MONTH,m);//从0开始 0代表一月 11代表12月
//        int   maxDate   =   cal.getActualMaximum(Calendar.DATE);

        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 1));
        HSSFRow row = sheet.createRow(2);
        row.setHeight((short) 0x190);
        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(getStyle(12,1,true,false));
//        cell.setCellValue("时间:"+year+"年"+month+"月"+"01日至"+year+"年"+month+"月"+maxDate+"日");
        cell.setCellValue("中心: -------- "+"部门: -------- ");

        //第三行右边部分
        Date date = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日");
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 3, headNames.length-1));
        HSSFCell cell2 = row.createCell(3);
        cell2.setCellStyle(getStyle(12,3,true,false));
        cell2.setCellValue("制表时间: "+sdf.format(date));

        //第四行表头
        boolean b = (headNames != null && headNames.length > 0);
        if (b) {
            HSSFRow row2 = sheet.createRow(3);
            row2.setHeight((short) 0x289);
            HSSFCell fcell = null;

            HSSFCellStyle cellStyle = getStyle(15,2,true,true); // 建立新的cell样式

            for (int i = 0; i < headNames.length; i++) {
                fcell = row2.createCell(i);

                fcell.setCellStyle(cellStyle);
                fcell.setCellValue(headNames[i]);
                if (colWidths != null && i < colWidths.length) {
                    sheet.setColumnWidth(i, 32 * colWidths[i]);
                }
            }
        }
        return b ? 4 : 3;  //从哪一行开始渲染表体
    }

    /**
     * 创建表体数据
     * @param data           表体数据
     * @param cellStyle      样式
     * @param startRow       开始行
     * @param colKeys        值对应map的key
     */
    private void setCellData(List<Map> data, HSSFCellStyle cellStyle, int startRow,
                             String[] colKeys) {
        // 创建数据
        HSSFRow row = null;
        HSSFCell cell = null;
        int i = startRow;

        if (data != null && data.size() > 0) {
            DecimalFormat df = new DecimalFormat("#0.00");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Map<String, Object> rowData : data) {
                row = sheet.createRow(i);
                row.setHeight((short) 0x279);
                int j = 0;
                for (String key : colKeys) {
                    Object colValue = rowData.get(key);
                    if (key.equalsIgnoreCase("CITYNAME")){
                        colValue = colValue+"XX科技有限公司";
                    }else if (key.equalsIgnoreCase("ORDERSUM")||key.equalsIgnoreCase("TRANSFEE")||key.equalsIgnoreCase("ORDREALSUM")){
                        colValue = df.format(colValue);
                    }else if(key.equalsIgnoreCase("TIME")){
                        colValue = formatter.format(colValue);
                    }
                    cell = row.createCell(j);
                    cell.setCellStyle(cellStyle);
                    if (colValue != null) {
                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(colValue.toString());
                    }
                    j++;
                }
                i++;
            }
        }
    }

    /**
     * 创建表尾
     * @param size
     * @param length
     */
    private void createTailCell(int size, int length) {
        HSSFCellStyle remarkStyle1 = getStyle(11,1,false,false);
        createRow(remarkStyle1,0x190,"经核对，确认以上数据真实无误。",size,size,0,length-2);

        HSSFCellStyle remarkStyle2 = getStyle(10,1,false,false);
        createRow(remarkStyle2,0x160,"(联系人：XXX；联系电话：13xxxxxxxx；邮箱:123456789@qq.com)",size+1,size+1,0,length-2);

        HSSFRow row3 = sheet.createRow(size+2);
        row3.setHeight((short) 0x379);
        sheet.addMergedRegion(new CellRangeAddress(size+3, size+3, 0, 1));
        HSSFRow row4 = sheet.createRow(size+3);
        row4.setHeight((short) 0x190);
        HSSFCell cell4 = row4.createCell(0);
        cell4.setCellStyle(getStyle(11,1,false,false));
        cell4.setCellValue("单位核对人：");

        sheet.addMergedRegion(new CellRangeAddress(size+3, size+3, 2, 4));
        HSSFCell cell15 = row4.createCell(2);
        cell15.setCellStyle(getStyle(11,1,false,false));
        cell15.setCellValue("单位制表人：");

        HSSFCellStyle dateStyle = getStyle(10,3,false,false);
        createRow(dateStyle,0x150,"公司公章                     ",size+8,size+8,0,length-1);

        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        createRow(dateStyle,0x150,year+"年  月   日",size+9,size+9,0,length-1);
    }

}
