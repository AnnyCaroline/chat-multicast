import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject;
import java.awt.print.Printable; 
    
public class ChatImple extends UnicastRemoteObject implements Chat 
{ 
    protected ChatImple() throws RemoteException  
    { 
        super(); 
    } 
    public void sendToMulticast(String message) throws RemoteException { 
        System.out.println(message);
    } 

    public String receiveFromMulticast() throws RemoteException{
        return "string received from multicast";
    }

}