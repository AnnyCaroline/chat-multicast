import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuracao {
 
    public String host;
    public String port;
    public String multicast;
    public Integer multicastPort;

    public Configuracao(){
        try{
            Properties props = new Properties();
            FileInputStream file = new FileInputStream("./conf.properties");
            props.load(file);

            this.host = props.getProperty("host");
            this.port = props.getProperty("port");
            this.multicast = props.getProperty("multicast");
            this.multicastPort = Integer.parseInt(props.getProperty("multicastPort"));
        } catch (Exception e){ 
            System.out.println("Erro ao tentar obter o arquivo de configurações");
        }
    }
}