import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.rmi.Naming;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.print.event.PrintJobListener;


class MulticastReceiver extends Thread {
    protected MulticastSocket socket = null;
    protected byte[] buf = new byte[5000];
    DatagramPacket packet;
    String received;

    public void run() {
        try {
            socket = new MulticastSocket(4446);
            InetAddress group = InetAddress.getByName("230.0.0.02");
            socket.joinGroup(group); 
            while (true) {

                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                int byteCount = packet.getLength();
                ByteArrayInputStream byteStream = new ByteArrayInputStream(buf);
                ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(byteStream));
                Object o = is.readObject();

                MulticastMessage msg = (MulticastMessage) o;

                String[] clients = Naming.list("rmi://localhost:9902/ChatService"); 

                for (String s: clients) { 
                    if (!msg.sender.equals("rmi:"+s) ){
                        Chat c = (Chat) Naming.lookup("rmi:"+s);
                        c.exibir(msg.message, msg.dataHora, msg.sender);
                    }
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