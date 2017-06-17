package gui2.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;

public class Student
{
	private DoubleProperty mark;
	private StringProperty firstName;
	private StringProperty lastName;
	private IntegerProperty age;


	public Student() {}

	public Student(double mark, String firstName, String lastName, int age) {
	    this.mark = new SimpleDoubleProperty(mark);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
	    this.age = new SimpleIntegerProperty(age);

    }

	public double getMark()
	{
		return this.mark.get();
	}
	
	public void setMark( double mark )
	{
		this.mark.set(mark);
	}

    public DoubleProperty markProperty() {
        return mark;
    }
	
	public String getFirstName()
	{
		return this.firstName.get();
	}

	public void setFirstName( String firstName )
	{
		this.firstName.set(firstName);
	}

    public StringProperty firstNameProperty() {
        return firstName;
    }

	public String getLastName()
	{
		return this.lastName.get();
	}
	
	public void setLastName( String lastName )
	{
		this.lastName.set(lastName);
	}

    public StringProperty lastNameProperty() {
        return lastName;
    }
	
	public int getAge()
	{
		return this.age.get();
	}
	
	public void setAge( int age )
	{
		this.age.set(age);
	}

    public IntegerProperty ageProperty() {
        return age;
    }
}
