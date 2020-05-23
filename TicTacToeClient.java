import java.io.*;
import java.net.*;
import java.util.ArrayList;
public class TicTacToeClient implements Runnable  {
    public static void main(String[] args) throws IOException {

        if (args.length != 3) {
            System.err.println(
                "Usage: java EchoClient <host name> <port number> <name>");
            System.exit(1);
        }

        String hostName = args[0];
        String name = args [2];
        int portNumber = Integer.parseInt(args[1]);
        Move fromServer;
        Table t1 = new Table();
        t1.populateList(name);//adding a player to list
        try (Socket tttSocket = new Socket(hostName, portNumber);) 
        {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String response = "";
            Move output;
            Move toServer;
            try (
            ObjectOutputStream OUT = 
                new ObjectOutputStream(tttSocket.getOutputStream()); 
            ObjectInputStream IN = 
                new ObjectInputStream(tttSocket.getInputStream());
            )
            {
                TicTacToeProtocol tttp = new TicTacToeProtocol();
                //reads in move object from server
                while ((fromServer = (Move)IN.readObject()) != null) {
                    //Step 6: Print message from server
                    Move temp = new Move("");
                    Move dummy = new Move("");
                    System.out.println(fromServer.getMove());

                    if (fromServer.getState().equals("avocado"))
                    {
                        tttp.processInput(fromServer);
                        //Computes clients' wins based on if they won or lost
                        //if client wins, one win is added 
                        if (fromServer.getResult().equals("winnerClient")) 
                        {
                            t1.addToList(name,1);
                            //t1.display();
                        }
                        //if server wins, no wins are added
                        if (fromServer.getResult().equals("winnerServer")) 
                        {
                            t1.addToList(name,0);
                            //t1.display();
                        }
                        //Asks if player wants to play again, and takes in their input
                        System.out.println("Do u want to play again? Y/N");
                        
                        while (true)
                        {
                            response = stdIn.readLine();
                            response = response.toUpperCase(); 
                            if (response.equals("Y") || response.equals("N"))
                            {
                               break;
                            }
                            System.out.println("Re-enter valid input, Y or N");

                        }
                        temp.setState(response); //storing answer in temp
                        /*If client answers "Y", this is processed by protocol
                         * and whatever returned is sent to Server */
                        if(temp.getState().equals("Y"))
                        {
                            output = tttp.processInput(temp);
                            output.setName(name);
                            OUT.writeObject(output);
                        }
                        //If user's response is no, game is ended and cannot be ran
                        if(response.equals("N"))
                            System.exit(0);
                    }
                    else 
                    {
                        if (!fromServer.getState().equals("continuemf"))
                            tttp.processInput(fromServer);
                        //Prompts user for their next turn
                        System.out.println(name+"'s turn:");
                        //reading a message from a user
                        String in = stdIn.readLine();
                        dummy.setMove(in);
                        /*Check if move entered by client is valid or taken, 
                         * If move isn't valid or is taken, client is asked to re-enter.
                         * Reads new input until move is valid and/or isn't taken*/
                        while(!tttp.isValidNumber(dummy) || (tttp.isTaken(dummy)))
                        {
                            if(!tttp.isValidNumber(dummy))
                                System.out.println("Invalid input; re-enter your number:");
                            if(tttp.isTaken(dummy)) 
                                System.out.println("Slot already taken; re-enter slot number:");
                            dummy.setMove(stdIn.readLine());
                        }     
                        if (tttp.isValidNumber(dummy))
                            temp.setMove(dummy.getMove());

                        if (temp.getMove() != null) 
                        {
                            //process move and store in toServer variable
                            toServer = tttp.processInput(temp);

                            //
                            if(toServer.getState().equals("avocado"))
                            {
                                temp.setState("avocado");
                                temp.setResult(toServer.getResult());
                                temp.setName(name);
                                OUT.writeObject(temp);    
                            }
                            else   
                            {
                                temp.setName(name);
                                OUT.writeObject(temp);
                            } //fromUserser
                        }
                    }
                }
            }
            catch (ClassNotFoundException p)
            {
                System.exit(1);
            }

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
    }

    public void run() 
    {
        //System.out.println("");
    }
}