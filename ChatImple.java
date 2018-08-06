import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.print.Printable;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress; 

    
public class ChatImple extends UnicastRemoteObject implements Chat 
{ 
    class MulticastPublisher {
        private DatagramSocket socket;
        private InetAddress group;
        private byte[] buf;
     
        public void multicast(MulticastMessage multicastMessage) throws IOException {
            socket = new DatagramSocket();
            group = InetAddress.getByName("230.0.0.02");

            ByteArrayOutputStream baos = new ByteArrayOutputStream(6400);
            ObjectOutputStream oos = new ObjectOutputStream(baos);

            oos.writeObject(multicastMessage);
            buf = baos.toByteArray();
            
            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446);

            socket.send(packet);
            oos.close();
            socket.close();
        }
    }
    
    protected ChatImple() throws RemoteException  
    { 
        super(); 
    }

    public void exibir(String message, Date dataHora, String sender) throws RemoteException { 
        //TODO: exibir data hora e sender
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        System.out.println(sender + ": " + message + "(" + dateFormat.format(dataHora) + ")");
    } 

    public void sendToServer(String message, String sender) throws RemoteException {         
        try {
            MulticastPublisher mp = new MulticastPublisher();
            mp.multicast(new MulticastMessage(message, sender)); 
        } catch (Exception e) {
            //TODO: handle exception
        }

    } 
}