
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
        //编号
        dataMap.put("parkName", "芜湖方特");
        dataMap.put("projectName", "芜湖方特");
        dataMap.put("deptName", "AVC");
        dataMap.put("problemName", "123");
        dataMap.put("problemtext", "AVC12321");
        dataMap.put("userName", "AVC");

        dataMap.put("imgStr", getImageStr());
        //日期
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dataMap.put("date", sdf.format(d));
        //Configuration 用于读取ftl文件
        Configuration configuration = new Configuration(new Version("2.3.0"));
        configuration.setDefaultEncoding("utf-8");

        /**
         * 以下是两种指定ftl文件所在目录路径的方式，注意这两种方式都是
         * 指定ftl文件所在目录的路径，而不是ftl文件的路径
         */
        //指定路径的第一种方式（根据某个类的相对路径指定）
//                configuration.setClassForTemplateLoading(this.getClass(), "");

        //指定路径的第二种方式，我的路径是C：/a.ftl
        configuration.setDirectoryForTemplateLoading(new File("static/word/problemWord1.ftl"));

        //输出文档路径及名称
        File outFile = new File("D:/报销信息导出.doc");

        //以utf-8的编码读取ftl文件
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
