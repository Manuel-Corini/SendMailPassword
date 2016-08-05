/*

 * If you don't go on this site:
 * https://www.google.com/settings/security/lesssecureapps for active 'app less secure'
 * you can't send email by your google account, don't forget to enter in the account
 * In this example you can decide to send even an attached

 */
package sendmailpassword;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class SendMailPassword {

    public static void main(String[] args) {

        final String username = "email@gmail.com"; // Your email
        final String password = "Password";          // Password of your account

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("guglielmo.marconi@gmail.com")); //Sender
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("nikola.tesla@gmail.com"));            //Receiver
            message.setSubject("Testing Subject");
            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();
            // Now set the actual message
            messageBodyPart.setText("This is message body");
            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = "path\\name.pdf";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Email sent !!!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
