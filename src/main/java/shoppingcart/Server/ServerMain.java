package shoppingcart.Server;

// Imports
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerMain {
    public static void main(String[] args) throws IOException {
        List<String> cartList = new ArrayList<>();
        Cart cart = null;

        // Create a server socket & listen to port
        int PORT = Integer.parseInt(args[1]);
        String folder = args[0];
        ServerSocket server = new ServerSocket(PORT);

        System.out.printf("Waiting for Connection on port %d \n", PORT);
        Socket sock = server.accept();
        System.out.println("Connection Accepted");
        Boolean isOpen = true;

        // Get input and output stream - bytes
        // Get the input stream
        InputStream is = sock.getInputStream();
        DataInputStream dis = new DataInputStream(is);

        // Get outputstream
        OutputStream os = sock.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);

        while (isOpen) {
            // Get incoming request
            String request = dis.readUTF().toLowerCase();
            String[] splitString = request.split(" ");
            String command = splitString[0];

            switch (command) {
                case "load":
                    String name = splitString[1];
                    cart = new Cart(name, folder);
                    cartList = cart.load();
                    String responseLoad = name + " shopping cart loaded\n";
                    dos.writeUTF(responseLoad);
                    break;

                case "exit":
                    is.close();
                    os.close();
                    sock.close();
                    server.close();
                    isOpen = false;

                case "list":
                    List<String> responseList = cart.list(cartList);
                    dos.writeUTF(responseList.toString() + "\n");
                    break;

                case "add":
                    List<String> toAdd = new ArrayList<>(Arrays.asList(splitString));
                    toAdd.remove(0);
                    cart.add(toAdd, cartList);
                    String responseAdd = "Items added to cart\n";
                    dos.writeUTF(responseAdd);
                    break;

                case "delete":
                    String responseDelete = cart.delete(splitString[1], cartList);
                    dos.writeUTF(responseDelete);
                    break;

                case "save":
                    cart.save(cartList);
                    String responseSave = "Cart contents saved to " + cart.getName() + ".\n";
                    dos.writeUTF(responseSave);
                    break;

                default:
                    break;
            }

        }

    }
}
