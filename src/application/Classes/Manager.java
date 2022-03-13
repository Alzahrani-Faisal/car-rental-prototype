package application.Classes;

import java.util.List;

public class Manager extends User
{
    private String id;
    private List<String> employeesIDs;

    public Manager(String name, String email, String id, List<String> employeesIDs)
    {
        super(name, email);
        this.id = id;
        this.employeesIDs = employeesIDs;
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

    public String getId() {
        return id;
    }

    public void setId(String newId) {
        this.id = newId;
    }

    public List<String> getEmployees() {
        return employeesIDs;
    }
    
    public String printEmployees(List<String> employees) {
    	String val = "";
    	if(employees.size() == 1 && employees.get(0).equals("null")) {
    		val = employees.get(0);
    		return val;
    	}
    	else if(employees.size() == 1) {
    		val = employees.get(0);
    		return val;
    	}
    	else {
	    	for(int i = 0; i < employees.size(); i++) {
	    		val += employees.get(i) + ", ";
	    	}
	    	val.substring(0, val.length() - 1);
			return val;
		}
    }

    @Override
    public String toString() {
        return "name: " + getName() + " email: " + getEmail()
                + " id: " + this.id + " employees: " + this.employeesIDs;
    }
}
