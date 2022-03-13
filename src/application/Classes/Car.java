package application.Classes;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Car
{
    private SimpleStringProperty name, color;
    private SimpleIntegerProperty year;
    private SimpleDoubleProperty mileage;
    private SimpleStringProperty id;
    private SimpleDoubleProperty price;

    public Car(String name, String color, int year, double mileage, String id, double price)
    {
        this.name = new SimpleStringProperty(name);
        this.color = new SimpleStringProperty(color);
        this.year = new SimpleIntegerProperty(year);
        this.mileage = new SimpleDoubleProperty(mileage);
        this.id = new SimpleStringProperty(id);
        this.price = new SimpleDoubleProperty(price);
    }
    
    public void setName(String newName) {
    	this.name = new SimpleStringProperty(newName);
    }
    
    public String getName() {
    	return name.get();
    }
    
    public void setColor(String newColor) {
    	this.color = new SimpleStringProperty(newColor);
    }
    
    public String getColor() {
    	return color.get();
    }
    
    public void setYear(int newYear) {
    	this.year = new SimpleIntegerProperty(newYear);
    }
    
    public int getYear() {
    	return year.get();
    }
    
    public void setMileage(double newMileage) {
    	this.mileage = new SimpleDoubleProperty(newMileage);
    }
    
    public double getMileage() {
    	return mileage.get();
    }
    
    public void setId(String newId) {
    	this.id = new SimpleStringProperty(newId);
    }
    
    public String getId() {
    	return id.get();
    }
    
    public void setPrice(double newPrice) {
    	this.price = new SimpleDoubleProperty(newPrice);
    }
    
    public double getPrice() {
    	return price.get();
    }

	@Override
    public String toString() {
        return "name: " + this.name +
                " color: " + this.color + " year: " + this.year +
                " mileage: " + this.mileage + " id: " + this.id;
    }
}

