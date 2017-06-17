package gui2;

import gui2.loggers.*;
import gui2.models.Student;
import gui2.models.User;
import gui2.views.LoginController;
import gui2.views.NewController;
import gui2.views.TabPaneController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    public ObservableList<Student> studentsData = FXCollections.observableArrayList();
    public List<User> users = new ArrayList<>();
    public User user = new User("asd", "asd");

    public boolean loginSuccess = false;





    public MainApp() {
        studentsData.add(new Student(5.0, "jan1", "kowalski1", 21));
        studentsData.add(new Student(5.0, "jan2", "kowalski2", 21));
        studentsData.add(new Student(5.0, "jan3", "kowalski3", 21));
        studentsData.add(new Student(5.0, "jan4", "kowalski4", 21));

        users.add(new User("asd", "asd"));
        users.add(new User("test2", "test2"));
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("JavaFX gui");

        initLogin();
        if (loginSuccess) {
            initRootLayout();
            initTabPane();
        }
    }

    private void initLogin() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/login.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage loginStage = new Stage();
            loginStage.setTitle("Login/Register");
            loginStage.setScene(new Scene(page));
            LoginController controller = loader.getController();
            controller.setLoginStage(loginStage);
            controller.setMainApp(this);
            loginStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/RootLayout.fxml"));
            this.rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(this.rootLayout);
            this.primaryStage.setScene(scene);
            this.primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initTabPane() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/tabPane.fxml"));
            AnchorPane tabPane = (AnchorPane) loader.load();

            this.rootLayout.setCenter(tabPane);

            // Give the controller access to the main app.
            TabPaneController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newStudent() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("views/new.fxml"));
        AnchorPane page = null;
        try {
            page = (AnchorPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage dialogStage = new Stage();
        dialogStage.setTitle("New Student");
        dialogStage.setScene(new Scene(page));
        NewController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setMainApp(this);
        dialogStage.showAndWait();
    }


//    private TextLogger textLogger = new TextLogger("text_logger.txt");
    private CompressedLogger compressedLogger = new CompressedLogger("compressed_logger.zip");
    private Logger[] loggers = new Logger[]{
            new TextLogger("text_logger.txt"),
//            compressedLogger,
//            new TextLogger("text_logger.txt"),
//            new CompressedLogger("compressed_logger.zip"),
//            new SerializedLogger("serialized_logger.bin"),
//            new BinaryLogger("binary_logger.bin")
    };


    public void addLog(String status, Student student) {
        for (Logger l : loggers) {
            l.log(status, student);
        }
    }


    @Override
    public void stop(){
        System.out.println("Stage is closing");
        for (Logger l : loggers) {
            try {
                l.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//
//        try {
//            textLogger.close();
////            compressedLogger.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }


    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    public ObservableList<Student> getStudentsData() {
        return this.studentsData;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
