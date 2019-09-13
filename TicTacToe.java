
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;
public class TicTacToe {
        
    public static void main(String[] args) {   
        
        
        play();
        
        
    }
    
    //Part A
    // a method that takes as input one integer n, representing the dimension of the board, and returns an n by n 
    // array of characters (i.e. a two dimensional array of type char) 
    public static char[][] createBoard(int n) {
        char[][] board = new char[n][n];
        // using a loop to set each element of each subarray to a space character ' '
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                board[i][j] = ' ';
            }
        }
        return board;
    }
    
    
    //Part B
    // a method that takes a 2 dimensional array of character as input and prints out the board
    public static void displayBoard(char[][] a) {
        int n = a.length;  // n represents the length of the 2D array which gives the dimension n by n of the board
        for (int i=0; i<n; i++) {
            printBorder(n);   // print the first border of +- on top
            System.out.print("|");    // print the leftmost | 
            for (int j=0; j<a[i].length; j++) {
                System.out.print(a[i][j] + "|");    // print the each element of subarray followed by | 
            }
            System.out.println();   
        }
        printBorder(n);        // print the last border to close up the board
    }
    
    // helper method for printing single line of +- for the top and bottom of board
    public static void printBorder(int n) {       
        // printing out the + and - of each row
        for (int j=0; j<2*n+1; j++) {   // for an n  by n board there are (2*n+1) +'s and -'s for each row
            // the +'s are in indexes 0,2,4,6... (i.e. even indexes)
            if (j%2==0) {
                System.out.print("+");
            } else {              // the -'s are in indexes 1,3,5,7... (i.e. in the odd indexes)
                System.out.print("-");
            }
        }
        System.out.println();
    }
    
    
    //Part C
    // a method that takes as input the board (a 2 dimensional array of character), the character to write, and two
    // integers x and y representing the position on the board where the character should be written
    // int x represents the rows, int y represents the columns
    // similar to n by n matrix; in this case (x,y) = (0,1) is the position in the first row, second column
    public static void writeOnBoard(char[][] a, char c, int x, int y) {
        int n = a.length;
        // throw an exception if the given coordinates are outside the board
        if (x<0 || x>=n || y<0 || y>=n) {
            throw new IllegalArgumentException("That position is outside the board!");
            
            // throw an exception if the coordinate already contains a character other than the space character
        } else if (a[x][y] != ' ') {
            throw new IllegalArgumentException("That position is already occupied,  please pick another position.");
        }
        // set the position(x,y) on the board as the character c given as input and print it out
        a[x][y] = c;          
    }
    
    
    // Part D
    // a method that takes the board as input and returns no value. Uses Scanner to get a move from the user
    public static void getUserMove(char[][] a) {
        int n = a.length;
        // storing the input from user into variables row and column
        Scanner read = new Scanner(System.in);
        int row = read.nextInt();       
        int column = read.nextInt();
        // keep asking user for move if the move entered as input is not valid
        while (!isValid(a, row, column)) {
            if (row<0 || row>n-1 || column<0 || column>n-1) {
                System.out.println("This move is outside the board. Please enter another move.");
            } else if (a[row][column] != ' ') {
                System.out.println("This position is already occupied. Please enter a new move.");
            }
            row = read.nextInt();
            column = read.nextInt();
        }
        // update the 2D array with the valid move from user
        writeOnBoard(a,'x',row,column);
        
    }
    
    // helper method for determining if a move is valid  (for Part D)
    public static boolean isValid(char[][] a, int row, int column) {
        int n = a.length;
        if (row<0 || row>n-1 || column<0 || column>n-1 || a[row][column] != ' ') {
            return false;
        }
        return true;   
    }
    
    
    //Part E
    // a method that takes the board as input and returns true if there's an "obvious move" the AI should do, false
    // otherwise
    public static boolean checkForObviousMove(char[][] a) {
        int position = 0;
        int n = a.length;
        
        for (int i=0; i<a.length; i++) {
            
            char[] row = rowArray(a,i);
            char[] col = columnArray(a,i);
            char[] diagOne = diagonalOneArray(a);
            char[] diagTwo = diagonalTwoArray(a);
            
            // must satisfy both winRow(a) AND countChar(row, 'o')==n-1 because if winRow(a) is satisfied
            // we need the array rowArray which takes input i to be the correct row. If had only if(winRow(a)) then if
            // i was 0 (i.e. at the start of the loop) and winRow(a) was true then we would be manipulating
            // the wrong rowArray array because it may not be the 1D array we need.
            // The 1D array we need is the one that only has one space open and in this case also has 2 o's
            if (winRow(a) && countChar(row, 'o') == n-1) {                
                for (int j=0; j<row.length; j++) {
                    if (row[j] == ' ') {    
                        position = j;
                    }
                }
                writeOnBoard(a,'o',i,position);  // Note the coordinates (i,position) where i is the row and position
                return true;         // is the column which we extracted from the for loop above
                
            } else if (winColumn(a) && countChar(col, 'o') == n-1) {  //
                for (int l=0; l<col.length; l++) {
                    if (col[l] == ' ') {
                        position = l;
                    }                
                }
                writeOnBoard(a,'o',position,i);  // the coordinates are (position, i) which is the opposite of 
                return true;                     // the coordinates for winRow above
                
            } else if (winDiagonalOne(a)) {
                for (int q=0; q<diagOne.length; q++) {
                    if (diagOne[q] == ' ') {
                        position = q;
                    }                
                }   
                writeOnBoard(a,'o',position,position);  // first diagonal has position (0,0)(1,1)(2,2)..
                return true;
                
            } else if (winDiagonalTwo(a)) {
                for (int q=0; q<diagTwo.length; q++) {
                    if (diagTwo[q] == ' ') {
                        position = q;
                    }                
                }  
                writeOnBoard(a,'o',position,n-1-position); //Second diagonal has position (2,0)(1,1)(0,2)
                return true;
            } 
        }
        
        // The for loop for checking if the AI is losing is after the for loop for checking if AI is winning as
        // we want the AI to choose to win the game insetead of blocking an obvious win for the user.
        for (int i=0; i<a.length; i++) {
            char[] row = rowArray(a,i);
            char[] col = columnArray(a,i);
            char[] diagOne = diagonalOneArray(a);
            char[] diagTwo = diagonalTwoArray(a);
            // the 4 conditions below check whether the AI is about to lose(the user is about to win) and 
            // gives instructions for the AI to block the user
            if (loseRow(a) && countChar(row, 'x') == n-1) {
                for (int k=0; k<row.length; k++) {
                    if (row[k] == ' ') {
                        position = k;
                    }                
                } 
                writeOnBoard(a,'o',i,position);
                return true;
                
            }  else if (loseColumn(a) && countChar(col, 'x') == n-1) {
                for (int m=0; m<col.length; m++) {
                    if (col[m] == ' ') {
                        position = m;
                    }                
                } 
                writeOnBoard(a,'o',position,i);
                return true;
                
            } else if (loseDiagonalOne(a)) {
                for (int q=0; q<diagOne.length; q++) {
                    if (diagOne[q] == ' ') {
                        position = q;
                    }                
                }  
                writeOnBoard(a,'o',position,position);
                return true;
                
            } else if (loseDiagonalTwo(a)) {
                for (int q=0; q<diagTwo.length; q++) {
                    if (diagTwo[q] == ' ') {
                        position = q;
                    }                
                } 
                writeOnBoard(a,'o',position,n-1-position);
                return true;
            }
        }
        // if there is no obvious move(i.e. the AI is not about to win, the user is not about to win) then return 
        // false 
        return false;
        
    }
    
    // 14 helper methods below
    
    // a method that takes the board and an int (used for specifying which row) as input
    // and returns a row of the board (as a 1 dimensional array) 
    public static char[] rowArray(char[][] a, int row) {
        int n = a.length;
        char[] array = new char[n];
        for (int i=0; i<n; i++) {
            array[i] = a[row][i];
        }
        return array;
    }
    
    // a method that takes the board and an int as input(used for specifying the column of interest)
    // and returns a column of the board (as a 1 dimensional array) 
    public static char[] columnArray(char[][] a, int column) {
        int n = a.length;
        char[] array = new char[n];
        for (int i=0; i<n; i++) {
            array[i] = a[i][column];
        }
        return array;
    }
    
    // a method that takes the board as input and returns the first diagonal of the board (as a 1 dimensional array) 
    public static char[] diagonalOneArray(char[][] a) {
        int n = a.length;
        char[] array = new char[n];
        for (int i=0; i<n; i++) {
            array[i] = a[i][i];
        }
        return array;
    }
    
    // a method that takes the board as input and returns the second diagonal of the board (as a 1 dimensional array)
    public static char[] diagonalTwoArray(char[][] a) {
        int n = a.length;
        char[] array = new char[n];
        for (int i=0; i<n; i++) {
            array[i] = a[i][n-1-i];
        }
        return array;
    }
    
    // a method that takes a 1 dimensional array of characters and a char c as input. The method counts
    // how many c's are in the array
    public static int countChar(char[] array, char c) {
        int counter = 0;
        for (int i=0; i<array.length; i++) {
            if (array[i] == c) {
                counter++;
            }
        }
        return counter;
    }
    
    // a method that returns true if there is a cell with a space character along 
    // a row/column/diagonal (given a 1D array) 
    public static boolean hasSpaceChar(char[] array) {
        for (int i=0; i<array.length; i++) {
            if (array[i] == ' ') {
                return true;
            }
        }
        return false;
    }
    
    // a method that checks whether the AI is winning on a row
    public static boolean winRow(char[][] a) {
        for (int i=0; i<a.length; i++) {
            // check each row i for a space character and the number of 'o' in the row
            // if there is 1 space character and 2 o's then the AI is about to win and method returns true
            if (hasSpaceChar(rowArray(a,i)) && countChar(rowArray(a,i),' ')==1 
                    && countChar(rowArray(a,i),'o')==a.length-1) {
                return true;
            } 
        }
        return false;
        
    }
    
    // a method that checks whether the AI is losing on a row (i.e. the user is about to win)
    // check each row i for a space character and the number of 'x' in the row
    // if there is 1 space character and 2 x's then the user is about to win and method returns true
    public static boolean loseRow(char[][] a) {
        for (int i=0; i<a.length; i++) {
            if (hasSpaceChar(rowArray(a,i)) && countChar(rowArray(a,i),' ')==1 
                    && countChar(rowArray(a,i),'x')==a.length-1) {
                return true;
            }
        }
        return false;
    }
    
    // a method that checks whether the AI is winning on a column
    // check each column i for a space character and the number of 'o' in the column
    // if there is 1 space character and 2 o's then the AI is about to win and method returns true
    public static boolean winColumn(char[][] a) {
        for (int i=0; i<a.length; i++) {
            if (hasSpaceChar(columnArray(a,i)) && countChar(columnArray(a,i),' ')==1 && 
                countChar(columnArray(a,i),'o')==a.length-1) {
                return true;
            } 
        }
        return false;
    }
    
    // a method that checks whether the AI is losing on a column (i.e the user is about to win)
    // check each column i for a space character and the number of 'x' in the column
    // if there is 1 space character and 2 x's then the user is about to win (AI about to lose) 
    // and method returns true
    public static boolean loseColumn(char[][] a) {
        for (int i=0; i<a.length; i++) {
            if (hasSpaceChar(columnArray(a,i)) && countChar(columnArray(a,i),' ')==1 
                    && countChar(columnArray(a,i),'x')==a.length-1) {
                return true;
            }
        }
        return false;
    }
    
    // a method that checks whether the AI is winning on the first diagonal
    // if there is 1 space character and 2 o's then the AI is about to win and method returns true
    public static boolean winDiagonalOne(char[][] a) {
        for (int i=0; i<a.length; i++) {
            if (hasSpaceChar(diagonalOneArray(a)) && countChar(diagonalOneArray(a),' ')==1 && 
                countChar(diagonalOneArray(a),'o')==a.length-1) {
                return true;
            } 
        }
        return false;
    }
    
    // a method that checks whether the AI is losing on the first diagonal (i.e. the user is about to win)
    // if there is 1 space character and 2 x's then the user is about to win(AI about to lose) and method returns true
    public static boolean loseDiagonalOne(char[][] a) {
        for (int i=0; i<a.length; i++) {
            if (hasSpaceChar(diagonalOneArray(a)) && countChar(diagonalOneArray(a),' ')==1 
                    && countChar(diagonalOneArray(a),'x')==a.length-1) {
                return true;
            }
        }
        return false;
    }
    
    // a method that checks whether the AI is winning on the second diagonal
    // if there is 1 space character and 2 o's then the AI is about to win and method returns true
    public static boolean winDiagonalTwo(char[][] a) {
        for (int i=0; i<a.length; i++) {
            if (hasSpaceChar(diagonalTwoArray(a)) && countChar(diagonalTwoArray(a),' ')==1 && 
                countChar(diagonalTwoArray(a),'o')==a.length-1) {
                return true;
            } 
        }
        return false;
    }
    
    // a method that checks whether the AI is losing on the second diagonal (i.e the user is about to win)
    // if there is 1 space character and 2 x's then the user is about to win(AI about to lose) and method returns true
    public static boolean loseDiagonalTwo(char[][] a) {
        for (int i=0; i<a.length; i++) {
            if (hasSpaceChar(diagonalTwoArray(a)) && countChar(diagonalTwoArray(a),' ')==1 
                    && countChar(diagonalTwoArray(a),'x')==a.length-1) {
                return true;
            }
        }
        return false;
    }
    
    
    //Part F
    // a method that takes the board as input and returns no value
    // this method is used to get a move form the AI
    public static void getAIMove(char[][] a) {
        // if there is an obvious move possible (i.e. the AI can win or the AI is about to lose(the user is about
        // to win) then we run the method checkForObvious move to get the AI to either place a move
        // to win the game or block the user from winning
        if (winRow(a)==true || loseRow(a)==true || winColumn(a)==true || loseColumn(a)==true || 
            winDiagonalOne(a)==true || loseDiagonalOne(a)==true || winDiagonalTwo(a)==true || 
            loseDiagonalTwo(a)==true) {
            checkForObviousMove(a);
            
        // if there is no obvious move possible the AI places a move randomly on the board
        } else if (winRow(a)!=true || loseRow(a)!=true || winColumn(a)!=true || loseColumn(a)!=true || 
                   winDiagonalOne(a)!=true || loseDiagonalOne(a)!=true || winDiagonalTwo(a)!=true || 
                   loseDiagonalTwo(a)!=true) {
            int n = a.length;
            //Use random class to generate random numbers between 0 (inclusive) and the board dimension(exclusive)
            Random randomGenerator = new Random();
            int row = randomGenerator.nextInt(n);
            int column = randomGenerator.nextInt(n);
            // if the coordinates generated are valid (i.e. exist on the board and the position is not yet occupied 
            // then place move on board
            if (isValid(a,row,column)) {
                writeOnBoard(a,'o',row,column);
                // if the coordinate generated is not valid (i.e position is not on board or already occupied,
                // then keep generating new coordinates until the coordinate is valid
            } else if (!isValid(a,row,column)) {
                while (!isValid(a,row,column)) {
                    row = randomGenerator.nextInt(n);
                    column = randomGenerator.nextInt(n);
                    
                }
                writeOnBoard(a,'o',row,column);  // once the coordinate is valid, write it on board
                
            }
        } else {   // terminate method for the case that there are no more moves left (i.e. board is filled)
            return;
        }
    }
    
    
    //Part G
    // a method that takes the board as input and returns a character
    // this method checks whether the AI or user has won the game
    // To win the game a whole row/column/diagonal must be filled with the same symbol o or x
    public static char checkForWinner(char[][] a) {
        for (int i=0; i<a.length; i++) {
            // if a whole row/diagonal/column is filled with x (I check this by checking whether the 
            // row/column/diagonal has the same number of x as the length of the board)
            // then return x (i.e the user has won)
            if (countChar(rowArray(a,i),'x')==a.length || countChar(columnArray(a,i),'x')==a.length ||
                countChar(diagonalOneArray(a),'x')==a.length || countChar(diagonalTwoArray(a),'x')==a.length) {
                return 'x';
            // if a whole row/column/diagonal is filled with o then the AI has won    
            } else if ((countChar(rowArray(a,i),'o')==a.length) || countChar(columnArray(a,i),'o')==a.length ||                       
                       countChar(diagonalOneArray(a),'o')==a.length || countChar(diagonalTwoArray(a),'o')==a.length) {
                return 'o';
            }
        }
        // if no one has won yet return the space character
        return ' ';  
    }
    
    
    //Part H
    // a method that takes no input and returns no value
    // this method is called to allow TicTacToe to be played
    public static void play() {
        
        String name; 
        int dimension;
        Scanner read = new Scanner(System.in);
        
        System.out.println("Please enter your name: ");
        name = read.nextLine();  // store the input of the user's name into variable name
        System.out.println("Welcome, " + name + "! Are you ready to play?");
        System.out.print("Please choose the dimension of your board: ");
        
        // if the user has not input an integer as the dimension of the board, continue to ask for an integer
        while (!read.hasNextInt()) {
            System.out.println("Please input an integer as the dimension of the board");
            read.nextLine();   // prevents inputMismatchException and also prevents Scanner from behaving weirdly 
                               // by emptying buffer before trying to get new input from user
        } 
        dimension = read.nextInt(); // once integer has been entered, set dimension of board to that integer
        
        // use a random class to simulate coin toss to see who goes first
        Random coinToss = new Random();
        int coinTossResult = coinToss.nextInt(2);  // coin toss will be in range [0,2) so possibilities are 0 or 1
        int userGoFirst = 0;  // user goes first is coin toss is 0
        int aIGoFirst = 1;    // AI goes first if toss result is 1
        System.out.println("The result of the coin toss is: " + coinTossResult);
        
        char[][] a = createBoard(dimension);
        int moves = 0;
        int n = a.length;
        if (coinTossResult == userGoFirst) {
            System.out.println("You have the first move");        
            // while there is no winner yet and the moves have not exceeded the number of spaces in the board (n*n),
            // continue alternating between the user's move and the AI's move
            while (checkForWinner(a) != 'x' && checkForWinner(a) != 'o' && moves < n*n) {
                // NOTE: for the user going first, I used moves <= n*n because for a 3 by 3 board
                // there are 9 possible moves total so after 4 moves each by the user and AI for a total of 8 moves
                // the while loop will still iterate and the user will get the last move
                // NOTE: if the board was 4 by 4 for 16 moves, the while loop would iterate 8 times and the 
                // AI would get the last move so the moves<=n*n for the user does not matter because the 9th iteration
                // of the while loop to get to that if statement is not possible
                if (moves <= n*n) {
                    System.out.print("Please enter your move: ");
                    getUserMove(a);   // get a move from the user then draw the board with this move updated
                    displayBoard(a);
                    moves++;
                    char c = checkForWinner(a);  // check for a winner after the board is updated
                    if (c == 'x') {
                        System.out.println("GAME OVER!");
                        System.out.println("You won");
                        return;
                    } else if (c == 'o') {
                        System.out.println("GAME OVER!");
                        System.out.println("You lost");
                        return;
                    }
                }
                // since the AI is going second in this case, moves<n*n so for a 3 by 3 board after both place 4 moves
                // the total for moves is 8 and we can still enter the while loop but the user gets the last move
                // because its if statement is moves<=n*n
                // The AI will not get a move because there are no more moves left hence the moves<n*n condition here
                if (moves < n*n) { 
                    System.out.println("The AI made its move: ");
                    getAIMove(a);
                    displayBoard(a);
                    moves++;
                    char c = checkForWinner(a);   // check for a winner after the AI's move
                    if (c == 'x') {
                        System.out.println("GAME OVER!");
                        System.out.println("You won");
                        return;
                    } else if (c == 'o') {
                        System.out.println("GAME OVER!");
                        System.out.println("You lost");
                        return;
                    } 
                }
                
            }
            // once the AI and user have filled up the board and no one has won yet, print the result: tie
            System.out.println("GAME OVER!");
            System.out.println("It's a tie");
            return;  // exit the method because the game is now over
        }
        // this is the condition for the AI winning the coin toss and getting the first move
        else {
            System.out.println("The AI has the first move");
            // while no one has won yet keep alternating between AI and user
            while (checkForWinner(a) != 'x' && checkForWinner(a) != 'o' && moves < n*n) {
                // here the AI gets the condition moves<= n*n because the AI will get the last move for a 3 by 3 board
                if (moves <= n*n) {
                    System.out.println("The AI made its move: ");
                    getAIMove(a);
                    displayBoard(a);
                    moves++;
                    char c = checkForWinner(a);  // check for a winner after the AI's move
                    if (c == 'x') {
                        System.out.println("GAME OVER!");
                        System.out.println("You won");
                        return;
                    } else if (c == 'o') {
                        System.out.println("GAME OVER!");
                        System.out.println("You lost");
                        return;
                    }
                }
                // this is the condition for the user who is going second after the AI in this case
                if (moves < n*n) {
                    System.out.print("Please enter your move: ");
                    getUserMove(a);
                    displayBoard(a);
                    moves++;
                    char c = checkForWinner(a);  // check for a winner after the user's move is updated onto the board
                    if (c == 'x') {
                        System.out.println("GAME OVER!");
                        System.out.println("You won");
                        return;
                    } else if (c == 'o') {
                        System.out.println("GAME OVER!");
                        System.out.println("You lost");
                        return;
                    }                    
                }                
            } 
        }
        // if no one has won after the while loop terminates, then the board must be filled without a winner
        // so print the result of the game: tie
        System.out.println("GAME OVER!");
        System.out.println("It's a tie");
        return;  // exit the method
        
        
        
    }
    
    
    
}