package laba8.pi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CMethods extends Remote {

    void register(Member o) throws RemoteException;
    List<Member> getAllClient() throws RemoteException;
}
