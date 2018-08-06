import java.rmi.Remote; 
import java.rmi.RemoteException;
import java.util.Date;

public interface Chat extends Remote { 
    public void exibir(MulticastMessage multicastMessage) throws RemoteException;
    public void sendToServer(MulticastMessage multicastMessage) throws RemoteException;
}