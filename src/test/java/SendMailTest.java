import org.junit.Test;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

public class SendMailTest {
    @Test
    public void test01() {
        Email email = EmailBuilder.startingBlank()
                .from("hytch(znxuchao)", "znxuchao@hytch.com")
                .to("韩美娟", "1559190162@qq.com")
                .withSubject("记得双击么么哒！")
                .withPlainText("窝窝头，一块钱四个，嘿嘿")
                .buildEmail();

        MailerBuilder
                .withSMTPServer("mail.hytch.com", 25, "znxuchao@hytch.com", "SZhq8610!!")
                .buildMailer()
                .sendMail(email);

    }
}
