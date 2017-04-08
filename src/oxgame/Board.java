/**   
* @Title: Board.java 
* @Package oxgame 
* @Description: the value of grid
* @author Administrator 
* @date 2016/3/11
* @version V1.0   
*/

package oxgame;

import java.util.Random;


/** 
* @ClassName: Board
* @Description: the class that represents the OXGame grid
* @author Administrator
* @date 2016/3/11 
*/
public class Board {
     public final int GRID_SIZE;  //the size of grid
     private int[][] grid;       //the grid
     Random random;
/** 
* @Title: Board 
* @Description: initial the Board
* @param Stage  int size
*/
    public Board(int size) {
        GRID_SIZE=size;//grid size
        random=new Random();//AI hit

        grid = new int [GRID_SIZE] [GRID_SIZE];//inital the grid
        
        //inital the gird 0
        for(int i=0;i<GRID_SIZE;i++) {  
            for(int j=0;j<GRID_SIZE;j++) {
             grid[i][j]=0;
          }
       }
    }
     
    
/** 
* @Title: addRandomTile 
* @Description: VS AI, and the Ai can add O or X automaticly
* @param int tile
* @return void 
*/
    public void addRandomTile(int tile) {
        int count=0;
        for(int m=0;m<GRID_SIZE;m++) {			
           for(int n=0;n<GRID_SIZE;n++) {
			   
              if(grid[m][n]==0){
                 count++;//keep track of empty spots
              }   
           }
        } 
        //if no open spot, exit game
        if(count==0) {
           return;
        }
        
        int location=random.nextInt(count);    
        count=-1;
	//AI hit grid randomly
        for(int m=0;m<GRID_SIZE;m++) {			
           for(int n=0;n<GRID_SIZE;n++) {			   
              if(grid[m][n]==0) {
                 count++; 
                 if(count==location){                  
                     grid[m][n]=tile;   //assign the content value              
                 }
              }   
           }
        }    
        
    }
    

/** 
* @Title: isDrawn 
* @Description: judge drawn game
* @param void
* @return boolean 
*/
    public boolean isDrawn(){
        int count=0;
	//get the grid cell not be hitted
        for(int m=0;m<GRID_SIZE;m++) {			
           for(int n=0;n<GRID_SIZE;n++) {			   
              if(grid[m][n]==0)
              {
                 count++;
              }   
           }
        } 
        
        if(count==0)
            return true;//drawn
        else
            return false;//not drawn
    }
 
/** 
* @Title: reSet 
* @Description: reset the game
* @param void
* @return void 
*/
    public void reSet() {
		
        for(int i=0;i<GRID_SIZE;i++) {  		
           for(int j=0;j<GRID_SIZE;j++) {			   
              grid[i][j]=0;
           }
        }
    }
   
/** 
* @Title: isHit 
* @Description: judge the xth and yth grid  can hit
* @param int x,int y
* @return boolean 
*/
    public boolean isHit(int x,int y) {
		
        if(grid[x][y]==0)
            return true;
        
        return false;
    }
    
  
/** 
* @Title: setHit 
* @Description: set the O or X
* @param int x,int y,int tile
* @return void 
*/
     public void setHit(int x,int y,int tile) {
		 
        if(grid[x][y]==0) {
            grid[x][y]=tile;
        }
    }
     
     
/** 
* @Title: getGrid 
* @Description: get the Grid
* @param void
* @return int [][]  
*/
     public int [][] getGrid(){
         return grid;
     }
    
/** 
* @Title: isWin 
* @Description: judge whether win
* @param int tile
* @return boolean 
*/
    public boolean isWin(int tile) {
        //horizontal
		
        for(int i=0;i<GRID_SIZE;i++) {
			
            int flag=1;
			
            for(int j=0;j<GRID_SIZE;j++) {
				
              if(grid[i][j]!=tile){
                  flag=0;
                  break;
              }
            }
			
            if(flag==1) {
                return true;
            }
            
            //vertical
            flag=1;  
			
            for(int j=0;j<GRID_SIZE;j++) {
				
              if(grid[j][i]!=tile) {
                  flag=0;
                  break;
              }
            }
            if(flag==1) {
                return true;
            }
        }
        //slanting
          int flag=1;
		  
          for(int i=0;i<GRID_SIZE;i++) {
			  
               if(grid[i][i]!=tile) {
                  flag=0;
                  break;
              }
          }
		  
        if(flag==1) {
            return true;//win
        }
        
          flag=1;
          for(int i=0;i<GRID_SIZE;i++) {
               if(grid[i][GRID_SIZE-1-i]!=tile) {
                  flag=0;
                  break;
              }
          }
		  
          if(flag==1) {
                return true;
          }
        
          return false;//not win
    }
    
}
