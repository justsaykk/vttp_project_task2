package shoppingcart.Server;

// Imports
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    public static void main(String[] args) throws IOException {
        // Create a server socket & listen to port
        int PORT = Integer.parseInt(args[1]);
        ServerSocket server = new ServerSocket(PORT);

        System.out.printf("Waiting for Connection on port %d \n", PORT);
        Socket sock = server.accept();
        System.out.println("Connection Accepted");

        // Get input and output stream - bytes
        // Get the input stream
        InputStream is = sock.getInputStream();
        DataInputStream dis = new DataInputStream(is);

        // Get outputstream
        OutputStream os = sock.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);

        // Perform Computation on request
        Boolean isOpen = true;
        while (isOpen) {
            // Get incoming request
            String request = dis.readUTF().toLowerCase();

            // Cases of input
            switch (request) {
                case "exit":
                    is.close();
                    os.close();
                    sock.close();
                    isOpen = false;
                    break;
                
                case "list":
                    // Code here
                break;
            
                default:
                    break;
            }
        }
    }
}
