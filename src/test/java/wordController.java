
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class wordController {
    public static String getImageStr() {
        String imgFile = "d:/xueli.jpg";
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }
    public static void main(String[] args) throws IOException, ParseException {
        Map<String,Object> dataMap = new HashMap<String, Object>();
        //���
        dataMap.put("parkName", "�ߺ�����");
        dataMap.put("projectName", "�ߺ�����");
        dataMap.put("deptName", "AVC");
        dataMap.put("problemName", "123");
        dataMap.put("problemtext", "AVC12321");
        dataMap.put("userName", "AVC");

        dataMap.put("imgStr", getImageStr());
        //����
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dataMap.put("date", sdf.format(d));
        //Configuration ���ڶ�ȡftl�ļ�
        Configuration configuration = new Configuration(new Version("2.3.0"));
        configuration.setDefaultEncoding("utf-8");

        /**
         * ����������ָ��ftl�ļ�����Ŀ¼·���ķ�ʽ��ע�������ַ�ʽ����
         * ָ��ftl�ļ�����Ŀ¼��·����������ftl�ļ���·��
         */
        //ָ��·���ĵ�һ�ַ�ʽ������ĳ��������·��ָ����
//                configuration.setClassForTemplateLoading(this.getClass(), "");

        //ָ��·���ĵڶ��ַ�ʽ���ҵ�·����C��/a.ftl
        configuration.setDirectoryForTemplateLoading(new File("static/word/problemWord1.ftl"));

        //����ĵ�·��������
        File outFile = new File("D:/������Ϣ����.doc");

        //��utf-8�ı����ȡftl�ļ�
        Template template = configuration.getTemplate("problemWord1.ftl", "utf-8");
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"), 10240);
        try {
            template.process(dataMap, out);
            out.close();
        } catch (TemplateException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
