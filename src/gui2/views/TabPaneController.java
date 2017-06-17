package gui2.views;

import gui2.MainApp;
import gui2.models.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class TabPaneController {

    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, Double> markColumn;
    @FXML private TableColumn<Student, String> firstNameColumn;
    @FXML private TableColumn<Student, String> lastNameColumn;
    @FXML private TableColumn<Student, Integer> ageColumn;

    private MainApp mainApp;

    public TabPaneController() {}

    @FXML
    public void initialize() {
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        markColumn.setCellValueFactory(new PropertyValueFactory<>("mark"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<Student, Integer>("age"));
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        studentTable.setItems(mainApp.getStudentsData());
    }

    public void addStudentAction() {
        mainApp.newStudent();
    }

    public void deleteStudentAction() {
        int selectedIndex = studentTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            studentTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Student Selected");
            alert.setContentText("Please select a student in the table.");

            alert.showAndWait();
        }
    }
}
