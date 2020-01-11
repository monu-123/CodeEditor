package codeeditor;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class CodeEditor extends Application{
    public static Stage primaryStage;
    public static Scene primaryScene;
    public static AnchorPane anchorPane;
    public static void main(String args[])
    {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        primaryStage=stage;
        stage.getIcons().add(new Image("/resources/sourceCodeEditor.png"));
        stage.setTitle("CodeEditor");
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(CodeEditor.class.getResource("/resources/main.fxml"));
        Parent root=loader.load();
        anchorPane=(AnchorPane)root;
        anchorPane.setStyle("-fx-fill:white; -fx-border-width: 0 0 1 0; -fx-border-color: silver;");
        VBox vbox1=(VBox) anchorPane.getChildren().get(0);
        VBox.setVgrow(anchorPane.getChildren().get(0), Priority.ALWAYS);
        VBox vbox2=(VBox) anchorPane.getChildren().get(1);
        ToolBar toolBar=(ToolBar) vbox1.getChildren().get(1);
        Button runButton=(Button)toolBar.getItems().get(0);
        vbox1.setStyle("-fx-fill:white");
        //runButton.setGraphic(new ImageView(new Image("/resources/play.png")));
        Scene scene=new Scene(root,900,700);
        primaryScene=scene;
        
        stage.setScene(scene);
        stage.show();
    }
}
