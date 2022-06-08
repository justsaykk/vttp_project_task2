package shoppingcart.Client;

import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import shoppingcart.Server.Cart;

public class ClientMain {

    public static void main(String[] args) throws IOException {
        Console cons = System.console();
        List<String> cartList = new ArrayList<>();
        Cart cart = null;

        // Command Server to load specified file

        String[] userAction = args[0].split("\\W"); // output: ["fred", "localhost", "3000"]
        cart = new Cart(userAction[0]);
        cartList = cart.load();
        System.out.printf("%s shopping cart loaded\n", userAction[0]);
        
        // Connect to server
        System.out.println("Connecting to localhost at port: 3000");
        Socket sock = new Socket("localhost", 3000);
        System.out.println("Connected");

        // Get input and output stream - bytes
        // Get the input stream
        InputStream is = sock.getInputStream();
        DataInputStream dis = new DataInputStream(is);

        // Get outputstream
        OutputStream os = sock.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);


        // while-loop control statement
        boolean isOpen = true;

        // This is the main program
        while (isOpen) {
            // Getting Command
            String userInput = cons.readLine(">> What would you like to do? \n");
            String[] splitString = userInput.split(" ");
            String userCommand = splitString[0].toLowerCase();

            switch (userCommand) {
                case "exit":
                    dos.writeUTF(userCommand);
                    dos.flush();
                    is.close();
                    os.close();
                    System.out.println("Thank you for shopping with us.\n");
                    sock.close();
                    isOpen = false; // Stopping the while-loop
                    break;

                case "list":
                    dos.writeUTF(userCommand);
                    dos.flush();
                    cart.list(cartList);
                    break;

                case "add":
                    List<String> toAdd = new ArrayList<>(Arrays.asList(splitString));
                    toAdd.remove(0);
                    cart.add(toAdd, cartList);
                    break;

                case "delete":
                    cart.delete(splitString[1], cartList);
                    break;

                case "save":
                    cart.save(cartList);
                    System.out.printf("Cart contents saved to %s\n", cart.getName());
                    break;

                default:
                    break;
            }
        }
    }
}
