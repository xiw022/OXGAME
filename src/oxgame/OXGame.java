/**   
* @Title: OXGame.java 
* @Package oxgame 
* @Description: play OX game
* @author Administrator 
* @date 2016/3/11
* @version V1.0   
*/
package oxgame;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/** 
* @ClassName: OXGame
* @Description: the class that run the OXGame 
* @author Administrator
* @date 2016/3/11 
*/
public class OXGame extends Application {
    
    private final int WIDTH=100; //the width of each tile
    //two button 
    Button btn_ai;   // run AI button
    Button btn_man;  //run MAN buttom
    
    Stage AIWindow;   //VS  AI
    Stage manWindow;  //VS man
    
    Board board;   //the grid
    StackPane sp;  //the span contains grid
    Text[][] tileTexts; // the turn text
    int gridSize=3;    //the size of grid
    
    int turn;   //who's turn
    boolean isGameOver; //judge game over
/** 
* @Title: start 
* @Description: start the game 
* @param Stage  primaryStage
* @return void 
*/
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("OXGame");
        
        //intial
        turn=1;  // whose turn 
        board=new Board(gridSize);  //initial grid
        tileTexts=new Text[gridSize][gridSize];
        //inital the text in grid
         for(int i=0;i<gridSize;i++)			 
            for(int j=0;j<gridSize;j++) {				
                tileTexts[i][j]=new Text();              
                tileTexts[i][j].setText("");
                tileTexts[i][j].setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
                tileTexts[i][j].setFill(Color.rgb(134, 125, 147));
            }
         
        //open AI window,and VS AI
        btn_ai=new Button();
        btn_ai.setText("VS AI");
        btn_ai.setMinWidth(150);
        //set the ActionEvent on btn_ai
        btn_ai.setOnAction(new EventHandler<ActionEvent>() {            
            @Override
            public void handle(ActionEvent event) {
                turn=1;
                isGameOver=false;
                board.reSet();
                initialAI(primaryStage); //show the window of AI
                updateGrid();  //update grid
            }
        });
        
        //open Man window,and VS Man
        btn_man=new Button();
        btn_man.setText("VS MAN");
        btn_man.setMinWidth(150);
        btn_man.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                turn=1;
                isGameOver=false;
                board.reSet();
                initialMan(primaryStage); //show the windows of MAN
                updateGrid();  //update grid
            }
         });
       
       
       //initial VBox
        VBox vbox=new VBox();
        vbox.setSpacing ( 100 ) ;
        vbox.setStyle ("-fx-background-color: rgb(131, 175, 155)") ;
        vbox.getChildren().addAll(btn_ai,btn_man);
        vbox.setAlignment(Pos.CENTER);
        primaryStage.setScene(new Scene(vbox,300,300));  
        primaryStage.show();  //show the window
    }
   
/** 
* @Title: updateGrid 
* @Description: update the scene depend on the board object
* @param void
* @return void 
*/      
void updateGrid()
{
    //get the grid
     int [][] grid=board.getGrid();
     // show the text by the value in grid
    for(int i=0;i<gridSize;i++) {			   
        for(int j=0;j<gridSize;j++) {				   
           if(grid[i][j]==0) {
               tileTexts[i][j].setText("");
           }
           if(grid[i][j]==1) {
               tileTexts[i][j].setText("O");
           }
            if(grid[i][j]==2) {
               tileTexts[i][j].setText("X");
           }
        }
    }
}

   
/** 
* @Title: initialAI 
* @Description: initial AI window and open it
* @param Stage primaryStage
* @return void 
*/  
void initialAI(Stage primaryStage)
{
    //initial the AI window
    AIWindow=new Stage();
    AIWindow.initModality(Modality.APPLICATION_MODAL);
    // set the layout of the AI window
    VBox root=new VBox();
    GridPane pane = new GridPane();
    pane.setAlignment(Pos.CENTER); 
    pane.setPadding(new Insets(0, 15, 0, 15));
    //set the text that shows the O or X in layout
    for(int i=0;i<gridSize;i++)
        for(int j=0;j<gridSize;j++){
            Rectangle rec=new Rectangle();
            rec.setWidth(WIDTH);
            rec.setHeight(WIDTH);
            rec.setFill(Color.rgb(174,221,129));
            pane.add(rec, j, i);
            pane.add(tileTexts[i][j], j, i);
            GridPane.setHalignment(tileTexts[i][j], HPos.CENTER);
        }
    // show the grid line
    pane.setGridLinesVisible(true);       
    pane.setOnMouseClicked(new EventHandler<MouseEvent>() {  
        public void handle(MouseEvent me) {  
            //judge the game is over or not
            if(isGameOver==true)
                return;

            int y=(int)me.getX()-15;
            int x=(int)me.getY();
            //judge the hit point whether in the grid
            
            //judge the hit in the grid
            if(x>=0&&x<=300&&y>=0&&y<=300) {
                int i=x/100;
                int j=y/100;
                System.out.println(i+","+j);

                if(!board.isHit(i, j))
                    return;
                // update the grid after hitting
                board.setHit(i, j, turn);
                updateGrid();
                
                //judge the man is win or not
                if( board.isWin(turn)) {//man win
                    String str=turn==1?"O":"X";
                    System.out.println("str Win");
                    Text gameOver=new Text();
                    gameOver.setText(str+" Win!");
                    gameOver.setFont(Font.font("Times New Roman", FontWeight.BOLD, 70));
                    gameOver.setFill(Color.BLACK); 
                    root.setStyle("-fx-opacity: 0.4;");
                    sp.getChildren().add(gameOver);
                    isGameOver=true;
                    return;
                }
                //judge is drawn or not
               if( board.isDrawn()) {//drawn game
                    String str="Drawn Game";                        
                    Text gameOver=new Text();
                    gameOver.setText(str);
                    gameOver.setFont(Font.font("Times New Roman", FontWeight.BOLD, 50));
                    gameOver.setFill(Color.BLACK);    
                    root.setStyle("-fx-opacity: 0.4;");
                    sp.getChildren().add(gameOver);
                    isGameOver=true;
                    return;
                }

                turn=turn==1?2:1;     
                //AI hit and update the grid
                board.addRandomTile(turn);
                updateGrid();
                //judge AI is win or not
                if( board.isWin(turn)) {//Ai win
                    String str=turn==1?"O":"X";
                    System.out.println("str Win");
                    Text gameOver=new Text();
                    gameOver.setText(str+" Win!");
                    gameOver.setFont(Font.font("Times New Roman", FontWeight.BOLD, 70));
                    gameOver.setFill(Color.BLACK);  
                    root.setStyle("-fx-opacity: 0.4;");
                    sp.getChildren().add(gameOver);
                    isGameOver=true;
                    return;
                }
                //judge is drawn or not
                if( board.isDrawn()) {//drawn game
                    String str="Drawn Game";                        
                    Text gameOver=new Text();
                    gameOver.setText(str);
                    gameOver.setFont(Font.font("Times New Roman", FontWeight.BOLD, 50));
                    gameOver.setFill(Color.BLACK);   
                    root.setStyle("-fx-opacity: 0.4;");
                    sp.getChildren().add(gameOver);
                    isGameOver=true;
                    return;
                }

                turn=turn==1?2:1;

            }
        }
    }); 
    //add the layout
    root.getChildren().add(pane);   
    root.setAlignment(Pos.CENTER);

    sp=new StackPane(); 
    sp.getChildren().add(root);

    Scene scene=new Scene(sp,310,301);
    AIWindow.setTitle("AI");
    AIWindow.setScene(scene);                
    AIWindow.setResizable(false);  
    AIWindow.show();//show window
}
    
     
/** 
* @Title: initialMan 
* @Description: initial Man window and open it
* @param Stage primaryStage
* @return void 
*/  
void initialMan(Stage primaryStage) {
    //initial manwindow
    manWindow=new Stage();
    manWindow.initModality(Modality.APPLICATION_MODAL);

    String str=turn==1?"O":"X";
    Text yourTurn=new Text();
    yourTurn.setText("TURN: "+str);
    yourTurn.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));
    
    //set the layout of MANWindow
    VBox root=new VBox();
    GridPane pane = new GridPane();
    pane.setAlignment(Pos.CENTER); 
    pane.setPadding(new Insets(0, 15, 0, 15));
    
    //add the text that shows O or X to grid
     for(int i=0;i<gridSize;i++)

        for(int j=0;j<gridSize;j++)  {
            Rectangle rec=new Rectangle();
            rec.setWidth(WIDTH);
            rec.setHeight(WIDTH);
            rec.setFill(Color.rgb(174,221,129));
            pane.add(rec, j, i);
            pane.add(tileTexts[i][j], j, i);
            GridPane.setHalignment(tileTexts[i][j], HPos.CENTER);
        }
     //shwo the grid lines
    pane.setGridLinesVisible(true);

    pane.setOnMouseClicked(new EventHandler<MouseEvent>() {  
        public void handle(MouseEvent me) { 
            //judge the game is over or not
            if(isGameOver==true)
                return;
            int y=(int)me.getX()-15;
            int x=(int)me.getY();
            //judge the hit is in grid or not
            if(x>=0&&x<=300&&y>=0&&y<=300)
            {
                int i=x/100;
                int j=y/100;
                System.out.println(i+","+j);
                
                if(!board.isHit(i, j))
                    return;
                //updathe the value in gird cell whith is hitted
                board.setHit(i, j, turn);
                updateGrid();
                // someone is win
                if( board.isWin(turn))  {   //win
                    String str=turn==1?"O":"X";
                    System.out.println("str Win");
                    Text gameOver=new Text();
                    gameOver.setText(str+" Win!");
                    gameOver.setFont(Font.font("Times New Roman", FontWeight.BOLD, 70));
                    gameOver.setFill(Color.BLACK);   
                    root.setStyle("-fx-opacity: 0.4;");
                    sp.getChildren().add(gameOver);
                    isGameOver=true;
                    return;
                }
                //the game is drawn
               if( board.isDrawn())//Drawn
                {
                    String str="Drawn Game";                        
                    Text gameOver=new Text();
                    gameOver.setText(str);
                    gameOver.setFont(Font.font("Times New Roman", FontWeight.BOLD, 50));
                    gameOver.setFill(Color.BLACK);    
                    root.setStyle("-fx-opacity: 0.4;");
                    sp.getChildren().add(gameOver);
                    isGameOver=true;
                    return;
                }

                turn=turn==1?2:1;                     
                String str=turn==1?"O":"X";      
                yourTurn.setText("TURN: "+str);
            }
        }
    }); 

    root.getChildren().addAll(yourTurn,pane);   
    root.setAlignment(Pos.CENTER);

    sp=new StackPane(); 
    sp.getChildren().add(root);

    Scene scene=new Scene(sp,310,401);
    manWindow.setTitle("MAN");
    manWindow.setScene(scene);                
    manWindow.setResizable(false);  
    manWindow.show(); 
}
    
/** 
* @Title: main 
* @Description: launch the programme
* @param String[] args
* @return void 
*/  
    public static void main(String[] args) {
        launch(args);
    }
    
}
