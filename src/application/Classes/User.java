package application.Classes;

public abstract class User
{
    private String name, email;

    public User(String name, String email)
    {
        this.name = name;
        this.email = email;
    }


    // Setters & Getters
    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }


}
