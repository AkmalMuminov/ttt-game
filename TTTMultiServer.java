import java.net.*;
import java.io.*;

public class TTTMultiServer {
    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println("Usage: java TTTMultiServer <port number> <server name>");
            System.exit(1);
        }
        
        int portNumber = Integer.parseInt(args[0]);
        String serverName = args[1];
        Table table = new Table();
        boolean listening = true;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) { 
            while (listening) {
                new TTTMultiServerThread( serverSocket.accept(), serverName,table).start(); 
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}