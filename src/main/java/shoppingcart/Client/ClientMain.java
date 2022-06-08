package shoppingcart.Client;

import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientMain {
    public static void main(String[] args)
            throws IOException {

        // Getting and processing the user input args
        String[] splitString = args[0].split("\\W"); // output: ["fred", "localhost", "3000"]
        String user = splitString[0];
        String localhost = splitString[1];
        int port = Integer.parseInt(splitString[2]);

        // Connect to server
        System.out.printf("Attempting to connect to server at %s on %s port %d\n", localhost, user, port);
        Socket sock = new Socket(localhost, port);
        Boolean isOpen = true;
        System.out.printf("Connected to shopping cart server at %s on %s port %d\n", localhost, user, port);

        // Get input and output stream - bytes
        // Get the input stream
        InputStream is = sock.getInputStream();
        DataInputStream dis = new DataInputStream(is);

        // Get outputstream
        OutputStream os = sock.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);

        // Request server to load file
        dos.writeUTF("load " + user);
        dos.flush();
        String response = dis.readUTF();
        System.out.println(response);
        Console cons = System.console();

        while (isOpen) {
            // Getting Command
            String userInput = cons.readLine(">> What would you like to do? \n");
            String[] userInputSplit = userInput.split(" ");
            String userCommand = userInputSplit[0].toLowerCase();

            if (userCommand.equals("exit")) {
                dos.writeUTF("exit");
                dos.flush();
                is.close();
                os.close();
                sock.close();
                System.out.println("Thank you for shopping with us.\n");
                isOpen = false; // Stopping the while-loop
                break;
            } else {
                dos.writeUTF(userInput);
                dos.flush();
                String res = dis.readUTF();
                System.out.print(res);
            }
        }
    }
}
