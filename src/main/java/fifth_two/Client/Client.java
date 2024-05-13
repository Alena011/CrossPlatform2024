package fifth_two.Client;

import fifth_two.Data.ActivePerson;
import fifth_two.Data.Person;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

public class Client {

    private static final int BUFFER_SIZE = 512;

    private final ActivePerson users;
    private DatagramSocket socket;
    private final int serverPort;
    private InetAddress serverAddress;

    public Client(String address, int serverPort) {
        this.serverPort = serverPort;
        users = new ActivePerson();

        try {
            serverAddress = InetAddress.getByName(address);
            socket = new DatagramSocket();
            socket.setSoTimeout(10000);
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
    }

    public void work() {
        byte[] buffer = new byte[BUFFER_SIZE];
        try {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);
            socket.send(packet);
            System.out.println("Request sent to server...");
            while (true) {
                packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                if (packet.getLength() == 0) break;
                try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(
                        packet.getData(), 0, packet.getLength()))) {
                    Person person = (Person) ois.readObject();
                    users.add(person);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
        System.out.println("Received user data from server. Registered users: " + users.size());
        System.out.println("Registered users: " + users);
    }

    public static void main(String[] args) {
        new Client("localhost", 8080).work();
    }
}
