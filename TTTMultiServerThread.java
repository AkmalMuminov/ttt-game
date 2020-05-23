import java.util.InputMismatchException;
import java.net.*;
import java.io.*;

public class TTTMultiServerThread extends Thread {
    private Socket socket = null;
    private String name = null;
    private Table table = null;
    public TTTMultiServerThread(Socket socket, String serverName, Table table) {
        super("TTTMultiServerThread");
        this.socket = socket;
        this.name = serverName;
        this.table = table;
    }

    public void run() {
        BufferedReader stdIn =
            new BufferedReader(new InputStreamReader(System.in));

        try (
        ObjectOutputStream OUT =
            new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream IN =
                new ObjectInputStream(socket.getInputStream());
        ) {
            Move input, output, toClient;
            Move tummy = new Move("");
            String response = "";
            boolean set = false;
            TicTacToeProtocol tttp = new TicTacToeProtocol();
            output = tttp.processInput(null);
            OUT.writeObject(output);

            while ((input = (Move) IN.readObject()) != null)
            {

                System.out.println("This is " + input.getName() + "'s move: "+ input.getMove());

                if(!set)
                {
                    table.populateList(input.getName());
                    set = !set;
                }
                /* When the state is avocado, it signifies that a game has ended.
                 * Here, the player name is added to the leaderboard, sorted and displayed */
                if (input.getState().equals("avocado"))
                {
                    output = tttp.processInput(input);
                    table.addToList(input.getName(),1);
                    table.sort();
                    table.display();
                    OUT.writeObject(input);
                    //input.setMove("");//resets avocado
                }
                else if(input.getState().equals("Y"))
                {
                    tttp.processInput(input);
                    tummy.setState("continuemf");
                    OUT.writeObject(tummy);
                }
                else
                {
                    output = tttp.processInput(input);
                    Move temp = new Move("");
                    Move dummy = new Move("");
                    String in = stdIn.readLine();
                    dummy.setMove(in);
                     /*Check if move entered is valid or taken,
                         * If move isn't valid or is taken, server is asked to re-enter.
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

                    toClient = tttp.processInput(temp);
                    if(toClient.getState().equals("avocado"))
                    {
                        temp.setState("avocado");
                        table.display();
                    }
                    if (toClient.getResult().equals("winnerServer"))
                    {
                        temp.setResult("winnerServer");
                        table.display();
                    }
                    OUT.writeObject(temp);
                }
            }
            socket.close();
        }
        catch(ClassNotFoundException p)
        {
            System.exit(1);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}