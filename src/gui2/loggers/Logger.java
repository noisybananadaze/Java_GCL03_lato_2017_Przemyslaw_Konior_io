package gui2.loggers;

import gui2.models.Student;

import java.io.Closeable;
import java.io.IOException;


public interface Logger extends Closeable
{
    void log(String status, Student student);
//    void close() throws IOException;
}



