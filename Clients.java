package clients;

import java.io.*;
import java.net.*;
import java.awt.event.*;
import javax.swing.*;

public class Clients {
// create input and output stream
// JFrame created
    BufferedReader input;
    PrintWriter output;
    JFrame frame = new JFrame("Chat Client");
    JTextField text = new JTextField(50);
    JTextArea message = new JTextArea(10, 50);
    
    public Clients() {
text.setEditable(false);
message.setEditable(false);
frame.getContentPane().add(text, "South");
frame.getContentPane().add(new JScrollPane(message), "Center");
frame.pack();
text.addActionListener(new ActionListener(){
    
    // send message and reset entered text to blank
    public void actionPerformed(ActionEvent e) {
output.println(text.getText());
text.setText("");
}
});
}
    //ask for ip address
  private String getServerAddress() {
        return JOptionPane.showInputDialog(
            frame,
            "Enter IP Address of the Server:",
            "IP",
            JOptionPane.QUESTION_MESSAGE);
    }

    // ask for username 
    private String getName() {
        return JOptionPane.showInputDialog(
            frame,
            "Choose a username:",
            "Username",
            JOptionPane.PLAIN_MESSAGE);
    }
    // enter same port number as server
    private int getPortNumber() {
        return Integer.parseInt(JOptionPane.showInputDialog(
            frame,
                "Enter Port Number",
                "Port",
                JOptionPane.QUESTION_MESSAGE));
    }

     //connect to server then enters the processing loop
    private void run() throws IOException {

        // creates connection and start input and output stream
        String serverAddress = getServerAddress();
        int portNumber = getPortNumber();
        Socket socket = new Socket(serverAddress, portNumber);
        input = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(), true);

        // reads messages that are sent
        while (true) {
            String line = input.readLine();
            if (line.startsWith("ENTERNAME")) {
                output.println(" " + getName());
            } else if (line.startsWith("ACCEPTNAME")) {
                text.setEditable(true);
            } else if (line.startsWith("MESSAGE")) {
                message.append(line.substring(8) + "\n");
            }
            else if(line.startsWith("QUIT")){
                System.exit(0);
            }
        }
    }
    public static void main(String[] args) throws IOException {
        Clients client = new Clients();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        client.run();
    }
    
}
