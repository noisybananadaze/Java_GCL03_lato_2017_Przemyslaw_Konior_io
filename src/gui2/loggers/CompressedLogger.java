package gui2.loggers;

import gui2.models.Student;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class CompressedLogger implements Logger, Closeable
{
    private String fileName;
    private File zipName;
    private FileOutputStream zipFile;

    public CompressedLogger(String name )
    {
        fileName = name;
        zipName = new File( fileName );
        if ( !zipName.isFile() )
        {
            try
            {
                zipFile = new FileOutputStream( zipName );
                ZipOutputStream outs = new ZipOutputStream( zipFile );
                outs.closeEntry();
                outs.close();
            }
            catch ( IOException e ) {e.printStackTrace();}
        }
    }


    public static void addFilesToExistingZip( File zipFile, File file ) throws IOException
    {
        File tempFile = File.createTempFile( zipFile.getName(), null );
        tempFile.delete();

        boolean renameOk = zipFile.renameTo( tempFile );
        if ( !renameOk )
            throw new RuntimeException( "nie można było zmienić nazwy pliku " + zipFile.getAbsolutePath() + " na " + tempFile.getAbsolutePath() );

        byte[] buf = new byte[1024];

        ZipInputStream zip = new ZipInputStream( new FileInputStream( tempFile ) );
        ZipOutputStream out = new ZipOutputStream( new FileOutputStream( zipFile ) );

        ZipEntry entry = zip.getNextEntry();
        while ( entry != null )
        {
            String name = entry.getName();
            boolean notInFiles = true;
            if ( file.getName().equals( name ) )
            {
                notInFiles = false;
                break;
            }

            if ( notInFiles )
            {
                out.putNextEntry( new ZipEntry( name ) );
                int len;
                while ( ( len = zip.read( buf ) ) > 0 )
                    out.write( buf, 0, len );
            }
            entry = zip.getNextEntry();
        }
        zip.close();
        InputStream in = new FileInputStream( file );
        out.putNextEntry( new ZipEntry( file.getName() ) );

        int len;
        while ( ( len = in.read( buf ) ) > 0 )
            out.write( buf, 0, len );

        out.closeEntry();
        in.close();
        out.close();
        tempFile.delete();
    }

    @Override
    public void log( String status, Student student )
    {
        File tmpfile;
        SimpleDateFormat dateformat = new SimpleDateFormat( "dd.MM.yyyy_HH.mm.ss.SS" );
        String c_name = dateformat.format( new Date() ) + ".txt";

        TextLogger tmpTextLog = new TextLogger( c_name );
        tmpTextLog.log( status, student );
        try
        {
            tmpTextLog.close();
            tmpfile = new File( c_name );
            addFilesToExistingZip( zipName, tmpfile );
            tmpfile.delete();
        }
        catch ( IOException e ) {e.printStackTrace();}
    }

    @Override
    public void close() throws IOException
    {
        zipFile.close();
    }
}
