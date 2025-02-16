package no.hvl.dat110.messaging;

import java.net.Socket;
import java.io.IOException;

public class MessagingClient {

    private String server;
    private int port;

    public MessagingClient(String server, int port) {
        this.server = server;
        this.port = port;
    }

    public MessageConnection connect() {
        try {
            // Opprett en socketforbindelse til serveren
            Socket clientSocket = new Socket(server, port);

            // Returner en MessageConnection som bruker denne socketen
            return new MessageConnection(clientSocket);

        } catch (IOException e) {
            System.out.println("Could not connect to server: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
