package OD;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class FxmlController { // implements Initializable
    private Captcha captcha = new Captcha();
    private nl.captcha.Captcha captcha1 = captcha.GenerateCaptcha();
    private String email = "";
    private String code = "";
    private boolean isEmailCorrect = false;
    private boolean isCaptchaCorrect = false;
    private boolean isCodeCorrect = false;
    private long startTime = 0;
    private long endTime = 0;

    @FXML
    private AnchorPane anchorPane;

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
            errorText.setFill(Color.WHITE);
        } else {
            errorText.setText("Nieprawidłowy adres e-mail.");
            errorText.setFill(Color.RED);
            return;
        }
    }

    @FXML
    void sendEmail(ActionEvent event) {
        //errorText.setText(" ");
        if(isEmailCorrect){
            String captcha = captchaField.getText();
            System.out.println(captcha1);
            isCaptchaCorrect = Captcha.CaptchaTest(captcha, captcha1);
            if(isCaptchaCorrect){
                SendEmail se = new SendEmail();
                code = se.SendEmail(email);
                System.out.println("Captcha poprawnie. Kod został wysałny na twój adres e-mail. "+code);
                errorText.setText("Captcha wypełniona poprawnie. Kod został wysłany na podany adres e-mail.");
                startTime = System.currentTimeMillis();
                errorText.setFill(Color.WHITE);
            } else {
                errorText.setText("Nieprawidłowo przepisany ciąg z captchy.");
                errorText.setFill(Color.RED);
                return;
            }
        } else {
            errorText.setText("Nieprawidłowy adres e-mail.");
            errorText.setFill(Color.RED);
            return;
        }
    }

    @FXML
    void loginButton(ActionEvent event) throws IOException {
        endTime = System.currentTimeMillis();
        String usersCode = codeField.getText();
        isCodeCorrect = SendEmail.EmailTest(code, usersCode);
        if(isEmailCorrect & isCaptchaCorrect){

            if(endTime - startTime < 300000){//limit czasu
            if(isCodeCorrect){
                System.out.println("Brawo. Udało Ci się zalogować.");
                errorText.setText("Zalogowałeś się!"); // Brawo udało Ci się!
                System.out.println("Czas: "+((endTime - startTime)/60000));
                errorText.setFill(Color.WHITE);
                FXMLLoader fxmlLoader = new FXMLLoader(NewMain.class.getResource("resources/od-fxml2.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 641, 401);
                Stage stage = (Stage) anchorPane.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
                } else {
                errorText.setText("Nieprawidłowy kod.");
                errorText.setFill(Color.RED);
                return;
            }}else{
                errorText.setText("Przekroczono czas wykorzystania kodu.");
                errorText.setFill(Color.RED);
                System.out.println("Czas: "+((endTime - startTime)/60000));
            }
        } else {
            if(isEmailCorrect){
                errorText.setText("Nieprawidłowo przepisany ciąg z captchy.");
                errorText.setFill(Color.RED);
                return;
            } else {
                errorText.setText("Nieprawidłowy adres e-mail.");
                errorText.setFill(Color.RED);
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
