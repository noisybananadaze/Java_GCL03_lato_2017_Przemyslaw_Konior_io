package gui2.Storage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class StudentsStorage
{
    private String fileName;
    private String storageInfoFile;
    private int ile_image;
    private int[] size;
    private int[] place;
    private boolean storagefull;

    public StudentsStorage(String fname )
    {
        fileName = fname;
        storageInfoFile = "storageinfo.bin";
        ile_image = 0;
        storagefull = false;
        try {loadInfoAboutImages();}
        catch ( IOException e ) {e.printStackTrace();}
    }

    public void addImage( String imagesource ) throws IOException
    {
        loadInfoAboutImages();

        if ( !storagefull )
        {
            //zamiana zdjęcia na tablicę bajtów
            BufferedImage image = ImageIO.read( new File( imagesource ) );
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ImageIO.write( image, "jpg", b );
            byte[] imageByteArray = b.toByteArray();

            //zapis informacji o zdjęciach z pliku storage
            size[ile_image] = imageByteArray.length;
            if ( ile_image != 0 ) place[ile_image] = place[ile_image - 1] + size[ile_image - 1];
            ile_image++;
            saveInfo();

            //zapis zdjęcia do pliku storage
            FileOutputStream fos = new FileOutputStream( fileName, true );
            DataOutputStream dos = new DataOutputStream( fos );
            dos.write( imageByteArray );
            dos.close();
        } else
            System.out.println( "Magazyn zdjęć pełny!" );

    }

    public void loadInfoAboutImages() throws IOException
    {
        //jeśli istnieje plik z informacjami o zdjęciach z pliku storage, to ładujemy z niego info
        if ( new File( storageInfoFile ).isFile() )
        {
            DataInputStream dis_info = new DataInputStream( new FileInputStream( storageInfoFile ) );
            ile_image = dis_info.readInt();
            if ( ile_image == 20 )
                storagefull = true;
            else
            {
                size = new int[ile_image + 1];
                place = new int[ile_image + 1];
                for ( int i = 0; i < ile_image; i++ )
                {
                    size[i] = dis_info.readInt();
                    place[i] = dis_info.readInt();
                }
            }
            dis_info.close();
        } else
        {
            size = new int[1];
            place = new int[1];
            place[0] = 0;
        }
    }

    public void saveInfo() throws IOException
    {
        DataOutputStream dos_info = new DataOutputStream( new FileOutputStream( storageInfoFile ) );
        dos_info.writeInt( ile_image );
        for ( int i = 0; i < ile_image; i++ )
        {
            dos_info.writeInt( size[i] );
            dos_info.writeInt( place[i] );
        }
        dos_info.close();
    }

    public byte[] readImage( int whichOne ) throws IOException
    {
        byte[] imagebytes = null;
        if ( whichOne < ile_image )
        {
            FileInputStream fis = new FileInputStream( fileName );
            DataInputStream dis = new DataInputStream( fis );

            byte[] all = new byte[dis.available()];
            imagebytes = new byte[size[whichOne]];
            dis.read( all );

            int j = 0;
            for ( int i = place[whichOne]; i < place[whichOne] + size[whichOne]; i++ )
                imagebytes[j++] = all[i];

            dis.close();
        } else
            System.out.println( "Nie ma takiego zdjęcia" );

        return imagebytes;
    }

    public void saveAllImage() throws IOException
    {
        for ( int i = 0; i < ile_image; i++ )
        {
            DataOutputStream dos_saveimage = new DataOutputStream( new FileOutputStream( "readedimages\\" + ( i + 1 ) + ".jpg" ) );
            dos_saveimage.write( readImage( i ) );
            dos_saveimage.close();
        }
    }


    public void deleteImage( int whichOne ) throws IOException
    {
        if ( new File( fileName ).isFile() && whichOne < ile_image )
        {
            //plik ze zdjęciami
            FileInputStream fis = new FileInputStream( fileName );
            DataInputStream dis = new DataInputStream( fis );

            byte[] all = new byte[dis.available()];
            byte[] imagebytes = new byte[dis.available() - size[whichOne]];
            dis.read( all );

            int j = 0;
            if ( whichOne > 0 )
                for ( int i = 0; i < place[whichOne - 1] + size[whichOne - 1]; i++ )
                    imagebytes[j++] = all[i];

            if ( whichOne != ( ile_image - 1 ) )
                for ( int i = place[whichOne + 1]; i < place[ile_image - 1] + size[ile_image - 1]; i++ )
                    imagebytes[j++] = all[i];

            dis.close();

            //plik z info
            ile_image--;
            int[] si = new int[ile_image];
            int[] pl = new int[ile_image];

            for ( int i = whichOne; i < ile_image; i++ )
            {
                if ( i == whichOne )
                    place[i + 1] = place[i];
                else
                    place[i + 1] = size[i] + place[i];
            }

            for ( int i = 0; i < whichOne; i++ )
            {
                si[i] = size[i];
                pl[i] = place[i];
            }
            for ( int i = whichOne + 1; i < ile_image + 1; i++ )
            {
                si[i - 1] = size[i];
                pl[i - 1] = place[i];
            }

            size = new int[ile_image];
            for ( int i = 0; i < ile_image; i++ )
                size[i] = si[i];

            place = new int[ile_image];
            for ( int i = 0; i < ile_image; i++ )
                place[i] = pl[i];

            saveInfo();

            //zapis wszystkich zdjęć do pliku
            FileOutputStream fos = new FileOutputStream( fileName );
            DataOutputStream dos = new DataOutputStream( fos );
            dos.write( imagebytes );
            dos.close();
        } else
            System.out.println( "Nie ma pliku ze zdjęciami, albo takiego zdjęcia." );
    }
}
