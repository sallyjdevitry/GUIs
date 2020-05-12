import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class main extends Application {
    public static final int SIZE = 9;
    TextField[][] cells = new TextField[SIZE][SIZE];
    Button solveButton = new Button("Solve");
    Button clearButton = new Button("Clear");
    Label statusLab = new Label();
    int[][] PuzzleArray = new int[9][9];


    public void start(Stage primaryStage) throws FileNotFoundException {
        getRandomPuzzleIntoList();
        //2d array of GridPane
        GridPane[][] panels = new GridPane[3][3];
        //main GridPane
        GridPane grid = new GridPane();
        grid.setStyle("-fx-border-color: blue");

        //loop to initialize the gridpane

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                grid.add(panels[row][col] = new GridPane(), row, col);
                panels[row][col].setStyle("-fx-border-color: blue");
            }
        }
        //initializing the 2d array of GridPane
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if ((PuzzleArray[row][col])!=0){
                cells[row][col] = new TextField(String.valueOf(PuzzleArray[row][col]));}
                else {
                    cells[row][col] = new TextField("");
                }
                panels[row / 3][col / 3].add(cells[row][col], col % 3, row % 3);
                cells[row][col].setPrefColumnCount(1);
            }
        }
        //hbox to store buttons and label
        HBox hb = new HBox(5);
        hb.getChildren().addAll(solveButton, clearButton);
        hb.setAlignment(Pos.CENTER);
        BorderPane bp = new BorderPane();
        bp.setCenter(grid);
        bp.setBottom(hb);
        bp.setTop(statusLab);
        bp.setAlignment(statusLab, Pos.CENTER);
        Scene scene = new Scene(bp, 400, 400);
        primaryStage.setTitle("Sudoku!!");
        primaryStage.setScene(scene);
        primaryStage.show();

        solveButton.setOnAction(event -> solve());

        clearButton.setOnAction(event -> {
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    //clearing the values in each cell
                    cells[row][col].setText(" ");
                    cells[row][col].setStyle("-fx-text-fill:black");
                }
            }
            try {
                start(primaryStage);
                statusLab.setText(" ");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

    }

    //method to solve the puzzle
    public void solve() {
        //create 2d array of ints to store the vals in the cells
        int[][] grid = new int[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                //if cell is empty assign zero.
                if (cells[row][col].getText().trim().length() == 0) {
                    grid[row][col] = 0;
                }
                //if not then convert the value to int and store it in myGrid
                else {
                    grid[row][col] = Integer.parseInt(cells[row][col].getText());
                    cells[row][col].setStyle("-fx-text-fill: black");
                }
            }
        }
        //call isValid to check it
        if (!Sudoku.isValid(grid)) {
            statusLab.setText("Cannot find solution! Click clear to get a new puzzle");

        }
        //call search method to solve sudoku
        else if (Sudoku.search(grid)) {
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    if(cells[row][col].getText().isEmpty()){
                    cells[row][col].setStyle("-fx-text-fill: blue");
                    cells[row][col].setText(grid[row][col] + " "); }

                    else{
                        continue;
                    }
                }
            }
        }
        //or no solution
        else {
            statusLab.setText("No Solution. Click clear to get a new puzzle.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


    public void getRandomPuzzleIntoList() throws FileNotFoundException {
        int randID = (int)(Math.random() * 7 + 1);
        File file = new File("src/Sudoku"+ String.valueOf(randID) + ".txt");
        System.out.println("src/Sudoku"+ String.valueOf(randID) + ".txt");
        Scanner scan = new Scanner(file);
        int current;
        while (scan.hasNextInt()) {
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    current = scan.nextInt();
                    PuzzleArray[row][col] = current;
                }
            }
        }
    }
}

