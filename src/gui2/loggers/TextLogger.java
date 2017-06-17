package gui2.loggers;

import gui2.models.Student;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TextLogger implements Logger
{
    private String fileName;
    BufferedWriter textLog;

    public TextLogger( String name )
    {
        fileName = name;
        try {textLog = new BufferedWriter( new OutputStreamWriter( new FileOutputStream( fileName, true ), StandardCharsets.UTF_8 ) );}
        catch ( IOException e ) {e.printStackTrace();}
    }

    @Override
    public void log( String status, Student student )
    {
        SimpleDateFormat dateformat = new SimpleDateFormat( "dd.MM.yyyy_HH.mm.ss.SS" );
        try
        {
            textLog.write( dateformat.format( new Date() ) + " [" + status + "] " + student.getMark() + " " + student.getFirstName() + " " + student.getLastName() + " " + student.getAge() );
            textLog.newLine();
            textLog.flush();
        }
        catch ( IOException e ) {e.printStackTrace();}
    }

    @Override
    public void close() throws IOException
    {
        textLog.close();
    }
}
