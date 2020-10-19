import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import com.sun.prism.paint.Color;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Arcade extends Application {

	File highScoreFile2 = new File("Scoring");
	File checkerFile = new File("CheckerFile");
	
	Timeline timelineIntro;
	KeyFrame keyFrameIntro;
	int changeXrec2 = -3;
	int changeYrec2 = -3;
	int changeXcirc = 4;
	int changeYcirc = 4;
	int changeXsqr = 2;
	int changeYsqr = -2;
	
	//breakout instance variables
    Random rng = new Random();
    Text numLives, score;
    Rectangle brick;
	Timeline timeline;
	KeyFrame keyFrame;
	Stage gameOver;
	Rectangle[][] row2;
	Rectangle[][] row4;
	Rectangle[][] row6;
	Rectangle[][] row8;
    double changeX, changeY;
    int lives = 3;
    int keepScore = 0;
    int numHits = 0;
    boolean changed = false;
    boolean changed2 = false;
    boolean changed3 = false;
    boolean changed4 = false;
    boolean changed5 = false;
    String difficulty = "";
    
    //Checkers instance variables
    GridPane pieces = new GridPane();
    //final Pane spring = new Pane();
    double clickX;
	double clickY;
	int checkerRow = -1;
    int checkerCol = -1;
    int emptyRow = -1; 
    int emptyCol = -1;
    boolean clickedChecker = false;
    boolean clickedEmpty = false;
    Circle piece;
    boolean orangeTurn = false;
    int numOrange, numBlue;
    Text whichMove = new Text("Blue Turn");
    int numOrangeMoves;
    int numBlueMoves;
    
    private Timeline paddleAnimation = new Timeline();
    
    @Override
    public void start(Stage stage) {

	/* You are allowed to rewrite this start method, add other methods, 
	 * files, classes, etc., as needed. This currently contains some 
	 * simple sample code for mouse and keyboard interactions with a node
	 * (rectangle) in a group. 
	 */
	
    Pane intro = new Pane();
    
    //Text team = new Text("\"The Mamals\"");
    Text names = new Text("Zachary Cauley & Brian Woolfolk");
    Text project = new Text("cs1302-arcade");
    Button enterArcade = new Button("Enter Arcade!");
    
    names.setStyle("-fx-fill: White;");
    //team.setStyle("-fx-fill: White;");
    project.setStyle("-fx-fill: White;");
    
    //team.setLayoutX(200);
    //team.setLayoutY(260);
    names.setLayoutX(200);
    names.setLayoutY(300);
    project.setLayoutX(200);
    project.setLayoutY(330);
    enterArcade.setLayoutX(200);
    enterArcade.setLayoutY(360);
    
    Rectangle rec2 = new Rectangle(170, 100, javafx.scene.paint.Color.RED);
    rec2.setLayoutX(200);
    rec2.setLayoutY(200);
    
    Circle circ = new Circle(80, javafx.scene.paint.Color.GREEN);
    circ.setLayoutX(200);
    circ.setLayoutY(200);
    
    Rectangle sqr = new Rectangle(100, 100, javafx.scene.paint.Color.BLUE);
    sqr.setLayoutX(200);
    sqr.setLayoutY(200);
    
    
	intro.getChildren().addAll(names, project, enterArcade, rec2, circ, sqr);
    Scene introScene = new Scene(intro, 960, 540);

    intro.setStyle("-fx-background-image: url(https://i.imgur.com/H5MU94n.png);"
			+ "-fx-background-size: cover, auto;");
    
    stage.setScene(introScene);
    stage.sizeToScene();
    stage.show();
    changeXrec2 = -3;
    changeYrec2 = -3;
    
   
   
    EventHandler<ActionEvent> handlerTeam = event2 -> {
    		rec2.setLayoutX(rec2.getLayoutX() + changeXrec2);
    		rec2.setLayoutY(rec2.getLayoutY() + changeYrec2);
    		
    		circ.setLayoutX(circ.getLayoutX() + changeXcirc);
    		circ.setLayoutY(circ.getLayoutY() + changeYcirc);
    		
    		sqr.setLayoutX(sqr.getLayoutX() + changeXsqr);
    		sqr.setLayoutY(sqr.getLayoutY() + changeYsqr);
    		
    		//bounce off walls for rectangle
    		if (rec2.getLayoutX() >= (960) || rec2.getLayoutX() <= 0) {
    			changeXrec2 *= -1;
    		}
    		
    		if (rec2.getLayoutY() <= (0) || rec2.getLayoutY() >= 540) {
    			changeYrec2 *= -1;
    		}
    		
    		//bounce off walls for circle
    		if (circ.getLayoutX() >= (960) || circ.getLayoutX() <= 0) {
    			changeXcirc *= -1;
    		}
    		
    		if (circ.getLayoutY() <= (0) || circ.getLayoutY() >= 540) {
    			changeYcirc *= -1;
    		}
    		
    		//bounce off walls for square
    		if (sqr.getLayoutX() >= (960) || sqr.getLayoutX() <= 0) {
    			changeXsqr *= -1;
    		}
    		
    		if (sqr.getLayoutY() <= (0) || sqr.getLayoutY() >= 540) {
    			changeYsqr *= -1;
    		}
    };
    
    
    
    keyFrameIntro = new KeyFrame(Duration.millis(15), handlerTeam);
	timelineIntro = new Timeline();
	timelineIntro.setCycleCount(Timeline.INDEFINITE);
	timelineIntro.getKeyFrames().add(keyFrameIntro);
	timelineIntro.play();
    
    
	 
	BorderPane start = new BorderPane();
	
	//Creating Top Menu
    MenuBar menu = new MenuBar();
    Menu file = new Menu("File");
    Menu highScoreButton = new Menu("High Scores");
    MenuItem breakoutScore = new MenuItem("Breakout");
    MenuItem checkersScore = new MenuItem("Checkers");
    MenuItem exit = new MenuItem("Exit");
	
    
    //exit set on action
    exit.setOnAction(e -> {
    	Platform.exit();
    	System.exit(0);
    });
    
    checkersScore.setOnAction(e14 -> {
    	String[] highScoreArray = new String[200];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(checkerFile));
            String line = reader.readLine();
            int count = 0;
            while (line != null)                 // read the score file line by line
            {
                try {
                   // int score = Integer.parseInt(line.trim());   // parse each line as an int
                    //if (score > highScore)                       // and keep track of the largest
                   // { 
                     //   highScore = score; 
                   // }
                	
                		highScoreArray[count] = line.trim();
                		System.out.println(highScoreArray[count]);
                		count++;
                	
                	
                	
                } catch (NumberFormatException e1) {
                    // ignore invalid scores
                    //System.err.println("ignoring invalid score: " + line);
                }
                line = reader.readLine();
            }
            reader.close();

        } catch (IOException ex) {
            System.err.println("ERROR reading scores from file");
        }
        //System.out.println(highScore);
        int[] scoreArray = new int[highScoreArray.length];
        
        for (int i = 1; i <highScoreArray.length; i++) {
        	//System.out.println(highScoreArray[i]);
        	if (highScoreArray[i] != null && highScoreArray[i] != "") {
	        	int space = highScoreArray[i].indexOf(' ');
	        	//System.out.println(space);

	        	String scoreString = highScoreArray[i].substring(space+1);
	        	int scoreInt = Integer.parseInt(scoreString);
	        	scoreArray[i] = scoreInt;
        	}
        }
        
        int n = scoreArray.length;
        
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (scoreArray[j] < scoreArray[j+1])
                {
                    // swap temp and arr[i]
                    int temp = scoreArray[j];
                    scoreArray[j+1] = scoreArray[j];
                    scoreArray[j] = temp;
                    
                    String tempS = highScoreArray[j+1];
                    highScoreArray[j+1] = highScoreArray[j];
                    highScoreArray[j] = tempS;
                }
        
        Stage scoreStage = new Stage();
		Pane scorePane = new Pane();
		
		String finalHighScore = "High Scores Table!";
		for (int i = 0; i < 5; i++) {
			finalHighScore += "\n" + highScoreArray[i];		
			}
		
		Text topScores = new Text(finalHighScore);
		scoreStage.initModality(Modality.APPLICATION_MODAL);
		topScores.setLayoutX(20);
		topScores.setLayoutY(20);
		scorePane.getChildren().addAll(topScores);
		Scene scene3 = new Scene(scorePane, 230, 200);
		scoreStage.setScene(scene3);
		scoreStage.sizeToScene();
		scoreStage.setResizable(false);
		scoreStage.show();
    });
    
    breakoutScore.setOnAction(e12 -> {
    	//int highScore = 0;
    	String[] highScoreArray = new String[200];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(highScoreFile2));
            String line = reader.readLine();
            int count = 0;
            while (line != null)                 // read the score file line by line
            {
                try {
                   // int score = Integer.parseInt(line.trim());   // parse each line as an int
                    //if (score > highScore)                       // and keep track of the largest
                   // { 
                     //   highScore = score; 
                   // }
                	
                		highScoreArray[count] = line.trim();
                		System.out.println(highScoreArray[count]);
                		count++;
                	
                	
                	
                } catch (NumberFormatException e1) {
                    // ignore invalid scores
                    //System.err.println("ignoring invalid score: " + line);
                }
                line = reader.readLine();
            }
            reader.close();

        } catch (IOException ex) {
            System.err.println("ERROR reading scores from file");
        }
        //System.out.println(highScore);
        int[] scoreArray = new int[highScoreArray.length];
        
        for (int i = 1; i <highScoreArray.length; i++) {
        	//System.out.println(highScoreArray[i]);
        	if (highScoreArray[i] != null && highScoreArray[i] != "") {
	        	int space = highScoreArray[i].indexOf(' ');
	        	//System.out.println(space);

	        	String scoreString = highScoreArray[i].substring(space+1);
	        	int scoreInt = Integer.parseInt(scoreString);
	        	scoreArray[i] = scoreInt;
        	}
        }
        
        int n = scoreArray.length;
        
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (scoreArray[j] > scoreArray[j+1])
                {
                    // swap temp and arr[i]
                    int temp = scoreArray[j];
                    scoreArray[j] = scoreArray[j+1];
                    scoreArray[j+1] = temp;
                    
                    String tempS = highScoreArray[j];
                    highScoreArray[j] = highScoreArray[j+1];
                    highScoreArray[j+1] = tempS;
                }
        
        Stage scoreStage = new Stage();
		Pane scorePane = new Pane();
		
		String finalHighScore = "High Scores Table!";
		for (int i = 199; i > 194; i--) {
			finalHighScore += "\n" +highScoreArray[i];		
			}
		
		Text topScores = new Text(finalHighScore);
		scoreStage.initModality(Modality.APPLICATION_MODAL);
		topScores.setLayoutX(20);
		topScores.setLayoutY(20);
		scorePane.getChildren().addAll(topScores);
		Scene scene3 = new Scene(scorePane, 230, 200);
		scoreStage.setScene(scene3);
		scoreStage.sizeToScene();
		scoreStage.setResizable(false);
		scoreStage.show();
    });
    
    file.getItems().add(exit);
    highScoreButton.getItems().addAll(breakoutScore, checkersScore);
    menu.getMenus().addAll(file, highScoreButton);
    start.setTop(menu);
    
    Button breakout = new Button();
    Image breakoutImage = new Image("https://upload.wikimedia.org/wikipedia/en/7/7a/BreakOut_arcadeflyer.png");
    breakout.setGraphic(new ImageView(breakoutImage));
    
    //Breakout Game
    //8 rows, 14 bricks each
    breakout.setOnAction(e -> {
    	keepScore = 0;
    	lives = 3;
    	Group breakoutGroup = new Group();
    	Stage breakoutStage = new Stage();
    	breakoutStage.initModality(Modality.APPLICATION_MODAL);
    	Scene breakoutScene = new Scene(breakoutGroup, 605, 400);
    	Button  play = new Button("Play");
    	//play.relocate(300, 200);
    //breakoutGroup.getChildren().add(play);
    	
    	//instruction window
        Stage instructionStage = new Stage();
		Pane instructionPane = new Pane();
		Button startGame = new Button("Start");
		Text iMessage = new Text("Use arrowkeys to move paddle");
		instructionStage.initModality(Modality.APPLICATION_MODAL);
		startGame.setLayoutX(80);
		startGame.setLayoutY(50);
		iMessage.setLayoutX(20);
		iMessage.setLayoutY(100);
		startGame.setOnAction(e11 -> {
			instructionStage.close();
		});
		instructionPane.getChildren().addAll(iMessage, startGame);
		Scene scene3 = new Scene(instructionPane, 230, 200);
		instructionStage.setScene(scene3);
		instructionStage.sizeToScene();
		instructionStage.setResizable(false);
    	
    	//default level
    	Button defaultB = new Button("Default");
    	defaultB.relocate(300, 200);
    breakoutGroup.getChildren().add(defaultB);
    	
    	//Paddle
    	Rectangle paddle = new Rectangle();
    	paddle.setX(275);
    	paddle.setY(385);
    	paddle.setWidth(200);
    	paddle.setHeight(10);
    	breakoutGroup.getChildren().add(paddle);
    	
    	//num lives
    	numLives = new Text("Lives = " + lives);
    	numLives.setX(20);
    	numLives.setY(20);
    	breakoutGroup.getChildren().add(numLives);
    	
    	//score
    	score = new Text("Difficulty: " + difficulty + " Score = "+ keepScore);
    	score.setX(350);
    	score.setY(20);
    	breakoutGroup.getChildren().add(score);
    	
    	//difficulty
    	//difficultyTop = new Text(difficulty);
    	//difficultyTop.setX(400);
    	//difficultyTop.setY(20);
    	
    
    	//ball
    	Circle ball = new Circle(6);
    	ball.relocate(300, 375);
    	ball.setFill(javafx.scene.paint.Color.CRIMSON);
    	breakoutGroup.getChildren().add(ball);
    	
    	//adding to gridpane
    	GridPane pane = new GridPane();
    	makeBricks();
    	for (int i = 0; i < row2.length; i++){
    	    for (int j = 0; j < row2[1].length; j++){
    	    		pane.add(row8[i][j], i, j);
    	    }
    	}
    	for (int i = 0; i < row4.length; i++) {
    		for (int j = 0; j < row2[1].length; j++) {
    			pane.add(row6[i][j], i, j+2);
    		}
    	}
    	for (int i = 0; i < row4.length; i++) {
    		for (int j = 0; j < row2[1].length; j++) {
    			pane.add(row4[i][j], i, j+4);
    		}
    	}
    	for (int i = 0; i < row4.length; i++) {
    		for (int j = 0; j < row2[1].length; j++) {
    			pane.add(row2[i][j], i, j+6);
    		}
    	}
    	pane.setLayoutX(3);
    	pane.setLayoutY(30);
    	breakoutGroup.getChildren().add(pane);
    	
    	//exit button
    	Button exitGame = new Button("Return Home");
    	exitGame.setOnAction(e5 -> {
    		gameOver.close();
    		breakoutStage.close();
    	});
    	
    	//hard mode button
    	Button hard = new Button("Hard");
    	hard.relocate(100, 200);
    	breakoutGroup.getChildren().add(hard);
    	
    	Button newGame = new Button("Play Again");
    	newGame.setOnAction(e4 -> {
    		timeline.pause();
    		breakoutGroup.getChildren().remove(numLives);
    		breakoutGroup.getChildren().remove(score);
    		lives = 3;
    		keepScore = 0;
    		numLives = new Text("Lives = " + lives);
    		numLives.setX(20);
    		numLives.setY(20);
    		score = new Text("Difficulty: " + difficulty + " Score = "+ keepScore);
        	score.setX(350);
        	score.setY(20);
        	breakoutGroup.getChildren().remove(play);
        	breakoutGroup.getChildren().add(defaultB);
        	defaultB.relocate(100, 200);
        	breakoutGroup.getChildren().add(hard);
        	hard.relocate(200, 200);
        	breakoutGroup.getChildren().add(numLives);
    		breakoutGroup.getChildren().add(score);
    		ball.relocate(300, 358);
    		paddle.setWidth(200);
    		paddle.setX(275);
    		paddle.setY(385);
    		changeX = -3;
    		changeY = -3;
    		numHits = 0;
    		pane.getChildren().clear();
    		breakoutGroup.getChildren().remove(pane);
    		for (int i = 0; i < row2.length; i++) {
	    		for (int j = 0; j < row2[1].length; j++) {
	    			if (row2[i][j] != null) {
	    				row2[i][j].setFill(javafx.scene.paint.Color.TRANSPARENT);
	    				row2[i][j] = null;
	    			}
	    		}
    		}
    		for (int i = 0; i < row4.length; i++) {
	    		for (int j = 0; j < row4[1].length; j++) {
	    			if (row4[i][j] != null) {
	    				row4[i][j].setFill(javafx.scene.paint.Color.TRANSPARENT);
	    				row4[i][j] = null;
	    			}
	    		}
    		}
    		for (int i = 0; i < row6.length; i++) {
	    		for (int j = 0; j < row6[1].length; j++) {
	    			if (row6[i][j] != null) {
	    				row6[i][j].setFill(javafx.scene.paint.Color.TRANSPARENT);
	    				row6[i][j] = null;
	    			}
	    		}
    		}
    		for (int i = 0; i < row8.length; i++) {
	    		for (int j = 0; j < row8[1].length; j++) {
	    			if (row8[i][j] != null) {
	    				row8[i][j].setFill(javafx.scene.paint.Color.TRANSPARENT);
	    				row8[i][j] = null;
	    			}
	    		}
    		}
    		makeBricks();
    		for (int i = 0; i < row2.length; i++){
	    	    for (int j = 0; j < row2[1].length; j++){
	    	    		pane.add(row8[i][j], i, j);
	    	    }
	    	}
	    	for (int i = 0; i < row4.length; i++) {
	    		for (int j = 0; j < row2[1].length; j++) {
	    			pane.add(row6[i][j], i, j+2);
	    		}
	    	}
	    	for (int i = 0; i < row4.length; i++) {
	    		for (int j = 0; j < row2[1].length; j++) {
	    			pane.add(row4[i][j], i, j+4);
	    		}
	    	}
	    	for (int i = 0; i < row4.length; i++) {
	    		for (int j = 0; j < row2[1].length; j++) {
	    			pane.add(row2[i][j], i, j+6);
	    		}
	    	}
    		breakoutGroup.getChildren().add(pane);
        	pane.setLayoutX(3);
        	pane.setLayoutY(30);
        	gameOver.close();
    	});
    	
    	//hard mode set on action
    	hard.setOnAction(e6 -> {
    		difficulty = "Hard";
    		breakoutGroup.getChildren().remove(score);
    		score = new Text("Difficulty: " + difficulty + " Score = "+ keepScore);
    		score.setX(350);
			score.setY(20);
			breakoutGroup.getChildren().add(score);
    		paddle.setWidth(90);
    		changeX = -3.5;
    		changeY = -3.5;
    		//breakoutGroup.getChildren().remove(difficultyTop);
    		//breakoutGroup.getChildren().add(difficultyTop);
    		breakoutGroup.getChildren().remove(defaultB);
    		breakoutGroup.getChildren().remove(hard);
    		timeline.play();
    	});
    	
    	//making ball bounce
    	changeX = -3;
    	changeY = -3;
	    EventHandler<ActionEvent> handler = event -> {
	    		ball.setLayoutX(ball.getLayoutX() + changeX);
	    		ball.setLayoutY(ball.getLayoutY() + changeY);
	    		
	    		//bounce off walls
	    		if (ball.getLayoutX() >= (605 - ball.getRadius()) || ball.getLayoutX() <= (0 + ball.getRadius())) {
	    			changeX *= -1;
	    		}
	    		
	    		if (ball.getLayoutY() <= (0 + ball.getRadius())) {
	    			changeY *= -1;
	    			if (paddle.getWidth() == 200) {
	    				paddle.setWidth(100);
	    			}
	    			else if (paddle.getWidth() == 90)
	    			{
	    				paddle.setWidth(45);
	    			}
	    		}
    		
    		//lose a life
    		if (ball.getLayoutY() >= (400 - ball.getRadius())) {
    			lives--;
    			changeY *= -1;
    			//num lives
    			breakoutGroup.getChildren().remove(numLives);
    	    		numLives = new Text("Lives = " + lives);
    	    		numLives.setX(20);
    	    		numLives.setY(20);
    	    		play.relocate(300, 200);
    	    		breakoutGroup.getChildren().add(numLives);
    	    		breakoutGroup.getChildren().add(play);
    	    		timeline.pause();
	        	ball.relocate(300, 358);
	        	paddle.setX(275);
	        	paddle.setY(385);
	        	if (paddle.getWidth() == 200 || paddle.getWidth() == 100) {
	        		changeX = -3;
	        		changeY = -3;
	        	}
	        	else
	        	{
	        		changeX = -3.5;
	        		changeY = -3.5;
	        	}
	        	numHits = 0;
    	    		play.setOnAction(ee -> {
    	        		breakoutGroup.getChildren().remove(play);
    	        		timeline.play();	
    	            	
    	        	});
    	    		changed = false;
    	    		changed2 = false;
    	    		changed3 = false;
    	    		changed4 = false;
    		}
    		
    		//2nd game when score = 448
    		if (keepScore == 448 && changed5 == false) {
    			changed5 = true;
    			timeline.pause();
	        	ball.relocate(300, 358);
	        	paddle.setX(275);
	        	paddle.setY(385);
	        	if (paddle.getWidth() == 200 || paddle.getWidth() == 100) {
	        		changeX = -3;
	        		changeY = -3;
	        		paddle.setWidth(200);
	        	}
	        	else
	        	{
	        		changeX = -3.5;
	        		changeY = -3.5;
	        		paddle.setWidth(90);
	        	}
	        	numHits = 0;
	        	breakoutGroup.getChildren().add(play);
    	    		play.setOnAction(ee -> {
    	        		breakoutGroup.getChildren().remove(play);
    	        		timeline.play();	
    	            	
    	        	});
    			makeBricks();
    			breakoutGroup.getChildren().remove(pane);
    	    	for (int i = 0; i < row2.length; i++){
    	    	    for (int j = 0; j < row2[1].length; j++){
    	    	    		pane.add(row8[i][j], i, j);
    	    	    }
    	    	}
    	    	for (int i = 0; i < row4.length; i++) {
    	    		for (int j = 0; j < row2[1].length; j++) {
    	    			pane.add(row6[i][j], i, j+2);
    	    		}
    	    	}
    	    	for (int i = 0; i < row4.length; i++) {
    	    		for (int j = 0; j < row2[1].length; j++) {
    	    			pane.add(row4[i][j], i, j+4);
    	    		}
    	    	}
    	    	for (int i = 0; i < row4.length; i++) {
    	    		for (int j = 0; j < row2[1].length; j++) {
    	    			pane.add(row2[i][j], i, j+6);
    	    		}
    	    	}
    	    	pane.setLayoutX(3);
    	    	pane.setLayoutY(30);
    	    	breakoutGroup.getChildren().add(pane);
    			
    		}
    		
    		//game over
    		if (lives == 0) {
    			timeline.pause();
    			gameOver = new Stage();
    			gameOver.initModality(Modality.APPLICATION_MODAL);
    			Pane overMessage = new Pane();
    			Text message = new Text("Game Over\nScore: " + keepScore + "\n Please enter your initials (Three letters):");
    			TextField name = new TextField("---");
    			Button getName = new Button("Save Score");
    			
    			getName.setOnAction(e13 -> {
        			String playerName = name.getText();
        			
        			if (playerName.length() > 3) {
        				playerName = playerName.substring(0, 3);
        			}
        			if (playerName.equals("")) {
        				playerName = "AAA";
        			}
        			
        			try {
        		        BufferedWriter output = new BufferedWriter(new FileWriter(highScoreFile2, true));
        		        output.newLine();
        		        output.append("" + playerName);
        		        output.close();

        		    } catch (IOException ex1) {
        		        System.out.printf("ERROR writing score to file: %s\n", ex1);
        		    }
        			overMessage.getChildren().remove(getName);
        			
        			try {
        		        BufferedWriter output = new BufferedWriter(new FileWriter(highScoreFile2, true));
        		        output.append(" " + keepScore);
        		        output.close();

        		    } catch (IOException ex1) {
        		        System.out.printf("ERROR writing score to file: %s\n", ex1);
        		    }
    			});
    			
    			message.setLayoutX(80);
    			message.setLayoutY(50);
    			exitGame.setLayoutX(120);
    			newGame.setLayoutX(20);
    			name.setLayoutX(80);
    			name.setLayoutY(100);
    			getName.setLayoutX(250);
    			getName.setLayoutY(100);
    			
    			
    			
    			overMessage.getChildren().addAll(message, name, getName, newGame, exitGame);
    			Scene scene2 = new Scene(overMessage, 400, 200);
    			gameOver.setScene(scene2);
    			gameOver.sizeToScene();
    			gameOver.setResizable(false);
    			gameOver.show();
    			
    		}
    		
    		//bounce off paddle
    		if (paddle.getBoundsInParent().intersects(ball.getBoundsInParent())) {
    			numHits++;
    			changeY *= -1;
    		}
    		
    		//speed up on 4 hits
    		if (numHits == 4 && changed == false) {
    			changed = true;
    			changeX *= 1.1;
    			changeY *= 1.1;
    		}
    		
    		//speed up on 12 hits
    		if (numHits == 12 && changed2 == false) {
    			changed2 = true;
    			changeX *= 1.1;
    			changeY *= 1.1;
    		}
    		
    		//Speed up on orange hit
    		for (int i = 0; i < row2.length; i++) {
	    		for (int j = 0; j < row2[1].length; j++) {
	    			if (row6[i][j] == null) {
    		
			    		if (changed3 == false) {
			    			changed3 = true;
			    			changeX *= 1.1;
			    			changeY *= 1.1;
			    		}
	    			}
	    		}
    		}
    		
    		//speed up on red hit
    		for (int i = 0; i < row2.length; i++) {
	    		for (int j = 0; j < row2[1].length; j++) {
	    			if (row8[i][j] == null) {
    		
			    		if (changed4 == false) {
			    			changed4 = true;
			    			changeX *= 1.1;
			    			changeY *= 1.1;
			    		}
	    			}
	    		}
    		}
    		
    		//breaking brick and increasing score
	    	for (int i = 0; i < row2.length; i++) {
	    		for (int j = 0; j < row2[1].length; j++) {
	    			if (row2[i][j] != null) {
	    				Bounds brickBounds = row2[i][j].getBoundsInParent();
	    				double ballX = ball.getLayoutX() + ball.getRadius();
	    				double ballY = ball.getLayoutY() + ball.getRadius();
	    				if ((ballX <= brickBounds.getMaxX()  && ballX >= brickBounds.getMinX()) && 
	    						   (ballY <= brickBounds.getMaxY() + 40  && ballY >= brickBounds.getMinY() + 40)) {
	    					changeY *= -1;
	    					breakoutGroup.getChildren().remove(score);
	    					keepScore = keepScore + 1;
	    					score = new Text("Difficulty: " + difficulty + " Score = "+ keepScore);
	    					score.setX(350);
	    					score.setY(20);
	    					breakoutGroup.getChildren().add(score);
	    					row2[i][j].setFill(javafx.scene.paint.Color.TRANSPARENT);
	    					row2[i][j] = null;
	    				}
	    			}
	    		}
	    	}
	    	//breaking brick and increasing score
	    	for (int i = 0; i < row4.length; i++) {
	    		for (int j = 0; j < row4[1].length; j++) {
	    			if (row4[i][j] != null) {
	    				Bounds brickBounds2 = row4[i][j].getBoundsInParent();
	    				double ballX2 = ball.getLayoutX() + ball.getRadius();
	    				double ballY2 = ball.getLayoutY() + ball.getRadius();
	    				if ((ballX2 <= brickBounds2.getMaxX()  && ballX2 >= brickBounds2.getMinX()) && 
	    						   (ballY2 <= brickBounds2.getMaxY() + 40  && ballY2 >= brickBounds2.getMinY() + 40)) {
	    					changeY *= -1;
	    					breakoutGroup.getChildren().remove(score);
	    					keepScore = keepScore + 3;
	    					score = new Text("Difficulty: " + difficulty + " Score = "+ keepScore);
	    					score.setX(350);
	    					score.setY(20);
	    					breakoutGroup.getChildren().add(score);
	    					row4[i][j].setFill(javafx.scene.paint.Color.TRANSPARENT);
	    					row4[i][j] = null;
	    				}
	    			}
	    		}
	    	}
	    	//breaking brick and increasing score
	    	for (int i = 0; i < row6.length; i++) {
	    		for (int j = 0; j < row6[1].length; j++) {
	    			if (row6[i][j] != null) {
	    				Bounds brickBounds3 = row6[i][j].getBoundsInParent();
	    				double ballX3 = ball.getLayoutX() + ball.getRadius();
	    				double ballY3 = ball.getLayoutY() + ball.getRadius();
	    				if ((ballX3 <= brickBounds3.getMaxX()  && ballX3 >= brickBounds3.getMinX()) && 
	    						   (ballY3 <= brickBounds3.getMaxY() + 40  && ballY3 >= brickBounds3.getMinY() + 40)) {
	    					changeY *= -1;
	    					breakoutGroup.getChildren().remove(score);
	    					keepScore = keepScore + 5;
	    					score = new Text("Difficulty: " + difficulty + " Score = "+ keepScore);
	    					score.setX(350);
	    					score.setY(20);
	    					breakoutGroup.getChildren().add(score);
	    					row6[i][j].setFill(javafx.scene.paint.Color.TRANSPARENT);
	    					row6[i][j] = null;
	    				}
	    			}
	    		}
	    	}
	    	//breaking brick and increasing score
	    	for (int i = 0; i < row8.length; i++) {
	    		for (int j = 0; j < row8[1].length; j++) {
	    			if (row8[i][j] != null) {
	    				Bounds brickBounds4 = row8[i][j].getBoundsInParent();
	    				double ballX4 = ball.getLayoutX() + ball.getRadius();
	    				double ballY4 = ball.getLayoutY() + ball.getRadius();
	    				if ((ballX4 <= brickBounds4.getMaxX()  && ballX4 >= brickBounds4.getMinX()) && 
	    						   (ballY4 <= brickBounds4.getMaxY() + 40  && ballY4 >= brickBounds4.getMinY() + 40)) {
	    					changeY *= -1;
	    					breakoutGroup.getChildren().remove(score);
	    					keepScore = keepScore + 7;
	    					score = new Text("Difficulty: " + difficulty + " Score = "+ keepScore);
	    					score.setX(350);
	    					score.setY(20);
	    					breakoutGroup.getChildren().add(score);
	    					row8[i][j].setFill(javafx.scene.paint.Color.TRANSPARENT);
	    					row8[i][j] = null;
	    				}
	    			}
	    		}
	    	}
    	};
   
    	keyFrame = new KeyFrame(Duration.millis(15), handler);
    	timeline = new Timeline();
    	timeline.setCycleCount(Timeline.INDEFINITE);
    	timeline.getKeyFrames().add(keyFrame);
    	defaultB.setOnAction(ee -> {
    		//breakoutGroup.getChildren().remove(difficultyTop);
    		difficulty = "Default";
    		breakoutGroup.getChildren().remove(score);
    		score = new Text("Difficulty: " + difficulty + " Score = "+ keepScore);
    		score.setX(350);
			score.setY(20);
			breakoutGroup.getChildren().add(score);
    		//breakoutGroup.getChildren().add(difficultyTop);
    		breakoutGroup.getChildren().remove(defaultB);
    		breakoutGroup.getChildren().remove(hard);
    		timeline.play();	
    	});
    	
    	breakoutStage.setOnCloseRequest(e3 -> {
    		timeline.pause();
    	});
    	
    	breakoutScene.setOnKeyReleased(e9 -> {
    		
	    		switch(e9.getCode()) {
	    		case LEFT: 
	    				paddleAnimation.getKeyFrames().clear();
	    				paddleAnimation.stop();
	    			
	    		break;
	    		case RIGHT: 
	    				paddleAnimation.getKeyFrames().clear();
	    				paddleAnimation.stop();
	    		default:
	    		}
    		
    	});
    	
    	breakoutScene.setOnKeyPressed(e2 -> {
    		if (paddle.getWidth() == 200) {
	    		switch(e2.getCode()) {
	    		case LEFT: 
	    			paddleAnimation.pause();
	    			if (paddle.getX() > 1) {
		    			paddleAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(10), e7 -> {
		    			if (paddle.getX() > 1)
		    			paddle.setX(paddle.getX() - 10);
		    			}));
		                paddleAnimation.setCycleCount(Timeline.INDEFINITE);
		                paddleAnimation.play();
		    			
	    			}
	    		break;
	    		case RIGHT: 
	    			paddleAnimation.pause();
	    			if (paddle.getX() < 440) {
		    			paddleAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(10), e7 -> {
		    			if (paddle.getX() < 440)
		    			paddle.setX(paddle.getX() + 10);
		    			}));
		                paddleAnimation.setCycleCount(Timeline.INDEFINITE);
		                paddleAnimation.play();
	    			}
	    		break;
	    		
	    		default:
	    		}
    		}
    		else if (paddle.getWidth() == 100)
    		{
    			switch(e2.getCode()) {
	    		case LEFT: 
	    			paddleAnimation.pause();
	    			if (paddle.getX() > 1) {
		    			paddleAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(10), e7 -> {
		    			if (paddle.getX() > 1)
		    			paddle.setX(paddle.getX() - 10);
		    			}));
		                paddleAnimation.setCycleCount(Timeline.INDEFINITE);
		                paddleAnimation.play();
		    			
	    			}
	    		break;
	    		case RIGHT: 
	    			paddleAnimation.pause();
	    			if (paddle.getX() < 520) {
		    			paddleAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(10), e7 -> {
		    			if (paddle.getX() < 520)
		    			paddle.setX(paddle.getX() + 10);
		    			}));
		                paddleAnimation.setCycleCount(Timeline.INDEFINITE);
		                paddleAnimation.play();
	    			}
	    		break;
	    		
	    		default:
	    		}
    		}
    		else if (paddle.getWidth() == 90) {
    			switch(e2.getCode()) {
	    		case LEFT: 
	    			paddleAnimation.pause();
	    			if (paddle.getX() > 1) {
		    			paddleAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(10), e7 -> {
		    			if (paddle.getX() > 1)
		    			paddle.setX(paddle.getX() - 10);
		    			}));
		                paddleAnimation.setCycleCount(Timeline.INDEFINITE);
		                paddleAnimation.play();
		    			
	    			}
	    		break;
	    		case RIGHT: 
	    			paddleAnimation.pause();
	    			if (paddle.getX() < 510) {
		    			paddleAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(10), e7 -> {
		    			if (paddle.getX() < 510)
		    			paddle.setX(paddle.getX() + 10);
		    			}));
		                paddleAnimation.setCycleCount(Timeline.INDEFINITE);
		                paddleAnimation.play();
	    			}
	    		break;
	    		
	    		default:
	    		}
    		}
    		else if (paddle.getWidth() == 45) {
    			switch(e2.getCode()) {
	    		case LEFT: 
	    			paddleAnimation.pause();
	    			if (paddle.getX() > 1) {
		    			paddleAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(10), e7 -> {
		    			if (paddle.getX() > 1)
		    			paddle.setX(paddle.getX() - 10);
		    			}));
		                paddleAnimation.setCycleCount(Timeline.INDEFINITE);
		                paddleAnimation.play();
		    			
	    			}
	    		break;
	    		case RIGHT: 
	    			paddleAnimation.pause();
	    			if (paddle.getX() < 550) {
		    			paddleAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(10), e7 -> {
		    			if (paddle.getX() < 550)
		    			paddle.setX(paddle.getX() + 10);
		    			}));
		                paddleAnimation.setCycleCount(Timeline.INDEFINITE);
		                paddleAnimation.play();
	    			}
	    		break;
	    		
	    		default:
	    		}
    		}
    		
    	});
    	
    	//breakoutGroup.getChildren().add(difficultyTop);
    	breakoutStage.setResizable(false);
    	breakoutStage.setTitle("Breakout!");
    	breakoutStage.setScene(breakoutScene);
    	breakoutStage.show();
    	instructionStage.show();
    });
    
	//CHECKERS!!!!!!
	
    Button checkers = new Button();
    Image checkersImage = new Image("https://d2v9y0dukr6mq2.cloudfront.net/video/thumbnail/XIlSq6i/checkers-game-board-games-peice-piece_4j6bxt7v__F0000.png");
    ImageView checkersView = new ImageView(checkersImage);
    checkersView.setFitHeight(277);
    checkersView.setFitWidth(359);
    numOrangeMoves = 0;
    numBlueMoves =0;

    checkers.setGraphic(checkersView);
    
    checkers.setOnAction(e8 -> {
    	numOrange = 12;
    numBlue = 12;
    	checkerRow = -1;
        checkerCol = -1;
        emptyRow = -1; 
        emptyCol = -1;
        orangeTurn = false;
    	
    	Group checkersGroup = new Group();
    	Stage checkersStage = new Stage();
    	checkersStage.initModality(Modality.APPLICATION_MODAL);
    	Scene checkersScene = new Scene(checkersGroup, 480, 500);
    	
    	//creating gridpane for checker board
    	GridPane checkerBoard = new GridPane();
    	
    	//instruction window
        Stage instructionStage = new Stage();
		Pane instructionPane = new Pane();
		Button startGame = new Button("Start");
		Text message = new Text("Click on piece and then click \non empty space to move");
		instructionStage.initModality(Modality.APPLICATION_MODAL);
		startGame.setLayoutX(80);
		startGame.setLayoutY(50);
		message.setLayoutX(20);
		message.setLayoutY(100);
		startGame.setOnAction(e -> {
			instructionStage.close();
		});
		instructionPane.getChildren().addAll(message, startGame);
		Scene scene2 = new Scene(instructionPane, 230, 200);
		instructionStage.setScene(scene2);
		instructionStage.sizeToScene();
		instructionStage.setResizable(false);
    	
    	
    	checkersGroup.setOnMouseClicked(new EventHandler<MouseEvent>() {
    		public void handle(MouseEvent me) {
    	        clickX = me.getSceneX();
    	        clickY = me.getSceneY();
    	        System.out.println(clickX + " " + clickY);
    	        
    	        
    	        boolean pieceClicked = false;
    	        
    	        for (Node space : pieces.getChildren()) {
    	    		Point2D nodeCord = space.localToScene(0.0, 0.0);
    	    		double spaceX = nodeCord.getX();
    	    		double spaceY = nodeCord.getY();
    	    		
    	    		if (clickX > spaceX - 30 && clickX < spaceX + 30 && clickY > spaceY - 30 && clickY < spaceY + 30) {
    	    			//System.out.println("clicked" + space.getId());
    	    			
    	    			checkerRow = GridPane.getRowIndex(space);
    	    			checkerCol = GridPane.getColumnIndex(space);
    	    			pieceClicked = true;
    	    			//System.out.println(checkerRow + " " + checkerCol);
    	    			clickedChecker = true;
    	    		}
    	    	}
    	        
    	        
    	        for (Node space : checkerBoard.getChildren()) {
    	    		Point2D nodeCord = space.localToScene(0.0, 0.0);
    	    		double spaceX = nodeCord.getX();
    	    		double spaceY = nodeCord.getY();
    	    		//System.out.println(spaceX + " " + spaceY);
    	    		
    	    		if (clickX >= spaceX && clickX < spaceX + 60 && clickY >= spaceY && clickY < spaceY + 60) {
    	    			
    	    			if (checkerCol != -1 && checkerRow != -1 && pieceClicked == false) {
	    	    			emptyRow = GridPane.getRowIndex(space);
	    	    			emptyCol = GridPane.getColumnIndex(space);
	    	    			//System.out.println(emptyRow + " " + emptyCol);
	    	    			if (clickedChecker == true) {
	    	    			clickedEmpty = true;
	    	    			}
    	    			}
    	    		}
    	    	}
    	        
    	        
    	        //Jumping logic for orange v2
    	        if (clickedChecker == true && clickedEmpty == true) {
    	        	int middleCol;
    	        	if (emptyCol < checkerCol) {
    	        		middleCol = emptyCol +1;
    	        	} else {
    	        		middleCol = emptyCol -1;
    	        	}
    	        	
    	        	//Checking not jumping same color
    	        	boolean notSameColor = true;
    	        	 for (Node space : pieces.getChildren()) {
    	        		 if (emptyRow + 1 == GridPane.getRowIndex(space) && middleCol == GridPane.getColumnIndex(space)) {
		    	        		if (space.getId().equals("Orange") || space.getId().equals("Red")) {
		    	        			notSameColor = false;
		    	        		}
    	        		 }
    	        	 }
    	        	 
    	        	 boolean isOrange = false;
    	        	 for (Node space : pieces.getChildren()) {
    	        		 if (checkerRow == GridPane.getRowIndex(space) && checkerCol == GridPane.getColumnIndex(space)) {
    	        			 if (space.getId().equals("Orange")) {
		    	        			isOrange = true;
    	        			 }
    	        		 }
    	        	 }
    	        	 boolean isRed = false;
    	        	 for (Node space : pieces.getChildren()) {
    	        		 if (checkerRow == GridPane.getRowIndex(space) && checkerCol == GridPane.getColumnIndex(space)) {
    	        			 if (space.getId().equals("Red")) {
		    	        			isRed = true;
    	        			 }
    	        		 }
    	        	 }
    	        	 
    	        	 //checks valid jumping space
    	        	 System.out.println(emptyRow + " " + emptyCol);
		    	     System.out.println(checkerRow + " " + checkerCol);
		    	 if (isOrange && orangeTurn && (((emptyRow == checkerRow + 1) && (emptyCol == checkerCol + 1 || emptyCol == checkerCol -1))
    	        			|| (((emptyRow == checkerRow + 2) && (emptyCol == checkerCol + 2 || emptyCol == checkerCol -2) && 
    	        					!isEmpty(pieces, emptyRow - 1, middleCol)) && notSameColor))) {
    	        			System.out.println(isOrange);
		    	        System.out.println(emptyRow + " " + emptyCol);
		    	        System.out.println(checkerRow + " " + checkerCol);
		    	        
		    	        Circle newPiece = new Circle(20);
			    	       for (Node space : pieces.getChildren()) {
			    	        	
			    	        	if (checkerRow == GridPane.getRowIndex(space) && checkerCol == GridPane.getColumnIndex(space)) {
			    	        		
			    	        		if (space.getId().equals("Orange")) {
			    	        			//newPiece.setFill(javafx.scene.paint.Color.ORANGE);
			    	        			newPiece = new Circle(20, javafx.scene.paint.Color.ORANGE);
			    	        			newPiece.setId("Orange");
			    	        		} else if (space.getId().equals("Blue")) {
			    	        			//newPiece.setFill(javafx.scene.paint.Color.BLUE);
			    	        			newPiece = new Circle(20, javafx.scene.paint.Color.BLUE);
			    	        			newPiece.setId("Blue");
			    	        		}else if (space.getId().equals("Red")) {
			    	        			newPiece = new Circle(20, javafx.scene.paint.Color.RED);
			    	        			newPiece.setId("Red");
			    	        		}else if (space.getId().equals("Purple")) {
			    	        			newPiece = new Circle(20, javafx.scene.paint.Color.PURPLE);
			    	        			newPiece.setId("Purple");
			    	        		}
			    	        	}
			    	       } 
		    	        
		    	        
			    	    //Moves the piece
		    	        Node jumpedSpace = null;
			    	       if ((emptyRow == checkerRow + 2) && (emptyCol == checkerCol + 2 || emptyCol == checkerCol -2)) {
			    	    	   for (Node space : pieces.getChildren()) {
				    	        	
				    	        	if (checkerRow + 1 == GridPane.getRowIndex(space) && middleCol == GridPane.getColumnIndex(space))  {
				    	        		//pieces.getChildren().remove(space);
				    	        		jumpedSpace = space;
				    	        	}
				    	        }
			    	    	   pieces.getChildren().remove(jumpedSpace);
			    	    	   numBlue--;
			    	       }
		    	        
		    	        Node spaceChecker = null;
		    	        
		    	        for (Node space : pieces.getChildren()) {
		    	        	
		    	        	if (checkerRow == GridPane.getRowIndex(space) && checkerCol == GridPane.getColumnIndex(space)) {
		    	        		//pieces.getChildren().remove(space);
		    	        		spaceChecker = space;
		    	        	}
		    	        }
		    	        
		    	        pieces.getChildren().remove(spaceChecker);
		    	       
		    	       Node spaceEmpty = null;
		    	       for (Node space : pieces.getChildren()) {
		    	        	
		    	        	if (emptyRow == GridPane.getRowIndex(space) && emptyCol == GridPane.getColumnIndex(space)) {
		    	        		spaceEmpty = space;
		    	        	}
		    	       }
		    	        
		    	       
		    	       
		    	        pieces.getChildren().remove(spaceEmpty);
		    	        GridPane.setRowIndex(newPiece, emptyRow);
		    			GridPane.setColumnIndex(newPiece, emptyCol);
		    			if (emptyRow == 7) {
		    				newPiece.setFill(javafx.scene.paint.Color.RED);
		    				newPiece.setId("Red");
		    			}
		    	        pieces.getChildren().add(newPiece);
		    	        
		    	        orangeTurn = false;
		    	        numOrangeMoves++;

    	        }
    	        
    	        //jumping logic for red king
    	        //checks valid jumping space
	        	 System.out.println(emptyRow + " " + emptyCol);
	    	     System.out.println(checkerRow + " " + checkerCol);
	    	 if (isRed && orangeTurn && (((emptyRow == checkerRow + 1 || emptyRow == checkerRow - 1) && (emptyCol == checkerCol + 1 || emptyCol == checkerCol -1))
	        			|| (((emptyRow == checkerRow + 2 || emptyRow == checkerRow - 2) && (emptyCol == checkerCol + 2 || emptyCol == checkerCol -2) && 
	        					(!isEmpty(pieces, emptyRow - 1, middleCol) || !isEmpty(pieces, emptyRow + 1, middleCol))) && notSameColor))) {
	        			System.out.println(isRed);
	    	        System.out.println(emptyRow + " " + emptyCol);
	    	        System.out.println(checkerRow + " " + checkerCol);
	    	        
	    	        Circle newPiece = new Circle(20);
		    	       for (Node space : pieces.getChildren()) {
		    	        	
		    	        	if (checkerRow == GridPane.getRowIndex(space) && checkerCol == GridPane.getColumnIndex(space)) {
		    	        		
		    	        		if (space.getId().equals("Orange")) {
		    	        			//newPiece.setFill(javafx.scene.paint.Color.ORANGE);
		    	        			newPiece = new Circle(20, javafx.scene.paint.Color.ORANGE);
		    	        			newPiece.setId("Orange");
		    	        		} else if (space.getId().equals("Blue")) {
		    	        			//newPiece.setFill(javafx.scene.paint.Color.BLUE);
		    	        			newPiece = new Circle(20, javafx.scene.paint.Color.BLUE);
		    	        			newPiece.setId("Blue");
		    	        		}else if (space.getId().equals("Red")) {
		    	        			newPiece = new Circle(20, javafx.scene.paint.Color.RED);
		    	        			newPiece.setId("Red");
		    	        		}else if (space.getId().equals("Purple")) {
		    	        			newPiece = new Circle(20, javafx.scene.paint.Color.PURPLE);
		    	        			newPiece.setId("Purple");
		    	        		}
		    	        	}
		    	       } 
	    	        
	    	        
		    	    //Moves the piece
	    	        Node jumpedSpace = null;
		    	       if ((emptyRow == checkerRow + 2 || emptyRow == checkerRow - 2) && (emptyCol == checkerCol + 2 || emptyCol == checkerCol -2)) {
		    	    	   for (Node space : pieces.getChildren()) {
			    	        	
			    	        	if ((checkerRow + 1 == GridPane.getRowIndex(space) || checkerRow - 1 == GridPane.getRowIndex(space)) 
			    	        			&& middleCol == GridPane.getColumnIndex(space))  {
			    	        		//pieces.getChildren().remove(space);
			    	        		jumpedSpace = space;
			    	        	}
			    	        }
		    	    	   pieces.getChildren().remove(jumpedSpace);
		    	    	   numBlue--;
		    	       }
	    	        
	    	        Node spaceChecker = null;
	    	        
	    	        for (Node space : pieces.getChildren()) {
	    	        	
	    	        	if (checkerRow == GridPane.getRowIndex(space) && checkerCol == GridPane.getColumnIndex(space)) {
	    	        		//pieces.getChildren().remove(space);
	    	        		spaceChecker = space;
	    	        	}
	    	        }
	    	        
	    	        pieces.getChildren().remove(spaceChecker);
	    	       
	    	       Node spaceEmpty = null;
	    	       for (Node space : pieces.getChildren()) {
	    	        	
	    	        	if (emptyRow == GridPane.getRowIndex(space) && emptyCol == GridPane.getColumnIndex(space)) {
	    	        		spaceEmpty = space;
	    	        	}
	    	        } 
	    	       
	    	       
	    	        pieces.getChildren().remove(spaceEmpty);
	    	        GridPane.setRowIndex(newPiece, emptyRow);
	    			GridPane.setColumnIndex(newPiece, emptyCol);
	    			if (emptyRow == 7) {
	    				newPiece.setFill(javafx.scene.paint.Color.RED);
	    				newPiece.setId("Red");
	    			}
	    	        pieces.getChildren().add(newPiece);
	    	        
	    	        orangeTurn = false;
	    	        numOrangeMoves++;
	        }
    	        }
    	        
    	        
    	      //Jumping Logic for Blue and Purple
    	        if (clickedChecker == true && clickedEmpty == true) {
    	        	int middleCol;
    	        	if (emptyCol < checkerCol) {
    	        		middleCol = emptyCol +1;
    	        	} else {
    	        		middleCol = emptyCol -1;
    	        	}
    	        	
    	        	
    	        	//Checking not jumping same color
    	        	boolean notSameColor = true;
    	        	 for (Node space : pieces.getChildren()) {
    	        		 if (emptyRow - 1 == GridPane.getRowIndex(space) && middleCol == GridPane.getColumnIndex(space)) {
		    	        		if (space.getId().equals("Blue") || space.getId().equals("Purple")) {
		    	        			notSameColor = false;
		    	        		}
    	        		 }
    	        	 }
    	        	 
    	        	 boolean isBlue = false;
    	        	 for (Node space : pieces.getChildren()) {
    	        		 if (checkerRow == GridPane.getRowIndex(space) && checkerCol == GridPane.getColumnIndex(space)) {
    	        			 if (space.getId().equals("Blue")) {
		    	        			isBlue = true;
    	        			 }
    	        		 }
    	        	 }
    	        	 
    	        	 boolean isPurple = false;
    	        	 for (Node space : pieces.getChildren()) {
    	        		 if (checkerRow == GridPane.getRowIndex(space) && checkerCol == GridPane.getColumnIndex(space)) {
    	        			 if (space.getId().equals("Purple")) {
		    	        			isPurple = true;
    	        			 }
    	        		 }
    	        	 }
    	        	
    	        	 
    	        	 //jumping logic for Purple King
    	        	 //checks valid jumping space
    	        	 	System.out.println(emptyRow + " " + emptyCol);
		    	    System.out.println(checkerRow + " " + checkerCol);
    	        	if (isPurple && !orangeTurn && (((emptyRow == checkerRow - 1 || emptyRow == checkerRow + 1) && (emptyCol == checkerCol + 1 || emptyCol == checkerCol -1))
    	        			|| (((emptyRow == checkerRow - 2 || emptyRow == checkerRow + 2) && (emptyCol == checkerCol + 2 || emptyCol == checkerCol -2) && 
    	        					(!isEmpty(pieces, emptyRow + 1, middleCol) || !isEmpty(pieces, emptyRow - 1, middleCol))) && notSameColor))) {
    	        			System.out.println(isPurple);
		    	        System.out.println(emptyRow + " " + emptyCol);
		    	        System.out.println(checkerRow + " " + checkerCol);
		    	        
		    	        Circle newPiece = new Circle(20);
			    	       for (Node space : pieces.getChildren()) {
			    	        	
			    	        	if (checkerRow == GridPane.getRowIndex(space) && checkerCol == GridPane.getColumnIndex(space)) {
			    	        		
			    	        		if (space.getId().equals("Orange")) {
			    	        			//newPiece.setFill(javafx.scene.paint.Color.ORANGE);
			    	        			newPiece = new Circle(20, javafx.scene.paint.Color.ORANGE);
			    	        			newPiece.setId("Orange");
			    	        		} else if (space.getId().equals("Blue")) {
			    	        			//newPiece.setFill(javafx.scene.paint.Color.BLUE);
			    	        			newPiece = new Circle(20, javafx.scene.paint.Color.BLUE);
			    	        			newPiece.setId("Blue");
			    	        		}else if (space.getId().equals("Red")) {
			    	        			newPiece = new Circle(20, javafx.scene.paint.Color.RED);
			    	        			newPiece.setId("Red");
			    	        		}else if (space.getId().equals("Purple")) {
			    	        			newPiece = new Circle(20, javafx.scene.paint.Color.PURPLE);
			    	        			newPiece.setId("Purple");
			    	        		}
			    	        	}
			    	       } 
		    	        
		    	        
			    	    //Moves the piece
		    	        Node jumpedSpace = null;
			    	       if ((emptyRow == checkerRow - 2 || emptyRow == checkerRow + 2) && (emptyCol == checkerCol + 2 || emptyCol == checkerCol -2)) {
			    	    	   for (Node space : pieces.getChildren()) {
				    	        	
				    	        	if ((checkerRow - 1 == GridPane.getRowIndex(space) || checkerRow + 1 == GridPane.getRowIndex(space)) && 
				    	        			middleCol == GridPane.getColumnIndex(space))  {
				    	        		//pieces.getChildren().remove(space);
				    	        		jumpedSpace = space;
				    	        	}
				    	        }
			    	    	   pieces.getChildren().remove(jumpedSpace);
			    	    	   numOrange--;
			    	       }
		    	        
		    	        Node spaceChecker = null;
		    	        
		    	        for (Node space : pieces.getChildren()) {
		    	        	
		    	        	if (checkerRow == GridPane.getRowIndex(space) && checkerCol == GridPane.getColumnIndex(space)) {
		    	        		//pieces.getChildren().remove(space);
		    	        		spaceChecker = space;
		    	        	}
		    	        }
		    	        
		    	        pieces.getChildren().remove(spaceChecker);
		    	       
		    	       Node spaceEmpty = null;
		    	       for (Node space : pieces.getChildren()) {
		    	        	
		    	        	if (emptyRow == GridPane.getRowIndex(space) && emptyCol == GridPane.getColumnIndex(space)) {
		    	        		spaceEmpty = space;
		    	        	}
		    	        	
		    	        } 
		    	       
		    	       
		    	        pieces.getChildren().remove(spaceEmpty);
		    	        GridPane.setRowIndex(newPiece, emptyRow);
		    			GridPane.setColumnIndex(newPiece, emptyCol);
		    			
		    			if (emptyRow == 0) {
		    				newPiece.setFill(javafx.scene.paint.Color.PURPLE);
		    				newPiece.setId("Purple");
		    			}
		    			
		    	        pieces.getChildren().add(newPiece);
		    	        
		    	        orangeTurn = true;
		    	        numBlueMoves++;

    	        }
    	        
    	        	//jumping logic for blue
   	        	 //checks valid jumping space
   	        	 System.out.println(emptyRow + " " + emptyCol);
		    	     System.out.println(checkerRow + " " + checkerCol);
   	        	if (isBlue && !orangeTurn && (((emptyRow == checkerRow - 1) && (emptyCol == checkerCol + 1 || emptyCol == checkerCol -1))
   	        			|| (((emptyRow == checkerRow - 2) && (emptyCol == checkerCol + 2 || emptyCol == checkerCol -2) && 
   	        					!isEmpty(pieces, emptyRow + 1, middleCol)) && notSameColor))) {
   	        			System.out.println(isBlue);
		    	        System.out.println(emptyRow + " " + emptyCol);
		    	        System.out.println(checkerRow + " " + checkerCol);
		    	        
		    	        Circle newPiece = new Circle(20);
			    	       for (Node space : pieces.getChildren()) {
			    	        	
			    	        	if (checkerRow == GridPane.getRowIndex(space) && checkerCol == GridPane.getColumnIndex(space)) {
			    	        		
			    	        		if (space.getId().equals("Orange")) {
			    	        			//newPiece.setFill(javafx.scene.paint.Color.ORANGE);
			    	        			newPiece = new Circle(20, javafx.scene.paint.Color.ORANGE);
			    	        			newPiece.setId("Orange");
			    	        		} else if (space.getId().equals("Blue")) {
			    	        			//newPiece.setFill(javafx.scene.paint.Color.BLUE);
			    	        			newPiece = new Circle(20, javafx.scene.paint.Color.BLUE);
			    	        			newPiece.setId("Blue");
			    	        		}else if (space.getId().equals("Red")) {
			    	        			newPiece = new Circle(20, javafx.scene.paint.Color.RED);
			    	        			newPiece.setId("Red");
			    	        		}else if (space.getId().equals("Purple")) {
			    	        			newPiece = new Circle(20, javafx.scene.paint.Color.PURPLE);
			    	        			newPiece.setId("Purple");
			    	        		}
			    	        	}
			    	       } 
		    	        
		    	        
			    	    //Moves the piece
		    	        Node jumpedSpace = null;
			    	       if ((emptyRow == checkerRow - 2) && (emptyCol == checkerCol + 2 || emptyCol == checkerCol -2)) {
			    	    	   for (Node space : pieces.getChildren()) {
				    	        	
				    	        	if (checkerRow - 1 == GridPane.getRowIndex(space) && middleCol == GridPane.getColumnIndex(space))  {
				    	        		//pieces.getChildren().remove(space);
				    	        		jumpedSpace = space;
				    	        	}
				    	        }
			    	    	   pieces.getChildren().remove(jumpedSpace);
			    	    	   numOrange--;
			    	       }
		    	        
		    	        Node spaceChecker = null;
		    	        
		    	        for (Node space : pieces.getChildren()) {
		    	        	
		    	        	if (checkerRow == GridPane.getRowIndex(space) && checkerCol == GridPane.getColumnIndex(space)) {
		    	        		//pieces.getChildren().remove(space);
		    	        		spaceChecker = space;
		    	        	}
		    	        }
		    	        
		    	        pieces.getChildren().remove(spaceChecker);
		    	       
		    	       Node spaceEmpty = null;
		    	       for (Node space : pieces.getChildren()) {
		    	        	
		    	        	if (emptyRow == GridPane.getRowIndex(space) && emptyCol == GridPane.getColumnIndex(space)) {
		    	        		spaceEmpty = space;
		    	        	}
		    	        	
		    	        } 
		    	       
		    	       
		    	        pieces.getChildren().remove(spaceEmpty);
		    	        GridPane.setRowIndex(newPiece, emptyRow);
		    			GridPane.setColumnIndex(newPiece, emptyCol);
		    			
		    			if (emptyRow == 0) {
		    				newPiece.setFill(javafx.scene.paint.Color.PURPLE);
		    				newPiece.setId("Purple");
		    			}
		    			
		    	        pieces.getChildren().add(newPiece);
		    	        
		    	        orangeTurn = true;
		    	        numBlueMoves++;
   	        }
   	    	 		clickedChecker = false;
   	    	 		clickedEmpty = false;
    	        }
    	        
    	        //Setting whose turn it is
            if (orangeTurn != true) {
            	whichMove.setText("Blue Turn. Orange Moves: " + numOrangeMoves + " Blue Moves: " + numBlueMoves );
            }
            else if (orangeTurn == true) {
            	whichMove.setText("Orange Turn. Orange Moves: " + numOrangeMoves + " Blue Moves: " + numBlueMoves );
            }
            
            
        	
        	//Check eligible moves left
        	
	        	boolean blueMovesLeft = false;
	        	boolean orangeMovesLeft = false;
	        	
	        	
	        	for (int row = 0; row < 8; row++) {
	        		for (int col = 0; col <8; col++) {
			        	for (Node space : pieces.getChildren()) {
				        	if (space.getId().equals("Blue") || space.getId().equals("Purple")) {
					        	if (row == GridPane.getRowIndex(space) && col == GridPane.getColumnIndex(space)) {
					        		
				        				if (isEmpty(pieces, row - 1, col - 1)) {
				        					blueMovesLeft = true;
				        					System.out.println(blueMovesLeft);
				        				} else if (isEmpty(pieces, row -1, col +1)) {
				        					blueMovesLeft = true;
				        					System.out.println(blueMovesLeft);
				        				} else if (space.getId().equals("Purple") && isEmpty(pieces, row +1, col -1)) {
				        					blueMovesLeft = true;
				        					System.out.println(blueMovesLeft);
				        				} else if (space.getId().equals("Purple") && isEmpty(pieces, row +1, col + 1)) {
				        					blueMovesLeft = true;
				        					System.out.println(blueMovesLeft);
				        				}
				        				
				        				if (blueMovesLeft == false) {
				        					if (isEmpty(pieces, row - 2, col + 2) && (checkColor(pieces, row - 1, col + 1).equals("Red") 
				        							|| checkColor(pieces, row - 1, col + 1).equals("Orange"))) {
				        						blueMovesLeft = true;
				        						System.out.println(blueMovesLeft);
				        					} else if (isEmpty(pieces, row - 2, col - 2) && (checkColor(pieces, row -1, col -1).equals("Red") 
				        							|| checkColor(pieces, row -1, col -1).equals("Orange"))) {
				        						blueMovesLeft = true;
				        						System.out.println(blueMovesLeft);
				        					} else if (space.getId().equals("Purple") && isEmpty(pieces, row + 2, col - 2) && (checkColor(pieces, row + 1, col -1).equals("Red") 
				        							|| checkColor(pieces, row + 1, col -1).equals("Orange"))) {
				        						blueMovesLeft = true;
				        						System.out.println(blueMovesLeft);
				        					} else if (space.getId().equals("Purple") && isEmpty(pieces, row + 2, col + 2) && (checkColor(pieces, row + 1, col + 1).equals("Red") 
				        							|| checkColor(pieces, row + 1, col + 1).equals("Orange"))) {
				        						blueMovesLeft = true;
				        						System.out.println(blueMovesLeft);
				        					}
				        				}
					        	}
				        		
				        	}
				        	
				        } 
	        		}
	        	}
        	
	        	if (blueMovesLeft == false || (numBlue == 0 && numOrange > 0)) {
	        		Stage gameOver = new Stage();
	    			Pane overMessage = new Pane();
	    			Button exitGame = new Button("Exit Game");
	    			Button newGame = new Button("New Game");
	    			
	    			exitGame.setOnAction(e16 -> {
	    				checkersStage.close();
	    				gameOver.close();
	    			});
	    			
	    			newGame.setOnAction(e15 -> {
	    				checkersInit(checkersGroup, checkerBoard);
	    				numOrange = 12;
	    				numBlue = 12;
	    				numOrangeMoves = 0;
	    				numBlueMoves = 0;
	    				orangeTurn = false;
	    				//adding turn notification
	    		    	whichMove = new Text("Blue Turn. Orange Moves: " + numOrangeMoves + " Blue Moves: " + numBlueMoves );
	    		    	whichMove.setX(20);
	    		    	whichMove.setY(495);
	    		    	checkersGroup.getChildren().add(whichMove);
	    		    	
	    		    	checkersStage.show();
	    		    	instructionStage.show();
	    				gameOver.close();
	    			});
	    			
	    			Text message = new Text("Orange Wins with " + numOrangeMoves + "\nPlease enter your initials (Three letters):");
	    			message.setLayoutX(80);
	    			message.setLayoutY(50);
	    			exitGame.setLayoutX(120);
	    			newGame.setLayoutX(20);
	    			TextField name = new TextField("---");
	    			Button getName = new Button("Save Score");
	    			name.setLayoutX(80);
	    			getName.setLayoutX(250);
	    			name.setLayoutY(120);
	    			getName.setLayoutY(120);
	    			
	    			overMessage.getChildren().addAll(message, newGame, exitGame, name, getName);
	    			Scene scene2 = new Scene(overMessage, 400, 150);
	    			gameOver.setScene(scene2);
	    			gameOver.sizeToScene();
	    			gameOver.setResizable(false);
	    			gameOver.show();
	    			
	    			
	 
	    			
	    			
	    			getName.setOnAction(e13 -> {
	        			String playerName = name.getText();
	        			
	        			if (playerName.length() > 3) {
	        				playerName = playerName.substring(0, 3);
	        			}
	        			if (playerName.equals("")) {
	        				playerName = "AAA";
	        			}
	        			
	        			try {
	        		        BufferedWriter output = new BufferedWriter(new FileWriter(checkerFile, true));
	        		        output.newLine();
	        		        output.append("" + playerName);
	        		        output.close();

	        		    } catch (IOException ex1) {
	        		        System.out.printf("ERROR writing score to file: %s\n", ex1);
	        		    }
	        			overMessage.getChildren().remove(getName);
	        			
	        			try {
	        		        BufferedWriter output = new BufferedWriter(new FileWriter(checkerFile, true));
	        		        output.append(" " + numOrangeMoves);
	        		        output.close();

	        		    } catch (IOException ex1) {
	        		        System.out.printf("ERROR writing score to file: %s\n", ex1);
	        		    }
	    			});
	        	}
	        	
	        	for (int row = 0; row < 8; row++) {
	        		for (int col = 0; col <8; col++) {
			        	for (Node space : pieces.getChildren()) {
				        	if (space.getId().equals("Red") || space.getId().equals("Orange")) {
					        	if (row == GridPane.getRowIndex(space) && col == GridPane.getColumnIndex(space)) {
					        		
				        				if (isEmpty(pieces, row + 1, col - 1)) {
				        					orangeMovesLeft = true;
				        					System.out.println(orangeMovesLeft);
				        				} else if (isEmpty(pieces, row + 1, col +1)) {
				        					orangeMovesLeft = true;
				        					System.out.println(orangeMovesLeft);
				        				} else if (space.getId().equals("Red") && isEmpty(pieces, row - 1, col - 1)) {
				        					orangeMovesLeft = true;
				        					System.out.println(orangeMovesLeft);
				        				} else if (space.getId().equals("Red") && isEmpty(pieces, row - 1, col + 1)) {
				        					orangeMovesLeft = true;
				        					System.out.println(orangeMovesLeft);
				        				}
				        				
				        				if (orangeMovesLeft == false) {
				        					if (isEmpty(pieces, row + 2, col + 2) && (checkColor(pieces, row + 1, col + 1).equals("Blue") 
				        							|| checkColor(pieces, row + 1, col + 1).equals("Purple"))) {
				        						orangeMovesLeft = true;
				        						System.out.println(orangeMovesLeft);
				        					} else if (isEmpty(pieces, row + 2, col - 2) && (checkColor(pieces, row +1, col -1).equals("Blue") 
				        							|| checkColor(pieces, row +1, col -1).equals("Purple"))) {
				        						orangeMovesLeft = true;
				        						System.out.println(orangeMovesLeft);
				        					} else if (space.getId().equals("Red") && isEmpty(pieces, row - 2, col - 2) && (checkColor(pieces, row - 1, col -1).equals("Blue") 
				        							|| checkColor(pieces, row - 1, col -1).equals("Purple"))) {
				        						orangeMovesLeft = true;
				        						System.out.println(orangeMovesLeft);
				        					} else if (space.getId().equals("Red") && isEmpty(pieces, row - 2, col + 2) && (checkColor(pieces, row - 1, col + 1).equals("Blue") 
				        							|| checkColor(pieces, row - 1, col + 1).equals("Purple"))) {
				        						orangeMovesLeft = true;
				        						System.out.println(orangeMovesLeft);
				        					}
				        				}
					        	}
				        		
				        	}
				        	
				        } 
	        		}
	        	}
        	
	        	if (orangeMovesLeft == false || (numOrange == 0 && numBlue > 0)) {
	        		Stage gameOver = new Stage();
	    			Pane overMessage = new Pane();
	    			Button exitGame = new Button("Exit Game");
	    			Button newGame = new Button("New Game");
	    			Text message = new Text("Blue Wins with " + numBlueMoves + "\nPlease enter your initials (Three letters):");
	    			message.setLayoutX(80);
	    			message.setLayoutY(50);
	    			exitGame.setLayoutX(120);
	    			newGame.setLayoutX(20);
	    			
	    			exitGame.setOnAction(e16 -> {
	    				checkersStage.close();
	    				gameOver.close();
	    			});
	    			
	    			newGame.setOnAction(e15 -> {
	    				checkersInit(checkersGroup, checkerBoard);
	    				numOrange = 12;
	    				numBlue = 12;
	    				numOrangeMoves = 0;
	    				numBlueMoves = 0;
	    				orangeTurn = false;
	    				//adding turn notification
	    				whichMove = new Text("Blue Turn. Orange Moves: " + numOrangeMoves + " Blue Moves: " + numBlueMoves );
	    		    	whichMove.setX(20);
	    		    	whichMove.setY(495);
	    		    	checkersGroup.getChildren().add(whichMove);
	    		    	
	    		    	checkersStage.show();
	    		    	instructionStage.show();
	    				gameOver.close();
	    			});
	    			
	    			
	    			
	    			TextField name = new TextField("---");
	    			Button getName = new Button("Save Score");
	    			name.setLayoutX(80);
	    			getName.setLayoutX(250);
	    			name.setLayoutY(120);
	    			getName.setLayoutY(120);
	    			
	    			overMessage.getChildren().addAll(message, newGame, exitGame, name, getName);
	    			
	    			Scene scene2 = new Scene(overMessage, 400, 150);
	    			gameOver.setScene(scene2);
	    			gameOver.sizeToScene();
	    			gameOver.setResizable(false);
	    			gameOver.show();
	    			
	    			
	    			
	    			getName.setOnAction(e13 -> {
	        			String playerName = name.getText();
	        			
	        			if (playerName.length() > 3) {
	        				playerName = playerName.substring(0, 3);
	        			}
	        			if (playerName.equals("")) {
	        				playerName = "AAA";
	        			}
	        			
	        			try {
	        		        BufferedWriter output = new BufferedWriter(new FileWriter(checkerFile, true));
	        		        output.newLine();
	        		        output.append("" + playerName);
	        		        output.close();

	        		    } catch (IOException ex1) {
	        		        System.out.printf("ERROR writing score to file: %s\n", ex1);
	        		    }
	        			overMessage.getChildren().remove(getName);
	        			
	        			try {
	        		        BufferedWriter output = new BufferedWriter(new FileWriter(checkerFile, true));
	        		        output.append(" " + numBlueMoves);
	        		        output.close();

	        		    } catch (IOException ex1) {
	        		        System.out.printf("ERROR writing score to file: %s\n", ex1);
	        		    }
	    			});
	        	}
	        	
    	    }
    	});
    	
    	//putting rectangles in gridpane
    	for (int i = 0; i < 8; i++) {
    		for (int j = 0; j < 8; j++) {
    			Rectangle rec = new Rectangle(60, 60);
    			if((i % 2 == 0 && j % 2 == 1) || (i % 2 == 1 && j % 2 == 0)) {
    			rec.setFill(javafx.scene.paint.Color.BLACK);
    			}
    			else if((i % 2 == 0 && j % 2== 0) || (i % 2== 1 && j % 2 == 1)) {
    			rec.setFill(javafx.scene.paint.Color.CRIMSON);
    			}
    			
    			GridPane.setRowIndex(rec, i);
    			GridPane.setColumnIndex(rec, j);
    			checkerBoard.getChildren().addAll(rec);
    		}
    	}
    	checkersGroup.getChildren().add(checkerBoard);
    	
    	checkersInit(checkersGroup, checkerBoard);
    	
    	checkersStage.setResizable(false);
    	checkersStage.setTitle("Checkers!");
    	checkersStage.setScene(checkersScene);
    	
     //adding turn notification
    	whichMove = new Text("Blue Turn. Orange Moves: " + numOrangeMoves + " Blue Moves: " + numBlueMoves );
    	whichMove.setX(20);
    whichMove.setY(495);
    	checkersGroup.getChildren().add(whichMove);
    	
    	checkersStage.show();
    	instructionStage.show();
    	
    });
    
    
    
    
    HBox games = new HBox(75);
    games.setPadding(new Insets(75, 75, 75, 75));
    games.getChildren().addAll(breakout, checkers);
    start.setCenter(games);
    
    games.setStyle("-fx-background-image: url(https://i.imgur.com/H5MU94n.png);"
    				+ "-fx-background-size: cover, auto;");
    	
    Scene scene = new Scene(start, 960, 540);
    stage.setResizable(false);
    stage.setTitle("cs1302-arcade!");
    
    enterArcade.setOnAction(e12 -> {
    	stage.setScene(scene);
    	stage.sizeToScene();
        stage.show();
        timelineIntro.pause();
    	
    });
    
	

	// the group must request input focus to receive key events
	// @see https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Node.html#requestFocus--
	//group.requestFocus();

    } // start
    
    /**
     * fills 4 different arrays with rectangles representing the different levels of bricks
     * @return void
     */
    public void makeBricks() {
	    row2 = new Rectangle[14][2];
	    row4 = new Rectangle[14][2];
	    row6 = new Rectangle[14][2];
	    row8 = new Rectangle[14][2];
	    	for (int i = 0; i < row2.length; i++) {
	    		for (int j = 0; j < row2[1].length; j++) {
	    			brick = new Rectangle(43, 10);
	    			brick.setFill(javafx.scene.paint.Color.YELLOW);
	    			row2[i][j] = brick;
	    		}
	    	}
	    	//creating 3rd and 4th rows of bricks
	    	for (int i = 0; i < row4.length; i++) {
	    		for (int j = 0; j < row4[1].length; j++) {
	    			brick = new Rectangle(43, 10);
	    			brick.setFill(javafx.scene.paint.Color.GREEN);
	    			row4[i][j] = brick;
	    		}
	    	}
	    	//creating 5th and 6th rows of bricks
	    	for (int i = 0; i < row6.length; i++) {
	    		for (int j = 0; j < row6[1].length; j++) {
	    			brick = new Rectangle(43, 10);
	    			brick.setFill(javafx.scene.paint.Color.ORANGE);
	    			row6[i][j] = brick;
	    		}
	    	}
	    	//creating last two rows of bricks
	    	for (int i = 0; i < row8.length; i++) {
	    		for (int j = 0; j < row8[1].length; j++) {
	    			brick = new Rectangle(43, 10);
	    			brick.setFill(javafx.scene.paint.Color.RED);
	    			row8[i][j] = brick;
	    		}
	    	}
    }
    
    /**
	 * This method sets up the checker pieces on the board
	 * @param checkersGroup The root of the scene
	 * @param checkerBoard The checkerboard that the pieces are on
	 * @return void
	 */
    public void checkersInit(Group checkersGroup, GridPane checkerBoard) {
    	
    	pieces = new GridPane();
    	
    	pieces.setPadding(new Insets(10));
    	for (int i = 0; i < 8; i ++) {
    		for (int j = 0; j < 8; j++) {
    			piece = new Circle(20);	
    			if (i < 3) {
    				piece.setId("Orange");
    			} else {
    				piece.setId("Blue");
    			}
    			pieces.setHgap(20);
    			pieces.setVgap(18);
    			RowConstraints con = new RowConstraints();
    			con.setPrefHeight(42);
    			ColumnConstraints con2 = new ColumnConstraints();
    			con2.setPrefWidth(40);
    			pieces.getRowConstraints().add(con);
    			pieces.getColumnConstraints().add(con2);
    			 
    			//spring.minHeightProperty().bind(pieces.heightProperty());
    			
    			
    			if ((i == 0 || i ==2) && j % 2 == 1 ) {
	    			//Circle piece = new Circle(20);
	    			piece.setFill(javafx.scene.paint.Color.ORANGE);
	    			GridPane.setRowIndex(piece, i);
	    			GridPane.setColumnIndex(piece, j);
	    			pieces.getChildren().add(piece);
    			} else if (i == 1 && j % 2 == 0) {
    				//Circle piece = new Circle(20);
	    			piece.setFill(javafx.scene.paint.Color.ORANGE);
	    			GridPane.setRowIndex(piece, i);
	    			GridPane.setColumnIndex(piece, j);
	    			pieces.getChildren().add(piece);
    			} else if ((i == 7 || i == 5) && j % 2 == 0) {
    				piece.setFill(javafx.scene.paint.Color.BLUE);
	    			GridPane.setRowIndex(piece, i);
	    			GridPane.setColumnIndex(piece, j);
	    			pieces.getChildren().add(piece);
    			} else if(i == 6 && j % 2 == 1) {
    				piece.setFill(javafx.scene.paint.Color.BLUE);
	    			GridPane.setRowIndex(piece, i);
	    			GridPane.setColumnIndex(piece, j);
	    			pieces.getChildren().add(piece);
    			} 
    			else {
    				//GridPane.setRowIndex(spring, i);
	    			//GridPane.setColumnIndex(spring, j);
    			}
	    	}
    	}
    	checkersGroup.getChildren().clear();
    	checkersGroup.getChildren().addAll(checkerBoard, pieces);
    }
    
    
    /**
	 * This method returns the piece in the given row and column
	 * @param pieces The GridPane of the checker pieces in the game
	 * @param row The row of the gameboard
	 * @param col The column of the gameboard
	 * @return Node The node at the given row and column
	 */
    public Node checkSpace(GridPane pieces, int row, int col) {
    	for (Node space : pieces.getChildren()) {
    		if (GridPane.getColumnIndex(space) == col && GridPane.getRowIndex(space) == row) {
    			return space;
    		}
    	}
    	return null;
    }
    
    /**
	 * This method returns the color of the piece in the given row and column
	 * @param pieces The GridPane of the checker pieces in the game
	 * @param row The row of the gameboard
	 * @param col The column of the gameboard
	 * @return String The name of the color of the piece
	 */
    public String checkColor(GridPane pieces, int row, int col) {
    	
    	if (row < 0 || col < 0 || row > 7 || col > 7) {
    		return "";
    	}
    	for (Node space : pieces.getChildren()) {
    		if (GridPane.getColumnIndex(space) == col && GridPane.getRowIndex(space) == row) {
    			return space.getId();
    		}
    	}
    	return null;
    }

    /**
	 * This method checks if the given row and column contains a piece
	 * @param pieces The GridPane of the checker pieces in the game
	 * @param row The row of the gameboard
	 * @param col The column of the gameboard
	 * @return boolean Returns true if there is no piece, returns false
	 * if there is a piece
	 */
    public boolean isEmpty(GridPane pieces, int row, int col) {
    	if (row < 0 || col < 0 || row > 7 || col > 7) {
    		return false;
    	}
    	if (checkSpace(pieces, row, col) == null || checkSpace(pieces, row, col) == null) {
    		return true;
    	}
    	return false;
    }
    
    public static void main(String[] args) {
		try {
		    Application.launch(args);
		} catch (UnsupportedOperationException e) {
		    System.out.println(e);
		    System.err.println("If this is a DISPLAY problem, then your X server connection");
		    System.err.println("has likely timed out. This can generally be fixed by logging");
		    System.err.println("out and logging back in.");
		    System.exit(1);
		} // try
    } // main

} // ArcadeApp
