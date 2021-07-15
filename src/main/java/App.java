import com.opencsv.bean.CsvToBeanBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class App extends Application {

    private static Stage primaryStage;
    private static Map<String, List<AssetObject>> assetList = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {

        App.primaryStage = primaryStage;

        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HomePage.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(800);
        primaryStage.setMinWidth(1280);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setTitle("Asset Tracker");
        primaryStage.show();

        createAssetObjects();

    }

    private void createAssetObjects(){
        // Crypto List
        List<AssetObject> cryptoAssets = null;
        try {
            cryptoAssets = new CsvToBeanBuilder(new FileReader("Data/finInfo_simple.txt")).withType(CryptoAsset.class).build().parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        cryptoAssets.forEach(x -> x.getData());
        assetList.put("cryptoAssets", cryptoAssets);

    }

    public static Stage getPrimaryStage(){
        return primaryStage;
    }

    public static List<AssetObject> getCryptoAssets() {
        return assetList.get("cryptoAssets");
    }
}
