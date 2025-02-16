package no.hvl.dat110.rpc;

import java.util.HashMap;
import no.hvl.dat110.messaging.*;

public class RPCServer {

    private MessagingServer msgserver;
    private MessageConnection connection;
    
    // HashMap for å registrere RPC-metoder
    private HashMap<Byte, RPCRemoteImpl> services;

    public RPCServer(int port) {
        this.msgserver = new MessagingServer(port);
        this.services = new HashMap<>();
    }

    public void run() {
        RPCRemoteImpl rpcstop = new RPCServerStopImpl(RPCCommon.RPIDSTOP, this);
        register(RPCCommon.RPIDSTOP, rpcstop);

        System.out.println("RPC SERVER RUN - Waiting for connections...");

        while (true) {  // Uendelig løkke for å akseptere flere tilkoblinger
            try {
                connection = msgserver.accept(); // Vente på en klient
                System.out.println("RPC SERVER ACCEPTED CONNECTION");

                while (true) { // Behandle forespørsler fra klienten
                    Message requestmsg = connection.receive();
                    
                    if (requestmsg == null || requestmsg.getData().length == 0) {
                        System.out.println("RPCServer warning: Mottok en tom melding eller ingen data!");
                        break; // Går ut av den indre løkka og venter på ny klient
                    }

                    byte[] request = requestmsg.getData();
                    byte rpcid = request[0];
                    byte[] param = new byte[request.length - 1];
                    System.arraycopy(request, 1, param, 0, param.length);

                    RPCRemoteImpl method = services.get(rpcid);
                    if (method == null) {
                        System.out.println("RPCServer error: Ingen metode funnet for RPC ID " + rpcid);
                        continue;
                    }

                    byte[] returnval = method.invoke(param);
                    byte[] response = new byte[returnval.length + 1];
                    response[0] = rpcid;
                    System.arraycopy(returnval, 0, response, 1, returnval.length);

                    connection.send(new Message(response));
                }

                System.out.println("Client disconnected, waiting for new connections...");
            } catch (Exception e) {
                System.out.println("RPCServer error: " + e.getMessage());
            }
        }
    }


    public void register(byte rpcid, RPCRemoteImpl impl) {
        services.put(rpcid, impl);
    }

    public void stop() {
        try {
            if (connection != null) {
                connection.close();
            }
            if (msgserver != null) {
                msgserver.stop();
            }
        } catch (Exception e) {
            System.out.println("Error during server shutdown: " + e.getMessage());
        }
    }
}
