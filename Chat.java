import java.rmi.Remote; 
import java.rmi.RemoteException;
import java.util.Date;

public interface Chat extends Remote { 
    public void exibir(String message, Date dataHora, String sender) throws RemoteException;
    public void sendToServer(String message, String sender) throws RemoteException;
}