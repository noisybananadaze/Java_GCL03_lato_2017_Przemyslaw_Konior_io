package gui2.views;

import gui2.Storage.StudentsStorage;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class StorageController
{
    private StudentsStorage storage;

    @FXML TextField fieldImage, fieldLoadImage, fieldDeleteImage;

    public void init()
    {
        storage = new StudentsStorage( "studentsstorage.bin" );
    }

    @FXML
    private void addImage()
    {
        try {storage.addImage( fieldImage.getText() );}
        catch ( IOException e ) {e.printStackTrace();}
    }

    @FXML
    private void loadImage()
    {
        try
        {
            int whichOne = Integer.parseInt( fieldLoadImage.getText() );
            byte[] image = storage.readImage( whichOne );
            DataOutputStream dos_saveimage = new DataOutputStream( new FileOutputStream( "readedimages\\" + whichOne + ".jpg" ) );
            dos_saveimage.write( image );
            dos_saveimage.close();
        }
        catch ( IOException e ) {e.printStackTrace();}
    }

    @FXML
    private void deleteImage()
    {
        try
        {
            int whichOne = Integer.parseInt( fieldDeleteImage.getText() );
            storage.deleteImage( whichOne );
        }
        catch ( IOException e ) {e.printStackTrace();}
    }

    @FXML
    private void loadAllImage()
    {
        try {storage.saveAllImage();}
        catch ( IOException e ) {e.printStackTrace();}
    }
}
