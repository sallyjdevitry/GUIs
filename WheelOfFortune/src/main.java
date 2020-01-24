import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.util.Duration;

//import javax.print.attribute.standard.Media;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Scanner;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;




public class main extends Application {
    String phrase;
    int i = 0;
    int counter = 0;
    List<String> phrasesList = new ArrayList<>();
    int changer = 0;
    int changerIn = 0;
    int correctCount = 0;
    int numberOfSpaces = 0;
    String [] winningsList = {"200", "500", "400", "600", "700", "100", "800", "300"};
    Color [] colorList = {Color.RED, Color.BLUE, Color.ORANGE, Color.MEDIUMPURPLE, Color.YELLOW, Color.HOTPINK, Color.GREEN, Color.TRANSPARENT};
    int [] winningsOrder = {400, 700, 800, 500, 100, 500, 100, 400,200, 800, 100, 700, 700,100,800,300,500,700,300,600,300};
    int changer1 = 0;
    int numListID = 0;
    double theta = (Math.PI)/12;
    int scoreNum = 0;
    int wheelRotation = 415;
    int winningsID = 0;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        counter = 0;
        numberOfSpaces = 0;

        MediaPlayer musicplayer;

        Media mp3MusicFile = new Media(getClass().getResource("WOFIntro.mp3").toExternalForm());

        musicplayer = new MediaPlayer(mp3MusicFile);
        musicplayer.setAutoPlay(true);
        musicplayer.setVolume(0.9);   //
        musicplayer.play();


        //getting the phrases into a list
        try {
            Scanner scanner = new Scanner(new File("src/phrases.txt"));
            while (scanner.hasNextLine()) {
                phrasesList.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //getting a random phrase for the round
        phrase = getRandomPhrase(phrasesList);

        //setting up the panes
        Pane rootPane = new Pane();
        Pane pane = new Pane();
        Pane wheelPane = new Pane();
        rootPane.getChildren().add(pane);
        rootPane.getChildren().add(wheelPane);

        pane.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY)));
        wheelPane.setBackground (new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        wheelPane.setLayoutX(15);
        wheelPane.setLayoutY(15);

        //add yellow arrow
        Image arrow = new Image("https://www.pngkey.com/png/detail/170-1709969_arrow-outline-yellow-left-yellow-right-arrow-png.png");
        ImageView imageView = new ImageView(arrow);
        imageView.setX(410);
        imageView.setY(125);
        imageView.setRotate(-15);
        imageView.setFitHeight(50);
        imageView.setFitWidth(100);

        pane.getChildren().add(imageView);

        //creating colored closed arcs with winning value in them
        while (changer1<360 && numListID <=7){
            Arc arc1 = new Arc(210, 210, 210,210, changer1, 45);
            arc1.setType(ArcType.ROUND);
            arc1.setFill(colorList[numListID]);
            arc1.setStroke(Color.BLACK);

            wheelPane.getChildren().add(arc1);

            Text winnings = new Text(winningsList[numListID]);
            winnings.setX(190 + 160*Math.cos((theta)));
            winnings.setY(210 + 160*Math.sin((theta)));
            winnings.setFont(Font.font(20));
            wheelPane.getChildren().add(winnings);

            changer1+= 45;
            numListID+=1;
            theta -= (Math.PI/4); }

        //score box
        Text score = new Text("SCORE");
        score.setX(940);
        score.setY(100);
        score.setUnderline(true);
        score.setFont(Font.font(22));
        pane.getChildren().add(score);

        Rectangle scoreRect = new Rectangle(900, 70, 160, 110);
        scoreRect.setFill(Color.TRANSPARENT);
        scoreRect.setStroke(Color.BLACK);
        pane.getChildren().add(scoreRect);

        Text startScore = new Text("0");
        startScore.setX(970);
        startScore.setY(145);
        startScore.setFont(Font.font(24));
        pane.getChildren().add(startScore);

        //add spin button
        Button spinBt = new Button("SPIN!");
        spinBt.setLayoutX(600);
        spinBt.setLayoutY(70);
        rootPane.getChildren().add(spinBt);

        changer = 0;
        changerIn = 0;

        spinBt.setOnAction(event -> {
            makeWheelSpin(wheelPane);

            //add textbox for letter entry
            TextField letter = new TextField();
            Label label1 = new Label("Enter your guess(lowercase):");
            Button guessButton = new Button("guess!");
            VBox vb = new VBox();
            vb.setPadding(new Insets(140, 5, 5, 600));
            vb.getChildren().addAll(label1, letter, guessButton);
            pane.getChildren().add(vb);

            phrase = phrase.toLowerCase();
            System.out.println(phrase);


            guessButton.setOnAction(event1 -> {

                if (letter.getText().isEmpty()){
                    System.out.print(" ");
                }

                else if (phrase.contains(letter.getText())) {
                    scoreNum += winningsOrder[winningsID];

                    winningsID+=1;
                    pane.getChildren().remove(startScore);
                    Rectangle coverUp = new Rectangle(950, 120, 100, 50);
                    coverUp.setFill(Color.WHITESMOKE);
                    pane.getChildren().add(coverUp);
                    Text resetScore = new Text (Integer.toString(scoreNum));
                    resetScore.setX(970);
                    resetScore.setY(145);
                    resetScore.setFont(Font.font(24));
                    pane.getChildren().add(resetScore);

                    int indToPrint = phrase.indexOf(letter.getText());

                    while (indToPrint >= 0) {
                        Text toPrint = new Text(letter.getText());

                        toPrint.setFont(Font.font(30));
                        toPrint.setX(50 + (30 * indToPrint));
                        toPrint.setY(550);
                        pane.getChildren().add(toPrint);
                        correctCount+=1;

                        indToPrint = phrase.indexOf(letter.getText(), indToPrint + 1);


                    }


                    Text isCorrect = new Text(letter.getText() + " is Correct!");
                    isCorrect.setFill(Color.GREEN);
                    isCorrect.setX(605);
                    isCorrect.setY(230 + changer);
                    changer += 17;

                    pane.getChildren().add(isCorrect);
                    letter.clear();


                    if (correctCount == (phrase.length()-numberOfSpaces)){
                        Text winner1 = new Text("HOORAY!");
                        Text winner2 = new Text("YOU WIN.");

                        //give the option of clearing the board with a combo box.
                        ObservableList<String> options = FXCollections.observableArrayList("Reset Board");
                        final ComboBox comboBox = new ComboBox(options);
                        comboBox.setLayoutX(750);
                        comboBox.setLayoutY(350);
                        pane.getChildren().add(comboBox);
                        comboBox.setOnAction(event2 -> {
                            pane.getChildren().clear();
                            wheelPane.getChildren().clear();
                            vb.getChildren().clear();
                            correctCount = 0;
                            counter = 0;
                            numberOfSpaces = 0;
                            rootPane.getChildren().clear();
                            changer1 =0;
                            numListID = 0;
                            scoreNum = 0;
                            winningsID = 0;
                            wheelRotation = 415;
                            start(primaryStage);});


                        winner1.setFill(Color.GREEN);
                        winner2.setFill(Color.GREEN);
                        winner1.setFont(Font.font(100));
                        winner2.setFont(Font.font(100));
                        winner1.setX(600);
                        winner1.setY(100);
                        winner2.setX(600);
                        winner2.setY(200);
                        pane.getChildren().add(winner1);
                        pane.getChildren().add(winner2);

                    }


                } else {

                    Text isIncorrect = new Text(letter.getText() + " is Incorrect:(");
                    winningsID+=1;

                    isIncorrect.setFill(Color.RED);
                    isIncorrect.setX(710);
                    isIncorrect.setY(222 + changerIn);
                    changerIn += 17;

                    pane.getChildren().add(isIncorrect);

                    scoreNum = 0;
                    pane.getChildren().remove(startScore);
                    Rectangle coverUp = new Rectangle(950, 120, 100, 50);
                    coverUp.setFill(Color.WHITESMOKE);
                    pane.getChildren().add(coverUp);
                    Text resetScore = new Text("0");
                    resetScore.setX(970);
                    resetScore.setY(145);
                    resetScore.setFont(Font.font(24));
                    pane.getChildren().add(resetScore);

                    letter.clear();
                    counter += 1;

                }

            });

            wheelRotation+=25;
        });

        i = 0;


        outputBlankLines(pane);

        Scene scene = new Scene(rootPane, 1200.0D, 600.0D);
        scene.setFill(Color.WHITE);
        primaryStage.setTitle("WHEEL OF FORTUNE");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    //functions

    public void outputBlankLines(Pane pane){
        int startX = 50;
        int endX = 70;
        while (i < phrase.length()) {

            if (phrase.charAt(i)!= ' ') {

                Line underlines = new Line(startX, 550, endX, 550);
                underlines.setStroke(Color.BLACK);
                pane.getChildren().add(underlines);

            }
            else if (phrase.charAt(i)== ' ') {
                numberOfSpaces += 1;
            }

            i++;
            startX += 30;
            endX += 30;
        }
    }

    public void makeWheelSpin(Pane wheelPane){
        RotateTransition rt = new RotateTransition(Duration.millis(4000), wheelPane);
        rt.setByAngle(wheelRotation);
        rt.setAxis(Rotate.Z_AXIS);
        rt.setCycleCount(1);
        rt.setRate(2);
        rt.play();

    }



    public String getRandomPhrase(List list){
        int getterInd = (int)(Math.random()*25);

        String string = (list.get(getterInd)).toString();
        return string;
    }

}
