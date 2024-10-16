package MultithreadedSocket;

import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;


public class MultiThreadedServer
{
  private static final AtomicBoolean serverRunning = new AtomicBoolean(false);

  public static void main(String[] args)
  {
    int port = 8080;

    try (ServerSocket serverSocket = new ServerSocket(port))
    {
      System.out.println("Server started. Waiting for clients...");
      serverRunning.set(true);

      // Start server chat command listener
      new MultiThreadedServer().serverChat();

      while (serverRunning.get())
      {
        try {
          Socket clientSocket = serverSocket.accept();
          System.out.println("Client connected from address " + clientSocket.getInetAddress());

          // Create a new thread for each client
          new ClientHandler(clientSocket).start();
        } catch (SocketException e) {
          if (!serverRunning.get()) {
            System.out.println("Server has been stopped, not accepting new connections.");
            break;
          }
        }
      }

      System.out.println("Server stopped.");
      serverSocket.close();
    }
    catch (IOException e)
    {
      System.out.println("Server could not be created on port " + port + ".\nExiting...");
    }
  }

  // Method to handle the server chat and shutdown commands
  void serverChat()
  {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    Thread commandListener = new Thread(() -> {
      while (serverRunning.get())
      {
        try
        {
          String command = in.readLine();  // Read input from console
          if (command != null && command.equalsIgnoreCase("bye"))
          {
            System.out.println("Shutting down the server...");
            serverRunning.set(false);

            try
            {
              // Create a new socket to "unblock" the serverSocket.accept()
              new Socket("localhost", 8080).close();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
    commandListener.start();
  }
}