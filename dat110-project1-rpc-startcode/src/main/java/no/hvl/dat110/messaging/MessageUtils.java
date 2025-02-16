package no.hvl.dat110.messaging;

import java.util.Arrays;

public class MessageUtils {

    public static final int SEGMENTSIZE = 128;
    public static final int MESSAGINGPORT = 8080; // 🔹 Lagt til
    public static final String MESSAGINGHOST = "localhost"; // 🔹 Lagt til

    public static byte[] encapsulate(Message message) {
        byte[] segment = new byte[SEGMENTSIZE]; 
        byte[] data = message.getData(); 

        segment[0] = (byte) data.length;
        System.arraycopy(data, 0, segment, 1, data.length);

        return segment;
    }

    public static Message decapsulate(byte[] segment) {
        int length = segment[0];
        byte[] data = Arrays.copyOfRange(segment, 1, 1 + length);
        return new Message(data);
    }
}
