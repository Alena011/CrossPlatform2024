package sixth.second;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MSReceiver implements Messenger {

    private Task ui;
    private MulticastSocket group;
    private InetAddress address;
    private String name;
    private Response response;
    private int port;

    public MSReceiver(InetAddress address, int port, String name, Task ui) {
        this.name = name;
        this.address = address;
        this.ui = ui;
        this.port = port;
        try {
            group = new MulticastSocket(port);
            group.setTimeToLive(2);
            group.joinGroup(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        response = new Response(group, address, ui);
        new Thread(response).start();
    }

    @Override
    public void stop() {
        response.cancel();
        try {
            group.leaveGroup(address);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            group.close();
        }
    }

    @Override
    public void send() {
        new Thread(new Request(group, name, address, port, ui)).start();
    }
}