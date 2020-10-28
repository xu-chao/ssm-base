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
     * ������Ԫ��
     * @param style    ��ʽ
     * @param height   �и�
     * @param value    ����ʾ������
     * @param row1     ��ʼ��
     * @param row2     ������
     * @param col1     ��ʼ��
     * @param col2     ������
     */
    private void createRow(HSSFCellStyle style, int height, String value, int row1, int row2, int col1, int col2){

        sheet.addMergedRegion(new CellRangeAddress(row1, row2, col1, col2));  //���ôӵ�row1�кϲ�����row2�У���col1�кϲ���col2��
        HSSFRow rows = sheet.createRow(row1);        //���õڼ���
        rows.setHeight((short) height);              //�����и�
        HSSFCell cell = rows.createCell(col1);       //�������ݿ�ʼ����
        cell.setCellStyle(style);                    //������ʽ
        cell.setCellValue(value);                    //���ø��е�ֵ
    }

    /**
     * ������ʽ
     * @param fontSize   �����С
     * @param align  ˮƽλ��  ���Ҿ���2 ����3 Ĭ�Ͼ��� ��ֱ��Ϊ����
     * @param bold   �Ƿ�Ӵ�
     * @return
     */
    private HSSFCellStyle getStyle(int fontSize,int align,boolean bold,boolean border){
        HSSFFont font = workbook.createFont();
        font.setFontName("����");
        font.setFontHeightInPoints((short) fontSize);// �����С
        if (bold){
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        }
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);                         //��������
        style.setAlignment((short) align);          // ���Ҿ���2 ����3 Ĭ�Ͼ���
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// ���¾���1
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
     * �������ݼ�����Excel��������Excel�ļ���
     * @param data ���ݼ�
     * @param sheetName Excel��sheet��Ԫ����
     * @param headNames �б�ͷ��������
     * @param colKeys ��key,���ݼ����ݸ�key���а�˳��ȡֵ
     * @return
     * @throws IOException
     */
    public InputStream getExcelFile(List<Map> data, String sheetName, String[] headNames,
                                    String[] colKeys, int colWidths[], HttpServletRequest request) throws IOException {
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet(sheetName);
        // ������ͷ startRow������忪ʼ����
        int startRow = createHeadCell( headNames, colWidths,request);

        // ������������
        HSSFCellStyle cellStyle = getStyle(14,2,false,true); // �����µ�cell��ʽ
        setCellData(data, cellStyle, startRow, colKeys);

        //������β
        createTailCell(data.size()+4,headNames.length);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        byte[] ba = baos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        return bais;
    }

    /**
     * ������ͷ
     *
     * @param headNames
     * @param colWidths
     */
    private int createHeadCell( String[] headNames, int colWidths[],HttpServletRequest request) throws IOException {
        // ��ͷ����
        HSSFCellStyle titleStyle = getStyle(22,2,true,false);//��ʽ
        createRow(titleStyle,0x549,"���ڻ�ǿ�����������޹�˾",0,0,0,headNames.length-1);
        //      ���ر�־
        String path = request.getServletContext().getRealPath("/");
        FileInputStream stream = new FileInputStream(path + "/static/images/fangte.png");
        byte[] bytes = new byte[(int) stream.getChannel().size()];
        stream.read(bytes);//��ȡͼƬ������������
        int pictureIdx = workbook.addPicture(bytes, HSSFWorkbook.PICTURE_TYPE_JPEG);
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) 0, 0, (short) 1, 1);
        HSSFPicture pict = patriarch.createPicture(anchor, pictureIdx);
//        pict.resize(); //��ͼƬԭʼ��С����ʾ
        //�ڶ���
        HSSFCellStyle unitStyle = getStyle(12,1,true,false);
        createRow(unitStyle,0x190,"��λ: ���ڻ�ǿ�����������޹�˾",1,1,0,headNames.length-1);

        //��������߲���
//        year = Integer.parseInt(bDate.substring(0,4));
//        String month = bDate.substring(4,6);
//        int m = Integer.parseInt(month)-1;
//        Calendar   cal   =   Calendar.getInstance();
//        cal.set(Calendar.YEAR,year);
//        cal.set(Calendar.MONTH,m);//��0��ʼ 0����һ�� 11����12��
//        int   maxDate   =   cal.getActualMaximum(Calendar.DATE);

        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 1));
        HSSFRow row = sheet.createRow(2);
        row.setHeight((short) 0x190);
        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(getStyle(12,1,true,false));
//        cell.setCellValue("ʱ��:"+year+"��"+month+"��"+"01����"+year+"��"+month+"��"+maxDate+"��");
        cell.setCellValue("����: -------- "+"����: -------- ");

        //�������ұ߲���
        Date date = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy��MM��dd��");
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 3, headNames.length-1));
        HSSFCell cell2 = row.createCell(3);
        cell2.setCellStyle(getStyle(12,3,true,false));
        cell2.setCellValue("�Ʊ�ʱ��: "+sdf.format(date));

        //�����б�ͷ
        boolean b = (headNames != null && headNames.length > 0);
        if (b) {
            HSSFRow row2 = sheet.createRow(3);
            row2.setHeight((short) 0x289);
            HSSFCell fcell = null;

            HSSFCellStyle cellStyle = getStyle(15,2,true,true); // �����µ�cell��ʽ

            for (int i = 0; i < headNames.length; i++) {
                fcell = row2.createCell(i);

                fcell.setCellStyle(cellStyle);
                fcell.setCellValue(headNames[i]);
                if (colWidths != null && i < colWidths.length) {
                    sheet.setColumnWidth(i, 32 * colWidths[i]);
                }
            }
        }
        return b ? 4 : 3;  //����һ�п�ʼ��Ⱦ����
    }

    /**
     * ������������
     * @param data           ��������
     * @param cellStyle      ��ʽ
     * @param startRow       ��ʼ��
     * @param colKeys        ֵ��Ӧmap��key
     */
    private void setCellData(List<Map> data, HSSFCellStyle cellStyle, int startRow,
                             String[] colKeys) {
        // ��������
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
                        colValue = colValue+"XX�Ƽ����޹�˾";
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
     * ������β
     * @param size
     * @param length
     */
    private void createTailCell(int size, int length) {
        HSSFCellStyle remarkStyle1 = getStyle(11,1,false,false);
        createRow(remarkStyle1,0x190,"���˶ԣ�ȷ������������ʵ����",size,size,0,length-2);

        HSSFCellStyle remarkStyle2 = getStyle(10,1,false,false);
        createRow(remarkStyle2,0x160,"(��ϵ�ˣ�XXX����ϵ�绰��13xxxxxxxx������:123456789@qq.com)",size+1,size+1,0,length-2);

        HSSFRow row3 = sheet.createRow(size+2);
        row3.setHeight((short) 0x379);
        sheet.addMergedRegion(new CellRangeAddress(size+3, size+3, 0, 1));
        HSSFRow row4 = sheet.createRow(size+3);
        row4.setHeight((short) 0x190);
        HSSFCell cell4 = row4.createCell(0);
        cell4.setCellStyle(getStyle(11,1,false,false));
        cell4.setCellValue("��λ�˶��ˣ�");

        sheet.addMergedRegion(new CellRangeAddress(size+3, size+3, 2, 4));
        HSSFCell cell15 = row4.createCell(2);
        cell15.setCellStyle(getStyle(11,1,false,false));
        cell15.setCellValue("��λ�Ʊ��ˣ�");

        HSSFCellStyle dateStyle = getStyle(10,3,false,false);
        createRow(dateStyle,0x150,"��˾����                     ",size+8,size+8,0,length-1);

        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        createRow(dateStyle,0x150,year+"��  ��   ��",size+9,size+9,0,length-1);
    }

}
