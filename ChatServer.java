import java.rmi.Naming;

import javax.print.event.PrintJobListener; 
    
public class ChatServer { 
    ChatServer() { 
        try { 
            Chat c = new ChatImple(); 
            Naming.rebind("rmi://localhost:9902/ChatService", c); 
        }  
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 
    public static void main(String[] args) { 
        System.out.println("Servidor");
        new ChatServer(); 
        System.out.println("Servidor - ");
    } 
}  