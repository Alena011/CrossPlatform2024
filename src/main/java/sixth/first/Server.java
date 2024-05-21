package sixth.first;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server extends AbstractServer {
    public final static int DEFAULT_PORT = 7;

    public Server() {
        super(DEFAULT_PORT);
    }

    @Override
    public void respond(DatagramSocket socket, DatagramPacket request) throws IOException {
        DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), request.getAddress(), request.getPort());
        socket.send(reply);
    }

    public static void main(String[] args) {
        AbstractServer server = new Server();
        Thread t = new Thread(server);
        t.start();

        System.out.println("Start server...");
        try {
            Thread.sleep(40000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        server.shutDown();
        System.out.println("Finish server...");
    }
}
