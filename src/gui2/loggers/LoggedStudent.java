package gui2.loggers;

import gui2.models.Student;

import java.io.Serializable;

public class LoggedStudent extends Student implements Serializable
{
    private Status status;
    private long unixTime;

    public LoggedStudent()
    {
        unixTime = System.currentTimeMillis() / 1000L;
    }

    public Status getStatus()
    {
        return status;
    }

    public void setStatus( Status status )
    {
        this.status = status;
    }

    public long getUnixTime()
    {
        return unixTime;
    }

    public void setUnixTime( long unixTime ) {this.unixTime = unixTime;}
}
