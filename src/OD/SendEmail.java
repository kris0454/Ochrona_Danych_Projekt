package OD;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class SendEmail {
    public static String SendEmail(String email) {
        // create a string of all characters
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        // create random string builder
        StringBuilder sb = new StringBuilder();

        // create an object of Random class
        Random random = new Random();

        // specify length of random string
        int length = 5;

        for(int i = 0; i < length; i++) {

            // generate random index number
            int index = random.nextInt(alphabet.length());

            // get character specified by index
            // from the string
            char randomChar = alphabet.charAt(index);

            // append the character to string builder
            sb.append(randomChar);
        }
        String kod = sb.toString();
        String host="smtp.gamil.com";
        final String user="ania.studia18@gmail.com";//"kkretkowski454@gmail.com";//change accordingly
        final String password="ix2zC&F?";//change accordingly
        final String port = "587";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, password);
                    }
                });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(email));
            message.setSubject("Projekt OD: kod");
            message.setText("Wiadomość z kodem: "+kod);//poprawienie wiadomości
            Transport.send(message);
            System.out.println("Wiadomość wysłana poprawnie.");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
        return kod;
    }
    public static boolean EmailTest(String kod,String kodemail){
        if (kod.equals(kodemail)) {
            System.out.println("Poprawnie");
            return true;
        } else {
            System.out.println("Błędnie");
            return false;
        }
    }
}

