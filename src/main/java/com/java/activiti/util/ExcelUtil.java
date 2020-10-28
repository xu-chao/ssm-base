package com.java.activiti.util;

import org.apache.commons.configuration2.HierarchicalConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.tree.ImmutableNode;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author Liu
 * @since 2019/7/11.
 */
public class ExcelUtil {

    // Ĭ�������ļ���
    private static final String DEFAULT_FILE_NAME = "ExcelUtilConfig.xml";

    private XMLConfiguration xmlConfig;

    // ʵ������Excel��ӳ���ϵ
    private Map<String, MappingInfo> excelMappingInfo;

    public ExcelUtil() {
        init(DEFAULT_FILE_NAME);
    }

    public ExcelUtil(String fileLocation) {
        init(fileLocation);
    }

    /**
     * �� ExcelUtil ���г�ʼ�� ��ɨ�������ļ����������õĲ���
     *
     * @throws ConfigurationException
     */
    private void init(String fileLocation) {
        // ��������� excelMappingInfo ӳ��
        excelMappingInfo = new HashMap<>();

        Configurations configs = new Configurations();
        try {
            xmlConfig = configs.xml(fileLocation);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

        if (xmlConfig == null) {
            return;
        }

        /*
         * ɨ�� XMl �����ò���
         */
        // ɨ�� entity �ڵ�
        List<Object> entities = xmlConfig.getList("entity[@class]");
        if (entities == null) {
            return;
        }
        int entityNum = entities.size();
        for (int i = 0; i < entityNum; i++) {
            MappingInfo mappingInfo = new MappingInfo();

            // ���ȫ�޶�����
            String className = xmlConfig.getString("entity(" + i + ")[@class]");
            mappingInfo.setClassName(className);

            // ɨ�� property �ڵ�
            List<HierarchicalConfiguration<ImmutableNode>> properties = xmlConfig
                    .configurationsAt("entity(" + i + ").property");
            for (HierarchicalConfiguration<ImmutableNode> property : properties) {
                // ����
                String field = property.getString("field");
                String value = property.getString("value");

                mappingInfo.addFieldsMap(field, value);
                mappingInfo.addValuesMap(value, field);
            }

            // �� entity ��ӵ� excelMappingInfo
            excelMappingInfo.put(className, mappingInfo);
        }
    }

    /**
     * ��ȡ Excel �ļ��е����� Excel �ļ��е�ÿһ�д�����һ������ʵ���������и��е�����ֵ��ӦΪ�����еĸ�������ֵ
     * ��ȡʱ����Ҫָ����ȡĿ�����������Ի����ص�ӳ����Ϣ������Ҫ��ö������������ļ���ע��
     *
     * @param classType Ŀ����������
     * @param file      ������Դ�� Excel �ļ�
     * @return �������ɸ�Ŀ�����ʵ���� List
     */
    public List<Object> excelReader(Class<? extends Object> classType, MultipartFile file) {
        if (file == null)
            return null;

        // ��ʼ����Ŷ�ȡ����� List
        List<Object> content = new ArrayList<>();

        // ��ȡ������ӳ����Ϣ
        String className = classType.getName();
        MappingInfo mappingInfo = excelMappingInfo.get(className);
        if (mappingInfo == null)
            return null;

        // ��ȡ Excel �ļ�
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet dataSheet = workbook.getSheetAt(0);
            Row row;
            Cell cell;
            Iterator<Row> rowIterator = dataSheet.iterator();
            Iterator<Cell> cellIterator;

            // ��ȡ��һ�б�ͷ��Ϣ
            if (!rowIterator.hasNext())
                return null;
            List<String> methodList = new ArrayList<>();// setter �����б�
            List<Class<?>> fieldTypeList = new ArrayList<>();// Ŀ��������������б�
            row = rowIterator.next();
            cellIterator = row.iterator();
            String field;
            while (cellIterator.hasNext()) {
                cell = cellIterator.next();
                field = mappingInfo.valuesMap.get(cell.getStringCellValue());
                Class<?> fieldType = classType.getDeclaredField(field).getType();

                fieldTypeList.add(cell.getColumnIndex(), fieldType);
                methodList.add(cell.getColumnIndex(), getSetterMethodName(field));
            }

            // ���ж�ȡ������ݣ���������ֵ������
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                cellIterator = row.iterator();
                Object elem = classType.newInstance();

                // ��ȡ��Ԫ��
                while (cellIterator.hasNext()) {
                    cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();

                    Class<?> fieldType = fieldTypeList.get(columnIndex);
                    String methodName = methodList.get(columnIndex);

                    // ��ȡ��Ԫ���ֵ�������ö����ж�Ӧ������
                    Object value = getCellValue(fieldType, cell);
                    if (value == null) continue;
                    setField(elem, methodName, value);
                }
                // ������
                content.add(elem);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return content;
    }

    /**
     * �� List �е�Ԫ�ض���д�뵽 Excel �У�����ÿһ�������һ�У�ÿһ�е�����Ϊ���������
     *
     * @param classType Ŀ����������
     * @param elems     ������Դ�� List
     * @return
     */
    public File excelWriter(Class<? extends Object> classType, List<?> elems) {

        if (classType == null || elems == null)
            return null;

        // ��ȡ������ӳ����Ϣ
        String className = classType.getName();
        MappingInfo mappingInfo = excelMappingInfo.get(className);
        if (mappingInfo == null)
            return null;

        File excel = null;
        try {
            // ������ʱ�ļ�
            excel = File.createTempFile("excel", ".xslx");

            // ��ȡ�� class �ж���� field, ������Ӧ����Ϣ���浽 List ��
            List<String> fieldList = new ArrayList<>();
            List<String> methodList = new ArrayList<>();
            List<String> valuesList = new ArrayList<>();
            Set<String> fields = mappingInfo.fieldsMap.keySet();
            if (fields == null)
                return null;
            for (String field : fields) {
                fieldList.add(field);
                methodList.add(getGetterMethodName(field));
                valuesList.add(mappingInfo.fieldsMap.get(field));
            }

            // ���� workBook ����
            Workbook workbook = new XSSFWorkbook();
            // ���� sheet ����
            Sheet sheet = workbook.createSheet();

            int rowCount = 0;
            int cellCount;
            Row row;
            Cell cell;

            // д���һ�б�ͷ
            row = sheet.createRow(rowCount++);
            cellCount = 0;
            for (String value : valuesList) {
                cell = row.createCell(cellCount);
                cell.setCellValue(value);
                cellCount++;
            }

            // д����������
            for (Object elem : elems) {
                row = sheet.createRow(rowCount++);
                cellCount = 0;
                for (String methodName : methodList) {
                    Object value = getField(elem, methodName);
                    cell = row.createCell(cellCount++);
                    setCellValue(value, workbook, cell);
                }
            }

            // �� workBook д�뵽 tempFile ��
            FileOutputStream outputStream = new FileOutputStream(excel);
            workbook.write(outputStream);

            outputStream.flush();
            outputStream.close();
            workbook.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return excel;
    }

    /**
     * �÷������ڻ�ȡ��Ԫ�� cell �е�ֵ
     *
     * @param fieldType ָ����ȡ��ֵ������
     * @param cell      ��Ԫ��
     * @return ��Ԫ���е�ֵ
     */
    private Object getCellValue(Class<?> fieldType, Cell cell) {
        if (cell == null)
            return null;

        int cellType = cell.getCellType();
        Object value = null;
        if (cellType == Cell.CELL_TYPE_STRING) {
            if (fieldType.equals(String.class)) {
                value = cell.getStringCellValue();
            }
        } else if (cellType == Cell.CELL_TYPE_NUMERIC) {
            if (fieldType.equals(String.class)) {
                value = new DecimalFormat("0").format(cell.getNumericCellValue());
            } else if (fieldType.equals(Date.class)) {// && HSSFDateUtil.isCellDateFormatted(cell)
                value = new Date(cell.getDateCellValue().getTime());
            } else if (fieldType.equals(Long.class)) {
                Double v = cell.getNumericCellValue();
                value = v.longValue();
            } else if (fieldType.equals(Integer.class)) {
                Double v = cell.getNumericCellValue();
                value = v.intValue();
            } else {
                value = cell.getNumericCellValue();
            }
        } else if (cellType == Cell.CELL_TYPE_BOOLEAN) {
            if (fieldType.equals(Boolean.class)) {
                value = cell.getBooleanCellValue();
            }
        } else if (cellType == Cell.CELL_TYPE_FORMULA) {

        } else if (cellType == Cell.CELL_TYPE_ERROR) {

        } else if (cellType == Cell.CELL_TYPE_BLANK) {

        }
        return value;
    }

    /**
     * ���� Excel ��Ԫ���ֵ
     *
     * @param value ֵ
     * @param cell  ��Ԫ��
     */
    private void setCellValue(Object value, Workbook workbook, Cell cell) {
        if (cell == null || value == null)
            return;

        Class<?> valueClassType = value.getClass();
        if (valueClassType.equals(String.class)) {
            String v = (String) value;
            cell.setCellValue(v);
        } else if (valueClassType.equals(Integer.class)) {
            Integer v = (Integer) value;
            cell.setCellValue(v);
        } else if (valueClassType.equals(Long.class)) {
            Long v = (Long) value;
            cell.setCellValue(v);
        } else if (valueClassType.equals(Double.class)) {
            Double v = (Double) value;
            cell.setCellValue(v);
        } else if (valueClassType.equals(Boolean.class)) {
            Boolean v = (Boolean) value;
            cell.setCellValue(v);
        } else if (valueClassType.equals(Date.class)) {
            Date v = (Date) value;
            CellStyle cellStyle = workbook.createCellStyle();
            CreationHelper creationHelper = workbook.getCreationHelper();
            cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy/mm/dd"));
            cell.setCellValue(v);
            cell.setCellStyle(cellStyle);
        }
    }

    /**
     * �÷����������ö��������Ե�ֵ ͨ������Ŀ��������Զ�Ӧ�� setter ���������Ҫ��Ŀ������������ setter���󣬷���ֵ���ɹ�
     *
     * @param targetObject Ŀ�����
     * @param methodName   setter ������
     * @param field        ����������ֵ
     * @throws Exception
     */
    private void setField(Object targetObject, String methodName, Object field) throws Exception {
        // ��� setter ����ʵ��
        Class<?> targetObjectType = targetObject.getClass();
        Class<?> fieldType = field.getClass();
        Method setterMethod = targetObjectType.getMethod(methodName, fieldType);

        // ���÷���
        setterMethod.invoke(targetObject, field);
    }

    /**
     * ��ȡĿ�������ĳ�����Ե�ֵ��ͨ������Ŀ��������Զ�Ӧ�� getter ���������Ҫ��Ŀ������������ getter ���󣬷���ֵ���ɹ�
     *
     * @param targetObject Ŀ�����
     * @param methodName   getter ������
     * @return ���ظ����Ե�ֵ
     * @throws Exception
     */
    private Object getField(Object targetObject, String methodName) throws Exception {
        // ��� getter ����ʵ��
        Class<?> targetObjectType = targetObject.getClass();
        Method getterMethod = targetObjectType.getMethod(methodName);

        // ���÷���
        return getterMethod.invoke(targetObject);
    }

    /**
     * ���� setter �����ķ�����
     *
     * @param field �ֶ���
     * @return
     */
    private String getSetterMethodName(String field) {
        // ת��Ϊ����ĸ��д
        String name = field.replaceFirst(field.substring(0, 1), field.substring(0, 1).toUpperCase());
        // ƴ�� set ������
        return "set" + name;
    }

    /**
     * ���� getter �����ķ�����
     *
     * @param field �ֶ���
     * @return
     */
    private String getGetterMethodName(String field) {
        // ת��Ϊ����ĸ��д
        String name = field.replaceFirst(field.substring(0, 1), field.substring(0, 1).toUpperCase());
        // ƴ�� get ������
        return "get" + name;
    }

    /**
     * �ö�������˸���ע�ᵽ ExcelUtil �����ӳ����Ϣ ӳ����Ϣ������ע���������ͣ����������� Excel ������ӳ��
     *
     * @author Ken
     */
    private class MappingInfo {
        private String className;
        private Map<String, String> fieldsMap = new HashMap<>();
        private Map<String, String> valuesMap = new HashMap<>();

        @SuppressWarnings("unused")
        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public void addFieldsMap(String field, String value) {
            fieldsMap.put(field, value);
        }

        public void addValuesMap(String value, String field) {
            valuesMap.put(value, field);
        }
    }
}
