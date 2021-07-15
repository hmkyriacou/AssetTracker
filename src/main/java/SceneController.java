import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import jdk.nashorn.internal.IntDeque;

import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Objects;
import java.util.Stack;

public class SceneController {
    private Stack<String> scenes = new Stack<>();

    protected void changeSceneTo(String scene){
        scenes.push(scene);
        String path = scene + ".fxml";
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
            App.getPrimaryStage().getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    protected void back(){
        String scene = "HomePage";
        try{
            scenes.pop();
            scene = scenes.pop();
        } catch (EmptyStackException ignored){}
        changeSceneTo(scene);
    }
}
