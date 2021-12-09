package OD;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Pattern;

public class FxmlController {
    private Captcha captcha = new Captcha();
    private nl.captcha.Captcha captcha1 = captcha.GenerateCaptcha();
    private String email = "";
    private String code = "";
    private boolean isEmailCorrect = false;
    private boolean isCaptchaCorrect = false;
    private boolean isCodeCorrect = false;

    @FXML
    private TextField emailField;

    @FXML
    private ImageView captchaImageView;

    @FXML
    private TextField captchaField;

    @FXML
    private TextField codeField;

    @FXML
    private Text errorText;

    @FXML
    void showCaptcha(ActionEvent event) {
        email = emailField.getText();
        isEmailCorrect = isValid(email);
        if(isEmailCorrect){
            captcha1 = captcha.GenerateCaptcha();
            Image captchaImage = SwingFXUtils.toFXImage(captcha1.getImage(), null);
            captchaImageView.setImage(captchaImage);
            errorText.setText("Prawidłowy adres e-mail. Możesz teraz wypełnić pole captchy.");
        } else {
            errorText.setText("Nieprawidłowy adres e-mail.");
            return;
        }
    }

    @FXML
    void sendEmail(ActionEvent event) {
        if(isEmailCorrect){
            String captcha = captchaField.getText();
            isCaptchaCorrect = Captcha.CaptchaTest(captcha, captcha1);
            if(isCaptchaCorrect){
                SendEmail se = new SendEmail();
                code = se.SendEmail(email);
                System.out.println("Captcha poprawnie. Kod został wysałny na twój adres e-mail. "+code);
                errorText.setText("Captcha wypełniona poprawnie. Kod został wysłany na podany adres e-mail.");
            } else {
                errorText.setText("Nieprawidłowo przepisany ciąg z captchy.");
                return;
            }
        } else {
            errorText.setText("Nieprawidłowy adres e-mail.");
            return;
        }
    }

    @FXML
    void loginButton(ActionEvent event) {
        String usersCode = codeField.getText();
        isCodeCorrect = SendEmail.EmailTest(code, usersCode);
        if(isEmailCorrect & isCaptchaCorrect){
            if(isCodeCorrect){
                System.out.println("Brawo. Udało Ci się zalogować.");
                errorText.setText("Brawo udało Ci się!");
            } else {
                errorText.setText("Nieprawidłowy kod.");
                return;
            }
        } else {
            if(isEmailCorrect){
                errorText.setText("Nieprawidłowo przepisany ciąg z captchy.");
                return;
            } else {
                errorText.setText("Nieprawidłowy adres e-mail.");
                return;
            }
        }
    }

    public static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+"[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pat.matcher(email).matches();
    }
}
