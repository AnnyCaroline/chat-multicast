import java.rmi.Naming; 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChatClient { 

    public static void main(String[] args)  
    { 
        String input = "";
        Chat c = null;

        try{        
            BufferedReader br = null; 
            br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Bem vindo ao chat");
            System.out.print("Digite o seu nome:");
            input = br.readLine();

            c = new ChatImple(); 
            Naming.rebind("rmi://localhost:9902/Chat"+input, c); 

            final String userName = input;
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    try {
                        Naming.unbind("rmi://localhost:9902/Chat" + userName);     
                    } catch (Exception e) {
                        System.out.println("Erro ao tentar desconectar o cliente");
                    }
                    
                    System.out.println("");
                    System.out.println("Tchau :)");
                }
            });            

            while(true){
                input = br.readLine();

                Chat server = (Chat) Naming.lookup("rmi://localhost:9902/ChatService"); 
                server.sendToServer(input, "rmi://localhost:9902/Chat" + userName);
            }
        }  
        catch (Exception e){ 
            System.out.println(e); 
        } 
    } 
}