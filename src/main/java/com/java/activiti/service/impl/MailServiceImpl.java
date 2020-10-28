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
//        // 设置发件人信息
//        helper.setFrom(this.from, this.fromName);
//        // 设置发件人
//        helper.setTo(to);
//        // 设置主题
//        helper.setSubject(subject);
//        // 设置邮件内容
//        helper.setText(content);
//        // 发送邮件
//        try {
//            logger.info("邮件发送中...");
//            this.mailSender.send(mailMessage);
//            logger.info("发送邮件完成");
//        }catch (Exception e){
//            logger.error(e.getMessage());
//        }
//    }

    public Boolean sendMail(String subject, Repair content, String emailName, String to, String basePath) throws MessagingException, GeneralSecurityException, UnsupportedEncodingException {

        Properties prop = new Properties();
        prop.setProperty("mail.host", host); //// 设置QQ邮件服务器
        prop.setProperty("mail.transport.protocol", protocol); // 邮件发送协议
        prop.setProperty("mail.smtp.auth", "true"); // 需要验证用户名密码

        // 关于QQ邮箱，还要设置SSL加密，加上以下代码即可
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);

        //使用JavaMail发送邮件的5个步骤

        //创建定义整个应用程序所需的环境信息的 Session 对象

        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                //发件人邮件用户名、授权码
                return new PasswordAuthentication(userName, password);
            }
        });


        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);

        //2、通过session得到transport对象
        Transport ts = session.getTransport();

        //3、使用邮箱的用户名和授权码连上邮件服务器
        ts.connect(host, userName, password);

        //4、创建邮件

        //创建邮件对象
        MimeMessage message = new MimeMessage(session);

        //指明邮件的发件人
        message.setFrom(new InternetAddress(userName,emailName, "UTF-8"));

        //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 准备图片数据
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

        //邮件的标题
        message.setSubject(subject);
        //邮件的文本内容
        //message.setContent("你好啊！", "text/html;charset=UTF-8");
        // 准备正文数据
        MimeBodyPart text = new MimeBodyPart();
        text.setContent("" +
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> \n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                " <head>\n" +
                "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "  <title>运管控系统提醒你查收</title>\n" +
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
                "\t\t\t\t\t  <span style=\"font-weight: 500; letter-spacing:1px;\">运管控系统提醒你查收</span>\n" +
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
                "\t\t\t\t\t\t              <a href=\"http://www.fangte.xyz\" style=\"color: #fff; font-family: 'Raleway', arial; font-size: 18px; text-decoration:none; font-weight:500; letter-spacing:.5px;\">点进进入\n" +
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
                "\t\t                        <p style=\"color: #8b8b8b; font-family: 'Raleway', arial; font-weight: 400; font-size: 14px; line-height:26px;\">故障发生日期：" + formatter.format(content.getRepairDate()) + "<br />故障填报日期：" + formatter.format(content.getRecordDate()) + "<br />故障发生地点：" + content.getRepairPlace() + "</p>\n" +
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
                "\t                        <p style=\"color: #8b8b8b; font-family: 'Raleway', arial; font-weight: 400; font-size: 14px; line-height:26px;\">故障程度：" + content.getRepairLevel() + "<br />故障类型：" + content.getRepairType() + "<br />故障填报人工号：" + content.getUser().getId() + "</p>\n" +
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
                "\t                          故障内容：\n" +
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
                "\t      <a href=\"#\" style=\"color: #bcc9dd; text-decoration:none;\">关于我们</a>\n" +
                "\t      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" +
                "\t      <a href=\"#\" style=\"color: #bcc9dd; text-decoration:none;\">我们的团队</a>\n" +
                "\t      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" +
                "\t      <a href=\"#\" style=\"color: #bcc9dd; text-decoration:none;\">运管控系统</a>\n" +
                "\t      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" +
                "\t      <a href=\"#\" style=\"color: #bcc9dd; text-decoration:none;\">联系我们</a>\n" +
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
        // 描述数据关系
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text);
        mm.addBodyPart(image1);
        mm.addBodyPart(image2);
        mm.addBodyPart(image3);
        mm.addBodyPart(image4);
        mm.setSubType("related");

        //设置到消息中，保存修改
        message.setContent(mm);
        message.saveChanges();

        try {
            //5、发送邮件
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
//                .from("运管控系统提醒你查收", from)
//                .to(emailName, to)
//                //email单例
////                .to("xuchao", "znxuchao@hytch.com")
//                //email多例
////                .ccWithFixedName("xuchao", "znxuchao@hytch.com", "znxuchao@hytch.com")
//                //email回复
////                .withReplyTo("xuchao", "znxuchao@hytch.com")
//                .withSubject(subject)
//                //email嵌入Html
//                .withHTMLText()
//                //email普通内容
////                .withPlainText(content)
//                //email嵌入图片
////                .withEmbeddedImage("img", imageByteArray, "image/png")
//                .withEmbeddedImage("arrow_white", new FileDataSource(basePath + "/static/images/arrow_white.png"))
//                .withEmbeddedImage("features_icon1", new FileDataSource(basePath + "/static/images/features_icon1.png"))
//                .withEmbeddedImage("features_icon2", new FileDataSource(basePath + "/static/images/features_icon2.png"))
//                .withEmbeddedImage("small_img1", new FileDataSource(basePath + "/static/images/small_img1.jpg"))
//                //email嵌入pdf
////                .withAttachment("pdf", pdfByteArray, "application/pdf")
//                //email嵌入doc
//                .withAttachment("附件", new FileDataSource(basePath + files.get(0).getPath()))
//                //email头部协议
////                .withHeader("X-Priority", 5)
//                //email不常用设置
////                .withReturnReceiptTo()
////                .withDispositionNotificationTo("znxuchao@hytch.com")
////                .withBounceTo("znxuchao@hytch.com")
//                .buildEmail();
//
//        Mailer mailer = MailerBuilder
//                .withSMTPServer(host, Integer.parseInt(port), userName, password)
////                .withTransportStrategy(TransportStrategy.SMTP_TLS)
//                //email代理服务器
////                .withProxy("socksproxy.host.com", 1080, "proxy user", "proxy password")
//                .withSessionTimeout(10 * 1000)
//                //关闭email验证
//                .clearEmailAddressCriteria()
//                //email协议类型
////                .withProperty("mail.smtp.sendpartial", true)
//                //开启log4j
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
        prop.setProperty("mail.host", host); //// 设置QQ邮件服务器
        prop.setProperty("mail.transport.protocol", protocol); // 邮件发送协议
        prop.setProperty("mail.smtp.auth", "true"); // 需要验证用户名密码

        // 关于QQ邮箱，还要设置SSL加密，加上以下代码即可
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);

        //使用JavaMail发送邮件的5个步骤

        //创建定义整个应用程序所需的环境信息的 Session 对象

        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                //发件人邮件用户名、授权码
                return new PasswordAuthentication(userName, password);
            }
        });


        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);

        //2、通过session得到transport对象
        Transport ts = session.getTransport();

        //3、使用邮箱的用户名和授权码连上邮件服务器
        ts.connect(host, userName, password);

        //4、创建邮件

        //创建邮件对象
        MimeMessage message = new MimeMessage(session);

        //指明邮件的发件人
        message.setFrom(new InternetAddress(userName,emailName, "UTF-8"));

        //指明邮件的收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        // 准备图片数据
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
        // 准备正文数据
        MimeBodyPart text = new MimeBodyPart();
        text.setContent("" +
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> \n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                " <head>\n" +
                "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "  <title>问题反馈系统提醒你查收</title>\n" +
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
                "\t\t\t\t\t  <span style=\"font-weight: 500; letter-spacing:1px;\">问题反馈系统提醒你查收</span>\n" +
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
                "\t\t\t\t\t\t              <a href=\"http://10.98.98.80:8080/fangte/main#\" style=\"color: #fff; font-family: 'Raleway', arial; font-size: 18px; text-decoration:none; font-weight:500; letter-spacing:.5px;\">点进进入\n" +
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
                "\t\t                        <p style=\"color: #8b8b8b; font-family: 'Raleway', arial; font-weight: 400; font-size: 14px; line-height:26px;\">工程名称：" + content.getPark().getParkName() + "<br />项目名称：" + content.getProject().getProjectName() + "<br />专业：" + content.getSubjectID() + "</p>\n" +
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
                "\t                        <p style=\"color: #8b8b8b; font-family: 'Raleway', arial; font-weight: 400; font-size: 14px; line-height:26px;\">提交时间：" + dateString + "<br />提交人：" + content.getUser().getFirstName() + content.getUser().getLastName() + "</p>\n" +
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
                "\t                          问题描述：\n" +
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
                "\t      <a href=\"#\" style=\"color: #bcc9dd; text-decoration:none;\">关于我们</a>\n" +
                "\t      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" +
                "\t      <a href=\"#\" style=\"color: #bcc9dd; text-decoration:none;\">我们的团队</a>\n" +
                "\t      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" +
                "\t      <a href=\"#\" style=\"color: #bcc9dd; text-decoration:none;\">问题反馈系统</a>\n" +
                "\t      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" +
                "\t      <a href=\"#\" style=\"color: #bcc9dd; text-decoration:none;\">联系我们</a>\n" +
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

        // 描述数据关系
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text);
        mm.addBodyPart(image1);
        mm.addBodyPart(image2);
        mm.addBodyPart(image3);
        mm.addBodyPart(image4);
        mm.setSubType("related");

        //设置到消息中，保存修改
        message.setContent(mm);
        message.saveChanges();
////                .withEmbeddedImage("img", imageByteArray, "image/png")
//                .withEmbeddedImage("arrow_white", new FileDataSource(basePath + "/static/images/arrow_white.png"))
//                .withEmbeddedImage("features_icon1", new FileDataSource(basePath + "/static/images/features_icon1.png"))
//                .withEmbeddedImage("features_icon2", new FileDataSource(basePath + "/static/images/features_icon2.png"))
//                .withEmbeddedImage("small_img1", new FileDataSource(basePath + "/static/images/small_img1.jpg"))
        //邮件的标题
        message.setSubject(subject);
        //邮件的文本内容
        //message.setContent("你好啊！", "text/html;charset=UTF-8");



        try {
            //5、发送邮件
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
        prop.setProperty("mail.host", host); //// 设置QQ邮件服务器
        prop.setProperty("mail.transport.protocol", protocol); // 邮件发送协议
        prop.setProperty("mail.smtp.auth", "true"); // 需要验证用户名密码

        // 关于QQ邮箱，还要设置SSL加密，加上以下代码即可
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);

        //使用JavaMail发送邮件的5个步骤

        //创建定义整个应用程序所需的环境信息的 Session 对象

        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                //发件人邮件用户名、授权码
                return new PasswordAuthentication(userName, password);
            }
        });


        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);

        //2、通过session得到transport对象
        Transport ts = session.getTransport();

        //3、使用邮箱的用户名和授权码连上邮件服务器
        ts.connect(host, userName, password);

        //4、创建邮件

        //创建邮件对象
        MimeMessage message = new MimeMessage(session);

        //指明邮件的发件人
        message.setFrom(new InternetAddress(userName,emailName, "UTF-8"));

        //指明邮件的收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        // 准备图片数据
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
        // 准备正文数据
        MimeBodyPart text = new MimeBodyPart();
        text.setContent("" +
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> \n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                " <head>\n" +
                "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "  <title>会议室系统提醒你查收</title>\n" +
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
                "\t\t\t\t\t  <span style=\"font-weight: 500; letter-spacing:1px;\">会议室系统提醒你查收</span>\n" +
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
                "\t\t\t\t\t\t              <a href=\"http://10.95.2.26:8080/erp/main#\" style=\"color: #fff; font-family: 'Raleway', arial; font-size: 18px; text-decoration:none; font-weight:500; letter-spacing:.5px;\">点进进入\n" +
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
                "\t\t                        <p style=\"color: #8b8b8b; font-family: 'Raleway', arial; font-weight: 400; font-size: 14px; line-height:26px;\">公园名称：" + huiyishi.getPark().getParkName() + "<br />提交人：" + huiyishi.getUserID().getAllName() + "<br />备注：" + huiyishi.getRemark1() + "</p>\n" +
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
                "\t                        <p style=\"color: #8b8b8b; font-family: 'Raleway', arial; font-weight: 400; font-size: 14px; line-height:26px;\">提交时间：" + dateString + "<br />ID：" + huiyishi.getHysID() + "</p>\n" +
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
                "\t                          会议室描述：\n" +
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
                "\t      <a href=\"#\" style=\"color: #bcc9dd; text-decoration:none;\">关于我们</a>\n" +
                "\t      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" +
                "\t      <a href=\"#\" style=\"color: #bcc9dd; text-decoration:none;\">我们的团队</a>\n" +
                "\t      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" +
                "\t      <a href=\"#\" style=\"color: #bcc9dd; text-decoration:none;\">问题反馈系统</a>\n" +
                "\t      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" +
                "\t      <a href=\"#\" style=\"color: #bcc9dd; text-decoration:none;\">联系我们</a>\n" +
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

        // 描述数据关系
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text);
        mm.addBodyPart(image1);
        mm.addBodyPart(image2);
        mm.addBodyPart(image3);
        mm.addBodyPart(image4);
        mm.setSubType("related");

        //设置到消息中，保存修改
        message.setContent(mm);
        message.saveChanges();
////                .withEmbeddedImage("img", imageByteArray, "image/png")
//                .withEmbeddedImage("arrow_white", new FileDataSource(basePath + "/static/images/arrow_white.png"))
//                .withEmbeddedImage("features_icon1", new FileDataSource(basePath + "/static/images/features_icon1.png"))
//                .withEmbeddedImage("features_icon2", new FileDataSource(basePath + "/static/images/features_icon2.png"))
//                .withEmbeddedImage("small_img1", new FileDataSource(basePath + "/static/images/small_img1.jpg"))
        //邮件的标题
        message.setSubject(subject);
        //邮件的文本内容
        //message.setContent("你好啊！", "text/html;charset=UTF-8");



        try {
            //5、发送邮件
            ts.sendMessage(message, message.getAllRecipients());
        } catch (MailException e) {
            return MailServiceException.SEND_ERROR;
        }finally {
            ts.close();
        }
        return MailServiceException.SEND_SUCCESS;
    }
}
