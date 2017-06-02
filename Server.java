package server;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class Server {
private static int Port;

//USED FOR USERNAMES
private static final HashSet<String> usernames = new HashSet<>();
private static final HashSet<PrintWriter> writers = new HashSet<>();
JFrame frame = new JFrame("Chat Server");
    //enter port number for server
    public int portNumb() {
        return Integer.parseInt(JOptionPane.showInputDialog(
            frame,
                "Enter Port Number",
                "Port",
                JOptionPane.QUESTION_MESSAGE));
    }
    //allows port number to be entered
private void run()throws IOException{
    Port = portNumb();    
}

public static void main(String[] args) throws IOException{
    Server server = new Server();
    server.run();
    System.out.println("Port Number: " + Port);
    ServerSocket socketListener = new ServerSocket(Port);
        try{
            while(true){
                new Handle(socketListener.accept()).start();
            }
            }
        finally{
            socketListener.close();
        }   
    }
private static class Handle extends Thread{
    private BufferedReader input;
    private PrintWriter output;
    private String userName;
    private final Socket connection; 
    
        
    private Handle(Socket socket){
        this.connection = socket;
    }
    @Override
public void run(){
    try{
        //input and output streams
    input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    output = new PrintWriter(connection.getOutputStream(), true);
    // username selection
        while (true)
            {
                output.println("ENTERNAME");
                userName = input.readLine();
                if (userName == null){
                    return;
                }
                synchronized (usernames) {
                    if(!usernames.contains(userName)){
                        usernames.add(userName);
                        break;
                    }
                }
            }
            output.println("ACCEPTNAME");
            writers.add(output);
            //reads users input
            while(true){
                String read = input.readLine();
                if ("quit".equals(read)){
                    output.println("QUIT");
                }
                else if(read == null)
                {
                    return;
                }
                for(PrintWriter writer : writers){
                    writer.println("MESSAGE" + userName + ": " + read);
                }
            }
        }
        catch(IOException e){
        System.out.println(e);
    }
    }

}
}