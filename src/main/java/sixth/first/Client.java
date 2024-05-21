package sixth.first;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Client {
    public final static int PORT = 7;

    public static void main(String[] args) {
        String hostname = "localhost";
        if (args.length > 0) {
            hostname = args[0];
        }
        try {
            InetAddress ia = InetAddress.getByName(hostname);
            DatagramSocket socket = new DatagramSocket();
            Thread sender = new ResponseThread(socket, ia, PORT);
            sender.start();
            Thread receiver = new RequestThread(socket);
            receiver.start();
        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (SocketException ex) {
            System.err.println(ex);
        }
    }
}
