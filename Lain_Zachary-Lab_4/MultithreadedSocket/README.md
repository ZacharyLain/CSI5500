# Multi-threaded Socket Server
## Objective
Use a multi-threaded server to allow multiple clients to connect to the server at the same time.

## Implementation
The ```MultiThreadedServer``` class starts the server similar to the single-threaded server. The difference between these two servers is that the multi-threaded server creates a new thread for each client that connects to the server.
This is done through th ```ClientHandler``` class which extends the ```Thread``` class. The ```ClientHandler``` class is responsible for handling the client's connection to the server.

The ```Client``` class is used to connect to the server. The client sends messages to the server and receives messages from the server.
This updated ```Client``` class is able to send messages to the server until the client types "exit" to close the connection.

## Improvements
While this implementation is an improvement compared to the single-threaded server, it is still limited in terms of scalability. The server can only handle a limited number of clients due to the limitations of the system resources.
This could be fixed through Thread Pooling rather than creating a new thread for each client that connects to the server.