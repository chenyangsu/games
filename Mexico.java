
        
public class Mexico {
    
    public static void main(String[] args) {
        
        double buyIn = Double.parseDouble(args[0]);
        double payLoseRound = Double.parseDouble(args[1]);
        playMexico(buyIn, payLoseRound);
     
    }
    
    
    // 1a. A method to simulate a dice roll
    // This method takes no inputs and returns a value between 1 and 6(inclusive). It is a value method.
    public static int diceRoll() {
        int min = 1;
        int max = 6;
        int roll = (int) (min + (Math.random()*(max-min+1)));  // a +1 is required to make the roll 6 inclusive
        return roll;       
    }
    
    
    // 1b. A method to compute the score of a Player
    // This method take 2 integer values as inputs, and returns an int. This is a value method
    public static int getScore(int diceOne, int diceTwo) {
        
        String rollOneLarger = "" + diceOne + diceTwo; // concatenation must be stored in a String
        String rollTwoLarger = "" + diceTwo + diceOne;
        int scoreOne = Integer.parseInt(rollOneLarger); // result of concatenation converted to an int
        int scoreTwo = Integer.parseInt(rollTwoLarger);
        
        if (diceOne>diceTwo) {
            return scoreOne;
        } else {                   // represents the result if diceTwo>diceOne or diceTwo=diceOne
            return scoreTwo;
        }    
    }
    
    
    // 1c. A method to simulate one round of Mexico
    // This method take one input of type String and returns an int. This is a value method
    public static int playOneRound(String playerName) {
        
        int playerFirstDice = diceRoll();  // storing result of first dice roll
        int playerSecondDice = diceRoll();
        int playerScore = getScore(playerFirstDice, playerSecondDice);  // getting score using method from 1b.
        System.out.println(playerName + " rolled: " + playerFirstDice + " " + playerSecondDice);
        System.out.println(playerName + "'s score is: " + playerScore);
        return playerScore;  // value method returns an int representing the score obtained by player
    }
    
    
    // 1d. A method to determine the winner of one round
    // This method takes two inputs representing scores obtained by Giulia and David and returns a String with name of winner
    //This is a value method.
    public static String getWinner(int scoreGiulia, int scoreDavid) {
        
        // There are 21 scores possible for a player to get: 21 > doubles > normal 
        // i.e. 21, 66 55 44 33 22 11, 65 64 63 62 61 54 53 52 51 43 42 41 32 31
        
        // result for if Giulia and David have the same score
        if (scoreGiulia == scoreDavid) {  
            return "tie";
        
        // result for the possibilities of 21, and doubles(66, 55, 44.. to 11)
        } else if (scoreGiulia == 21) {
            return "Giulia";
        } else if (scoreDavid == 21) {
            return "David";
        } else if (scoreGiulia == 66) {
            return "Giulia";
        } else if (scoreDavid == 66) {
            return "David";
        } else if (scoreGiulia == 55) {
            return "Giulia";
        } else if (scoreDavid == 55) {
            return "David";
        } else if (scoreGiulia == 44) {
            return "Giulia";
        } else if (scoreDavid == 44) {
            return "David";
        } else if (scoreGiulia == 33) {
            return "Giulia";
        } else if (scoreDavid == 33) {
            return "David";
        } else if (scoreGiulia == 22) {
            return "Giulia";
        } else if (scoreDavid == 22) {
            return "David";
        } else if (scoreGiulia == 11) {
            return "Giulia";
        } else if (scoreDavid == 11) {
            return "David";
            
        // result for possibilities other than tie, 21, or doubles
        } else {
            if (scoreGiulia > scoreDavid) {
                return "Giulia";
            } else {
                return "David";
            }
        }
    }
    
    
    // 1e. A method to check if the buy in and the base bet are set correctly
    // This method takes two doubles as input and returns a boolean value. This is a value method.
    public static boolean canPlay(double buyIn, double payLoseRound) {
        // only if these two conditions are both met will the player be allowed to play
        if (payLoseRound<=buyIn && buyIn%payLoseRound==0) {        
            return true;
        // if the above two conditions are not met, return false.
        } else {
            return false;
        }
    }
    
    
    // 1f. A method to simulate a game of Mexico
    // This method takes two doubles as input, and does not return any value. This is a void method.
    public static void playMexico(double buyIn, double payLoseRound) {
        if (canPlay(buyIn, payLoseRound) == false) {
            System.out.println("Insufficient funds. The game cannot be played.");
            return;
        } else {
            int roundNumber = 1;
            double giuliaCash = buyIn;  // storing the buyIn as each player's cash so this value can be deducted
            double davidCash = buyIn;   // at the end of each round for the loser of that round
            
            while (giuliaCash !=0.0 && davidCash !=0.0) {   // game will only continue if both players still have cash
                System.out.println("Round " + roundNumber);
                System.out.println();              
                int giuliaScore = playOneRound("Giulia");
                int davidScore = playOneRound("David");
                String result = getWinner(giuliaScore, davidScore);
                if (result.equals("Giulia") || result.equals("David")) {
                    System.out.println(result + " wins this round");
                } else {
                    System.out.println("It's a tie. Roll again!");
                }
                
                // code for subtracting cash from loser of round
                String winner = getWinner(giuliaScore, davidScore);   
                if (winner.equals("Giulia")) {
                    davidCash = davidCash-payLoseRound;
                } else if (winner.equals("David")){
                    giuliaCash = giuliaCash-payLoseRound;  
                }                                            // nothing happens (no one is deducted cash) when have tie
                System.out.println();
                roundNumber++;
            }
            
            if (giuliaCash == 0.0) {  // Person with 0.0 cash loses game, and the other person is winner
                System.out.println("David won the game!");
            } else if (davidCash == 0.0) {
                System.out.println("Giulia won the game!");
            }    
        }  
    }        
}
        
