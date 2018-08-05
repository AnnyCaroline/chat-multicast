import java.rmi.Naming; 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChatClient  
{ 
    public static void main(String[] args)  
    { 
        try
        { 
            Chat c = (Chat) Naming.lookup("rmi://localhost:9902/ChatService"); 
            
            BufferedReader br = null; 
            br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Bem vindo ao chat");

            String input;
            while(true){
                System.out.print("> ");
                input = br.readLine();
                c.sendToMulticast(input);
            }
        }  
        catch (Exception e)  
        { 
            System.out.println(e); 
        } 
    } 
}