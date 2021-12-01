package OD;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	System.out.print("Podaj adres e-mail: ");
	Scanner odpowiedz = new Scanner(System.in);
	String email = odpowiedz.next();
	System.out.println("Potwierdz, że nie jesteś robotem. ");
	Captcha captcha = new Captcha();
	nl.captcha.Captcha captcha1 = captcha.GenerateCaptcha();
	System.out.println("Captcha: "+captcha1.getAnswer());

	boolean wynik = false;
	while (wynik == false){
        System.out.print("Twoja odpowiedz: ");
        String Answer = odpowiedz.next();
        wynik = captcha.CaptchaTest(Answer,captcha1);
    }
    SendEmail se = new SendEmail();
	String kod = se.SendEmail(email);
	System.out.println("Captcha poprawnie. Kod został wysałny na twój adres e-mail. "+kod);
	System.out.print("Podaj kod z wiadomości: ");
    boolean wynikkod = false;
    while(wynikkod == false){
        System.out.print("Twoja odpowiedz: ");
        String kodEmail = odpowiedz.next();
        wynikkod = se.EmailTest(kod,kodEmail);
    }
	System.out.println("Brawo. Udało Ci się zalogować.");
    }
}
