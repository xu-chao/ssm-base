package com.java.activiti.service.impl;

import com.java.activiti.exception.MailServiceException;
import com.java.activiti.model.File;
import com.java.activiti.model.Images;
import com.java.activiti.model.gaizao.Huiyishi;
import com.java.activiti.model.question.QuestionInfo;
import com.java.activiti.model.Repair;
import com.java.activiti.service.FileService;
import com.java.activiti.service.MailService;
import com.sun.mail.util.MailSSLSocketFactory;
import org.simplejavamail.MailException;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

@Service("mailService")
public class MailServiceImpl implements MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Resource
    private FileService fileService;

    @Resource(name = "javaMailSender")
    private JavaMailSenderImpl mailSender;

    @Value("${mail.smtp.host}")
    private String host;

    @Value("${mail.transport.protocol}")
    private String protocol;

    @Value("${mail.smtp.username}")
    private String userName;

    @Value("${mail.smtp.port}")
    private String port;

    @Value("${mail.smtp.password}")
    private String password;

    @Value("${mail.smtp.from}")
    private String from;

    @Value("${mail.smtp.from.name}")
    private String fromName;

//    public void sendMail(String subject, String content, String to) throws Exception {
//        System.getProperties().setProperty("mail.mime.splitlongparameters","false");
//        MimeMessage mailMessage = this.mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true, "utf-8");
//        // ���÷�������Ϣ
//        helper.setFrom(this.from, this.fromName);
//        // ���÷�����
//        helper.setTo(to);
//        // ��������
//        helper.setSubject(subject);
//        // �����ʼ�����
//        helper.setText(content);
//        // �����ʼ�
//        try {
//            logger.info("�ʼ�������...");
//            this.mailSender.send(mailMessage);
//            logger.info("�����ʼ����");
//        }catch (Exception e){
//            logger.error(e.getMessage());
//        }
//    }

    public Boolean sendMail(String subject, Repair content, String emailName, String to, String basePath) throws MessagingException, GeneralSecurityException, UnsupportedEncodingException {

        Properties prop = new Properties();
        prop.setProperty("mail.host", host); //// ����QQ�ʼ�������
        prop.setProperty("mail.transport.protocol", protocol); // �ʼ�����Э��
        prop.setProperty("mail.smtp.auth", "true"); // ��Ҫ��֤�û�������

        // ����QQ���䣬��Ҫ����SSL���ܣ��������´��뼴��
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);

        //ʹ��JavaMail�����ʼ���5������

        //������������Ӧ�ó�������Ļ�����Ϣ�� Session ����

        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                //�������ʼ��û�������Ȩ��
                return new PasswordAuthentication(userName, password);
            }
        });


        //����Session��debugģʽ�������Ϳ��Բ鿴��������Email������״̬
        session.setDebug(true);

        //2��ͨ��session�õ�transport����
        Transport ts = session.getTransport();

        //3��ʹ��������û�������Ȩ�������ʼ�������
        ts.connect(host, userName, password);

        //4�������ʼ�

        //�����ʼ�����
        MimeMessage message = new MimeMessage(session);

        //ָ���ʼ��ķ�����
        message.setFrom(new InternetAddress(userName,emailName, "UTF-8"));

        //ָ���ʼ����ռ��ˣ����ڷ����˺��ռ�����һ���ģ��Ǿ����Լ����Լ���
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // ׼��ͼƬ����
        MimeBodyPart image1 = new MimeBodyPart();
        DataHandler dh1 = new DataHandler(new FileDataSource(basePath + "/static/images/arrow_white.png"));
        image1.setDataHandler(dh1);
        image1.setContentID("arrow_white");

        MimeBodyPart image2 = new MimeBodyPart();
        DataHandler dh2 = new DataHandler(new FileDataSource(basePath + "/static/images/features_icon1.png"));
        image2.setDataHandler(dh2);
        image2.setContentID("features_icon1");

        MimeBodyPart image3 = new MimeBodyPart();
        DataHandler dh3 = new DataHandler(new FileDataSource(basePath + "/static/images/features_icon2.png"));
        image3.setDataHandler(dh3);
        image3.setContentID("features_icon2");

        MimeBodyPart image4 = new MimeBodyPart();
        DataHandler dh4 = new DataHandler(new FileDataSource(basePath + "/static/images/small_img1.jpg"));
        image4.setDataHandler(dh4);
        image4.setContentID("small_img1");

        //�ʼ��ı���
        message.setSubject(subject);
        //�ʼ����ı�����
        //message.setContent("��ð���", "text/html;charset=UTF-8");
        // ׼����������
        MimeBodyPart text = new MimeBodyPart();
        text.setContent("" +
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> \n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                " <head>\n" +
                "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "  <title>�˹ܿ�ϵͳ���������</title>\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
                "  <style type=\"text/css\">\n" +
                "    \n" +
                "\thtml{\n" +
                "\t\twidth: 100%; \n" +
                "\t}\n" +
                "\n" +
                "\tbody{\n" +
                "\t\twidth: 100%;  \n" +
                "\t\tmargin:0; \n" +
                "\t\tpadding:0; \n" +
                "\t\t-webkit-font-smoothing: antialiased;\n" +
                "\t\tmso-margin-top-alt:0px; \n" +
                "\t\tmso-margin-bottom-alt:0px; \n" +
                "\t\tmso-padding-alt: 0px 0px 0px 0px;\n" +
                "\t\tbackground: #ffffff;\n" +
                "\t}\n" +
                "\n" +
                "\tp,h1,h2,h3,h4{\n" +
                "\t\tmargin-top:0;\n" +
                "\t\tmargin-bottom:0;\n" +
                "\t\tpadding-top:0;\n" +
                "\t\tpadding-bottom:0;\n" +
                "\t}\n" +
                "\n" +
                "\ttable{\n" +
                "\t\tfont-size: 14px;\n" +
                "\t\tborder: 0;\n" +
                "\t}\n" +
                "\n" +
                "\timg{\n" +
                "\t\tborder: none!important;\n" +
                "\t}\n" +
                "\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body style=\"margin: 0; padding: 0;\">\n" +
                "\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#dfe7f2\" style=\"background-size: cover; -webkit-background-size: cover; -moz-background-size: cover -o-background-size: cover; background-position: 0 0; background-repeat: no-repeat; text-align:center;\">\n" +
                "\t  <tr>\n" +
                "\t  \t<td>\n" +
                "\t  \t\t<table width=\"600\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t  \t\t\t<tr>\n" +
                "\t\t\t\t\t<td width=\"100%\" height=\"20\"></td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td style=\"color: #646262; font-family: 'Raleway', arial; font-size: 40px; text-align:center; font-weight:200;\" align=\"center\">\n" +
                "\t\t\t\t\t  <span style=\"font-weight: 500; letter-spacing:1px;\">�˹ܿ�ϵͳ���������</span>\n" +
                "\t\t\t\t\t</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td width=\"100%\" height=\"20\"></td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t\t\t<table width=\"250\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; -moz-border-radius: 100px; -webkit-border-radius: 100px; border-radius: 100px; background: #ec5482;\">\n" +
                "\t\t\t\t\t\t    <tr>\n" +
                "\t\t\t\t\t\t      <td width=\"100%\" height=\"15\"></td>\n" +
                "\t\t\t\t\t\t    </tr>\n" +
                "\t\t\t\t\t\t    <tr>\n" +
                "\t\t\t\t\t\t      <td>               \n" +
                "\t\t\t\t\t\t        <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; text-align:center;\">\n" +
                "\t\t\t\t\t\t          <tr>\n" +
                "\t\t\t\t\t\t            <td align=\"center\" style=\"text-align:center;\">\n" +
                "\t\t\t\t\t\t              <a href=\"http://www.fangte.xyz\" style=\"color: #fff; font-family: 'Raleway', arial; font-size: 18px; text-decoration:none; font-weight:500; letter-spacing:.5px;\">�������\n" +
                "\t\t\t\t\t\t              \t<img src=\"cid:arrow_white\" width=\"17\" height=\"7\" border=\"0\" alt=\"\" title=\"\" style=\"padding:0 0 0 5px; vertical-align:middle;\"/>\n" +
                "\t\t\t\t\t\t              </a>\n" +
                "\t\t\t\t\t\t            </td>\n" +
                "\t\t\t\t\t\t          </tr>\n" +
                "\t\t\t\t\t\t        </table>\n" +
                "\n" +
                "\t\t\t\t\t\t      </td>\n" +
                "\t\t\t\t\t\t    </tr>\n" +
                "\t\t\t\t\t\t    <tr>\n" +
                "\t\t\t\t\t\t      <td width=\"100%\" height=\"15\"></td>\n" +
                "\t\t\t\t\t\t    </tr>\n" +
                "\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td width=\"100%\" height=\"20\"></td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t  \t\t</table>\n" +
                "\t  \t</td>\n" +
                "\t  </tr>\n" +
                "\t</table> \n" +
                "\n" +
                "\t<table width=\"600\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#ffffff\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t  <tr>\n" +
                "\t    <td width=\"100%\" height=\"50\"></td>\n" +
                "\t  </tr>\n" +
                "\t  <tr>\n" +
                "\t    <td>\n" +
                "\t      <table width=\"600\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t        <tr>\n" +
                "\t          <td>\n" +
                "\t            <table width=\"280\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t              <tr>\n" +
                "\t                <td style=\"vertical-align:top;\">\n" +
                "\n" +
                "\t                  <table width=\"36\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t                    <tr>\n" +
                "\t                      <td width=\"100%\" height=\"7\"></td>\n" +
                "\t                    </tr>\n" +
                "\t                    <tr>\n" +
                "\t                      <td>\n" +
                "\t                        <img src=\"cid:features_icon1\" border=\"0\" alt=\"\" title=\"\" style=\"display: block;\"/>\n" +
                "\t                      </td>\n" +
                "\t                    </tr>\n" +
                "\t                  </table>\n" +
                "\t                </td>\n" +
                "\t                <td width=\"20\" style=\"width:20px;\"></td>\n" +
                "\t                <td>\n" +
                "\t                \t<table cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t\t                    <tr>\n" +
                "\t\t                      <td>\n" +
                "\t\t                        <p style=\"color: #8b8b8b; font-family: 'Raleway', arial; font-weight: 400; font-size: 14px; line-height:26px;\">���Ϸ������ڣ�" + formatter.format(content.getRepairDate()) + "<br />��������ڣ�" + formatter.format(content.getRecordDate()) + "<br />���Ϸ����ص㣺" + content.getRepairPlace() + "</p>\n" +
                "\t\t                      </td>\n" +
                "\t\t                    </tr>\n" +
                "\t\t                    <tr>\n" +
                "\t\t                      <td width=\"100%\" height=\"30\"></td>\n" +
                "\t\t                    </tr>\t\t                   \n" +
                "\t                \t</table>\n" +
                "\t                </td>\n" +
                "\t              </tr>\n" +
                "\t            </table>\n" +
                "\n" +
                "\t            <table width=\"280\" align=\"right\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t              <tr>\n" +
                "\t                <td style=\"vertical-align:top;\">\n" +
                "\t                  <table width=\"36\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t                    <tr>\n" +
                "\t                      <td width=\"100%\" height=\"7\"></td>\n" +
                "\t                    </tr>\n" +
                "\t                    <tr>\n" +
                "\t                      <td>\n" +
                "\t                        <img src=\"cid:features_icon2\" border=\"0\" alt=\"\" title=\"\" style=\"display: block;\"/>\n" +
                "\t                      </td>\n" +
                "\t                    </tr>\n" +
                "\t                  </table>\n" +
                "\n" +
                "\t                </td>\n" +
                "\t                <td width=\"20\" style=\"width:20px;\"></td>\n" +
                "\t                <td>\n" +
                "\n" +
                "\t                  <table cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t                    <tr>\n" +
                "\t                      <td>\n" +
                "\t                        <p style=\"color: #8b8b8b; font-family: 'Raleway', arial; font-weight: 400; font-size: 14px; line-height:26px;\">���ϳ̶ȣ�" + content.getRepairLevel() + "<br />�������ͣ�" + content.getRepairType() + "<br />������˹��ţ�" + content.getUser().getId() + "</p>\n" +
                "\t                      </td>\n" +
                "\t                    </tr>\n" +
                "\t                    <tr>\n" +
                "\t                      <td width=\"100%\" height=\"30\"></td>\n" +
                "\t                    </tr>\t                    \n" +
                "\t                  </table>\n" +
                "\t                </td>\n" +
                "\t              </tr>\n" +
                "\t            </table>\n" +
                "\t          </td>\n" +
                "\t        </tr>     \n" +
                "\t      </table>\n" +
                "\t    </td>\n" +
                "\t  </tr>\n" +
                "\t  <tr>\n" +
                "\t    <td width=\"100%\" height=\"50\"></td>\n" +
                "\t  </tr>\n" +
                "\t</table>\n" +
                "\n" +
                "\n" +
                "\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#fcfcfc\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t  <tr>\n" +
                "\t    <td>\n" +
                "\t      <table width=\"600\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#fcfcfc\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t        <tr>\n" +
                "\t          <td>\n" +
                "\t            <table cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t              <tr>\n" +
                "\t                <td width=\"187\" style=\"vertical-align:top;\">\n" +
                "\t                  <table cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t                    <tr>\n" +
                "\t                      <td width=\"100%\" height=\"6\"></td>\n" +
                "\t                    </tr>\n" +
                "\t                    <tr>\n" +
                "\t                      <td>\n" +
                "\t                        <a href=\"#\">\n" +
                "\t                          <img src=\"cid:small_img1.jpg\" border=\"0\" alt=\"\" title=\"\" width=\"187\" height=\"155\" style=\"display: block;\"/>\n" +
                "\t                        </a>\n" +
                "\t                      </td>\n" +
                "\t                    </tr>\n" +
                "\t                  </table>\n" +
                "\t                </td>\n" +
                "\t                <td width=\"40\" style=\"width:40px;\"></td>\n" +
                "\t                <td>\n" +
                "\t                  <table cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t                    <tr>\n" +
                "\t                      <td>\n" +
                "\t                        <a href=\"#\" style=\"color: #676767; font-family: 'Raleway', arial; font-size: 16px; font-weight: 600; text-transform:uppercase; letter-spacing:.5px; line-height:26px; text-decoration:none;\">\n" +
                "\t                          �������ݣ�\n" +
                "\t                        </a>\n" +
                "\t                      </td>\n" +
                "\t                    </tr>\n" +
                "\t                    <tr>\n" +
                "\t                      <td width=\"100%\" height=\"15\"></td>\n" +
                "\t                    </tr>\n" +
                "\t                    <tr>\n" +
                "\t                      <td style=\"color: #8b8b8b; font-family: 'Raleway', arial; font-weight: 400; font-size: 14px; letter-spacing:.5px; line-height:26px;\">\n" +
                "\t                        " + content.getRepairContext() + "\n" +
                "\t                      </td>\n" +
                "\t                    </tr>\n" +
                "\t                  </table>\n" +
                "\t                </td>\n" +
                "\t              </tr>                    \n" +
                "\t            </table>\n" +
                "\t          </td>\n" +
                "\t        </tr>\n" +
                "\t\t\t<tr>\n" +
                "\t           <td width=\"100%\" height=\"50\"></td>\n" +
                "\t        </tr>\n" +
                "\t      </table>\n" +
                "\t    </td>\n" +
                "\t  </tr>      \n" +
                "\t</table>\n" +
                "\n" +
                "\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#38404b\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t  <tr>\n" +
                "\t    <td width=\"100%\" height=\"40\"></td>\n" +
                "\t  </tr>\n" +
                "\t  <tr>\n" +
                "\t    <td style=\"text-align:center; color: #bcc9dd; font-family: 'Raleway', arial; font-weight: 400; font-size: 14px; letter-spacing:.5px;\">\n" +
                "\t      <a href=\"#\" style=\"color: #bcc9dd; text-decoration:none;\">��������</a>\n" +
                "\t      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" +
                "\t      <a href=\"#\" style=\"color: #bcc9dd; text-decoration:none;\">���ǵ��Ŷ�</a>\n" +
                "\t      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" +
                "\t      <a href=\"#\" style=\"color: #bcc9dd; text-decoration:none;\">�˹ܿ�ϵͳ</a>\n" +
                "\t      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" +
                "\t      <a href=\"#\" style=\"color: #bcc9dd; text-decoration:none;\">��ϵ����</a>\n" +
                "\t    </td>\n" +
                "\t  </tr>\n" +
                "\t  <tr>\n" +
                "\t    <td width=\"100%\" height=\"40\"></td>\n" +
                "\t  </tr>\n" +
                "\t  <tr>\n" +
                "\t    <td style=\"text-align:center; color:#bcc9dd; font-family: 'Raleway', arial; font-weight: 400; font-size: 12px; letter-spacing:.5px;\">Copyright &copy; 2019. HQFT All rights reserved.</td>\n" +
                "\t  </tr>\n" +
                "\t  <tr>\n" +
                "\t    <td width=\"100%\" height=\"40\"></td>\n" +
                "\t  </tr>\n" +
                "\t</table>\n" +
                "</body>\n" +
                "</html>", "text/html;charset=UTF-8");
        // �������ݹ�ϵ
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text);
        mm.addBodyPart(image1);
        mm.addBodyPart(image2);
        mm.addBodyPart(image3);
        mm.addBodyPart(image4);
        mm.setSubType("related");

        //���õ���Ϣ�У������޸�
        message.setContent(mm);
        message.saveChanges();

        try {
            //5�������ʼ�
            ts.sendMessage(message, message.getAllRecipients());
        } catch (MailException e) {
            return MailServiceException.SEND_ERROR;
        }finally {
            ts.close();
        }
        return MailServiceException.SEND_SUCCESS;
//
////        List<File> files = fileService.findFilesByRepairID(content.getRepairFileID());
////        List<Images> images = fileService.findFilesByRepairID(content.getRepairFileID());
//        Email email = EmailBuilder.startingBlank()
//                .from("�˹ܿ�ϵͳ���������", from)
//                .to(emailName, to)
//                //email����
////                .to("xuchao", "znxuchao@hytch.com")
//                //email����
////                .ccWithFixedName("xuchao", "znxuchao@hytch.com", "znxuchao@hytch.com")
//                //email�ظ�
////                .withReplyTo("xuchao", "znxuchao@hytch.com")
//                .withSubject(subject)
//                //emailǶ��Html
//                .withHTMLText()
//                //email��ͨ����
////                .withPlainText(content)
//                //emailǶ��ͼƬ
////                .withEmbeddedImage("img", imageByteArray, "image/png")
//                .withEmbeddedImage("arrow_white", new FileDataSource(basePath + "/static/images/arrow_white.png"))
//                .withEmbeddedImage("features_icon1", new FileDataSource(basePath + "/static/images/features_icon1.png"))
//                .withEmbeddedImage("features_icon2", new FileDataSource(basePath + "/static/images/features_icon2.png"))
//                .withEmbeddedImage("small_img1", new FileDataSource(basePath + "/static/images/small_img1.jpg"))
//                //emailǶ��pdf
////                .withAttachment("pdf", pdfByteArray, "application/pdf")
//                //emailǶ��doc
//                .withAttachment("����", new FileDataSource(basePath + files.get(0).getPath()))
//                //emailͷ��Э��
////                .withHeader("X-Priority", 5)
//                //email����������
////                .withReturnReceiptTo()
////                .withDispositionNotificationTo("znxuchao@hytch.com")
////                .withBounceTo("znxuchao@hytch.com")
//                .buildEmail();
//
//        Mailer mailer = MailerBuilder
//                .withSMTPServer(host, Integer.parseInt(port), userName, password)
////                .withTransportStrategy(TransportStrategy.SMTP_TLS)
//                //email���������
////                .withProxy("socksproxy.host.com", 1080, "proxy user", "proxy password")
//                .withSessionTimeout(10 * 1000)
//                //�ر�email��֤
//                .clearEmailAddressCriteria()
//                //emailЭ������
////                .withProperty("mail.smtp.sendpartial", true)
//                //����log4j
//                .withDebugLogging(true)
//                .buildMailer();
//
//        try {
//            mailer.sendMail(email);
//        } catch (MailException e) {
//            return MailServiceException.SEND_ERROR;
//        }
//        return MailServiceException.SEND_SUCCESS;
    }

    @Override
    public Boolean sendMailQestion(String subject, QuestionInfo content, String emailName, String to, String basePath) throws MessagingException, GeneralSecurityException, UnsupportedEncodingException {
        Properties prop = new Properties();
        prop.setProperty("mail.host", host); //// ����QQ�ʼ�������
        prop.setProperty("mail.transport.protocol", protocol); // �ʼ�����Э��
        prop.setProperty("mail.smtp.auth", "true"); // ��Ҫ��֤�û�������

        // ����QQ���䣬��Ҫ����SSL���ܣ��������´��뼴��
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);

        //ʹ��JavaMail�����ʼ���5������

        //������������Ӧ�ó�������Ļ�����Ϣ�� Session ����

        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                //�������ʼ��û�������Ȩ��
                return new PasswordAuthentication(userName, password);
            }
        });


        //����Session��debugģʽ�������Ϳ��Բ鿴��������Email������״̬
        session.setDebug(true);

        //2��ͨ��session�õ�transport����
        Transport ts = session.getTransport();

        //3��ʹ��������û�������Ȩ�������ʼ�������
        ts.connect(host, userName, password);

        //4�������ʼ�

        //�����ʼ�����
        MimeMessage message = new MimeMessage(session);

        //ָ���ʼ��ķ�����
        message.setFrom(new InternetAddress(userName,emailName, "UTF-8"));

        //ָ���ʼ����ռ���
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        // ׼��ͼƬ����
        MimeBodyPart image1 = new MimeBodyPart();
        DataHandler dh1 = new DataHandler(new FileDataSource(basePath + "/static/images/arrow_white.png"));
        image1.setDataHandler(dh1);
        image1.setContentID("arrow_white");

        MimeBodyPart image2 = new MimeBodyPart();
        DataHandler dh2 = new DataHandler(new FileDataSource(basePath + "/static/images/features_icon1.png"));
        image2.setDataHandler(dh2);
        image2.setContentID("features_icon1");

        MimeBodyPart image3 = new MimeBodyPart();
        DataHandler dh3 = new DataHandler(new FileDataSource(basePath + "/static/images/features_icon2.png"));
        image3.setDataHandler(dh3);
        image3.setContentID("features_icon2");

        MimeBodyPart image4 = new MimeBodyPart();
        DataHandler dh4 = new DataHandler(new FileDataSource(basePath + "/static/images/small_img1.jpg"));
        image4.setDataHandler(dh4);
        image4.setContentID("small_img1");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(content.getQuestionDate());
        // ׼����������
        MimeBodyPart text = new MimeBodyPart();
        text.setContent("" +
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> \n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                " <head>\n" +
                "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "  <title>���ⷴ��ϵͳ���������</title>\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
                "  <style type=\"text/css\">\n" +
                "    \n" +
                "\thtml{\n" +
                "\t\twidth: 100%; \n" +
                "\t}\n" +
                "\n" +
                "\tbody{\n" +
                "\t\twidth: 100%;  \n" +
                "\t\tmargin:0; \n" +
                "\t\tpadding:0; \n" +
                "\t\t-webkit-font-smoothing: antialiased;\n" +
                "\t\tmso-margin-top-alt:0px; \n" +
                "\t\tmso-margin-bottom-alt:0px; \n" +
                "\t\tmso-padding-alt: 0px 0px 0px 0px;\n" +
                "\t\tbackground: #ffffff;\n" +
                "\t}\n" +
                "\n" +
                "\tp,h1,h2,h3,h4{\n" +
                "\t\tmargin-top:0;\n" +
                "\t\tmargin-bottom:0;\n" +
                "\t\tpadding-top:0;\n" +
                "\t\tpadding-bottom:0;\n" +
                "\t}\n" +
                "\n" +
                "\ttable{\n" +
                "\t\tfont-size: 14px;\n" +
                "\t\tborder: 0;\n" +
                "\t}\n" +
                "\n" +
                "\timg{\n" +
                "\t\tborder: none!important;\n" +
                "\t}\n" +
                "\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body style=\"margin: 0; padding: 0;\">\n" +
                "\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#dfe7f2\" style=\"background-size: cover; -webkit-background-size: cover; -moz-background-size: cover -o-background-size: cover; background-position: 0 0; background-repeat: no-repeat; text-align:center;\">\n" +
                "\t  <tr>\n" +
                "\t  \t<td>\n" +
                "\t  \t\t<table width=\"600\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t  \t\t\t<tr>\n" +
                "\t\t\t\t\t<td width=\"100%\" height=\"20\"></td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td style=\"color: #646262; font-family: 'Raleway', arial; font-size: 40px; text-align:center; font-weight:200;\" align=\"center\">\n" +
                "\t\t\t\t\t  <span style=\"font-weight: 500; letter-spacing:1px;\">���ⷴ��ϵͳ���������</span>\n" +
                "\t\t\t\t\t</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td width=\"100%\" height=\"20\"></td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t\t\t<table width=\"250\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; -moz-border-radius: 100px; -webkit-border-radius: 100px; border-radius: 100px; background: #ec5482;\">\n" +
                "\t\t\t\t\t\t    <tr>\n" +
                "\t\t\t\t\t\t      <td width=\"100%\" height=\"15\"></td>\n" +
                "\t\t\t\t\t\t    </tr>\n" +
                "\t\t\t\t\t\t    <tr>\n" +
                "\t\t\t\t\t\t      <td>               \n" +
                "\t\t\t\t\t\t        <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; text-align:center;\">\n" +
                "\t\t\t\t\t\t          <tr>\n" +
                "\t\t\t\t\t\t            <td align=\"center\" style=\"text-align:center;\">\n" +
                "\t\t\t\t\t\t              <a href=\"http://10.98.98.80:8080/fangte/main#\" style=\"color: #fff; font-family: 'Raleway', arial; font-size: 18px; text-decoration:none; font-weight:500; letter-spacing:.5px;\">�������\n" +
                "\t\t\t\t\t\t              \t<img src=\"cid:arrow_white\" width=\"17\" height=\"7\" border=\"0\" alt=\"\" title=\"\" style=\"padding:0 0 0 5px; vertical-align:middle;\"/>\n" +
                "\t\t\t\t\t\t              </a>\n" +
                "\t\t\t\t\t\t            </td>\n" +
                "\t\t\t\t\t\t          </tr>\n" +
                "\t\t\t\t\t\t        </table>\n" +
                "\n" +
                "\t\t\t\t\t\t      </td>\n" +
                "\t\t\t\t\t\t    </tr>\n" +
                "\t\t\t\t\t\t    <tr>\n" +
                "\t\t\t\t\t\t      <td width=\"100%\" height=\"15\"></td>\n" +
                "\t\t\t\t\t\t    </tr>\n" +
                "\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td width=\"100%\" height=\"20\"></td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t  \t\t</table>\n" +
                "\t  \t</td>\n" +
                "\t  </tr>\n" +
                "\t</table> \n" +
                "\n" +
                "\t<table width=\"600\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#ffffff\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t  <tr>\n" +
                "\t    <td width=\"100%\" height=\"50\"></td>\n" +
                "\t  </tr>\n" +
                "\t  <tr>\n" +
                "\t    <td>\n" +
                "\t      <table width=\"600\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t        <tr>\n" +
                "\t          <td>\n" +
                "\t            <table width=\"280\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t              <tr>\n" +
                "\t                <td style=\"vertical-align:top;\">\n" +
                "\n" +
                "\t                  <table width=\"36\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t                    <tr>\n" +
                "\t                      <td width=\"100%\" height=\"7\"></td>\n" +
                "\t                    </tr>\n" +
                "\t                    <tr>\n" +
                "\t                      <td>\n" +
                "\t                        <img src=\"cid:features_icon1\" border=\"0\" alt=\"\" title=\"\" style=\"display: block;\"/>\n" +
                "\t                      </td>\n" +
                "\t                    </tr>\n" +
                "\t                  </table>\n" +
                "\t                </td>\n" +
                "\t                <td width=\"20\" style=\"width:20px;\"></td>\n" +
                "\t                <td>\n" +
                "\t                \t<table cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t\t                    <tr>\n" +
                "\t\t                      <td>\n" +
                "\t\t                        <p style=\"color: #8b8b8b; font-family: 'Raleway', arial; font-weight: 400; font-size: 14px; line-height:26px;\">�������ƣ�" + content.getPark().getParkName() + "<br />��Ŀ���ƣ�" + content.getProject().getProjectName() + "<br />רҵ��" + content.getSubjectID() + "</p>\n" +
                "\t\t                      </td>\n" +
                "\t\t                    </tr>\n" +
                "\t\t                    <tr>\n" +
                "\t\t                      <td width=\"100%\" height=\"30\"></td>\n" +
                "\t\t                    </tr>\t\t                   \n" +
                "\t                \t</table>\n" +
                "\t                </td>\n" +
                "\t              </tr>\n" +
                "\t            </table>\n" +
                "\n" +
                "\t            <table width=\"280\" align=\"right\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t              <tr>\n" +
                "\t                <td style=\"vertical-align:top;\">\n" +
                "\t                  <table width=\"36\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t                    <tr>\n" +
                "\t                      <td width=\"100%\" height=\"7\"></td>\n" +
                "\t                    </tr>\n" +
                "\t                    <tr>\n" +
                "\t                      <td>\n" +
                "\t                        <img src=\"cid:features_icon2\" border=\"0\" alt=\"\" title=\"\" style=\"display: block;\"/>\n" +
                "\t                      </td>\n" +
                "\t                    </tr>\n" +
                "\t                  </table>\n" +
                "\n" +
                "\t                </td>\n" +
                "\t                <td width=\"20\" style=\"width:20px;\"></td>\n" +
                "\t                <td>\n" +
                "\n" +
                "\t                  <table cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t                    <tr>\n" +
                "\t                      <td>\n" +
                "\t                        <p style=\"color: #8b8b8b; font-family: 'Raleway', arial; font-weight: 400; font-size: 14px; line-height:26px;\">�ύʱ�䣺" + dateString + "<br />�ύ�ˣ�" + content.getUser().getFirstName() + content.getUser().getLastName() + "</p>\n" +
                "\t                      </td>\n" +
                "\t                    </tr>\n" +
                "\t                    <tr>\n" +
                "\t                      <td width=\"100%\" height=\"30\"></td>\n" +
                "\t                    </tr>\t                    \n" +
                "\t                  </table>\n" +
                "\t                </td>\n" +
                "\t              </tr>\n" +
                "\t            </table>\n" +
                "\t          </td>\n" +
                "\t        </tr>     \n" +
                "\t      </table>\n" +
                "\t    </td>\n" +
                "\t  </tr>\n" +
                "\t  <tr>\n" +
                "\t    <td width=\"100%\" height=\"50\"></td>\n" +
                "\t  </tr>\n" +
                "\t</table>\n" +
                "\n" +
                "\n" +
                "\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#fcfcfc\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t  <tr>\n" +
                "\t    <td>\n" +
                "\t      <table width=\"600\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#fcfcfc\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t        <tr>\n" +
                "\t          <td>\n" +
                "\t            <table cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t              <tr>\n" +
                "\t                <td width=\"187\" style=\"vertical-align:top;\">\n" +
                "\t                  <table cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t                    <tr>\n" +
                "\t                      <td width=\"100%\" height=\"6\"></td>\n" +
                "\t                    </tr>\n" +
                "\t                    <tr>\n" +
                "\t                      <td>\n" +
                "\t                        <a href=\"#\">\n" +
                "\t                          <img src=\"cid:small_img1.jpg\" border=\"0\" alt=\"\" title=\"\" width=\"187\" height=\"155\" style=\"display: block;\"/>\n" +
                "\t                        </a>\n" +
                "\t                      </td>\n" +
                "\t                    </tr>\n" +
                "\t                  </table>\n" +
                "\t                </td>\n" +
                "\t                <td width=\"40\" style=\"width:40px;\"></td>\n" +
                "\t                <td>\n" +
                "\t                  <table cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t                    <tr>\n" +
                "\t                      <td>\n" +
                "\t                        <a href=\"#\" style=\"color: #676767; font-family: 'Raleway', arial; font-size: 16px; font-weight: 600; text-transform:uppercase; letter-spacing:.5px; line-height:26px; text-decoration:none;\">\n" +
                "\t                          ����������\n" +
                "\t                        </a>\n" +
                "\t                      </td>\n" +
                "\t                    </tr>\n" +
                "\t                    <tr>\n" +
                "\t                      <td width=\"100%\" height=\"15\"></td>\n" +
                "\t                    </tr>\n" +
                "\t                    <tr>\n" +
                "\t                      <td style=\"color: #8b8b8b; font-family: 'Raleway', arial; font-weight: 400; font-size: 14px; letter-spacing:.5px; line-height:26px;\">\n" +
                "\t                        " + content.getProblemText() + "\n" +
                "\t                      </td>\n" +
                "\t                    </tr>\n" +
                "\t                  </table>\n" +
                "\t                </td>\n" +
                "\t              </tr>                    \n" +
                "\t            </table>\n" +
                "\t          </td>\n" +
                "\t        </tr>\n" +
                "\t\t\t<tr>\n" +
                "\t           <td width=\"100%\" height=\"50\"></td>\n" +
                "\t        </tr>\n" +
                "\t      </table>\n" +
                "\t    </td>\n" +
                "\t  </tr>      \n" +
                "\t</table>\n" +
                "\n" +
                "\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#38404b\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t  <tr>\n" +
                "\t    <td width=\"100%\" height=\"40\"></td>\n" +
                "\t  </tr>\n" +
                "\t  <tr>\n" +
                "\t    <td style=\"text-align:center; color: #bcc9dd; font-family: 'Raleway', arial; font-weight: 400; font-size: 14px; letter-spacing:.5px;\">\n" +
                "\t      <a href=\"#\" style=\"color: #bcc9dd; text-decoration:none;\">��������</a>\n" +
                "\t      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" +
                "\t      <a href=\"#\" style=\"color: #bcc9dd; text-decoration:none;\">���ǵ��Ŷ�</a>\n" +
                "\t      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" +
                "\t      <a href=\"#\" style=\"color: #bcc9dd; text-decoration:none;\">���ⷴ��ϵͳ</a>\n" +
                "\t      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" +
                "\t      <a href=\"#\" style=\"color: #bcc9dd; text-decoration:none;\">��ϵ����</a>\n" +
                "\t    </td>\n" +
                "\t  </tr>\n" +
                "\t  <tr>\n" +
                "\t    <td width=\"100%\" height=\"40\"></td>\n" +
                "\t  </tr>\n" +
                "\t  <tr>\n" +
                "\t    <td style=\"text-align:center; color:#bcc9dd; font-family: 'Raleway', arial; font-weight: 400; font-size: 12px; letter-spacing:.5px;\">Copyright &copy; 2019. HQFT All rights reserved.</td>\n" +
                "\t  </tr>\n" +
                "\t  <tr>\n" +
                "\t    <td width=\"100%\" height=\"40\"></td>\n" +
                "\t  </tr>\n" +
                "\t</table>\n" +
                "</body>\n" +
                "</html>", "text/html;charset=UTF-8");

        // �������ݹ�ϵ
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text);
        mm.addBodyPart(image1);
        mm.addBodyPart(image2);
        mm.addBodyPart(image3);
        mm.addBodyPart(image4);
        mm.setSubType("related");

        //���õ���Ϣ�У������޸�
        message.setContent(mm);
        message.saveChanges();
////                .withEmbeddedImage("img", imageByteArray, "image/png")
//                .withEmbeddedImage("arrow_white", new FileDataSource(basePath + "/static/images/arrow_white.png"))
//                .withEmbeddedImage("features_icon1", new FileDataSource(basePath + "/static/images/features_icon1.png"))
//                .withEmbeddedImage("features_icon2", new FileDataSource(basePath + "/static/images/features_icon2.png"))
//                .withEmbeddedImage("small_img1", new FileDataSource(basePath + "/static/images/small_img1.jpg"))
        //�ʼ��ı���
        message.setSubject(subject);
        //�ʼ����ı�����
        //message.setContent("��ð���", "text/html;charset=UTF-8");



        try {
            //5�������ʼ�
            ts.sendMessage(message, message.getAllRecipients());
        } catch (MailException e) {
            return MailServiceException.SEND_ERROR;
        }finally {
            ts.close();
        }
        return MailServiceException.SEND_SUCCESS;
    } @Override
    public Boolean sendMailHuiyishi(String subject, Huiyishi huiyishi, String emailName, String to, String basePath) throws MessagingException, GeneralSecurityException, UnsupportedEncodingException {
        Properties prop = new Properties();
        prop.setProperty("mail.host", host); //// ����QQ�ʼ�������
        prop.setProperty("mail.transport.protocol", protocol); // �ʼ�����Э��
        prop.setProperty("mail.smtp.auth", "true"); // ��Ҫ��֤�û�������

        // ����QQ���䣬��Ҫ����SSL���ܣ��������´��뼴��
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);

        //ʹ��JavaMail�����ʼ���5������

        //������������Ӧ�ó�������Ļ�����Ϣ�� Session ����

        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                //�������ʼ��û�������Ȩ��
                return new PasswordAuthentication(userName, password);
            }
        });


        //����Session��debugģʽ�������Ϳ��Բ鿴��������Email������״̬
        session.setDebug(true);

        //2��ͨ��session�õ�transport����
        Transport ts = session.getTransport();

        //3��ʹ��������û�������Ȩ�������ʼ�������
        ts.connect(host, userName, password);

        //4�������ʼ�

        //�����ʼ�����
        MimeMessage message = new MimeMessage(session);

        //ָ���ʼ��ķ�����
        message.setFrom(new InternetAddress(userName,emailName, "UTF-8"));

        //ָ���ʼ����ռ���
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        // ׼��ͼƬ����
        MimeBodyPart image1 = new MimeBodyPart();
        DataHandler dh1 = new DataHandler(new FileDataSource(basePath + "/static/images/arrow_white.png"));
        image1.setDataHandler(dh1);
        image1.setContentID("arrow_white");

        MimeBodyPart image2 = new MimeBodyPart();
        DataHandler dh2 = new DataHandler(new FileDataSource(basePath + "/static/images/features_icon1.png"));
        image2.setDataHandler(dh2);
        image2.setContentID("features_icon1");

        MimeBodyPart image3 = new MimeBodyPart();
        DataHandler dh3 = new DataHandler(new FileDataSource(basePath + "/static/images/features_icon2.png"));
        image3.setDataHandler(dh3);
        image3.setContentID("features_icon2");

        MimeBodyPart image4 = new MimeBodyPart();
        DataHandler dh4 = new DataHandler(new FileDataSource(basePath + "/static/images/small_img1.jpg"));
        image4.setDataHandler(dh4);
        image4.setContentID("small_img1");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(huiyishi.getHysDate());
        // ׼����������
        MimeBodyPart text = new MimeBodyPart();
        text.setContent("" +
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> \n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                " <head>\n" +
                "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "  <title>������ϵͳ���������</title>\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
                "  <style type=\"text/css\">\n" +
                "    \n" +
                "\thtml{\n" +
                "\t\twidth: 100%; \n" +
                "\t}\n" +
                "\n" +
                "\tbody{\n" +
                "\t\twidth: 100%;  \n" +
                "\t\tmargin:0; \n" +
                "\t\tpadding:0; \n" +
                "\t\t-webkit-font-smoothing: antialiased;\n" +
                "\t\tmso-margin-top-alt:0px; \n" +
                "\t\tmso-margin-bottom-alt:0px; \n" +
                "\t\tmso-padding-alt: 0px 0px 0px 0px;\n" +
                "\t\tbackground: #ffffff;\n" +
                "\t}\n" +
                "\n" +
                "\tp,h1,h2,h3,h4{\n" +
                "\t\tmargin-top:0;\n" +
                "\t\tmargin-bottom:0;\n" +
                "\t\tpadding-top:0;\n" +
                "\t\tpadding-bottom:0;\n" +
                "\t}\n" +
                "\n" +
                "\ttable{\n" +
                "\t\tfont-size: 14px;\n" +
                "\t\tborder: 0;\n" +
                "\t}\n" +
                "\n" +
                "\timg{\n" +
                "\t\tborder: none!important;\n" +
                "\t}\n" +
                "\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body style=\"margin: 0; padding: 0;\">\n" +
                "\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#dfe7f2\" style=\"background-size: cover; -webkit-background-size: cover; -moz-background-size: cover -o-background-size: cover; background-position: 0 0; background-repeat: no-repeat; text-align:center;\">\n" +
                "\t  <tr>\n" +
                "\t  \t<td>\n" +
                "\t  \t\t<table width=\"600\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t  \t\t\t<tr>\n" +
                "\t\t\t\t\t<td width=\"100%\" height=\"20\"></td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td style=\"color: #646262; font-family: 'Raleway', arial; font-size: 40px; text-align:center; font-weight:200;\" align=\"center\">\n" +
                "\t\t\t\t\t  <span style=\"font-weight: 500; letter-spacing:1px;\">������ϵͳ���������</span>\n" +
                "\t\t\t\t\t</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td width=\"100%\" height=\"20\"></td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t\t\t<table width=\"250\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; -moz-border-radius: 100px; -webkit-border-radius: 100px; border-radius: 100px; background: #ec5482;\">\n" +
                "\t\t\t\t\t\t    <tr>\n" +
                "\t\t\t\t\t\t      <td width=\"100%\" height=\"15\"></td>\n" +
                "\t\t\t\t\t\t    </tr>\n" +
                "\t\t\t\t\t\t    <tr>\n" +
                "\t\t\t\t\t\t      <td>               \n" +
                "\t\t\t\t\t\t        <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; text-align:center;\">\n" +
                "\t\t\t\t\t\t          <tr>\n" +
                "\t\t\t\t\t\t            <td align=\"center\" style=\"text-align:center;\">\n" +
                "\t\t\t\t\t\t              <a href=\"http://10.95.2.26:8080/erp/main#\" style=\"color: #fff; font-family: 'Raleway', arial; font-size: 18px; text-decoration:none; font-weight:500; letter-spacing:.5px;\">�������\n" +
                "\t\t\t\t\t\t              \t<img src=\"cid:arrow_white\" width=\"17\" height=\"7\" border=\"0\" alt=\"\" title=\"\" style=\"padding:0 0 0 5px; vertical-align:middle;\"/>\n" +
                "\t\t\t\t\t\t              </a>\n" +
                "\t\t\t\t\t\t            </td>\n" +
                "\t\t\t\t\t\t          </tr>\n" +
                "\t\t\t\t\t\t        </table>\n" +
                "\n" +
                "\t\t\t\t\t\t      </td>\n" +
                "\t\t\t\t\t\t    </tr>\n" +
                "\t\t\t\t\t\t    <tr>\n" +
                "\t\t\t\t\t\t      <td width=\"100%\" height=\"15\"></td>\n" +
                "\t\t\t\t\t\t    </tr>\n" +
                "\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td width=\"100%\" height=\"20\"></td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t  \t\t</table>\n" +
                "\t  \t</td>\n" +
                "\t  </tr>\n" +
                "\t</table> \n" +
                "\n" +
                "\t<table width=\"600\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#ffffff\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t  <tr>\n" +
                "\t    <td width=\"100%\" height=\"50\"></td>\n" +
                "\t  </tr>\n" +
                "\t  <tr>\n" +
                "\t    <td>\n" +
                "\t      <table width=\"600\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t        <tr>\n" +
                "\t          <td>\n" +
                "\t            <table width=\"280\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t              <tr>\n" +
                "\t                <td style=\"vertical-align:top;\">\n" +
                "\n" +
                "\t                  <table width=\"36\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t                    <tr>\n" +
                "\t                      <td width=\"100%\" height=\"7\"></td>\n" +
                "\t                    </tr>\n" +
                "\t                    <tr>\n" +
                "\t                      <td>\n" +
                "\t                        <img src=\"cid:features_icon1\" border=\"0\" alt=\"\" title=\"\" style=\"display: block;\"/>\n" +
                "\t                      </td>\n" +
                "\t                    </tr>\n" +
                "\t                  </table>\n" +
                "\t                </td>\n" +
                "\t                <td width=\"20\" style=\"width:20px;\"></td>\n" +
                "\t                <td>\n" +
                "\t                \t<table cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t\t                    <tr>\n" +
                "\t\t                      <td>\n" +
                "\t\t                        <p style=\"color: #8b8b8b; font-family: 'Raleway', arial; font-weight: 400; font-size: 14px; line-height:26px;\">��԰���ƣ�" + huiyishi.getPark().getParkName() + "<br />�ύ�ˣ�" + huiyishi.getUserID().getAllName() + "<br />��ע��" + huiyishi.getRemark1() + "</p>\n" +
                "\t\t                      </td>\n" +
                "\t\t                    </tr>\n" +
                "\t\t                    <tr>\n" +
                "\t\t                      <td width=\"100%\" height=\"30\"></td>\n" +
                "\t\t                    </tr>\t\t                   \n" +
                "\t                \t</table>\n" +
                "\t                </td>\n" +
                "\t              </tr>\n" +
                "\t            </table>\n" +
                "\n" +
                "\t            <table width=\"280\" align=\"right\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t              <tr>\n" +
                "\t                <td style=\"vertical-align:top;\">\n" +
                "\t                  <table width=\"36\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t                    <tr>\n" +
                "\t                      <td width=\"100%\" height=\"7\"></td>\n" +
                "\t                    </tr>\n" +
                "\t                    <tr>\n" +
                "\t                      <td>\n" +
                "\t                        <img src=\"cid:features_icon2\" border=\"0\" alt=\"\" title=\"\" style=\"display: block;\"/>\n" +
                "\t                      </td>\n" +
                "\t                    </tr>\n" +
                "\t                  </table>\n" +
                "\n" +
                "\t                </td>\n" +
                "\t                <td width=\"20\" style=\"width:20px;\"></td>\n" +
                "\t                <td>\n" +
                "\n" +
                "\t                  <table cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t                    <tr>\n" +
                "\t                      <td>\n" +
                "\t                        <p style=\"color: #8b8b8b; font-family: 'Raleway', arial; font-weight: 400; font-size: 14px; line-height:26px;\">�ύʱ�䣺" + dateString + "<br />ID��" + huiyishi.getHysID() + "</p>\n" +
                "\t                      </td>\n" +
                "\t                    </tr>\n" +
                "\t                    <tr>\n" +
                "\t                      <td width=\"100%\" height=\"30\"></td>\n" +
                "\t                    </tr>\t                    \n" +
                "\t                  </table>\n" +
                "\t                </td>\n" +
                "\t              </tr>\n" +
                "\t            </table>\n" +
                "\t          </td>\n" +
                "\t        </tr>     \n" +
                "\t      </table>\n" +
                "\t    </td>\n" +
                "\t  </tr>\n" +
                "\t  <tr>\n" +
                "\t    <td width=\"100%\" height=\"50\"></td>\n" +
                "\t  </tr>\n" +
                "\t</table>\n" +
                "\n" +
                "\n" +
                "\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#fcfcfc\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t  <tr>\n" +
                "\t    <td>\n" +
                "\t      <table width=\"600\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#fcfcfc\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t        <tr>\n" +
                "\t          <td>\n" +
                "\t            <table cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t              <tr>\n" +
                "\t                <td width=\"187\" style=\"vertical-align:top;\">\n" +
                "\t                  <table cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t                    <tr>\n" +
                "\t                      <td width=\"100%\" height=\"6\"></td>\n" +
                "\t                    </tr>\n" +
                "\t                    <tr>\n" +
                "\t                      <td>\n" +
                "\t                        <a href=\"#\">\n" +
                "\t                          <img src=\"cid:small_img1.jpg\" border=\"0\" alt=\"\" title=\"\" width=\"187\" height=\"155\" style=\"display: block;\"/>\n" +
                "\t                        </a>\n" +
                "\t                      </td>\n" +
                "\t                    </tr>\n" +
                "\t                  </table>\n" +
                "\t                </td>\n" +
                "\t                <td width=\"40\" style=\"width:40px;\"></td>\n" +
                "\t                <td>\n" +
                "\t                  <table cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t                    <tr>\n" +
                "\t                      <td>\n" +
                "\t                        <a href=\"#\" style=\"color: #676767; font-family: 'Raleway', arial; font-size: 16px; font-weight: 600; text-transform:uppercase; letter-spacing:.5px; line-height:26px; text-decoration:none;\">\n" +
                "\t                          ������������\n" +
                "\t                        </a>\n" +
                "\t                      </td>\n" +
                "\t                    </tr>\n" +
                "\t                    <tr>\n" +
                "\t                      <td width=\"100%\" height=\"15\"></td>\n" +
                "\t                    </tr>\n" +
                "\t                    <tr>\n" +
                "\t                      <td style=\"color: #8b8b8b; font-family: 'Raleway', arial; font-weight: 400; font-size: 14px; letter-spacing:.5px; line-height:26px;\">\n" +
                "\t                        " + huiyishi.getHysText() + "\n" +
                "\t                      </td>\n" +
                "\t                    </tr>\n" +
                "\t                  </table>\n" +
                "\t                </td>\n" +
                "\t              </tr>                    \n" +
                "\t            </table>\n" +
                "\t          </td>\n" +
                "\t        </tr>\n" +
                "\t\t\t<tr>\n" +
                "\t           <td width=\"100%\" height=\"50\"></td>\n" +
                "\t        </tr>\n" +
                "\t      </table>\n" +
                "\t    </td>\n" +
                "\t  </tr>      \n" +
                "\t</table>\n" +
                "\n" +
                "\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#38404b\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
                "\t  <tr>\n" +
                "\t    <td width=\"100%\" height=\"40\"></td>\n" +
                "\t  </tr>\n" +
                "\t  <tr>\n" +
                "\t    <td style=\"text-align:center; color: #bcc9dd; font-family: 'Raleway', arial; font-weight: 400; font-size: 14px; letter-spacing:.5px;\">\n" +
                "\t      <a href=\"#\" style=\"color: #bcc9dd; text-decoration:none;\">��������</a>\n" +
                "\t      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" +
                "\t      <a href=\"#\" style=\"color: #bcc9dd; text-decoration:none;\">���ǵ��Ŷ�</a>\n" +
                "\t      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" +
                "\t      <a href=\"#\" style=\"color: #bcc9dd; text-decoration:none;\">���ⷴ��ϵͳ</a>\n" +
                "\t      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" +
                "\t      <a href=\"#\" style=\"color: #bcc9dd; text-decoration:none;\">��ϵ����</a>\n" +
                "\t    </td>\n" +
                "\t  </tr>\n" +
                "\t  <tr>\n" +
                "\t    <td width=\"100%\" height=\"40\"></td>\n" +
                "\t  </tr>\n" +
                "\t  <tr>\n" +
                "\t    <td style=\"text-align:center; color:#bcc9dd; font-family: 'Raleway', arial; font-weight: 400; font-size: 12px; letter-spacing:.5px;\">Copyright &copy; 2019. HQFT All rights reserved.</td>\n" +
                "\t  </tr>\n" +
                "\t  <tr>\n" +
                "\t    <td width=\"100%\" height=\"40\"></td>\n" +
                "\t  </tr>\n" +
                "\t</table>\n" +
                "</body>\n" +
                "</html>", "text/html;charset=UTF-8");

        // �������ݹ�ϵ
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text);
        mm.addBodyPart(image1);
        mm.addBodyPart(image2);
        mm.addBodyPart(image3);
        mm.addBodyPart(image4);
        mm.setSubType("related");

        //���õ���Ϣ�У������޸�
        message.setContent(mm);
        message.saveChanges();
////                .withEmbeddedImage("img", imageByteArray, "image/png")
//                .withEmbeddedImage("arrow_white", new FileDataSource(basePath + "/static/images/arrow_white.png"))
//                .withEmbeddedImage("features_icon1", new FileDataSource(basePath + "/static/images/features_icon1.png"))
//                .withEmbeddedImage("features_icon2", new FileDataSource(basePath + "/static/images/features_icon2.png"))
//                .withEmbeddedImage("small_img1", new FileDataSource(basePath + "/static/images/small_img1.jpg"))
        //�ʼ��ı���
        message.setSubject(subject);
        //�ʼ����ı�����
        //message.setContent("��ð���", "text/html;charset=UTF-8");



        try {
            //5�������ʼ�
            ts.sendMessage(message, message.getAllRecipients());
        } catch (MailException e) {
            return MailServiceException.SEND_ERROR;
        }finally {
            ts.close();
        }
        return MailServiceException.SEND_SUCCESS;
    }
}
