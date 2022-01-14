package OD;

import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.gimpy.FishEyeGimpyRenderer;
import nl.captcha.noise.StraightLineNoiseProducer;

//to do przetestowanie captchy z javaFX
public class Captcha {
    static  nl.captcha.Captcha GenerateCaptcha() {
        nl.captcha.Captcha captcha = new nl.captcha.Captcha.Builder(200, 50)
                .addText()
                .addBackground(new GradiatedBackgroundProducer())
                .addNoise(new StraightLineNoiseProducer())
                .gimp(new FishEyeGimpyRenderer())
                .addBorder()
                .build();
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
