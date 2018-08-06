import java.io.Serializable;
import java.util.Date;

public class MulticastMessage implements Serializable{
    public String message;
    public Date dataHora;
    public String sender;

    public MulticastMessage(String message, String sender){
        this.message = message;
        this.dataHora = new Date();
        this.sender = sender;
    }
}
