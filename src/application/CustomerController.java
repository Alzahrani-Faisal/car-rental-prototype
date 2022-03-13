package application;
import java.text.DateFormat;
import application.Classes.*;
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

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class CustomerController implements Initializable {
    private double x, y;
    // This class is to instantiate the configuration of getting elements from the txt files.
    private Data data = new Data();

    @FXML
    private VBox pnItems = null;

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
    private TableColumn<?, ?> tablePrice;

    @FXML
    private Button btnOverview, btnCars, btnInfo, btnSignout,
            editName, editEmail, editPassword, savePassword,
            saveEmail, saveName, CancelEditingName,
            CancelEditingEmail, CancelEditingPassword;

    @FXML
    private Pane panelCars, panelAccountInfo, panelOverview,
            checkout, loading;
    
    @FXML
    private Label totalOrders, deliveredOrders, pendingOrders,
            confirmationLabel, accountNameError, accountEmailError,
            accountPasswordError, accountConfirmationError,
            usernameGuidlines, passwordGuidelines;
    
    @FXML
    private TextField name, email;

    @FXML
    private PasswordField password, passwordConfirmation;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	data.setupCars();
    	data.setupCustomers();
    	data.setupOrders();

    	setTooltips();
    	createOrdersList();
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
        if (actionEvent.getSource() == btnCars) {
            showCars();
        }
        if (actionEvent.getSource() == btnInfo) {
            showAccountInfo();
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
        panelAccountInfo.setVisible(false);
        panelCars.setVisible(false);
        checkout.setVisible(false);
    }

    public void showCars() {
        panelCars.setVisible(true);
        panelOverview.setVisible(false);
        panelAccountInfo.setVisible(false);
        checkout.setVisible(false);
    }

    public void showAccountInfo() {
        panelAccountInfo.setVisible(true);
        panelOverview.setVisible(false);
        panelCars.setVisible(false);
        checkout.setVisible(false);
    }
    
    private void setTooltips() {
    	Tooltip usernameTooltip = new Tooltip();

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

    	usernameGuidlines.setTooltip(usernameTooltip);
    	
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
            	Customer customer = new Customer(scanner.next(),
            			scanner.next(),
            			scanner.next(),
            			Arrays.asList((scanner.next().split(","))));
            	
                name.setText(customer.getName());
                email.setText(customer.getEmail());
                password.setText(customer.getPassword());
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
    	
    	Customer updatedCustomer = getCustomer();
    	
    	ArrayList<Customer> updatedList = data.getCustomers(); 
		String lineToDelete = getCustomer().getName() + "    " +
				  getCustomer().getEmail() + "    " +
				  getCustomer().getPassword() + "    " +
				  getCustomer().printCars(getCustomer().getCars());
    	
    	if(mouseEvent.getSource() == saveName) {
    		if(usernameValidator.validate(name.getText())) {
    			
    			saveName.setVisible(false);
        		editName.setVisible(true);
        		CancelEditingName.setVisible(false);
        		usernameGuidlines.setVisible(false);
        		
        		updatedCustomer.setName(name.getText());
        		
		        removeLineFromFile("src/customers.txt", lineToDelete);
        		
        		ListIterator<Customer> iterator = updatedList.listIterator();
        				
        		while (iterator.hasNext()) {
        		     Customer next = iterator.next();
        		     if (next.equals(getCustomer())) {
        		         //Replace element
        		         iterator.set(updatedCustomer);
        		     }
        		 }
        		
	    		savaCredintials(updatedCustomer);
        		data.setupCustomers();

	    		name.setEditable(false);
	    		
    		} else {
    			temporaryMessage(accountNameError);
    		}
    	}
    	
    	if(mouseEvent.getSource() == saveEmail) {
    		if(emailValidator.validate(email.getText())) {
	    		saveEmail.setVisible(false);
	    		editEmail.setVisible(true);
	    		CancelEditingEmail.setVisible(false);
	    		
	    		updatedCustomer.setEmail(email.getText());
        		
		        removeLineFromFile("src/customers.txt", lineToDelete);
        		
        		ListIterator<Customer> iterator = updatedList.listIterator();
        				
        		while (iterator.hasNext()) {
        		     Customer next = iterator.next();
        		     if (next.equals(getCustomer())) {
        		         //Replace element
        		         iterator.set(updatedCustomer);
        		     }
        		 }
        		
	    		savaCredintials(updatedCustomer);
        		data.setupCustomers();
        		
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
		    		
		    		updatedCustomer.setPassword(password.getText());
	        		
			        removeLineFromFile("src/customers.txt", lineToDelete);
	        		
	        		ListIterator<Customer> iterator = updatedList.listIterator();
	        				
	        		while (iterator.hasNext()) {
	        		     Customer next = iterator.next();
	        		     if (next.equals(getCustomer())) {
	        		         //Replace element
	        		         iterator.set(updatedCustomer);
	        		     }
	        		 }
	        		
		    		savaCredintials(updatedCustomer);
	        		data.setupCustomers();
	        		
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
    
    private static void savaCredintials(Customer customer) {
    	
    	Customer newCustomer = new Customer(customer.getName(),
    			customer.getEmail(),
    			customer.getPassword(),
    			customer.getCars());

    	BufferedWriter writer1;

    	try {
			writer1 = new BufferedWriter(
			        new FileWriter("src/customers.txt", true));
	    	writer1.write(newCustomer.getName() + "    ");
	        writer1.write(newCustomer.getEmail() + "    ");
	        writer1.write(newCustomer.getPassword() + "    ");
	        writer1.write(newCustomer.printCars(newCustomer.getCars()));
	        
	        writer1.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	BufferedWriter writer;
		try {
			writer = new BufferedWriter(
			        new FileWriter("src/credintials.txt", false));
			
			writer.write(newCustomer.getName() + "    ");
	        writer.write(newCustomer.getEmail() + "    ");
	        writer.write(newCustomer.getPassword() + "    ");
	        writer.write(newCustomer.printCars(newCustomer.getCars()));
	        
	        writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
    }
    
    private Customer getCustomer() {
    	Customer customer = null;
    	String path = "src/credintials.txt";
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
                customer = new Customer(scanner.next(), scanner.next(), 
                		scanner.next(), Arrays.asList(scanner.next()));
            }
        }
        scanner.close();
        
        return customer;
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

          String line = null;

          //Read from the original file and write to the new
          //unless content matches data to be removed.
          while ((line = br.readLine()) != null) {

            if (!line.trim().equals(lineToRemove)) {

              pw.println(line);
              pw.flush();
            }
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
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    @FXML
    private void goToCheckout(MouseEvent mouseEvent) throws IOException {

        Date currentDate = new Date();

        String date = dateFormat.format(currentDate);
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, 7);
        Date due = c.getTime();
        date += "-"+ (dateFormat.format(due));


    	panelCars.setVisible(false);
    	loading.setVisible(true);

        ObservableList<Car> allCars, singleCar;
        allCars = carsTable.getItems();
        singleCar = carsTable.getSelectionModel().getSelectedItems();
        Data data = new Data();
        data.setupOrders();
        ArrayList <Order> orders = data.getOrders();


        orders.add(new Order(getCustomer().getEmail() ,Integer.parseInt(singleCar.get(0).getId()),date,singleCar.get(0).getPrice(),"active"));
        FileOutputStream fileOutputStream = new FileOutputStream("src/orders.txt");
        PrintWriter printWriter = new PrintWriter(fileOutputStream);
        for (int i =0;i<orders.size();i++){
            printWriter.println(orders.get(i).getCustomerEmail()+"\t"+orders.get(i).getCarID()+"\t"+orders.get(i).getDate()+
                    "\t"+orders.get(i).getPrice()+"\t"+orders.get(i).getStatus());
            if(i==orders.size()-2){
                printWriter.print( orders.get((i+1)).getCustomerEmail()+"\t"+orders.get((i+1)).getCarID()+"\t"+orders.get((i+1)).getDate()+
                        "\t"+orders.get((i+1)).getPrice()+"\t"+orders.get((i+1)).getStatus());
            break;}
        }
        printWriter.close();
        fileOutputStream.close();
        PauseTransition visiblePause = new PauseTransition(
                Duration.seconds(1)
        );
        visiblePause.setOnFinished(
                event -> {
                    loading.setVisible(false);
                    showOverview();
                });
        visiblePause.play();

        createOrdersList();
    }


    @FXML
    private void exitCheckout(MouseEvent mouseEvent) {
    	checkout.setVisible(false);
    	loading.setVisible(true);
    	
        PauseTransition visiblePause = new PauseTransition(
                Duration.seconds(2)
        );
        visiblePause.setOnFinished(
                event -> {
                    loading.setVisible(false);
                    panelCars.setVisible(true);
                });
        visiblePause.play();
    }
    
    private void createOrdersList() {
    	pnItems.getChildren().clear();
    	int numberOfOrders = 0;
        Data data = new Data();
        data.setupOrders();
        ArrayList<Order> orders = data.getOrders();
        System.out.println(orders.size());
    	ArrayList<Order> customerOrders = new ArrayList<>();

    	int countActiveOrders = 0;
    	int countPendingOrders = 0;
    	
    	for(int i = 0; i < orders.size(); i++) {
            System.out.println(customerOrders.size());

    	    if(orders.get(i).getCustomerEmail().contentEquals(getCustomer().getEmail())){
    	        numberOfOrders+=1;
    		if(orders.get(i).getStatus().contentEquals("active")) {
    			countActiveOrders++;
    		}
    		if(data.getOrders().get(i).getStatus().equals("pending")) {
    			countPendingOrders++;
    		}
    		customerOrders.add(orders.get(i));
    	    }
    	}
        totalOrders.setText(Integer.toString(numberOfOrders));
    	deliveredOrders.setText(Integer.toString(countActiveOrders));
    	pendingOrders.setText(Integer.toString(countPendingOrders));

    	
    	// Adding elements to orders list - overview page
        HBox[] nodes = new HBox[customerOrders.size()];
        for (int i = 0; i < nodes.length; i++) {
            try {
                final int j = i;
                nodes[i] = FXMLLoader.load(getClass().getResource("CustomerItem.fxml"));

                //give the items some effect

                nodes[i].setOnMouseEntered(event -> {
                    nodes[j].setStyle("-fx-background-color : #0A0E3F");
                });
                nodes[i].setOnMouseExited(event -> {
                    nodes[j].setStyle("-fx-background-color : #02030A");
                });
                                
                ((Label) nodes[j].getChildren().get(0))
            	.setText(Integer.toString(customerOrders.get(i).getCarID()));
                ((Label) nodes[j].getChildren().get(1))
            	.setText(customerOrders.get(i).getDate());
                ((Label) nodes[j].getChildren().get(2)).setText(orders.get(i).getPrice()+"");
            
                ((Button) nodes[j].getChildren().get(3)).setText(customerOrders.get(i).getStatus()+"");
                
                pnItems.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

