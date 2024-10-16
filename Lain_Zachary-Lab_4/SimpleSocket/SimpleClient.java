import java.io.*;
import java.net.*;

public class SimpleClient
{
  public static void main(String[] args) throws IOException
  {
    Socket socket = new Socket("localhost", 8080);
    System.out.println("Connected to server.");

    // Receive message from server
    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    String message = in.readLine();
    System.out.println("Server says: " + message);

    // Send response to server
    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
    out.println("Hello from the client!");

    // Close connections
    in.close();
    out.close();
    socket.close();
  }
}
