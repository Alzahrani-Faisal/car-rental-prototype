package application.Classes;

public class Admin extends User
{
    private String password;

    public Admin(String name, String email, String password)
    {
        super(name, email);
        this.password = password;
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
}

