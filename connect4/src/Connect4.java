import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Connect4 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane pane = new GridPane();
//        pane.setAlignment(Pos.CENTER);
        Circle myCirc = new Circle(5);
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                pane.add(myCirc, i, j);
            }
        }
        Scene scene = new Scene(pane, 200, 250);
        primaryStage.setTitle("Connect 4");
        primaryStage.setScene(scene);

        primaryStage.show();

    }
}
