package application.Classes;

public class Employee extends User
{
    private String id;
    private String manager;

    public Employee(String name, String email, String id, String manager)
    {
        super(name, email);
        this.id = id;
        this.manager = manager;
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

    public String getManager() {
        return manager;
    }

    public void setManager(String newManager) {
        this.manager = newManager;
    }

    @Override
    public String toString() {
        return "name: " + getName() + " email: " + getEmail()
                + " ID: " + this.id + " manager: " + this.manager;
    }
}

