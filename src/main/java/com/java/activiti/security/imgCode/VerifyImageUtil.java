package com.java.activiti.security.imgCode;

import com.java.activiti.util.StringUtil;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * ������֤������
 *
 * @author: herb
 * @Description:
 * @Date: Created in 10:57 2018/6/25
 * @Modified By:
 */
public class VerifyImageUtil {

    private static int ORI_WIDTH = 298;  //Դ�ļ����
    private static int ORI_HEIGHT = 108;  //Դ�ļ��߶�
    private static int X;  //��ͼ����x
    private static int Y;  //��ͼ����y
    private static int WIDTH;  //ģ��ͼ���
    private static int HEIGHT;  //ģ��ͼ�߶�
    private static float xPercent;  //Xλ���ƶ��ٷֱ�
    private static float yPercent;  //Yλ���ƶ��ٷֱ�

    public static int getX() {
        return X;
    }

    public static int getY() {
        return Y;
    }

    public static float getxPercent() {
        return xPercent;
    }

    public static float getyPercent() {
        return yPercent;
    }
    /**
     * ����ģ����ͼ
     *
     * @param templateFile
     * @param targetFile
     * @param templateType
     * @param targetType
     * @return
     * @throws Exception
     */
    public static Map<String, Object> pictureTemplatesCut(byte[] templateFile, byte[] targetFile, String templateType, String targetType) throws Exception {
        Map<String, Object> pictureMap = new HashMap<>();
        // �ļ�����
        String templateFiletype = templateType;
        String targetFiletype = targetType;
        if (StringUtil.isEmpty(templateFiletype) || StringUtil.isEmpty(targetFiletype)) {
            throw new RuntimeException("file type is empty");
        }
        // Դ�ļ���
        //File Orifile = targetFile;
        //InputStream oriis = new FileInputStream(Orifile);

        // ģ��ͼ
        BufferedImage imageTemplate = ImageIO.read(new ByteArrayInputStream(templateFile));
        WIDTH = imageTemplate.getWidth();
        HEIGHT = imageTemplate.getHeight();
        // ģ��ͼ
        BufferedImage imageTarget = ImageIO.read(new ByteArrayInputStream(targetFile));
        ORI_WIDTH = imageTarget.getWidth();
        ORI_HEIGHT = imageTarget.getHeight();

        generateCutoutCoordinates();
        // ����ͼ��
        BufferedImage newImage = new BufferedImage(WIDTH, HEIGHT, imageTemplate.getType());
        Graphics2D graphics = newImage.createGraphics();
        graphics.setBackground(Color.white);

        int bold = 5;
        // ��ȡ����Ȥ��Ŀ������
        BufferedImage targetImageNoDeal = getTargetArea(X, Y, WIDTH, HEIGHT, new ByteArrayInputStream(targetFile), targetFiletype);


        // ����ģ��ͼƬ��ͼ
        newImage = DealCutPictureByTemplate(targetImageNoDeal, imageTemplate, newImage);

        // ���á�����ݡ�������
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setStroke(new BasicStroke(bold, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
        graphics.drawImage(newImage, 0, 0, null);
        graphics.dispose();

        ByteArrayOutputStream os = new ByteArrayOutputStream();//�½�����
        ImageIO.write(newImage, templateFiletype, os);//����ImageIO���ṩ��write��������bi��pngͼƬ������ģʽд������
        byte[] newImages = os.toByteArray();
        pictureMap.put("newImage", newImages);
        // Դͼ��������
        BufferedImage oriImage = ImageIO.read(new ByteArrayInputStream(targetFile));
        byte[] oriCopyImages = DealOriPictureByTemplate(oriImage, imageTemplate, X, Y);
        pictureMap.put("oriCopyImage", oriCopyImages);
        pictureMap.put("X",X);
        pictureMap.put("Y",Y);
        return pictureMap;
    }

    /**
     * ����ģ����ͼ
     *
     * @param templateFile
     * @param targetFile
     * @param templateType
     * @param targetType
     * @return
     * @throws Exception
     */
    public static Map<String, Object> pictureTemplatesCut(File templateFile, File targetFile, String templateType, String targetType) throws Exception {
        Map<String, Object> pictureMap = new HashMap<>();
        // �ļ�����
        String templateFiletype = templateType;
        String targetFiletype = targetType;
        if (StringUtil.isEmpty(templateFiletype) || StringUtil.isEmpty(targetFiletype)) {
            throw new RuntimeException("file type is empty");
        }
        // Դ�ļ���
        File Orifile = targetFile;
        InputStream oriis = new FileInputStream(Orifile);

        // ģ��ͼ
        BufferedImage imageTemplate = ImageIO.read(templateFile);
        WIDTH = imageTemplate.getWidth();
        HEIGHT = imageTemplate.getHeight();
        // ģ��ͼ
        BufferedImage imageTarget = ImageIO.read(Orifile);
        ORI_WIDTH = imageTarget.getWidth();
        ORI_HEIGHT = imageTarget.getHeight();

        generateCutoutCoordinates();
        // ����ͼ��
        BufferedImage newImage = new BufferedImage(WIDTH, HEIGHT, imageTemplate.getType());
        Graphics2D graphics = newImage.createGraphics();
        graphics.setBackground(Color.white);

        int bold = 5;
        // ��ȡ����Ȥ��Ŀ������
        BufferedImage targetImageNoDeal = getTargetArea(X, Y, WIDTH, HEIGHT, oriis, targetFiletype);


        // ����ģ��ͼƬ��ͼ
        newImage = DealCutPictureByTemplate(targetImageNoDeal, imageTemplate, newImage);

        // ���á�����ݡ�������
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setStroke(new BasicStroke(bold, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
        graphics.drawImage(newImage, 0, 0, null);
        graphics.dispose();

        ByteArrayOutputStream os = new ByteArrayOutputStream();//�½�����
        ImageIO.write(newImage, templateFiletype, os);//����ImageIO���ṩ��write��������bi��pngͼƬ������ģʽд������
        byte[] newImages = os.toByteArray();
        pictureMap.put("newImage", newImages);
        // Դͼ��������
        BufferedImage oriImage = ImageIO.read(Orifile);
        byte[] oriCopyImages = DealOriPictureByTemplate(oriImage, imageTemplate, X, Y);
        pictureMap.put("oriCopyImage", oriCopyImages);
        pictureMap.put("X",X);
        pictureMap.put("Y",Y);
        return pictureMap;
    }

    /**
     * ��ͼ��ԭͼ����
     *
     * @param oriImage
     * @param templateImage
     * @param x
     * @param y
     * @return
     * @throws Exception
     */
    private static byte[] DealOriPictureByTemplate(BufferedImage oriImage, BufferedImage templateImage, int x,
                                                   int y) throws Exception {
        // Դ�ļ�����ͼ����� ֧��alphaͨ����rgbͼ��
        BufferedImage ori_copy_image = new BufferedImage(oriImage.getWidth(), oriImage.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        // Դ�ļ�ͼ�����
        int[][] oriImageData = getData(oriImage);
        // ģ��ͼ�����
        int[][] templateImageData = getData(templateImage);

        //copy Դͼ����͸������
        for (int i = 0; i < oriImageData.length; i++) {
            for (int j = 0; j < oriImageData[0].length; j++) {
                int rgb = oriImage.getRGB(i, j);
                int r = (0xff & rgb);
                int g = (0xff & (rgb >> 8));
                int b = (0xff & (rgb >> 16));
                //��͸������
                rgb = r + (g << 8) + (b << 16) + (255 << 24);
                ori_copy_image.setRGB(i, j, rgb);
            }
        }

        for (int i = 0; i < templateImageData.length; i++) {
            for (int j = 0; j < templateImageData[0].length - 5; j++) {
                int rgb = templateImage.getRGB(i, j);
                //��Դ�ļ�����ͼ��(x+i,y+j)��������͸������
//                if (rgb != 16777215 && rgb <= 0) {
                if (rgb != -1) {
                    int rgb_ori = ori_copy_image.getRGB(x + i, y + j);
                    int r = (0xff & rgb_ori);
                    int g = (0xff & (rgb_ori >> 8));
                    int b = (0xff & (rgb_ori >> 16));
                    rgb_ori = r + (g << 8) + (b << 16) + (60 << 24);
                    ori_copy_image.setRGB(x + i, y + j, rgb_ori);
                } else {
                    //do nothing
                }
            }
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();//�½�����
        ImageIO.write(ori_copy_image, "png", os);//����ImageIO���ṩ��write��������bi��pngͼƬ������ģʽд������
        byte b[] = os.toByteArray();//�����л�ȡ�������顣
        return b;
    }


    /**
     * ����ģ��ͼƬ��ͼ
     *
     * @param oriImage
     * @param templateImage
     * @return
     */

    private static BufferedImage DealCutPictureByTemplate(BufferedImage oriImage, BufferedImage templateImage,
                                                          BufferedImage targetImage) throws Exception {
        // Դ�ļ�ͼ�����
        int[][] oriImageData = getData(oriImage);
        // ģ��ͼ�����
        int[][] templateImageData = getData(templateImage);//��ͼģ��
        // ģ��ͼ����
        try {
            for (int i = 0; i < templateImageData.length; i++) {
                // ģ��ͼƬ�߶�
                for (int j = 0; j < templateImageData[0].length; j++) {
                    // ���ģ��ͼ��ǰ���ص㲻�ǰ�ɫ copyԴ�ļ���Ϣ��Ŀ��ͼƬ��
                    int rgb = templateImageData[i][j];
                    if (rgb != -1) {
//                    if (rgb != 16777215 && rgb <= 0) {
                        targetImage.setRGB(i, j, oriImageData[i][j]);
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {/*����Խ�����������ҳ��Ͳ��᷵��ͼ�����⡣*/
//            log("X:"+X+ "||Y:"+Y,e);
        } catch (Exception e) {
//            log.error("X:"+X+ "||Y:"+Y,e);
        }
        return targetImage;
    }


    /**
     * ��ȡĿ������
     *
     * @param x            �����ͼ����x��λ��
     * @param y            �����ͼ����y��λ��
     * @param targetWidth  ��ͼ��Ŀ����
     * @param targetHeight ��ͼ��Ŀ��߶�
     * @param ois          Դ�ļ�������
     * @return
     * @throws Exception
     */
    private static BufferedImage getTargetArea(int x, int y, int targetWidth, int targetHeight, InputStream ois,
                                               String filetype) throws Exception {
        Iterator<ImageReader> imageReaderList = ImageIO.getImageReadersByFormatName(filetype);
        ImageReader imageReader = imageReaderList.next();
        // ��ȡͼƬ��
        ImageInputStream iis = ImageIO.createImageInputStream(ois);
        // ����Դ�е�ͼ��ֻ��˳���ȡ
        imageReader.setInput(iis, true);

        ImageReadParam param = imageReader.getDefaultReadParam();
        Rectangle rec = new Rectangle(x, y, targetWidth, targetHeight);
        param.setSourceRegion(rec);
        BufferedImage targetImage = imageReader.read(0, param);
        return targetImage;
    }

    /**
     * ����ͼ�����
     *
     * @param
     * @return
     * @throws Exception
     */
    private static int[][] getData(BufferedImage bimg) throws Exception {
        int[][] data = new int[bimg.getWidth()][bimg.getHeight()];
        for (int i = 0; i < bimg.getWidth(); i++) {
            for (int j = 0; j < bimg.getHeight(); j++) {
                data[i][j] = bimg.getRGB(i, j);
            }
        }
        return data;
    }

    /**
     * ������ɿ�ͼ����
     */
    private static void generateCutoutCoordinates() {
        Random random = new Random();
        int widthDifference = ORI_WIDTH - WIDTH;
        int heightDifference = ORI_HEIGHT - HEIGHT;

        if (widthDifference <= 0) {
            X = 5;

        } else {
            X = random.nextInt(ORI_WIDTH - WIDTH);
            if (X < WIDTH) {/*@herb �����ͼ���λ������*/
                X = WIDTH;
            }
        }

        if (heightDifference <= 0) {
            Y = 5;
        } else {
            Y = random.nextInt(ORI_HEIGHT - HEIGHT);
        }
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);

        xPercent = Float.parseFloat(numberFormat.format((float) X / (float) ORI_WIDTH));
        yPercent = Float.parseFloat(numberFormat.format((float) Y / (float) ORI_HEIGHT));
    }
}
