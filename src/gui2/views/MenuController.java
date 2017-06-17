package gui2.views;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class MenuController {

    @FXML
    public void closeAction() {
        Platform.exit();
    }

    @FXML
    public void aboutAction() {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("About");
        info.setHeaderText(null);
        info.setContentText("Przemysław Konior lab fxml");
        info.showAndWait();
    }

    @FXML
    public void keyPressed(KeyEvent key) {
        if (key.isControlDown() && key.getCode().equals(KeyCode.C)) {
            Platform.exit();
        }
    }
}
