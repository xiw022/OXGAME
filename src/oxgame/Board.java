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
     boolean[] isWin;
     boolean isDrawn;
     int[] rows;
     int[] cols;
     int diag;
     int diag2;
     int total;
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
        rows = new int[GRID_SIZE];
        cols = new int[GRID_SIZE];
        isWin = new boolean[2];
        isDrawn = false;
        diag = 0;
        diag2 = 0;
    }


/**
* @Title: addRandomTile
* @Description: VS AI, and the Ai can add O or X automaticly
* @param int tile
* @return void
*/
    public void addRandomTile(int tile) {
      int obj_tile=tile==1?2:1;
      int[][][] w_grid=new int[3][3][8];
      int tx,ty;
      int [] dx=new int[]{0, 1, 1, 1, 0, -1, -1, -1};
      int [] dy=new int[]{-1, -1, 0, 1, 1, 1, 0, -1};

      int cnt;
      for(int i=0;i<3;i++)
      {
          for(int j=0;j<3;j++)
          {
              if(grid[i][j]==0)
              {
                  for(int k=0;k<8;k++)
                  {
                      cnt=0;
                      tx=i;
                      ty=j;
                      for(int t=0;t<3;t++)
                      {
                          tx+=dx[k];
                          ty+=dy[k];
                          if(tx>=3||tx<0||ty>=3||ty<0)
                              break;
                          if(grid[tx][ty]==obj_tile)
                          {
                              cnt++;
                          }
                          else
                          {
                              break;
                          }
                      }
                      w_grid[i][j][k]=cnt;
                  }
              }
          }
      }
      int [][] ai=new int[3][3];
      int win;
      for(int i=0;i<3;i++)
      {
          for(int j=0;j<3;j++)
          {
              win=0;
              if(grid[i][j]==0)
              {
                for ( int k = 0; k < 4; k++ )  // direction
                {
                    if (  w_grid[i][j][k] + w_grid[i][j][k+4] >= 2 )
                        win += 100;
                    else if ( w_grid[i][j][k] + w_grid[i][j][k+4]  == 1 )
                        win += 10;
                    else
                        win+=1;
                }
                ai[i][j]=win;
              }
          }
      }
      int max=0;
      tx=-1;
      ty=-1;
      for(int i=0;i<3;i++)
      {
          for(int j=0;j<3;j++)
          {
              if(ai[i][j]>max)
              {
                  max=ai[i][j];
                  tx=i;
                  ty=j;
              }
          }
       }
      if(tx!=-1&&ty!=-1)
         grid[tx][ty]=tile;

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
    * @Title: isDrawn REFACTORED VERSION
    * @Description: judge drawn game
    * @param void
    * @return boolean
    */
        public boolean isDrawn2(){
            int count=0;
    	//get the grid cell not be hitted
            return isDrawn;
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
        for(int i = 0; i < 2; i ++) isWin[i] = false;
        
        for(int i = 0; i< 3; i ++) {
        	rows[i] = 0;
        	cols[i] = 0;
        }
        diag = 0;
        diag2 = 0;
        total = 0;
        isDrawn = false;
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
            int tmp = tile == 1 ? 1 : -1;
            rows[x] += tmp;
            cols[y] += tmp;
            if(x == y) diag += tmp;
           
            if(x + y + 1 == GRID_SIZE) diag2 += tmp;
            
            System.out.println("cols[x]: " + cols[x]);
            if(rows[x] == 3 || cols[y] == 3 || diag == 3 || diag2 == 3) {
            	//System.out.println("tile win: " +  tile);
            	  isWin[tile - 1] = true;
            }
            else if(rows[x] == -3 || cols[y] == -3 || diag == -3 || diag2 == -3) {
            	//System.out.println("tile win: " +  tile);
            	  isWin[tile - 1] = true;
            }
            else if(++total == GRID_SIZE*GRID_SIZE) {
              isDrawn = true;
            }
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
    
    /**
    * @Title: isWin REFACTORED VERSION
    * @Description: judge whether win
    * @param int tile
    * @return boolean
    */
        public boolean isWin2(int tile) {
        	  return isWin[tile - 1];
        }

}
