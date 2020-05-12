import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;



public class ConnectFour extends Application{
    boolean isPlayer1Turn = true;
    Circle[][] inputCircles = new Circle[6][7];
    int[] conFourIndexes = new int[8];
    int numFilled = 0;


    public void start(Stage stage){
        StackPane rootPane = new StackPane();
        GridPane grid = new GridPane();
        HBox hpane = new HBox();
        grid.setAlignment(Pos.BOTTOM_CENTER);

        Text firstTurnText = new Text(40, 10, "Welcome to connect4!! It is player 1's turn.");
        firstTurnText.setFont(Font.font("Courier", 20));
        hpane.getChildren().add(firstTurnText);


        //create all the circles
        for (int i = 0; i <inputCircles.length; i++) {
            for (int j = 0; j < inputCircles[i].length; j++) {
                inputCircles[i][j] = new Circle(40);
                inputCircles[i][j].setFill(Color.WHITE);
                grid.add(inputCircles[i][j], j, i);
                final int row = i;
                final int column = j;

                //set action to each circle
                inputCircles[i][j].setOnMouseClicked(event -> {
                    if (isAvailable(row, column) && isPlayer1Turn && inputCircles[row][column].getFill() == Color.WHITE) {
                        inputCircles[row][column].setFill(Color.RED);
                        if (fourInArow()) {
                            flashCircles();
                            Text winText = new Text("We have a winner!");
                            winText.setFont(Font.font("Courier", 26));
                            hpane.getChildren().clear();
                            hpane.getChildren().add(winText);


                        } else {
                            isPlayer1Turn = !isPlayer1Turn;
                            Text turnText = new Text("It is player 2's turn!");
                            turnText.setFont(Font.font("Courier", 26));
                            hpane.getChildren().clear();
                            hpane.getChildren().add(turnText);
                        }
                    } else if (isAvailable(row, column) && !isPlayer1Turn && inputCircles[row][column].getFill() == Color.WHITE) {
                        inputCircles[row][column].setFill(Color.YELLOW);
                        if (fourInArow()) {
                            flashCircles();
                            Text winText = new Text("We have a winner!");
                            winText.setFont(Font.font("Courier", 26));
                            hpane.getChildren().clear();
                            hpane.getChildren().add(winText);
                        } else {
                            isPlayer1Turn = true;
                            Text turnText = new Text("It is player 1's turn!");
                            turnText.setFont(Font.font("Courier", 26));
                            hpane.getChildren().clear();
                            hpane.getChildren().add(turnText);
                        }
                    } else if (!isAvailable(row, column) && isPlayer1Turn) {
                        Text turnText = new Text("It is player 1's turn!");
                        turnText.setFont(Font.font("Courier", 26));
                        hpane.getChildren().clear();
                        hpane.getChildren().add(turnText);
                    }
                    else {
                        Text turnText = new Text("It is player 2's turn!");
                        turnText.setFont(Font.font("Courier", 26));
                        hpane.getChildren().clear();
                        hpane.getChildren().add(turnText);
                    }

                });

            }
        }

        //checking for a draw
        for (int i = 0; i <inputCircles.length; i++) {
            for (int j = 0; j < inputCircles[i].length; j++) {
                if ((inputCircles[i][j].getFill() == Color.RED) || (inputCircles[i][j].getFill() == Color.YELLOW)) {
                    numFilled += 1;
                }
            }
        }
                if (numFilled == 42) {
                    Text turnText = new Text("It's a draw!");
                    turnText.setFont(Font.font("Courier", 26));
                    hpane.getChildren().clear();
                    hpane.getChildren().add(turnText);
                }

                rootPane.getChildren().addAll(hpane, grid);
                Scene scene = new Scene(rootPane, 575, 520);
                scene.setFill(Color.BLUE);
                stage.setTitle("Connect4!");
                stage.setScene(scene);
                stage.show();
            }



    public static void main(String[] args){
        launch(args);
    }

    private boolean isAvailable(int row, int col){
        if(row ==inputCircles.length-1){
            return inputCircles[row][col].getFill() ==Color.WHITE;
        }
        return (inputCircles[row+1][col].getFill()!=Color.WHITE);
    }

    public boolean fourInArow(){
        Color color = (isPlayer1Turn) ? Color.RED: Color.YELLOW;
        int occurrence = 0;
        for (int j = 0; j < inputCircles[0].length - 3; j++){
            //so the row is always starting on the last row
            int y = inputCircles.length - 1;
            int x = j;
            while (x < inputCircles[0].length && y >= 0){
                if (inputCircles[y][x].getFill() == color){
                    occurrence++;
                    if(occurrence ==4){
                        saveOccurrenceValue(y,x,false, true);
                        return true;
                    }
                } else {
                    occurrence = 0;
                }
                x++;
                y--;
            }
        }
        for (int i = inputCircles.length-2; i >2; i--){
            int x=0;
            int y=i;
            occurrence = 0;
            while (x<inputCircles[0].length && y>=0){
                if (inputCircles[y][x].getFill()==color){
                    occurrence++;
                    if(occurrence ==4){
                        saveOccurrenceValue(y,x,false,true);
                        return true;
                    }
                } else {
                    occurrence = 0;
                }
                x++;
                y--;
            }
        }
        for (int j = inputCircles[0].length-1; j>=3; j--){
            int y = inputCircles.length-1;
            int x = j;
            occurrence = 0;
            while(x>=0 && y>=0){
                if (inputCircles[y][x].getFill() == color){
                    occurrence++;
                    if (occurrence == 4) {
                        saveOccurrenceValue(y,x,false,false);
                        return true;
                    }
                } else {
                    occurrence = 0;
                }
                x--;
                y--;
            }
        }
        for (int i = inputCircles.length-2; i>2; i--){
            int x = inputCircles[0].length-1;
            int y = i;
            occurrence = 0;
            while(x>=0 && y>=0){
                if (inputCircles[y][x].getFill()== color){
                    occurrence++;
                    if(occurrence==4){
                        saveOccurrenceValue(y,x, false, false);
                        return true;
                    }
                } else {
                    occurrence=0;
                }
                x--;
                y--;
            }
        }
        //checking the rows
        for(int y=0;y<inputCircles.length; y++){
            for(int x=0; x<inputCircles[y].length; x++){
                if(inputCircles[y][x].getFill()==color){
                    occurrence++;
                    if(occurrence==4){
                        int sequence = 0;
                        for (int i=0; i<8; i+=2){
                            conFourIndexes[i] = y;
                            conFourIndexes[i+1] = x-sequence++;
                        }
                        return true;
                    }
                } else {
                    occurrence = 0;
                }
            }
            occurrence = 0;
        }
        //checking the columns
        for (int x=0; x<inputCircles[0].length; x++){
            for (int y=0; y<inputCircles.length; y++){
                if (inputCircles[y][x].getFill()==color){
                    occurrence++;
                    if (occurrence ==4){
                        int sequence = 0;
                        for (int i=0; i<8; i+=2){
                            conFourIndexes[i] = y-sequence++;
                            conFourIndexes[i+1] = x;
                        }
                        return true;
                    }
                } else{
                    occurrence = 0;
                }
            }
            occurrence = 0;
        }
        return false;
    }

    public void saveOccurrenceValue(int y, int x, boolean yIsIncrement, boolean xIsIncrement){
        int yIncrement = (yIsIncrement)? -1:1;
        int xIncrement = (xIsIncrement)? -1:1;
        for (int i=0; i<8; i+=2){
            conFourIndexes[i] = y;
            conFourIndexes[i+1] = x;
            y+= yIncrement;
            x+= xIncrement;
        }
    }

    public void flashCircles() {
        for (int i=0; i<8; i+=2){
            Circle c = inputCircles[conFourIndexes[i]][conFourIndexes[i+1]];
            for (int j=0; j<100; j++){
                c.setFill(Color.LAWNGREEN);
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.2), evt -> c.setVisible(false)),
                        new KeyFrame(Duration.seconds( 0.3), evt -> c.setVisible(true)));
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.play();

            }
        }
    }
}

