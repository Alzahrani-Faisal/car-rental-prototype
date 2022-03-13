package application.Classes;

import java.util.List;

public class Customer extends User
{
    private String password;
    private List<String> carsIDs;

    public Customer(String name, String email, String password, List<String> carsIDs)
    {
        super(name, email);
        this.password = password;
        this.carsIDs = carsIDs;
    }

    // Setters & Getters

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String newName) {
        super.setName(newName);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String newEmail) {
        super.setEmail(newEmail);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public List<String> getCars() {
        return carsIDs;
    }
    
    public String printCars(List<String> cars) {
    	String val = "";
    	if(cars.size() == 1 && cars.get(0).equals("null")) {
    		val = cars.get(0);
    		return val;
    	}
    	else if(cars.size() == 1) {
    		val = cars.get(0);
    		return val;
    	}
    	else {
	    	for(int i = 0; i < cars.size(); i++) {
	    		val += cars.get(i) + ",";
	    	}
	    	val.substring(0, val.length() - 1);
			return val;
		}
    }

    @Override
    public String toString() {
        return "name: " + getName() + " email: " + getEmail() +
                " password: " + this.password + " cars: " + this.carsIDs;
    }
}
