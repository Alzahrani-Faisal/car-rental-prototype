package application.Classes;

public class BlacklistedUser extends User{
	
	public BlacklistedUser(String name, String email) {
		super(name, email);
	}
	
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
    
    public String toString() {
    	return "name: " + getName() + " email: " + getEmail();
    }

}
