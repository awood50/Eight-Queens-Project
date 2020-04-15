/*
 * Andrew Wood
 * 9/1/19
 * 8Queens class that uses restarts and hill climb algorithms to prevent conflicting queen values on an 8x8 board
 * 
 */

import java.util.Random;
import java.util.*;


public class QueensAssignment {
  
    final private int [][] BOARD = new int[8][8]; //creates the 8x8 board with an integer array
    final private int [][] PRACTICE = new int[8][8];//more of a test value baord
    
    private boolean addBoard = true; //boolean to create a new board or not
    
    private int neighbors = 8;
    private int queens = 0;
    private int searchH = 0;//heuristic value
    private int numberOfMoves = 0; //moves to be calculated at the end
    private int numberOfRestarts = 0; //restarts to be calculated at the end
    
    
    
    
    
  
  
    public void initialize( ){ //void method that creates the initial board and fills it with values
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
               BOARD[i][j] = 0; //creates the board with values from 0-8
            }
        }
    }
  
  
    public void randomMap( )//method to create a new random map
    { 
      Random randValue = new Random( );//creates a random value
      int num;
      
      while(queens < 8)//while there are not 8 queens on the board
      {
            for(int i = 0; i < 8; i++)
            {
               BOARD[randValue.nextInt(7)][i] = 1;//gets the value of the next space
               queens++;//adds a queen to a space
            }
      }
      searchH = calculation(BOARD);//This is used to search the board for the certain heuristic value
    }
  
    
  
    public boolean rowQueens(int [][] example, int r) //This method determines if there are too many queens in a row
    { 
        boolean tooMany = false;//tooMany is already set to false so that problems can be found
        int count = 0;
      
        for(int i = 0; i < 8; i++)
        {
            if(example[i][r] == 1)//if there is a queen in the row
            {
                count++;//incremented if there is already a queen
            }
        }
        if(count > 1){//if more than one queen is in the row
            tooMany = true;//there are too many in the row 
        }
        return tooMany;//returns the value of the boolean
    }
    
    public boolean columnQueens(int [][] example, int c)//Determines if there are too many queens in a column
    { 
        boolean tooMany = false;//immediately set to false
        int count = 0;
        for(int i = 0; i < 8; i++)//iterates through the values
        {
            if(example[c][i] == 1)//if there is a queen in the column
            {
                count++;//increment
            }
        }
        if(count > 1)//if there is more than one queen in the column
        {
          tooMany = true;//boolean set to true
        }
        return tooMany;//return boolean value
    }
  
    public boolean diagonalQueens(int [][] example, int d, int g)//Checks diagonals for queens
    {
        boolean diaG = false;//diagonal queens set to false
      
        for(int i = 1; i < 8; i++)//iteration
        {
            if(diaG)
            {
                break;//used to start the loop from the beginning
            }

            if((d+i < 8)&&(g+i < 8))//finding the value of each diagonal
            {
                if(example[d+i][g+i] == 1)//finds queens
                {
                    diaG = true;//if total value of queens for both spaces is one, then it is too many
                }
            }
            if((d-i >= 0)&&(g-i >= 0))//checking each diagonal value
            {
                if(example[d-i][g-i] == 1)//if total value of queens for both spaces is one, then it is too many
                {
                    diaG = true;
                }
            }
            if((d+i < 8)&&(g-i >= 0))//checking each diagonal value
            {
                if(example[d+i][g-i] == 1)//if total value of queens for both spaces is one, then it is too many
                {
                    diaG = true;
                }
            }  
            if((d-i >= 0)&&(g+i < 8))//checking each diagonal value
            {
                if(example[d-i][g+i] == 1)//if total value of queens for both spaces is one, then it is too many
                {
                    diaG = true;
                }
            }  
        }
        return diaG; //returns if there are too many diagonal queens or not
    }
  
    public int calculation(int [][] example)//Method used to total the number of conflicting queens
    {
    
    boolean columnsFound;
    boolean rowsFound;
    boolean diagonalsFound;
    
    
    int count = 0;
      
        for(int i = 0; i < 8; i++){//iterates through board
            for(int j= 0; j < 8; j++)
            {
                if(example[i][j] == 1)
                {
                	columnsFound = columnQueens(example, i);//uses columnQueens method to calculate number of queens in columns
                	rowsFound = rowQueens(example, j);//uses rowQueens method to calculate number of queens in rows              	
                	diagonalsFound = diagonalQueens(example, i, j);//uses diagonalQueens method to calculate number of queens in diagonals
                  
                    if(rowsFound || columnsFound || diagonalsFound)//if there are queens found in any spot
                    {
                        count++;//increment number of queens
                    }
                }
            }
        }
        return count;//return number of queens
    }
    
    public void moving( )//Queens move and then calculates options of continuing, restarting, or ending
    { 
    	int[][] boards = new int[8][8]; //new array
        int numColumns; //column queens
        int columnsMin;
        int rowsMin;
        int columnValue = 0;
        addBoard = false; //create new board is set to false
      
        while(true) //while adding a board is set to true
        {
            numColumns = 0;
      
            for(int i = 0; i < 8; i++)//iterates
            {
                System.arraycopy(BOARD[i], 0, PRACTICE[i], 0, 8); //copies the original board onto a test board
            }
            while(numColumns < 8) //while the number of columns is less than 8
            {                                                                                                                                           
                for(int i = 0; i < 8;i++)
                {
                    PRACTICE[i][numColumns] = 0;//minimum columns of test board
                }
                for(int i = 0; i < 8; i++)
                {
                    if(BOARD[i][numColumns] == 1)
                    {
                        columnValue = i;//sets value of columns to that value
                    }
                    PRACTICE[i][numColumns] = 1;
                    boards[i][numColumns] = calculation(PRACTICE); //calculates queens on practice board
                    PRACTICE[i][numColumns] = 0;
                }
                PRACTICE[columnValue][numColumns] = 1;
                numColumns++;//increases the number of columns
            }
          
            if(Restart(boards))//uses restart method with the integer array
            {
            	queens = 0; //number of queens is set to 0
                for(int i = 0; i < 8; i++)
                {
                    for(int j = 0; j < 8; j++)
                    {
                       BOARD[i][j] = 0; //sets up board
                    }
                }
                randomMap( );//creates a new board
                System.out.println("RESTART");
                numberOfRestarts++;//increments restarts
            }
      
            columnsMin = minColumns(boards);//finds the minimum columns of neighbor
            rowsMin = minRows(boards);//finds the minimum rows of neighbor
      
            for(int i = 0; i < 8; i++)
            {
               BOARD[i][columnsMin] = 0;//board has minimum columns
            }
      
           BOARD[rowsMin][columnsMin] = 1;
           numberOfMoves++;//increases movement
            searchH = calculation(BOARD);
          
            if(calculation(BOARD) == 0)
            {
                System.out.println("\nCurrent State");
                for(int i = 0; i < 8; i++)
                {
                    for(int j = 0; j < 8; j++)
                    {
                        System.out.print(BOARD[i][j] + " ");
                    }
                    System.out.print("\n");
                }
           
            //print messages    
            System.out.println("Solution Found!");
            System.out.println("State changes: " + numberOfMoves);
            System.out.println("Restarts: " + numberOfRestarts);
            break;
            }

            //prints messages
            System.out.println("\n");
            System.out.println("Current h: " + searchH);
            System.out.println("Current State");
            for(int i = 0; i < 8; i++)
            {
                for(int j = 0; j < 8; j++)
                {
                    System.out.print(BOARD[i][j] + " ");
                }
                System.out.print("\n");
            }
            //Print messages
            System.out.println("Neighbors found with lower h: " + neighbors);
            System.out.println("Setting new current State");
        }
    }
    public int minColumns(int[][] example)//Minimum neighbor state columns found
    { 
        int cols = 8;//column number
        int minimum = 8;
        int count = 0;
      
        for(int i = 0; i < 8; i++)
        {
          for(int j = 0; j < 8; j++)
          {
              if(example[i][j] < minimum)//if array is less than the minimum number
              {
                  minimum = example[i][j];
                  cols = j;//columns equal to array value
              }
              if(example[i][j] < searchH){//uses searching heuristic
                  count++;//count increased
              }
          }
        }
        neighbors = count;
        return cols;//return number of columns
    }
    public int minRows(int[][] test)//same method as minColumns but with rows
    { 
        int rowsMin = 8;
        int minVal = 8;
      
        for(int i = 0; i < 8; i++)
        {
          for(int j = 0; j < 8; j++)
          {
              if(test[i][j] < minVal)
              {
                  minVal = test[i][j];
                  rowsMin = i;
              }
          }
        }
        return rowsMin;
    }
    public boolean Restart(int [][] example){//Calculates if restart is needed
        int counter = 8;
        boolean newMap = false;//set restart to false
      
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(example[i][j] < counter)//if array is less than 8
                {
                    counter = example[i][j]; //counter is equal to array value
                }
            }
        }
        if(neighbors == 0)//if the neighboring values ==0
        {
            newMap = true;//new map is needed
        }
        return newMap;//return value of new map needed or not
    }
  
    public static void main(String[] args) 
    {// creates object, creates initial random map, then initiates state change
     QueensAssignment test = new QueensAssignment( );//creates new instance of the class to use
     test.randomMap();//creates map
     test.moving();//moves queens
    }
}
