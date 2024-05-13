package fifth.client;

import fifth.interfaces.Finish;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {

            System.out.println("Connecting to server...");

            Socket client = new Socket("localhost", 12345);

            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());

            String classFile = "F:\\Projects\\Other\\Alyona\\CrossPlatform2024\\src\\main\\java\\fifth\\client\\Profession.java";
            out.writeObject(classFile);
            FileInputStream fis = new FileInputStream(classFile);
            byte[] b = new byte[fis.available()];
            fis.read(b);
            out.writeObject(b);

            Profession profession = new Profession(5);

            out.writeObject(profession);

            System.out.println("Job sent to server...");
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());

            classFile = (String) in.readObject();
            b = (byte[]) in.readObject();
            FileOutputStream fos = new FileOutputStream(classFile);
            fos.write(b);

            Finish finish = (Finish) in.readObject();

            System.out.println("result = " + finish.output() + ", time taken = " + finish.scoreTime() + "ns");

            // Закрываем потоки и сокет
            fis.close();
            fos.close();
            in.close();
            out.close();
            client.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
