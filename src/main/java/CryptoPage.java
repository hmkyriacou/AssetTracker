import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.opencsv.CSVWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class CryptoPage extends SceneController{

    @FXML
    private ScrollPane ListScrollPane;

    @FXML
    private VBox DataVBox;

    @FXML
    private Label valueLabel;

    @FXML
    private Label numAssetsLabel;

    @FXML
    private Label costLabel;

    @FXML
    private Label profitLossLabel;

    @FXML
    private JFXButton backButton;

    @FXML
    private StackPane dialogPane;

    public void initialize(){
        List<AssetObject> cryptoAssets = App.getCryptoAssets();
        VBox assetList = new VBox();
        assetList.setSpacing(5);
        HBox newAsset;
        for (AssetObject c : cryptoAssets){
            newAsset = new HBox();
            Label as = new Label("Asset: " + c.getAssetName());
            Label am = new Label("Amount: " + c.getAmount());
            Label co = new Label("Cost: " + c.getPurchaseValue());
            Label pu = new Label("Purchase Date: " + c.getPurchaseDate());

            as.setFont(new Font(15));
            am.setFont(new Font(15));
            co.setFont(new Font(15));
            pu.setFont(new Font(15));

            newAsset.getChildren().addAll(
                    as,
                    new Separator(Orientation.VERTICAL),
                    am,
                    new Separator(Orientation.VERTICAL),
                    co,
                    new Separator(Orientation.VERTICAL),
                    pu);
            newAsset.setSpacing(3);
            newAsset.setStyle("-fx-border-color: black");
            VBox.setMargin(newAsset, new Insets(5,5,5,5));
            assetList.getChildren().add(newAsset);
        }

        ListScrollPane.setContent(assetList);

        numAssetsLabel.setText(Integer.toString(cryptoAssets.size()));

        final double[] sum = {0};
        cryptoAssets.forEach(x -> sum[0] += x.getPurchaseValue());
        costLabel.setText(Double.toString(sum[0]));
        Double cost = sum[0];

        sum[0] = 0;
        cryptoAssets.forEach(x -> sum[0] += x.getCurrentValue());
        valueLabel.setText(Double.toString(sum[0]));
        Double value = sum[0];

        sum[0] = 0;
        cryptoAssets.forEach(x -> sum[0] += x.getCurrentPL());
        Double curPL = sum[0];
        System.out.println(curPL);

        profitLossLabel.setText(Double.toString(value-cost));

    }

    public void goBack(ActionEvent actionEvent) {
        super.back();
    }

    public void addAsset(ActionEvent actionEvent) {
        dialogPane.toFront();
        dialogPane.setDisable(false);
        JFXDialogLayout message = new JFXDialogLayout();
        message.setHeading(new Text("Add Asset"));

        /* ---------- Creating the Form ------------- */

        AnchorPane anc = new AnchorPane();
        GridPane newAssetForm = new GridPane();

        newAssetForm.setVgap(10);

        Label l = new Label("Asset Name: ");
        JFXTextField name = new JFXTextField();

        newAssetForm.add(l, 0, 0);
        newAssetForm.add(name, 1, 0);

        GridPane.setMargin(name, new Insets(0, 0, 0, 20));

        Label l2 = new Label("Amount: ");
        JFXTextField amount = new JFXTextField();

        newAssetForm.add(l2, 0, 1);
        newAssetForm.add(amount, 1, 1);

        GridPane.setMargin(amount, new Insets(0, 0, 0, 20));

        Label l3 = new Label("Cost: ");
        JFXTextField cost = new JFXTextField();

        newAssetForm.add(l3, 0, 2);
        newAssetForm.add(cost, 1, 2);

        GridPane.setMargin(cost, new Insets(0, 0, 0, 20));

        Label l4 = new Label("Date (yyyy-MM-dd): ");
        JFXTextField date = new JFXTextField();

        newAssetForm.add(l4, 0, 3);
        newAssetForm.add(date, 1, 3);

        GridPane.setMargin(date, new Insets(0, 0, 0, 20));

        anc.getChildren().add(newAssetForm);
        /* ---------- -------------- ------------- */

        message.setBody(anc);

        JFXDialog dialog = new JFXDialog(dialogPane, message, JFXDialog.DialogTransition.CENTER);

        JFXButton exit = new JFXButton("Submit");
        exit.setOnAction(event -> {

            addEntry(new CryptoAsset(name.getText(), Double.parseDouble(cost.getText()), Double.parseDouble(amount.getText()), LocalDate.parse(date.getText())));

            dialog.close();

            refresh(new ActionEvent());

        });
        dialog.setOnDialogClosed(event -> {
            dialogPane.setDisable(true);
            dialogPane.toBack();
        });

        message.setActions(exit);
        dialog.show();
    }

    private void addEntry(CryptoAsset cryptoAsset) {
        App.getCryptoAssets().add(cryptoAsset);
        cryptoAsset.getData();
        CSVWriter writer = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter("Data/finInfo_simple_test.txt", true);
            writer = new CSVWriter(fw, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

            String[] input = {cryptoAsset.getAssetName(), String.format("%.10f", cryptoAsset.getAmount()), (cryptoAsset.getPurchaseValue()).toString(), (cryptoAsset.getPurchaseDate()).toString()};
            writer.writeNext(input);
        } catch (IOException e) {
            e.printStackTrace();
        }



        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refresh(ActionEvent actionEvent) {
        initialize();
    }
}
