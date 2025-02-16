package no.hvl.dat110.rpc;

import no.hvl.dat110.messaging.*;

public class RPCClient {

    // Underliggende meldingsklient for RPC-kommunikasjon
    private MessagingClient msgclient;
    private MessageConnection connection;

    public RPCClient(String server, int port) {
        msgclient = new MessagingClient(server, port);
    }

    public void connect() {
        // Opprett en tilkobling til serveren
        connection = msgclient.connect();
    }

    public void disconnect() {
        // Lukk forbindelsen hvis den er aktiv
        if (connection != null) {
            connection.close();
        }
    }

    public byte[] call(byte rpcid, byte[] param) {
        byte[] request = RPCUtils.encapsulate(rpcid, param);
        connection.send(new Message(request));

        Message responsemsg = connection.receive();
        
        if (responsemsg == null) {
            System.out.println("RPCClient error: Ingen respons fra server!");
            return null; // Unngår å prøve å lese fra en tom melding
        }

        return RPCUtils.decapsulate(responsemsg.getData());
    }

}

