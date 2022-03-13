package application;

import application.Classes.*; 
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.PopupWindow.AnchorLocation;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AppController implements Initializable {
    private double x, y;
    // This class is to instantiate the configuration of getting elements from the txt files.
    private Data data = new Data();

    @FXML
    private Label signupBtn, loginPageText, btnCustomer,
            btnManager, btnEmployee, nameRegistrationError,
            emailRegistrationError, passwordRegistrationError,
            confirmationPasswordError, successfulSignupMessage,
            EmptyFormMessage, passwordGuidelines, usernameGuidelines,
            customerLoginError, managerLoginError, employeeLoginError,
            adminLoginError;
    
    @FXML
    private Button btnAdmin, customerLoginBtn,
            managerLoginBtn, employeeLoginBtn, adminLoginBtn;

    @FXML
    private Pane pn1Customer, pn1Manager, pn1Employee,
            pn1Admin, loading, pn1Signup;

    @FXML
    private TextField nameField, emailField, customerEmailField,
            managerEmailField, employeeEmailField, adminEmailField;

    @FXML
    private PasswordField passwordField, confirmationPasswordField,
            customerPasswordField, managerIDField, employeeIDField,
            adminPasswordField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
		data.setupCars();
        data.setupCustomers();
        data.setupOrders();
        data.setupEmployees();
        data.setupManagers();
        data.setupAdmin();
        
        setTooltips();

        customerLoginStyling();
        showCustomerForm();
    }

    public void closeApp(MouseEvent actionEvent) {
        System.exit(0);
    }

    public void minimizeStage(MouseEvent actionEvent) {
        ((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).setIconified(true);
    }

    // Start page buttons clicks handling
    public void handleClicks(MouseEvent actionEvent) {
        if (actionEvent.getSource() == btnCustomer) {
            customerLoginStyling();
            showCustomerForm();
        }
        if (actionEvent.getSource() == btnManager) {
            managerLoginStyling();
            showManagerForm();
        }
        if (actionEvent.getSource() == btnEmployee) {
            employeeLoginStyling();
            showEmployeeForm();
        }
        if (actionEvent.getSource() == btnAdmin) {
            adminLoginStyling();
            showAdminForm();
        }
        if (actionEvent.getSource() == signupBtn) {
            signupStyling();
            showSignupForm();
        }
    }

    // these methods for transition between pages
    public void showCustomerForm() {
        pn1Admin.setVisible(false);
        pn1Manager.setVisible(false);
        pn1Employee.setVisible(false);
        pn1Signup.setVisible(false);
        pn1Customer.setVisible(true);
    }

    public void showManagerForm() {
        pn1Admin.setVisible(false);
        pn1Manager.setVisible(true);
        pn1Employee.setVisible(false);
        pn1Signup.setVisible(false);
        pn1Customer.setVisible(false);
    }

    public void showEmployeeForm() {
        pn1Admin.setVisible(false);
        pn1Manager.setVisible(false);
        pn1Employee.setVisible(true);
        pn1Signup.setVisible(false);
        pn1Customer.setVisible(false);
    }

    public void showAdminForm() {
        pn1Admin.setVisible(true);
        pn1Manager.setVisible(false);
        pn1Employee.setVisible(false);
        pn1Signup.setVisible(false);
        pn1Customer.setVisible(false);
    }

    public void showSignupForm() {
        pn1Admin.setVisible(false);
        pn1Manager.setVisible(false);
        pn1Employee.setVisible(false);
        pn1Customer.setVisible(false);
        pn1Signup.setVisible(true);
    }

    // these methods for the styling of the pages
    private void customerLoginStyling() {
        btnCustomer.setStyle("-fx-border-style: hidden hidden solid hidden; -fx-border-width: 3; -fx-border-color: #111975; -fx-padding: 0 0 8 0;");
        btnManager.setStyle("-fx-border-style: none;");
        btnEmployee.setStyle("-fx-border-style: none;");

        loginPageText.setText("Sign In");
    }

    private void managerLoginStyling() {
        btnManager.setStyle("-fx-border-style: hidden hidden solid hidden; -fx-border-width: 3; -fx-border-color: #111975; -fx-padding: 0 0 8 0;");
        btnCustomer.setStyle("-fx-border-style: none;");
        btnEmployee.setStyle("-fx-border-style: none;");

        loginPageText.setText("Sign In");
    }

    private void employeeLoginStyling() {
        btnEmployee.setStyle("-fx-border-style: hidden hidden solid hidden; -fx-border-width: 3; -fx-border-color: #111975; -fx-padding: 0 0 8 0;");
        btnCustomer.setStyle("-fx-border-style: none;");
        btnManager.setStyle("-fx-border-style: none;");

        loginPageText.setText("Sign In");
    }

    private void adminLoginStyling() {
        btnCustomer.setStyle("-fx-border-style: none;");
        btnManager.setStyle("-fx-border-style: none;");
        btnEmployee.setStyle("-fx-border-style: none;");

        loginPageText.setText("Login as Admin");
    }

    private void signupStyling() {
        btnCustomer.setStyle("-fx-border-style: none;");
        btnManager.setStyle("-fx-border-style: none;");
        btnEmployee.setStyle("-fx-border-style: none;");

        loginPageText.setText("Sign Up");
    }
    
    // An effect to show the conditions of making a new account    
    private void setTooltips() {
    	Tooltip usernameTooltip = new Tooltip();
    	Tooltip passwordTooltip = new Tooltip();

    	usernameTooltip.setText("username must be between 3 to 15 characters and "
    			+ "symbols with at least one lowercase letter and one digit. "
    			+ "the allowed special symbols are \"- _\" only.");
    	usernameTooltip.setStyle("-fx-font: normal bold 11 Langdon; "
    	    + "-fx-base: #AE3522; "
    	    + "-fx-text-fill:  #111975;"
    	    + "-fx-background-color: #fff;"
    	    + "-fx-border: #fff;");
    	usernameTooltip.setPrefWidth(300);
    	usernameTooltip.setWrapText(true);
    	usernameTooltip.setAnchorLocation(AnchorLocation.WINDOW_TOP_RIGHT);
    	usernameTooltip.setShowDelay(Duration.seconds(0.1));
    	usernameTooltip.setHideDelay(Duration.seconds(4));
    	
    	passwordTooltip.setText("The password must be 6 to 20 characters long with at least one digit,"
    			+ " one uppercase letter, one lowercase letter and one special symbol (\"@#$%\")");
    	passwordTooltip.setStyle("-fx-font: normal bold 11 Langdon; "
    	    + "-fx-base: #AE3522; "
    		+ "-fx-background-color: #fff;"
    	    + "-fx-text-fill:  #111975;");
    	passwordTooltip.setPrefWidth(300);
    	passwordTooltip.setWrapText(true);
    	passwordTooltip.setAnchorLocation(AnchorLocation.WINDOW_TOP_RIGHT);
    	passwordTooltip.setShowDelay(Duration.seconds(0.1));
    	passwordTooltip.setHideDelay(Duration.seconds(4));

    	usernameGuidelines.setTooltip(usernameTooltip);
    	passwordGuidelines.setTooltip(passwordTooltip);
    }
    

    public void draggable(Parent root, Stage stage) {
        //drag it here
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
            stage.setOpacity(0.8f);
        });
        root.setOnDragDone(event -> {
            stage.setOpacity(1.0f);
        });
        root.setOnMouseReleased(event -> {
            stage.setOpacity(1.0f);
        });
    }

    //Login
    @FXML
    private void login(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getSource() == customerLoginBtn) {
        	for (Customer customer : data.getCustomers()) {
        		if(customer.getEmail().equals(customerEmailField.getText()) &&
        				customer.getPassword().equals(customerPasswordField.getText())) {
        			savaCredintials(customer);
                    loadCustomer(mouseEvent);
        		} else {
        			temporaryMessage(customerLoginError);
        		}
        	}
        }
        else if (mouseEvent.getSource() == managerLoginBtn) {
        	for (Manager manager : data.getManagers()) {
        		if(manager.getEmail().equals(managerEmailField.getText()) &&
        				manager.getId().equals(managerIDField.getText())) {
        			savaCredintials(manager);
                    loadManager(mouseEvent);
        		} else {
        			temporaryMessage(managerLoginError);
        		}
        	}
        }
        else if (mouseEvent.getSource() == employeeLoginBtn) {
        	for (Employee employee : data.getEmployees()) {
        		if(employee.getEmail().equals(employeeEmailField.getText()) &&
        				employee.getId().equals(employeeIDField.getText())) {
        			savaCredintials(employee);
                    loadEmployee(mouseEvent);
        		} else {
        			temporaryMessage(employeeLoginError);
        		}
        	}
        }
        	else if (mouseEvent.getSource() == adminLoginBtn) {
    		if(adminEmailField.getText().equals(data.getAdmin().getEmail()) &&
    				adminPasswordField.getText().equals(data.getAdmin().getPassword())) {
    			savaCredintials(new Admin("Admin", adminEmailField.getText(),
    					adminPasswordField.getText()));
    			
                loadAdmin(mouseEvent);
    		} else {
    			temporaryMessage(adminLoginError);
    		}
    		
        }
        
    }
        	

    // these methods to load and enter to the dashboard (login after authentication)
    public void loadCustomer(MouseEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("Customer.fxml"));
        Scene CustomerScene = new Scene(parent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        draggable(parent, window);
        window.setScene(CustomerScene);
        window.show();
    }

    public void loadManager(MouseEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("Manager.fxml"));
        Scene managerScene = new Scene(parent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        draggable(parent, window);

        window.setScene(managerScene);
        window.show();
    }

    public void loadEmployee(MouseEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("Employee.fxml"));
        Scene employeeScene = new Scene(parent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        draggable(parent, window);

        window.setScene(employeeScene);
        window.show();
    }

    public void loadAdmin(MouseEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("Admin.fxml"));
        Scene adminScene = new Scene(parent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        draggable(parent, window);

        window.setScene(adminScene);
        window.show();
    }
    
    //To save the user info so we can get them easily if we want. (like the browser cookies)    
    private static void savaCredintials(Object obj) throws IOException {
    	BufferedWriter writer = new BufferedWriter(
                new FileWriter("src/credintials.txt", false));
    
        if (obj instanceof Customer) {
        	writer.write(((Customer) obj).getName() + "    ");
            writer.write(((Customer) obj).getEmail() + "    ");
            writer.write(((Customer) obj).getPassword() + "    ");
            writer.write(((Customer) obj).printCars(((Customer) obj).getCars()));

            writer.close();
        }
        
        else if (obj instanceof Manager) {
        	writer.write(((Manager) obj).getName() + "    ");
            writer.write(((Manager) obj).getEmail() + "    ");
            writer.write(((Manager) obj).getId() + "    ");
            writer.write(((Manager) obj).printEmployees(((Manager) obj).getEmployees()));

            writer.close();
        }
        
        else if (obj instanceof Employee) {
        	writer.write(((Employee) obj).getName() + "    ");
            writer.write(((Employee) obj).getEmail() + "    ");
            writer.write(((Employee) obj).getId() + "    ");
            writer.write(((Employee) obj).getManager());

            writer.close();
        }
        
        else if (obj instanceof Admin) {
        	writer.write(((Admin) obj).getName() + "    ");
            writer.write(((Admin) obj).getEmail() + "    ");
            writer.write(((Admin) obj).getPassword());
            
            writer.close();
        }
        
    }
    
    private void temporaryMessage(Node msg) {
    	msg.setVisible(true);
    	
        PauseTransition visiblePause = new PauseTransition(
                Duration.seconds(5)
        );
        visiblePause.setOnFinished(
                event -> {
                    msg.setVisible(false);
                });
        visiblePause.play();
    }

    //all the below belongs to the sign up action
    
    public void register(MouseEvent mouseEvent) throws IOException {
    	
        // using special classes to validate the data        
        UsernameValidator usernameValidator = new UsernameValidator();
        EmailValidator emailValidator = new EmailValidator();
        PasswordValidator passwordValidator = new PasswordValidator();

        if (nameField.getText().isEmpty() || emailField.getText().isEmpty() ||
                passwordField.getId().isEmpty() || confirmationPasswordField.getText().isEmpty()) {
            temporaryMessage(EmptyFormMessage);
        } else {
            if (!usernameValidator.validate(nameField.getText())) {
            	temporaryMessage(nameRegistrationError);
                temporaryMessage(nameRegistrationError);
            }
            if (!emailValidator.validate(emailField.getText())) {
                temporaryMessage(emailRegistrationError);
            }
            if (!passwordValidator.validate(passwordField.getText())) {
                temporaryMessage(passwordRegistrationError);
            }
            if (!passwordField.getText().equals(confirmationPasswordField.getText())) {
                temporaryMessage(confirmationPasswordError);
            }

	        if (usernameValidator.validate(nameField.getText()) &&
	                emailValidator.validate(emailField.getText()) &&
	                passwordValidator.validate(passwordField.getText()) &&
	                passwordField.getText().equals(confirmationPasswordField.getText())) {
	        	
	            pn1Signup.setVisible(false);
	            loading.setVisible(true);
	
	            PauseTransition visiblePause = new PauseTransition(
	                    Duration.seconds(2)
	            );
	            visiblePause.setOnFinished(
	                    event -> {
	                        loading.setVisible(false);
	                        temporaryMessage(successfulSignupMessage);
	                        try {
	                        	for (Customer customer : data.getCustomers()) {
	                        		if(customer.getEmail().equals(emailField.getText())) {
	                        			EmptyFormMessage.setText("The user is already exist.");
	                        			temporaryMessage(EmptyFormMessage);
	                        		} else {
	                        			addUserToDatabase(createCustomer(nameField.getText(),
		                                         emailField.getText(), passwordField.getText(), null));
		                                successfulSignupMessage.setVisible(true);
	                        		}
	                        	}
	                        	
	                        } catch (IOException e) {
	                            e.printStackTrace();
	                        }
	                        
	                        customerLoginStyling();
	                        showCustomerForm();
	                    });
	            visiblePause.play();
	        }
	    }
    }
    
    private boolean userExist(String customerEmail, String newCustomer) {
    	if(customerEmail == newCustomer) {
    		return true;
    	}
    	return false;
    }

    private Customer createCustomer(String name, String
            email, String password, List<String> cars) {
        return new Customer(name, email, password, cars);
    }

    private void addUserToDatabase(Customer customer) throws
            IOException {
        BufferedWriter writer = new BufferedWriter(
                new FileWriter("src/customers.txt", true));

        writer.append("\n");
        writer.append(customer.getName()).append("    ");
        writer.append(customer.getEmail()).append("    ");
        writer.append(customer.getPassword()).append("    ");
        writer.append(null);

        writer.close();
    }

}

