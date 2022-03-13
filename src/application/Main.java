package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;



public class Main extends Application {
    private double x, y;

    @Override
    public void start(Stage primaryStage) throws Exception {
    	try {
            System.out.println(getClass().getResource("App.fxml").getPath());
            System.out.println("hi");
            Parent root = FXMLLoader.load(getClass().getResource("App.fxml"));
            primaryStage.setScene(new Scene(root));
            //set stage borderless
            primaryStage.initStyle(StageStyle.UNDECORATED);

            //drag it here
            root.setOnMousePressed(event -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });
            root.setOnMouseDragged(event -> {

                primaryStage.setX(event.getScreenX() - x);
                primaryStage.setY(event.getScreenY() - y);
                primaryStage.setOpacity(0.8f);
            });
            root.setOnDragDone(event -> {
                primaryStage.setOpacity(1.0f);
            });
            root.setOnMouseReleased(event -> {
                primaryStage.setOpacity(1.0f);
            });
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}

