package com.java.activiti.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang.StringUtils {

    /**
     * һ�����ж϶���򵥸�����Ϊ�ա�
     * @param objects
     * @author zhou-baicheng
     * @return ֻҪ��һ��Ԫ��ΪBlank���򷵻�true
     */
    public static boolean isBlank(Object...objects){
        Boolean result = false ;
        for (Object object : objects) {
            if(null == object || "".equals(object.toString().trim())
                    || "null".equals(object.toString().trim())){
                result = true ;
                break ;
            }
        }
        return result ;
    }

    public static String getRandom(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // �����ĸ��������
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // �ַ���
            if ("char".equalsIgnoreCase(charOrNum)) {
                // ȡ�ô�д��ĸ����Сд��ĸ
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) { // ����
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val.toLowerCase();
    }
    /**
     * һ�����ж϶���򵥸�����Ϊ�ա�
     * @param objects
     * @author zhou-baicheng
     * @return ֻҪ��һ��Ԫ�ز�ΪBlank���򷵻�true
     */
    public static boolean isNotBlank(Object...objects){
        return !isBlank(objects);
    }
    public static boolean isBlank(String...objects){
        Object[] object = objects ;
        return isBlank(object);
    }
    public static boolean isNotBlank(String...objects){
        Object[] object = objects ;
        return !isBlank(object);
    }
    public static boolean isBlank(String str){
        Object object = str ;
        return isBlank(object);
    }
    public static boolean isNotBlank(String str){
        Object object = str ;
        return !isBlank(object);
    }
    /**
     * �ж�һ���ַ����������д��ڼ���
     * @param baseStr
     * @param strings
     * @return
     */
    public static int indexOf(String baseStr,String[] strings){

        if(null == baseStr || baseStr.length() == 0 || null == strings)
            return 0;

        int i = 0;
        for (String string : strings) {
            boolean result = baseStr.equals(string);
            i = result ? ++i : i;
        }
        return i ;
    }
    /**
     * �ж�һ���ַ����Ƿ�ΪJSONObject,�Ƿ���JSONObject,���Ƿ���null
     * @param args
     * @return
     */
    public static net.sf.json.JSONObject isJSONObject(String args) {
        net.sf.json.JSONObject result = null ;
        if(isBlank(args)){
            return result ;
        }
        try {
            return net.sf.json.JSONObject.fromObject(args.trim());
        } catch (Exception e) {
            return result ;
        }
    }
    /**
     * �ж�һ���ַ����Ƿ�ΪJSONArray,�Ƿ���JSONArray,���Ƿ���null
     * @param args
     * @return
     */
    public static net.sf.json.JSONArray isJSONArray(Object args) {
        JSONArray result = new JSONArray();
        if(isBlank(args)){
            return null ;
        }
        if(args instanceof  net.sf.json.JSONArray){

            net.sf.json.JSONArray arr = (net.sf.json.JSONArray)args;
            for (Object json : arr) {
                if(json != null && json instanceof net.sf.json.JSONObject){
                    result.add(json);
                    continue;
                }else{
                    result.add(JSONObject.fromObject(json));
                }
            }
            return result;
        }else{
            return null ;
        }

    }
    public static String trimToEmpty(Object str){
        return (isBlank(str) ? "" : str.toString().trim());
    }

    /**
     * �� Strig  ���� BASE64 ����
     * @param str [Ҫ������ַ���]
     * @param bf  [true|false,true:ȥ����β�����'=',false:��������]
     * @return
     */
    public static String getBASE64(String str,boolean...bf) {
        if (StringUtils.isBlank(str)) return null;
        String base64 = new sun.misc.BASE64Encoder().encode(str.getBytes()) ;
        //ȥ�� '='
        if(isBlank(bf) && bf[0]){
            base64 = base64.replaceAll("=", "");
        }
        return base64;
    }

    /** �� BASE64 ������ַ��� s ���н���**/
    public static String getStrByBASE64(String s) {
        if (isBlank(s)) return "";
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b);
        } catch (Exception e) {
            return "";
        }
    }
    /**
     * ��Mapת����get�����������,�� {"name"=20,"age"=30} ת������ name=20&age=30
     * @param map
     * @return
     */
    public static String mapToGet(Map<? extends Object,? extends Object> map){
        String result = "" ;
        if(map == null || map.size() ==0){
            return result ;
        }
        Set<? extends Object> keys = map.keySet();
        for (Object key : keys ) {
            result += ((String)key + "=" + (String)map.get(key) + "&");
        }

        return isBlank(result) ? result : result.substring(0,result.length() - 1);
    }
    /**
     * ��һ�������ַ���,ת����Map ��"?a=3&b=4" ת��ΪMap{a=3,b=4}
     * @param args
     * @return
     */
    public static Map<String, ? extends Object> getToMap(String args){
        if(isBlank(args)){
            return null ;
        }
        args = args.trim();
        //�����?��ͷ,��?ȥ��
        if(args.startsWith("?")){
            args = args.substring(1,args.length());
        }
        String[] argsArray = args.split("&");

        Map<String,Object> result = new HashMap<String,Object>();
        for (String ag : argsArray) {
            if(!isBlank(ag) && ag.indexOf("=")>0){

                String[] keyValue = ag.split("=");
                //���value����keyֵ����� "="��,�Ե�һ��"="��Ϊ�� ,��  name=0=3  ת����,{"name":"0=3"}, �������������,�����޸�,���н��.

                String key = keyValue[0];
                String value = "" ;
                for (int i = 1; i < keyValue.length; i++) {
                    value += keyValue[i]  + "=";
                }
                value = value.length() > 0 ? value.substring(0,value.length()-1) : value ;
                result.put(key,value);

            }
        }

        return result ;
    }

    /**
     * ת����Unicode
     * @param str
     * @return
     */
    public static String toUnicode(String str) {
        String as[] = new String[str.length()];
        String s1 = "";
        for (int i = 0; i < str.length(); i++) {
            int v = str.charAt(i);
            if(v >=19968 && v <= 171941){
                as[i] = Integer.toHexString(str.charAt(i) & 0xffff);
                s1 = s1 + "\\u" + as[i];
            }else{
                s1 = s1 + str.charAt(i);
            }
        }
        return s1;
    }
    /**
     * �ϲ�����
     * @param v
     * @return
     */
    public static String merge(Object...v){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < v.length; i++) {
            sb.append(v[i]);
        }
        return sb.toString() ;
    }
    /**
     * �ַ���תurlcode
     * @param value
     * @return
     */
    public static String strToUrlcode(String value){
        try {
            value = java.net.URLEncoder.encode(value,"utf-8");
            return value ;
        } catch (UnsupportedEncodingException e) {
            LoggerUtils.error(StringUtils.class, "�ַ���ת��ΪURLCodeʧ��,value:" + value,e);
            e.printStackTrace();
            return null;
        }
    }
    /**
     * urlcodeת�ַ���
     * @param value
     * @return
     */
    public static String urlcodeToStr(String value){
        try {
            value = java.net.URLDecoder.decode(value,"utf-8");
            return value ;
        } catch (UnsupportedEncodingException e) {
            LoggerUtils.error(StringUtils.class, "URLCodeת��Ϊ�ַ���ʧ��;value:" + value,e);
            e.printStackTrace();
            return null;
        }
    }
    /**
     * �ж��ַ����Ƿ��������
     * @param txt
     * @return
     */
    public static Boolean containsCN(String txt){
        if(isBlank(txt)){
            return false;
        }
        for (int i = 0; i < txt.length(); i++) {

            String bb = txt.substring(i, i + 1);

            boolean cc = java.util.regex.Pattern.matches("[\u4E00-\u9FA5]", bb);
            if(cc)
                return cc ;
        }
        return false;
    }
    /**
     * ȥ��HTML����
     * @param news
     * @return
     */
    public static String removeHtml(String news) {
        String s = news.replaceAll("amp;", "").replaceAll("<","<").replaceAll(">", ">");

        Pattern pattern = Pattern.compile("<(span)?\\sstyle.*?style>|(span)?\\sstyle=.*?>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(s);
        String str = matcher.replaceAll("");

        Pattern pattern2 = Pattern.compile("(<[^>]+>)",Pattern.DOTALL);
        Matcher matcher2 = pattern2.matcher(str);
        String strhttp = matcher2.replaceAll(" ");


        String regEx = "(((http|https|ftp)(\\s)*((\\:)|��))(\\s)*(//|//)(\\s)*)?"
                + "([\\sa-zA-Z0-9(\\.|��)(\\s)*\\-]+((\\:)|(:)[\\sa-zA-Z0-9(\\.|��)&%\\$\\-]+)*@(\\s)*)?"
                + "("
                + "(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])"
                + "(\\.|��)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)"
                + "(\\.|��)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)"
                + "(\\.|��)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])"
                + "|([\\sa-zA-Z0-9\\-]+(\\.|��)(\\s)*)*[\\sa-zA-Z0-9\\-]+(\\.|��)(\\s)*[\\sa-zA-Z]*"
                + ")"
                + "((\\s)*(\\:)|(��)(\\s)*[0-9]+)?"
                + "(/(\\s)*[^/][\\sa-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&%\\$\\=~_\\-@]*)*";
        Pattern p1 = Pattern.compile(regEx,Pattern.DOTALL);
        Matcher matchhttp = p1.matcher(strhttp);
        String strnew = matchhttp.replaceAll("").replaceAll("(if[\\s]*\\(|else|elseif[\\s]*\\().*?;", " ");


        Pattern patterncomma = Pattern.compile("(&[^;]+;)",Pattern.DOTALL);
        Matcher matchercomma = patterncomma.matcher(strnew);
        String strout = matchercomma.replaceAll(" ");
        String answer = strout.replaceAll("[\\pP��������]", " ")
                .replaceAll("\r", " ").replaceAll("\n", " ")
                .replaceAll("\\s", " ").replaceAll("��", "");


        return answer;
    }
    /**
     * ������Ŀ�����ȥ��
     * @param array
     * @return
     */
    public static List<String> array2Empty(String[] array){
        List<String> list = new ArrayList<String>();
        for (String string : array) {
            if(StringUtils.isNotBlank(string)){
                list.add(string);
            }
        }
        return list;
    }
    /**
     * ������ת����set
     * @param array
     * @return
     */
    public static Set<?> array2Set(Object[] array) {
        Set<Object> set = new TreeSet<Object>();
        for (Object id : array) {
            if(null != id){
                set.add(id);
            }
        }
        return set;
    }
    /**
     * serializable toString
     * @param serializable
     * @return
     */
    public static String toString(Serializable serializable) {
        if(null == serializable){
            return null;
        }
        try {
            return (String)serializable;
        } catch (Exception e) {
            return serializable.toString();
        }
    }
}
