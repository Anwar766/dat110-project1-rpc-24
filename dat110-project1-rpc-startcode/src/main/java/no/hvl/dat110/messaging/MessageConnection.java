package no.hvl.dat110.messaging;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MessageConnection {

    private DataOutputStream outStream;
    private DataInputStream inStream;
    private Socket socket;

    public MessageConnection(Socket socket) {
        try {
            this.socket = socket;
            outStream = new DataOutputStream(socket.getOutputStream());
            inStream = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            System.out.println("Connection: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void send(Message message) {
        try {
            byte[] encodedMessage = MessageUtils.encapsulate(message);
            outStream.write(encodedMessage);
            outStream.flush();
        } catch (IOException e) {
            System.out.println("Error sending message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Message receive() {
        try {
            byte[] receivedData = new byte[128];

            int bytesRead = inStream.read(receivedData);
            if (bytesRead == -1) { // Tilkoblingen er lukket
                System.out.println("Warning: No data received, connection might be closed.");
                return null;
            }

            return MessageUtils.decapsulate(receivedData);
        } catch (IOException e) {
            System.out.println("Error receiving message: " + e.getMessage());
            return null;
        }
    }


    // 🔹 LAGT TIL: Metode for å lukke tilkoblingen
    public void close() {
        try {
            if (outStream != null) outStream.close();
            if (inStream != null) inStream.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.out.println("Error closing connection: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
