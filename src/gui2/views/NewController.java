package gui2.views;

import gui2.MainApp;
import gui2.models.Student;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewController {

    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField mark;
    @FXML private TextField age;


    private Stage dialogStage;
//    private Student student = new Student(3.0, "asd1", "asd3", 99);
    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void cancelAction() {
        dialogStage.close();
    }

    public void okAction() {
        Student student = new Student(Double.parseDouble(mark.getText()), firstName.getText(), lastName.getText(), Integer.parseInt(age.getText()));
        mainApp.studentsData.add(student);
        mainApp.addLog("ADDED", student);
        dialogStage.close();
    }
}
