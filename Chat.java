import java.rmi.Remote; 
import java.rmi.RemoteException; 
    
public interface Chat extends Remote { 
    public void sendToClient(String message) throws RemoteException;
    public void sendToServer(String message) throws RemoteException;
}