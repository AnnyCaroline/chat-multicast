import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuracao {
 
    public String host;
    public String port;
    public String multicast;

    public Configuracao(){
        try{
            Properties props = new Properties();
            FileInputStream file = new FileInputStream("./conf.properties");
            props.load(file);

            this.host = props.getProperty("host");
            this.port = props.getProperty("port");
            this.multicast = props.getProperty("multicast");
            
            // System.out.println("Host = " + this.host);
            // System.out.println("Port = " + this.port);
            // System.out.println("Multicast = " + this.multicast);
        } catch (Exception e){ 
            System.out.println("Erro ao tentar obter o arquivo de configurações");
        }
    }
}