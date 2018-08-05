import java.rmi.Naming; 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChatClient  
{ 
    public static void main(String[] args)  
    { 
        // try {
        //     BufferedReader br = null; 
        //     br = new BufferedReader(new InputStreamReader(System.in));

        //     System.out.println("Bem vindo ao chat");
        //     System.out.print("Digite o seu nome:");
        //     String input = br.readLine();

        //     Chat c = new ChatImple(); 
        //     Naming.rebind("rmi://localhost:9902/Chat"+input, c); 
        // } catch (Exception e) {
        //     System.out.println(e); 
        // }

        try
        { 
            //ENVIO
            //Chat c = (Chat) Naming.lookup("rmi://localhost:9902/ChatService"); 
            
            String input = null;
            BufferedReader br = null; 
            br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Bem vindo ao chat");
            System.out.print("Digite o seu nome:");
            input = br.readLine();

            Chat c = new ChatImple(); 
            Naming.rebind("rmi://localhost:9902/Chat"+input, c); 

            while(true){
                System.out.print("> ");
                input = br.readLine();
                c.sendToServer(input);
            }
        }  
        catch (Exception e)  
        { 
            System.out.println(e); 
        } 
    } 
}