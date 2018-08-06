import java.rmi.Naming;
import java.rmi.AlreadyBoundException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class ChatClient { 

    private static String getHostname(){
        StringBuffer output = new StringBuffer();
        Process p;
        try {
            p = Runtime.getRuntime().exec("hostname");
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";			
            while ((line = reader.readLine())!= null) {
                output.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();
    }

    public static void main(String[] args)  
    { 
        String input = "";
        Chat c = null;
        Configuracao confs = new Configuracao();
        Chat server;
        Thread t =null;

        try{  
            server = (Chat) Naming.lookup("rmi://"+confs.host+":"+confs.port+"/ChatService"); 

            System.out.println("Bem vindo ao chat");

            while(true){
                try{        
                    BufferedReader br = null; 
                    br = new BufferedReader(new InputStreamReader(System.in));
                    System.out.print("Digite o seu nome: ");
                    input = br.readLine();

                    input = getHostname() + "-" + input;

                    c = new ChatImple(); 

                    Naming.bind("rmi://"+confs.host+":"+confs.port+"/Chat"+input, c);
                    server.exibir(new MulticastMessage("---entrou---", input));

                    final String userName = input;

                    if (t!=null){
                        Runtime.getRuntime().removeShutdownHook(t);
                    }
                    
                    t = new Thread() {
                        public void run() {
                            try {
                                server.sendToServer(new MulticastMessage("sair", userName));
                                Naming.unbind("rmi://"+confs.host+":"+confs.port+"/Chat" + userName);     
                            } catch (Exception e) {
                                System.out.println("Erro ao tentar desconectar o cliente");
                            }
                            
                            System.out.println("");
                            System.out.println("Tchau :)");
                        }
                    };
                    Runtime.getRuntime().addShutdownHook(t);            

                    while(!input.equals("sair")){
                        input = br.readLine();
                        server.sendToServer(new MulticastMessage(input, userName));
                    }
                    //usuário digitou "sair"
                    try {
                        Naming.unbind("rmi://"+confs.host+":"+confs.port+"/Chat" + userName);     
                    } catch (Exception e) {
                        System.out.println("Erro ao tentar desconectar o cliente");
                    }

                }  
                catch (AlreadyBoundException e){ 
                    System.out.println("Usuário já registrado. Tente novamente."); 
                }
            }            
        }catch (Exception e){ 
            System.out.println("Erro"); 
        }
        
        
    } 
}