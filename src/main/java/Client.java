import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
  private static final Logger logger = Logger.getLogger("Client");
  public static void main(String[] args) throws InterruptedException{
    int ticket = -1;
    try (Socket socket = new Socket("127.0.0.1", 6379)) {
      logger.info("socket opened : "+socket.isConnected());
      InputStream input = socket.getInputStream();
      while (true){
        byte[] bytes = new byte[1024];
        int result = input.read(bytes);
        if(result==-1){
          logger.info("server closed");
          break;
        }
        String line = new String(bytes,0,result);
        logger.info("read value : "+line);
      }
    }catch (IOException e){
      logger.log(Level.INFO,"IOException : {}",e.getMessage());
    }
  }
}
