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
        private Configuracao confs;

        public MulticastPublisher() throws IOException{
            confs = new Configuracao();
            socket = new DatagramSocket();
            group = InetAddress.getByName(confs.multicast);
        }
     
        public void multicast(MulticastMessage multicastMessage) throws IOException {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(6400);
            ObjectOutputStream oos = new ObjectOutputStream(baos);

            oos.writeObject(multicastMessage);
            buf = baos.toByteArray();
            
            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, this.confs.multicastPort);

            socket.send(packet);
            oos.close();
        }
    }
    
    protected ChatImple() throws RemoteException{ 
        super(); 
    }

    public void exibir(MulticastMessage multicastMessage) throws RemoteException { 
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        System.out.println("[" + dateFormat.format(multicastMessage.dataHora) + "] " + multicastMessage.sender + ": " + multicastMessage.message);
    }

    public void sendToServer(MulticastMessage multicastMessage) throws RemoteException {         
        try {
            MulticastPublisher mp = new MulticastPublisher();
            mp.multicast(multicastMessage); 
        } catch (Exception e) {
            //TODO: handle exception
        }

    } 
}