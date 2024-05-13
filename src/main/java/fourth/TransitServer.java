package fourth;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TransitServer extends Thread {

    private CardRepository bnk = null;
    private ServerSocket servSock = null;
    private int serverPort = -1;
    private final boolean running = true;

    public TransitServer(int port) {
        this.bnk = new CardRepository();
        this.serverPort = port;
    }

    @Override
    public void run() {
        try {
            this.servSock = new ServerSocket(serverPort);
            System.out.println("Metro Server started");
            while (running) {
                System.out.println("New Client Waiting...");
                Socket sock = servSock.accept();
                System.out.println("New client: " + sock);

                // Створення об'єкта ClientHandler та передача йому екземпляра MetroCardBank
                UserManager ch = new UserManager(this.getBnk(), sock);
                ch.start();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        } finally {
            try {
                servSock.close();
                System.out.println("Metro Server stopped");
            } catch (IOException ex) {
                System.out.println("Error: " + ex);
            }
        }
    }


    public static void main(String[] args) {
        TransitServer srv = new TransitServer(7891);
        srv.start();
    }

    // Додайте метод getBnk(), який повертає екземпляр MetroCardBank
    public CardRepository getBnk() {
        return bnk;
    }
}
