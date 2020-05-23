//import org.jetbrains.annotations.NotNull;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Arrays;
import java.util.regex.*;

public class TicTacToeProtocol {
    private static final int WAITING = 0;
    private static final int IN_CONVERSATION = 1;
    //private static final int DISCONNECTED = 2;
    private int state = WAITING;
    String[] board = new String[9];
    String turn = "X";
    static int counter;
    // String result = "";
    // int theResult = 0;
    String winner = "";
    Move a = new Move("");
    public Move processInput(Move theMove) 
    {
        try (
        BufferedReader stdIn =
            new BufferedReader(new InputStreamReader(System.in));
        ) {
            if (state == WAITING) 
            {
                a.setMove("");    
                populateEmptyBoard(board);
                System.out.println("Welcome to 2 Player Tic Tac Toe.");
                System.out.println("--------------------------------");
                printBoard(board);
                state = IN_CONVERSATION; 
            } 
            else if ((state == IN_CONVERSATION))
            {  
                if (winner.equals("")) 
                {
                    int numInput = 0;
                    try 
                    {   
                        /*If the state if the move is "Y", procedures are set
                         * in place to restart the game. Clearing previous board,
                         * creating new move slots in board, and printing new board.
                         */
                        if (theMove.getState().equals("Y"))
                        {
                            clearTheBoard();
                            populateEmptyBoard(board);
                            printBoard(board);
                            a.setResult("");
                            a.setState("Y");
                            return a;
                        }     
                        //checks if move is valid
                        else if (isValidNumber(theMove))
                        {                        
                            numInput = Integer.parseInt(theMove.getMove());
                            //Ask to re-enter if move isn't a valid digit
                            if (!(numInput > 0 && numInput <= 9)) 
                            {
                                System.out.println("Invalid input; re-enter slot number:");
                            }
                        }
                    } catch (InputMismatchException e) 
                    {
                        System.out.println("Invalid input; re-enter slot number:");
                    }
                    //Switches turn once current user's input is valid and entered
                    if (board[numInput-1].equals(String.valueOf(numInput))) 
                    {
                        board[numInput-1] = turn;
                        if (turn.equals("X")) 
                        {
                            turn = "O";
                        } 
                        else 
                        {
                            turn = "X";
                        }
                        printBoard(board);
                        winner = checkWinner(board,turn);
                        if(winner.equals("X") || winner.equals("O"))
                        { 
                            System.out.println("Congratulations! " + winner + "'s have won! Thanks for playing.");
                            if (winner.equals("X")) 
                            {
                                a.setResult("winnerClient");
                            }
                            if(winner.equals("O"))
                                a.setResult("winnerServer");
                            winner = "";
                            turn = "X";
                            counter = 0;
                            a.setState("avocado");
                        }
                    } 
                }
                else if (winner.equalsIgnoreCase("draw")) 
                {
                    System.out.println("It's a draw! Thanks for playing.");
                } 
            }
        } 
        catch (IOException e) 
        {
            System.err.println("Couldn't get I/O for the connection to ");
            System.exit(1);
        }         
        return a;
    }         
    /*
    * Checks for winner by looking for three same consecutive turn values
    *
    * @param  board[]   
    * @param  turn      X or O
    * @return           the winner based on their turn value
    */
    //static @NotNull
    String checkWinner(String board[], String turn )
    {
        for (int i = 0; i < 8; i++) 
        {
            String line = null;
            switch (i) 
            {
                case 0:
                line = board[0] + board[1] + board[2];
                break;
                case 1:
                line = board[3] + board[4] + board[5];
                break;
                case 2:
                line = board[6] + board[7] + board[8];
                break;
                case 3:
                line = board[0] + board[3] + board[6];
                break;
                case 4:
                line = board[1] + board[4] + board[7];
                break;
                case 5:
                line = board[2] + board[5] + board[8];
                break;
                case 6:
                line = board[0] + board[4] + board[8];
                break;
                case 7:
                line = board[2] + board[4] + board[6];
                break;
            }
            if (line.equals("XXX")) 
            {
                return "X";
            } 
            else if (line.equals("OOO")) 
            {
                return "O";
            }
        }
        //Checks all boards slots for a draw
        for (int i = 0; i < 9; i++) 
        {
            if (board[i].equals("X") || board[i].equals("O"))
                counter++;
            if (counter < 8)
                break;
            else if (counter == 8)
                return "draw";
        }

        System.out.println(turn + "'s turn; enter a slot number to place " + turn + " in:");
        return "";
    }
    //Prints Tic-Tac-Toe board
    static void printBoard(String board[]) 
    { 
        System.out.println( "  " + board[0] + " | " + board[1] + " | " + board[2] + "  ");
        System.out.println(" ----------- ");
        System.out.println( "  " + board[3] + " | " + board[4] + " | " + board[5] + "  " );
        System.out.println(" ----------- ");
        System.out.println( "  " +board[6] + " | " + board[7] + " | " + board[8] + "  ");
    }
    //Populates an empty board using board array
    static void populateEmptyBoard(String board[]) 
    {
        for (int i = 0; i < 9; i++) 
        {
            board[i] = String.valueOf(i+1);
        }
    }
    //Check if Move object has been already taken
    public boolean isTaken(Move theMove)
    {
        String temp = theMove.getMove();
        //replace all non digits w/ empty string
        temp = temp.replaceAll("[^0-9.]","");
        if (temp.equals(""))
            return false;
        String i = board[Integer.parseInt(theMove.getMove())-1];        
        if (i.equals("X") || i.equals("O"))
            return true; 
        return false;
    } 
     /*
    * Check if move is valid. 
    *
    * @param  theMove   Move object; player's input of next move
    * @return         boolean value - whether or not move is valid or not
    */
    public boolean isValidNumber(Move theMove)
    {
        int check = 0;
        String temp = theMove.getMove();
        if(temp.equals("Y") || temp.equals("N"))
            return true;
        temp = temp.replaceAll("[^0-9.]","");
        if (temp.equals(""))
            return false;
        check = Integer.parseInt(theMove.getMove()); 
        if (check > 0 && check <= 9)
            return true;
        return false;
    }
    //Clears the board from all entered moves
    public void clearTheBoard()
    {
        for (int i = 0; i < 9; i++) 
        {
            board[i] = "";
        }
    }

}  