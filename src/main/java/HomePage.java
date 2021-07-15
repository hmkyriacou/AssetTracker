import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class HomePage extends SceneController {

    @FXML
    private JFXButton toCrypto;

    @FXML
    private JFXButton toStocks;

    @FXML
    private JFXButton toTotals;

    public void goToCrypto(ActionEvent actionEvent) {
        super.changeSceneTo("CryptoPage");
    }
}
