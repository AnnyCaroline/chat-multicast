import java.rmi.Remote; 
import java.rmi.RemoteException; 
    
public interface Chat extends Remote { 
    public void sendToMulticast(String message) throws RemoteException;
    public String receiveFromMulticast() throws RemoteException;
}