package application;

import application.Classes.*;

import java.io.*;
//import org.apache.commons.io.FileUtils;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.PopupWindow.AnchorLocation;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.stream.Stream;

public class AdminController implements Initializable {
    private double x, y;
    // This class is to instantiate the configuration of getting elements from the txt files.
    private Data data = new Data();
    
    @FXML
    private TableView<Car> carsTable;
    
    @FXML
    private TableColumn<Car, String> tableName;

    @FXML
    private TableColumn<Car, String> tableColor;

    @FXML
    private TableColumn<Car, Integer> tableYear;

    @FXML
    private TableColumn<Car, Double> tableMileage;

    @FXML
    private TableColumn<Car, String> tableId;
    
    @FXML
    private TableColumn<Car, Double> tablePrice;

    @FXML
    private VBox pnItems = null;
    
    @FXML
    private VBox pnItems1 = null;
    
    @FXML
    private VBox pnItems2 = null;
    
    @FXML
    private Button btnOverview, btnUsers, btnCars,
            btnBlacklist, btnInfo, btnSignout, editName,
            editEmail, editPassword, savePassword,
            saveEmail, saveName, CancelEditingName,
            CancelEditingEmail, CancelEditingPassword;

    @FXML
    private Pane panelOverview, panelUsers, panelCars,
            panelBlacklist, panelAccountInfo, loading,
            panelAddManager, panelAddEmployee, panelAddToBlacklist;
    
    @FXML 
    private Label usersNumber, customersNumber, managersNumber,
            employeesNumber, blacklistNumber, totalOrders,
            deliveredOrders, pendingOrders, confirmationLabel,
            accountNameError, accountEmailError, accountPasswordError,
            accountConfirmationError, usernameGuidlines, passwordGuidelines,
            blacklistEmailError, managerNameError, managerEmailError,
            managerUsernameGuidelines, employeeNameError, employeeEmailError,
            managerIdError, employeeUsernameGuidelines, emptyCarFieldsMessage;
    
    @FXML 
    private TextField name, email, managerName, managerEmail,
            managerId, employeeName, employeeEmail, employeeId,
            employeeManager, blacklistName, blacklistEmail,
            carName, carColor, carYear, carMileage, carId,
            carPrice;

    @FXML 
    private PasswordField password, passwordConfirmation;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	data.setupCustomers();
    	data.setupManagers();
    	data.setupEmployees();
    	data.setupAdmin();
    	data.setupCars();
    	data.setupOrders();
    	data.setupBlacklist();
    	
    	
    	setTooltips();
    	createOrdersList();
        createUsersList();
        createBlacklist();
        accountInfoSetup();
        
        tableName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        tableColor.setCellValueFactory(new PropertyValueFactory<>("Color"));
        tableYear.setCellValueFactory(new PropertyValueFactory<>("Year"));
        tableMileage.setCellValueFactory(new PropertyValueFactory<>("Mileage"));
        tableId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tablePrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        
        ObservableList<Car> observableList = FXCollections.observableArrayList(data.getCars());
        
        carsTable.setItems(observableList);

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
    
    @FXML
    void closeApp(MouseEvent event) {
    	System.exit(0);
    }

    @FXML
    void minimizeStage(MouseEvent event) {
        ((Stage) ((Button)event.getSource()).getScene().getWindow()).setIconified(true);
    }


    // menu buttons clicks handling
    public void handleClicks(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == btnOverview) {
            showOverview();
        }
        if (actionEvent.getSource() == btnUsers) {
            showUsers();
        }
        if (actionEvent.getSource() == btnCars) {
            showCars();
        }
        if (actionEvent.getSource() == btnBlacklist) {
            showBlacklist();
        }
        if (actionEvent.getSource() == btnInfo) {
            showInfo();
        }
        if(actionEvent.getSource() == btnSignout)
        {
            Parent parent = FXMLLoader.load(getClass().getResource("App.fxml"));
            Scene signoutScene = new Scene(parent);
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            draggable(parent, window);

            window.setScene(signoutScene);
            window.show();
        }
    }

    // these methods for transition between pages
    public void showOverview() {
        panelOverview.setVisible(true);
        panelUsers.setVisible(false);
        panelCars.setVisible(false);
        panelBlacklist.setVisible(false);
        panelAccountInfo.setVisible(false);
    }

    public void showUsers() {
        panelOverview.setVisible(false);
        panelUsers.setVisible(true);
        panelCars.setVisible(false);
        panelBlacklist.setVisible(false);
        panelAccountInfo.setVisible(false);
    }

    public void showCars() {
        panelOverview.setVisible(false);
        panelUsers.setVisible(false);
        panelCars.setVisible(true);
        panelBlacklist.setVisible(false);
        panelAccountInfo.setVisible(false);
    }

    public void showBlacklist() {
        panelOverview.setVisible(false);
        panelUsers.setVisible(false);
        panelCars.setVisible(false);
        panelBlacklist.setVisible(true);
        panelAccountInfo.setVisible(false);
    }

    public void showInfo() {
        panelOverview.setVisible(false);
        panelUsers.setVisible(false);
        panelCars.setVisible(false);
        panelBlacklist.setVisible(false);
        panelAccountInfo.setVisible(true);
    }
    
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

    	usernameGuidlines.setTooltip(usernameTooltip);
    	managerUsernameGuidelines.setTooltip(usernameTooltip);
    	employeeUsernameGuidelines.setTooltip(usernameTooltip);
    	
    	passwordGuidelines.setTooltip(passwordTooltip);
    }
    
    public void accountInfoSetup() {
    	String path  = "src/credintials.txt";
        FileInputStream credintialsFile = null;
        Scanner scanner = null;
        try {
            credintialsFile = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (credintialsFile != null) {
            scanner = new Scanner(credintialsFile);
            
            while (scanner.hasNextLine()) {
                name.setText(scanner.next());
                email.setText(scanner.next());
                password.setText(scanner.next());
            }
        }
        scanner.close();
    }
    
    @FXML
    private void editData(MouseEvent mouseEvent) {
    	if(mouseEvent.getSource() == editName) {
    		editName.setVisible(false);
    		name.setEditable(true);
    		name.setText("");
    		saveName.setVisible(true);
    		usernameGuidlines.setVisible(true);
    		CancelEditingName.setVisible(true);
    	}
    	else if (mouseEvent.getSource() == editEmail) {
    		editEmail.setVisible(false);
    		email.setEditable(true);
    		email.setText("");
    		saveEmail.setVisible(true);
    		CancelEditingEmail.setVisible(true);
    	}
    	
    	else if(mouseEvent.getSource() == editPassword) {
    		editPassword.setVisible(false);
    		password.setEditable(true);
    		password.setText("");
    		savePassword.setVisible(true);
    		passwordGuidelines.setVisible(true);
    		confirmationLabel.setVisible(true);
    		passwordConfirmation.setVisible(true);
    		CancelEditingPassword.setVisible(true);
    	}
    }
    
    @FXML
    private void cancelEditing(MouseEvent mouseEvent) {
    	if(mouseEvent.getSource() == CancelEditingName) {
    		editName.setVisible(true);
    		name.setEditable(false);
    		accountInfoSetup();
    		saveName.setVisible(false);
    		CancelEditingName.setVisible(false);
    		usernameGuidlines.setVisible(false);
    		}
    	
    	if(mouseEvent.getSource() == CancelEditingEmail) {
    		editEmail.setVisible(true);
    		email.setEditable(false);
    		accountInfoSetup();
    		saveEmail.setVisible(false);
    		CancelEditingEmail.setVisible(false);
    	}
    	
    	if(mouseEvent.getSource() == CancelEditingPassword) {
    		editPassword.setVisible(true);
    		password.setEditable(false);
    		passwordConfirmation.setVisible(false);
    		confirmationLabel.setVisible(false);
    		accountInfoSetup();
    		savePassword.setVisible(false);
    		CancelEditingPassword.setVisible(false);
    		passwordGuidelines.setVisible(false);
    		
    	}
    	
    }
    
    @FXML
    private void saveChange(MouseEvent mouseEvent) throws IOException {
    	UsernameValidator usernameValidator = new UsernameValidator();
    	EmailValidator emailValidator = new EmailValidator();
    	PasswordValidator passwordValidator = new PasswordValidator();
    	if(mouseEvent.getSource() == saveName) {
    		if(usernameValidator.validate(name.getText())) {
    			saveName.setVisible(false);
        		editName.setVisible(true);
        		CancelEditingName.setVisible(false);
        		usernameGuidlines.setVisible(false);
        		
	    		data.getAdmin().setName(name.getText());
	    		savaCredintials(data.getAdmin());
	    		name.setEditable(false);
	    		accountInfoSetup();
	    		
    		} else {
    			temporaryMessage(accountNameError);
    		}
    	}
    	
    	if(mouseEvent.getSource() == saveEmail) {
    		if(emailValidator.validate(email.getText())) {
	    		saveEmail.setVisible(false);
	    		editEmail.setVisible(true);
	    		CancelEditingEmail.setVisible(false);
	    		data.getAdmin().setEmail(email.getText());
	    		savaCredintials(data.getAdmin());
	    		email.setEditable(false);
	    		accountInfoSetup();
    		} else {
    			temporaryMessage(accountEmailError);
    		}
    	}
    	
    	if(mouseEvent.getSource() == savePassword) {
    		if(passwordValidator.validate(password.getText())) {
    			if(password.getText().equals(passwordConfirmation.getText())) {
		    		savePassword.setVisible(false);
		    		editPassword.setVisible(true);
		    		CancelEditingPassword.setVisible(false);
		    		passwordConfirmation.setVisible(false);
		    		confirmationLabel.setVisible(false);
		    		passwordGuidelines.setVisible(false);
		    		
		    		data.getAdmin().setPassword(password.getText());
		    		savaCredintials(data.getAdmin());
		    		password.setEditable(false);
		    		accountInfoSetup(); 
	    		} else {
	    			temporaryMessage(accountConfirmationError);
	    		}
    		} else {
    			temporaryMessage(accountPasswordError);
    		}
    	}
    	
    }
    
    @FXML
    private void goToAddNewManagerPanel(MouseEvent mouseEvent) {
    	panelUsers.setVisible(false);
    	loading.setVisible(true);
    	
        PauseTransition visiblePause = new PauseTransition(
                Duration.seconds(2)
        );
        visiblePause.setOnFinished(
                event -> {
                    loading.setVisible(false);
                    panelAddManager.setVisible(true);
                });
        visiblePause.play();
    }
    
    @FXML 
    private void cancelAddingNewManager(MouseEvent mouseEvent) {
    	panelAddManager.setVisible(false);
    	loading.setVisible(true);
    	
        PauseTransition visiblePause = new PauseTransition(
                Duration.seconds(2)
        );
        visiblePause.setOnFinished(
                event -> {
                    loading.setVisible(false);
                    panelUsers.setVisible(true);
                });
        visiblePause.play();
    }
    
    @FXML
    private void saveNewManager(MouseEvent mouseEvent) {
    	List<String> employeesList = new ArrayList<String>();
    	UsernameValidator usernameValidator = new UsernameValidator();
    	EmailValidator emailValidator = new EmailValidator();
    	
    	if(!usernameValidator.validate(managerName.getText())) {
    		temporaryMessage(managerNameError);
    	}
    	
    	else if(!emailValidator.validate(managerEmail.getText())) {
    		temporaryMessage(managerEmailError);
    	}
    	
    	else {
	    	employeesList.add("null");
	    	Manager newManager = new Manager(
	    			managerName.getText(),
	    			managerEmail.getText(),
	    			managerId.getText(), employeesList);
	    	BufferedWriter writer;
	
	    	try {
				writer = new BufferedWriter(
				        new FileWriter("src/managers.txt", true));
				writer.newLine();
		    	writer.write(newManager.getName() + "    ");
		        writer.write(newManager.getEmail() + "    ");
		        writer.write(newManager.getId() + "    ");
		        writer.write(newManager.printEmployees(employeesList));
		        
		        writer.close();
	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	data.getManagers().add(newManager);
	    	createUsersList();
	
	    	panelAddManager.setVisible(false);
	    	
	    	loading.setVisible(true);
	    	
	        PauseTransition visiblePause = new PauseTransition(
	                Duration.seconds(2)
	        );
	        visiblePause.setOnFinished(
	                event -> {
	                    loading.setVisible(false);
	                    panelUsers.setVisible(true);
	                });
	        visiblePause.play();
        }
    }
    
    @FXML
    private void goToAddNewEmployeePanel(MouseEvent mouseEvent) {
    	panelUsers.setVisible(false);
    	loading.setVisible(true);
    	
        PauseTransition visiblePause = new PauseTransition(
                Duration.seconds(2)
        );
        visiblePause.setOnFinished(
                event -> {
                    loading.setVisible(false);
                    panelAddEmployee.setVisible(true);
                });
        visiblePause.play();
    }
    
    @FXML 
    private void cancelAddingNewEmployee(MouseEvent mouseEvent) {
    	panelAddEmployee.setVisible(false);
    	loading.setVisible(true);
    	
        PauseTransition visiblePause = new PauseTransition(
                Duration.seconds(2)
        );
        visiblePause.setOnFinished(
                event -> {
                    loading.setVisible(false);
                    panelUsers.setVisible(true);
                });
        visiblePause.play();
    }
    
    @FXML
    private void saveNewEmployee(MouseEvent mouseEvent) {
    	UsernameValidator usernameValidator = new UsernameValidator();
    	EmailValidator emailValidator = new EmailValidator();
    	
    	boolean validManager = false;
    	for(Manager manager : data.getManagers()) {
    		if(employeeManager.getText().equals(manager.getId())) {
    			validManager = true;
    		}
    	}
    	
    	if(!usernameValidator.validate(employeeName.getText())) {
    		temporaryMessage(employeeNameError);
    	}
    	
    	else if(!emailValidator.validate(employeeEmail.getText())) {
    		temporaryMessage(employeeEmailError);
    	}
    	
    	else if(validManager == false) {
    		temporaryMessage(managerIdError);
    	}
    	
    	else {
	    	Employee newEmployee = new Employee(
	    			employeeName.getText(),
	    			employeeEmail.getText(),
	    			employeeId.getText(), employeeManager.getText());
	    	BufferedWriter writer;
	
	    	try {
				writer = new BufferedWriter(
				        new FileWriter("src/employees.txt", true));
				writer.newLine();
		    	writer.write(newEmployee.getName() + "    ");
		        writer.write(newEmployee.getEmail() + "    ");
		        writer.write(newEmployee.getId() + "    ");
		        writer.write(newEmployee.getManager());
		        
		        writer.close();
	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	data.getEmployees().add(newEmployee);
	    	createUsersList();
	
	    	panelAddEmployee.setVisible(false);
	    	
	    	loading.setVisible(true);
	    	
	        PauseTransition visiblePause = new PauseTransition(
	                Duration.seconds(2)
	        );
	        visiblePause.setOnFinished(
	                event -> {
	                    loading.setVisible(false);
	                    panelUsers.setVisible(true);
	                });
	        visiblePause.play();
        }
    }
    
    @FXML
    private void goToAddToBlacklistPanel(MouseEvent mouseEvent) {
    	panelBlacklist.setVisible(false);
    	loading.setVisible(true);
    	
        PauseTransition visiblePause = new PauseTransition(
                Duration.seconds(2)
        );
        visiblePause.setOnFinished(
                event -> {
                    loading.setVisible(false);
                    panelAddToBlacklist.setVisible(true);
                });
        visiblePause.play();
    }
    
    @FXML 
    private void cancelAddingToBlacklist(MouseEvent mouseEvent) {
    	panelAddToBlacklist.setVisible(false);
    	loading.setVisible(true);
    	
        PauseTransition visiblePause = new PauseTransition(
                Duration.seconds(2)
        );
        visiblePause.setOnFinished(
                event -> {
                    loading.setVisible(false);
                    panelBlacklist.setVisible(true);
                });
        visiblePause.play();
    }
    
    @FXML
    private void saveNewBlacklistedUser(MouseEvent mouseEvent) {
    	EmailValidator emailValidator = new EmailValidator();
    	if(emailValidator.validate(blacklistEmail.getText())) {
	    	BlacklistedUser blacklistedUser = new BlacklistedUser(
	    			blacklistName.getText(),
	    			blacklistEmail.getText());
	    	BufferedWriter writer;
	
	    	try {
				writer = new BufferedWriter(
				        new FileWriter("src/blacklist.txt", true));
				writer.newLine();
		    	writer.write(blacklistedUser.getName() + "    ");
		        writer.write(blacklistedUser.getEmail());
		        
		        writer.close();
	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	data.getBlacklist().add(blacklistedUser);
	    	createBlacklist();
	
	    	panelAddToBlacklist.setVisible(false);
	    	
	    	loading.setVisible(true);
	    	
	        PauseTransition visiblePause = new PauseTransition(
	                Duration.seconds(2)
	        );
	        visiblePause.setOnFinished(
	                event -> {
	                    loading.setVisible(false);
	                    panelBlacklist.setVisible(true);
	                });
	        visiblePause.play();
    	} else {
    		temporaryMessage(blacklistEmailError);
    	}
    }
    
    
    
    private static void savaCredintials(Admin admin) {
    	BufferedWriter writer1;

    	try {
			writer1 = new BufferedWriter(
			        new FileWriter("src/admin.txt", false));
			
	    	writer1.write(admin.getName() + "    ");
	        writer1.write(admin.getEmail() + "    ");
	        writer1.write(admin.getPassword());
	        
	        writer1.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	BufferedWriter writer;
		try {
			writer = new BufferedWriter(
			        new FileWriter("src/credintials.txt", false));
			
			writer.write(admin.getName() + "    ");
	        writer.write(admin.getEmail() + "    ");
	        writer.write(admin.getPassword());
	        
	        writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
    
    @FXML
    private void addCar(MouseEvent mouseEvent) {
    	if(carName.getText().isEmpty() || carColor.getText().isEmpty() ||
    			carYear.getText().isEmpty() || carMileage.getText().isEmpty() ||
    			carId.getText().isEmpty()) {
    		temporaryMessage(emptyCarFieldsMessage);
    	} else {
    		Car newCar = new Car(
    				carName.getText(),
    				carColor.getText(),
    				Integer.parseInt(carYear.getText()),
    				Double.parseDouble(carMileage.getText()),
    				carId.getText() , 
    				Double.parseDouble(carPrice.getText()));
    		data.getCars().add(newCar);
    		data.setupCars();
    		carsTable.getItems().add(newCar);
    		
    		try {
				BufferedWriter writer = new BufferedWriter(
				        new FileWriter("src/cars.txt", true));
				writer.newLine();
				writer.write(newCar.getName() + "    ");
				writer.write(newCar.getColor() + "    ");
				writer.write(newCar.getYear() + "    ");
				writer.write(newCar.getMileage() + "    ");
				writer.write(newCar.getId() + "    ");
				writer.write(Double.toString(newCar.getPrice()));
				
				writer.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    		
    		carName.setText("");
    		carColor.setText("");
    		carYear.setText("");
    		carMileage.setText("");
    		carId.setText("");
    	}
    }

    @FXML
    private void deleteCar(MouseEvent mouseEvent) throws IOException {
        ObservableList<Car> allCars, singleCar;
        allCars = carsTable.getItems();
        singleCar = carsTable.getSelectionModel().getSelectedItems();

        deleteCarFromFile(singleCar.get(0).getId());

        singleCar.forEach(allCars::remove);

    }

    private void deleteCarFromFile(String id) throws IOException {
        Data data = new Data();
        data.setupCars();
        ArrayList<Car> cars = data.getCars();
        FileOutputStream fileOutputStream = new FileOutputStream("src/cars.txt");
        PrintWriter printWriter = new PrintWriter(fileOutputStream);
        System.out.println("7654");
        System.out.println(cars.size());


        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getId().contentEquals(id))
                cars.remove(i);
        }
        for (int i = 0; i < cars.size(); i++) {
            printWriter.println(cars.get(i).getName() + "    " +
                    cars.get(i).getColor() + "    " +
                    cars.get(i).getYear() + "    " +
                    cars.get(i).getMileage() + "    " +
                    cars.get(i).getId()+"    "+ cars.get(i).getPrice());
            if (i == cars.size() - 2) {
                i += 1;
                printWriter.print(cars.get(i).getName() + "    " +
                        cars.get(i).getColor() + "    " +
                        cars.get(i).getYear() + "    " +
                        cars.get(i).getMileage() + "    " +
                        cars.get(i).getId()+ "    " + cars.get(i).getPrice());
                break;
            }
        }
        printWriter.close();
        fileOutputStream.close();
    }
    
    public void removeLineFromFile(String file, String lineToRemove) {

        try {

          File inFile = new File(file);

          if (!inFile.isFile()) {
            System.out.println("Parameter is not an existing file");
            return;
          }

          //Construct the new file that will later be renamed to the original filename.
          File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

          BufferedReader br = new BufferedReader(new FileReader(file));
          PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
          
//          long lineCount;
//          try (Stream<String> stream = Files.lines(new File(file).toPath(), StandardCharsets.UTF_8)) {
//            lineCount = stream.count();
//          }

          String line = null;
      

          //Read from the original file and write to the new
          //unless content matches data to be removed.
          while ((line = br.readLine()) != null) {
            if (!line.trim().equals(lineToRemove)) {
              
              pw.println(line);
              pw.flush();
            }
//            else if(line.trim().equals("")) {
////            	pw.print(line);
//                break;
//            }
          }
     
          pw.close();
          br.close();

          //Delete the original file
          if (!inFile.delete()) {
            System.out.println("Could not delete file");
            return;
          }

          //Rename the new file to the filename the original file had.
          if (!tempFile.renameTo(inFile))
            System.out.println("Could not rename file");

        }
        catch (FileNotFoundException ex) {
          ex.printStackTrace();
        }
        catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    
//    private void deleteEmptyLines(String fileName) {
//    	try
//    	{
//    	    String name = fileName;
//    	    List<String> lines = FileUtil.readLines(new File(name));
//
//    	    Iterator<String> i = lines.iterator();
//    	    while (i.hasNext())
//    	    {
//    	        String line = i.next();
//    	        if (line.trim().isEmpty())
//    	            i.remove();
//    	    }
//
//    	    FileUtils.writeLines(new File(name), lines);
//    	}
//    	catch (IOException e)
//    	{
//    	    e.printStackTrace();
//    	}
//    }
    
    private void createOrdersList() {
    	pnItems.getChildren().clear();
    	int numberOfOrders = data.getOrders().size();
    	totalOrders.setText(Integer.toString(numberOfOrders));
    	int countActiveOrders = 0;
    	int countPendingOrders = 0;
    	
    	for(int i = 0; i < numberOfOrders; i++) {
    		if(data.getOrders().get(i).getStatus().equals("active")) {
    			countActiveOrders++;
    		}
    		if(data.getOrders().get(i).getStatus().equals("pending")) {
    			countPendingOrders++;
    		}
    	}
    	
    	deliveredOrders.setText(Integer.toString(countActiveOrders));
    	pendingOrders.setText(Integer.toString(countPendingOrders));

    	
    	// Adding elements to orders list - overview page
        HBox[] nodes = new HBox[data.getOrders().size()];
        for (int i = 0; i < nodes.length; i++) {
            try {
                final int j = i;
                nodes[i] = FXMLLoader.load(getClass().getResource("managersItem.fxml"));

                //give the items some effect

                nodes[i].setOnMouseEntered(event -> {
                    nodes[j].setStyle("-fx-background-color : #0A0E3F");
                });
                nodes[i].setOnMouseExited(event -> {
                    nodes[j].setStyle("-fx-background-color : #02030A");
                });
                                
                ((Label) nodes[j].getChildren().get(0))
                	.setText(Integer.toString(data.getOrders().get(j).getCarID()));
                ((Label) nodes[j].getChildren().get(1))
                	.setText(data.getOrders().get(j).getCustomerEmail());
                ((Label) nodes[j].getChildren()
                		.get(2)).setText(data.getOrders().get(j).getDate());
                ((Label) nodes[j].getChildren()
                		.get(3)).setText(Double.toString(data.getOrders().get(j).getPrice()));
                ((Button) nodes[j].getChildren()
                		.get(4)).setText(data.getOrders().get(j).getStatus());
                
                pnItems.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void createUsersList() {
    	pnItems1.getChildren().clear();
    	int listSize = data.getCustomers().size() +
    				   data.getManagers().size() +
    			       data.getEmployees().size();
    	usersNumber.setText(Integer.toString(listSize));
    	customersNumber.setText(Integer.toString(data.getCustomers().size()));
    	managersNumber.setText(Integer.toString(data.getManagers().size()));
    	employeesNumber.setText(Integer.toString(data.getEmployees().size()));
    	// Adding elements to a specific list
        HBox[] nodes = new HBox[listSize];
        for (int i = 0; i < nodes.length - (data.getManagers().size() + data.getEmployees().size()); i++) {
            try {
                final int j = i;
                nodes[i] = FXMLLoader.load(getClass().getResource("usersItem.fxml"));

                //give the items some effect

                nodes[i].setOnMouseEntered(event -> {
                    nodes[j].setStyle("-fx-background-color : #0A0E3F");
                });
                nodes[i].setOnMouseExited(event -> {
                    nodes[j].setStyle("-fx-background-color : #02030A");
                });
                                
                ((Label) nodes[j].getChildren().get(0))
                	.setText(data.getCustomers().get(j).getName());
                ((Label) nodes[j].getChildren().get(1))
                	.setText(data.getCustomers().get(j).getEmail());
                ((Label) nodes[j].getChildren()
                		.get(2)).setText(data.getCustomers().get(j).getClass().getSimpleName());
                
                pnItems1.getChildren().add(nodes[i]);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
            for (int i1 = 0; i1 < nodes.length - (data.getCustomers().size() + data.getManagers().size()); i1++) {
                try {
                    final int j = i1;
                    nodes[i1] = FXMLLoader.load(getClass().getResource("usersItem.fxml"));

                    //give the items some effect

                    nodes[i1].setOnMouseEntered(event -> {
                        nodes[j].setStyle("-fx-background-color : #0A0E3F");
                    });
                    nodes[i1].setOnMouseExited(event -> {
                        nodes[j].setStyle("-fx-background-color : #02030A");
                    });
                                    
                    ((Label) nodes[j].getChildren().get(0))
                    	.setText(data.getEmployees().get(j).getName());
                    ((Label) nodes[j].getChildren().get(1))
                    	.setText(data.getEmployees().get(j).getEmail());
                    ((Label) nodes[j].getChildren()
                    		.get(2)).setText(data.getEmployees().get(j).getClass().getSimpleName());
                    
                    pnItems1.getChildren().add(nodes[i1]);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        
        for (int i1 = 0; i1 < nodes.length - (data.getCustomers().size() + data.getEmployees().size()); i1++) {
            try {
                final int j = i1;
                nodes[i1] = FXMLLoader.load(getClass().getResource("usersItem.fxml"));

                //give the items some effect

                nodes[i1].setOnMouseEntered(event -> {
                    nodes[j].setStyle("-fx-background-color : #0A0E3F");
                });
                nodes[i1].setOnMouseExited(event -> {
                    nodes[j].setStyle("-fx-background-color : #02030A");
                });
                                
                ((Label) nodes[j].getChildren().get(0))
                	.setText(data.getManagers().get(j).getName());
                ((Label) nodes[j].getChildren().get(1))
                	.setText(data.getManagers().get(j).getEmail());
                ((Label) nodes[j].getChildren()
                		.get(2)).setText(data.getManagers().get(j).getClass().getSimpleName());
                
                pnItems1.getChildren().add(nodes[i1]);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
       
    }
    
    private void createBlacklist() {
    	pnItems2.getChildren().clear();
    	blacklistNumber.setText(Integer.toString(data.getBlacklist().size()));
    	// Adding elements to orders list - overview page
        HBox[] nodes = new HBox[data.getBlacklist().size()];
        for (int i = 0; i < nodes.length; i++) {
            try {
                final int j = i;
                nodes[i] = FXMLLoader.load(getClass().getResource("blacklistItem.fxml"));

                //give the items some effect

                nodes[i].setOnMouseEntered(event -> {
                    nodes[j].setStyle("-fx-background-color : #0A0E3F");
                });
                nodes[i].setOnMouseExited(event -> {
                    nodes[j].setStyle("-fx-background-color : #02030A");
                });
                                
                ((Label) nodes[j].getChildren().get(0))
                	.setText(data.getBlacklist().get(j).getName());
                ((Label) nodes[j].getChildren().get(1))
                	.setText(data.getBlacklist().get(j).getEmail());
                
                pnItems2.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
