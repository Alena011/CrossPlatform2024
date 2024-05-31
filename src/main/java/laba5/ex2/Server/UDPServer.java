package laba5.ex2.Server;

import laba5.ex2.Data.ActivePerson;
import laba5.ex2.Data.Person;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {

    private static final int BUFFER_SIZE = 512;

    private final ActivePerson users;
    private DatagramSocket socket;
    private DatagramPacket packet;
    private InetAddress inetAddress;
    private int port;

    public UDPServer(int port) {
        try {
            socket = new DatagramSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        users = new ActivePerson();
    }

    private void work() {
        System.out.println("Server started...");
        try {
            while (true) {
                waitUserData();
                log();
                sentUserData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }


    private void waitUserData() throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        inetAddress = packet.getAddress();
        port = packet.getPort();
        Person person = new Person(inetAddress, port);
        if (!users.contains(person)) {
            users.add(person);
        }
    }

    private void sentUserData() throws IOException {
        byte[] buffer;
        for (Person person : users) {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                 ObjectOutputStream oos = new ObjectOutputStream(baos)) {

                oos.writeObject(person);
                buffer = baos.toByteArray();
                packet = new DatagramPacket(buffer, buffer.length, inetAddress, port);
                socket.send(packet);
            }
        }
        buffer = "End".getBytes();
        packet = new DatagramPacket(buffer, 0, inetAddress, port);
        socket.send(packet);
    }

    private void log() {
        System.out.println("Received data from: " + inetAddress + " on port: " + port);
    }


    public static void main(String[] args) {
        new UDPServer(8080).work();
    }
}
