import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.rmi.Naming;
import java.util.concurrent.TimeUnit;

import javax.print.event.PrintJobListener; 

class MulticastPublisher {
    private DatagramSocket socket;
    private InetAddress group;
    private byte[] buf;
 
    public void multicast(String multicastMessage) throws IOException {
        try {
            socket = new DatagramSocket();
            group = InetAddress.getByName("230.0.0.02");
            buf = multicastMessage.getBytes();
     
            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446);
            socket.send(packet);
            socket.close();  
        } catch (Exception e) {
            //TODO: handle exception
        }
        
    }
}

class MulticastReceiver extends Thread {
    protected MulticastSocket socket = null;
    protected byte[] buf = new byte[256];
    DatagramPacket packet;
    String received;

    MulticastReceiver(){
        super();
        try {
            socket = new MulticastSocket(4446);
    
            InetAddress group = InetAddress.getByName("230.0.0.02");
            socket.joinGroup(group); 
        } catch (Exception e) {
            //TODO: handle exception
        }
        
    }
 
    public void run() {
        try {
            while (true) {
                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                received = new String(packet.getData(), 0, packet.getLength());

                System.out.println(">> received: "+received);

                String[] clients = Naming.list("rmi://localhost:9902/ChatService"); 

                for (String s: clients) { 
                    System.out.println(s);
                    Chat c = (Chat) Naming.lookup("rmi:"+s);
                    c.sendToClient(received);
                }
            }     
        } catch (Exception e) {
            //TODO: handle exception
        }
    }
}

public class ChatServer { 
    ChatServer() { 
        try { 
            Chat c = new ChatImple(); 
            Naming.rebind("rmi://localhost:9902/ChatService", c); 
            
            MulticastPublisher mp = new MulticastPublisher();
            MulticastReceiver mr = new MulticastReceiver();
            mr.start();
        }  
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 
    public static void main(String[] args) { 
        System.out.println("Servidor");
        new ChatServer();
    } 
}  