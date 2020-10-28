import org.junit.Test;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

public class SendMailTest {
    @Test
    public void test01() {
        Email email = EmailBuilder.startingBlank()
                .from("hytch(znxuchao)", "znxuchao@hytch.com")
                .to("������", "1559190162@qq.com")
                .withSubject("�ǵ�˫��ôô�գ�")
                .withPlainText("����ͷ��һ��Ǯ�ĸ����ٺ�")
                .buildEmail();

        MailerBuilder
                .withSMTPServer("mail.hytch.com", 25, "znxuchao@hytch.com", "SZhq8610!!")
                .buildMailer()
                .sendMail(email);

    }
}
