package laba4;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TransitUser {

    private ObjectInputStream is = null;

    private ObjectOutputStream os = null;

    public TransitUser(String server, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(server, port), 1000);
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public void finish() {
        try {
            os.writeObject(new ShutdownProcedure());
            os.flush();
            System.out.println(is.readObject());
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error: " + ex);
        }
    }

    public void applyOperation(CardActivity op) {
        try {
            os.writeObject(op);
            os.flush();
            System.out.println(is.readObject());
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error: " + ex);
        }
    }

    public static void main(String[] args) {
        TransitUser cl = new TransitUser("localhost", 7891);
        MetroCardAddition op = new MetroCardAddition();
        op.getCrd().setUsr(new UserRegistry("Petr", "Petrov", "M", "25.12.1968"));
        op.getCrd().setSerNum("00001");
        op.getCrd().setCollege("KhNU"); // Corrected method name
        op.getCrd().setBalance(25);
        cl.applyOperation(op);
        cl.finish();

        cl = new TransitUser("localhost", 7891);
        cl.applyOperation(new FundsAddition("00001", 100));
        cl.applyOperation(new BalanceDisplay("00001"));
        cl.finish();
    }
}
