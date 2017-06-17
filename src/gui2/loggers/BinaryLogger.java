package gui2.loggers;

import gui2.models.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BinaryLogger implements Logger, Closeable
{
    private String fileName;
    private DataOutputStream dataOutputStream = null;
    private DataInputStream dataInputStream = null;

    public BinaryLogger(String name )
    {
        fileName = name;
        try
        {
            dataOutputStream = new DataOutputStream( new FileOutputStream( fileName, true ) );
            dataInputStream = new DataInputStream( new FileInputStream( fileName ) );
        }
        catch ( FileNotFoundException e ) {e.printStackTrace();}
    }

    @Override
    public void log( String status, Student student )
    {
        LoggedStudent l_student = new LoggedStudent()
        {{
            this.setMark( student.getMark() );
            this.setFirstName( student.getFirstName() );
            this.setLastName( student.getLastName() );
            this.setAge( student.getAge() );
            if ( status.equals( "ADDED" ) )
                this.setStatus( Status.ADDED );
            else
                this.setStatus( Status.REMOVED );
        }};

        save( l_student );
//        List<LoggedStudent> list = listStudents();
//        for ( int i = 0; i < list.size(); i++ )
//            System.out.println( list.get( i ).getMark()
//                    + " " + list.get( i ).getFirstName()
//                    + " " + list.get( i ).getLastName()
//                    + " " + list.get( i ).getAge()
//                    + " " + list.get( i ).getStatus()
//                    + " " + list.get( i ).getUnixTime() );
    }

    private void save( LoggedStudent l_st )
    {
        try
        {
            dataOutputStream.writeDouble( l_st.getMark() );
            dataOutputStream.writeUTF( l_st.getFirstName() );
            dataOutputStream.writeUTF( l_st.getLastName() );
            dataOutputStream.writeInt( l_st.getAge() );
            dataOutputStream.writeUTF( String.valueOf( l_st.getStatus() ) );
            dataOutputStream.writeLong( l_st.getUnixTime() );
        }
        catch ( IOException e ) {e.printStackTrace();}
    }

    public List<LoggedStudent> listStudents()
    {
        List<LoggedStudent> loggedStudentsList = new ArrayList<>();

        while ( true )
        {
            try
            {
                Status st;
                if ( dataInputStream.readUTF().equals( "ADDED" ) ) st = Status.ADDED;
                    else st = Status.REMOVED;

                loggedStudentsList.add( new LoggedStudent()
                {{
                    setMark( dataInputStream.readDouble() );
                    setFirstName( dataInputStream.readUTF() );
                    setLastName( dataInputStream.readUTF() );
                    setAge( dataInputStream.readInt() );
                    setStatus( st );
                    setUnixTime( dataInputStream.readLong() );
                }} );
            }
            catch ( EOFException e ) {break;}
            catch ( IOException e ) {e.printStackTrace();}
        }

        return loggedStudentsList;
    }


    @Override
    public void close() throws IOException
    {
        dataOutputStream.close();
        dataInputStream.close();
    }

}
