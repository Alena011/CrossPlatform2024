package laba5.ex1.server;

import laba5.ex1.interfaces.Performer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {

            System.out.println("Server started...");

            ServerSocket serverSocket = new ServerSocket(12345);
            Socket clientSocket = serverSocket.accept();

            // Уведомление о подключении клиента
            System.out.println("Client connected...");

            //поток ввода для приема информации от клиента
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            String classFile = (String) in.readObject();
            classFile = classFile.replaceFirst("client", "server");
            byte[] b = (byte[]) in.readObject();
            FileOutputStream fos = new FileOutputStream(classFile);
            fos.write(b);

            // Получаем объект - задание и вычисляем его
            Performer performer = (Performer) in.readObject();

            // вычесления
            System.out.println("Calculation started...");
            double startTime = System.nanoTime();
            Object output = performer.perform();
            double endTime = System.nanoTime();
            double completionTime = endTime - startTime;
            System.out.println("Calculation completed...");


            ResultImpl result = new ResultImpl(output, completionTime);
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            classFile = "src/main/java/laba5/ex1/server/ResultImpl.java";
            out.writeObject(classFile);
            FileInputStream fis = new FileInputStream(classFile);
            byte[] bo = new byte[fis.available()];
            fis.read(bo);
            out.writeObject(bo);
            out.writeObject(result);

            // Закрываем потоки и сокет
            fis.close();
            fos.close();
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
