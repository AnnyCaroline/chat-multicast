import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject;
import java.awt.print.Printable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress; 

    
public class ChatImple extends UnicastRemoteObject implements Chat 
{ 
    class MulticastPublisher {
        private DatagramSocket socket;
        private InetAddress group;
        private byte[] buf;
     
        public void multicast(String multicastMessage) throws IOException {
            socket = new DatagramSocket();
            group = InetAddress.getByName("230.0.0.02");
            buf = multicastMessage.getBytes();
     
            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446);
            socket.send(packet);
            socket.close();
        }
    }
    
    protected ChatImple() throws RemoteException  
    { 
        super(); 
    }

    public void sendToClient(String message) throws RemoteException { 
        System.out.println(message);
    } 

    public void sendToServer(String message) throws RemoteException { 
        
        try {
            System.out.println("sendToServer: "+message);
            MulticastPublisher mp = new MulticastPublisher();
            mp.multicast(message); 
        } catch (Exception e) {
            //TODO: handle exception
        }

    } 
}