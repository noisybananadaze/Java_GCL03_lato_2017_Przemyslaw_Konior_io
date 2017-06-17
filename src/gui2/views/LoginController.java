package gui2.views;

import gui2.MainApp;
import gui2.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

public class LoginController {

    @FXML private TextField loginUsername;
    @FXML private PasswordField loginPassword;
    @FXML private TextField registerUsername;
    @FXML private PasswordField registerPassword;
//    private boolean loginSuccess = false;
    private MainApp mainApp;
    private Stage loginStage;


    private String fileName = "config.properties";
    private Properties prop = new Properties();


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setLoginStage(Stage stage) {
        this.loginStage = stage;
    }

    public void registerAction() {
//        mainApp.users.add(new User(registerUsername.getText(), registerPassword.getText()));
//        mainApp.user.setUsername(registerUsername.getText());
//        mainApp.user.setPassword(registerPassword.getText());

        String username = registerUsername.getText();
        String password = registerPassword.getText();

        FileWriter output = null;
        try
        {
            output = new FileWriter( fileName, true );
            prop.clear();

            if ( !prop.contains( username ) )
            {
                String hashedPassword = hashMD5( password );

                prop.setProperty( username, username );
                prop.setProperty( username + "password", hashedPassword );

                prop.store( output, null );
//                labelNewError.setText( "" );
//                fieldNewUsername.clear();
//                fieldNewPassword.clear();
            }
//                labelNewError.setText( "This username is already used!" );

        }
        catch ( IOException io ) {io.printStackTrace();}
        finally
        {
            if ( output != null )
            {
                try
                {
                    output.close();
                }
                catch ( IOException e ) {e.printStackTrace();}
            }
        }
    }

    public void loginAction() {
//        for (User user : mainApp.users) {
//            if (user.getUsername().equals(loginUsername.getText()) && user.getPassword().equals(loginPassword.getText())) {
//                mainApp.loginSuccess = true;
//                loginStage.close();
//            }
//        }

//        if (mainApp.user.getUsername().equals(loginUsername.getText()) && mainApp.user.getPassword().equals(loginPassword.getText())) {
//            mainApp.loginSuccess = true;
//            loginStage.close();
//        }

        String username = loginUsername.getText();
        String password = loginPassword.getText();

        InputStream input = null;

        try
        {
            File file = new File( fileName );
            input = new FileInputStream( fileName );

            if ( file.isFile() )
            {
                prop.load( input );

                if ( prop.containsValue( username ) )
                {
                    String file_password = prop.getProperty( username + "password" );
                    String hashedPassword = hashMD5( password );

                    if ( file_password.equals( hashedPassword ) )
                    {
//                        ( (Node) event.getSource() ).getScene().getWindow().hide();
//                        Parent parent = FXMLLoader.load( getClass().getResource( "/view/MainView.fxml" ) );
//                        Stage stage = new Stage();
//                        Scene scene = new Scene( parent );
//                        stage.setScene( scene );
//                        stage.setTitle( "GUI MVC" );
//                        stage.show();
                        mainApp.loginSuccess = true;
                        loginStage.close();
                    }
                }
            }
        }
        catch ( IOException ex ) {ex.printStackTrace();}
        finally
        {
            if ( input != null )
            {
                try
                {
                    input.close();
                }
                catch ( IOException e ) {e.printStackTrace();}
            }
        }
    }


    public String hashMD5( String passwordToHash )
    {
        String generatedPassword = null;
        try
        {
            MessageDigest md = MessageDigest.getInstance( "MD5" );
            md.update( passwordToHash.getBytes() );
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for ( int i = 0; i < bytes.length; i++ )
            {
                sb.append( Integer.toString( ( bytes[i] & 0xff ) + 0x100, 16 ).substring( 1 ) );
            }
            generatedPassword = sb.toString();
        }
        catch ( NoSuchAlgorithmException e ) {e.printStackTrace();}

        return generatedPassword;
    }
}
