package no.hvl.dat110.messaging;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MessagingServer {

    private ServerSocket welcomeSocket;

    public MessagingServer(int port) {
        try {
            this.welcomeSocket = new ServerSocket(port);
        } catch (IOException ex) {
            System.out.println("Messaging server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public MessageConnection accept() {
        try {
            // Vent på en klientforbindelse
            Socket connectionSocket = welcomeSocket.accept();

            // Opprett og returner en MessageConnection for den tilkoblede klienten
            return new MessageConnection(connectionSocket);

        } catch (IOException e) {
            System.out.println("Error accepting connection: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void stop() {
        try {
            if (welcomeSocket != null) {
                welcomeSocket.close();
            }
        } catch (IOException ex) {
            System.out.println("Messaging server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
