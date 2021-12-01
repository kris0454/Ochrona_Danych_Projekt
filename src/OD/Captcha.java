package OD;

//to do przetestowanie captchy z javaFX
public class Captcha {
    static  nl.captcha.Captcha GenerateCaptcha() {//generowanie captchy do dostosowania później z javaFX
        nl.captcha.Captcha captcha = new nl.captcha.Captcha.Builder(200, 50)
                .addText()
                .build();
        //System.out.println(captcha.getAnswer());
        return captcha;
    }
    static Boolean CaptchaTest(String answer,nl.captcha.Captcha captcha) {//sprawdzanie czy captcha poprawnie
        if (captcha.isCorrect(answer)) {
            System.out.println("Poprawnie");
            return true;
        } else {
            System.out.println("Błędnie");
            return false;
        }
    }
}
