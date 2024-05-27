package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EchoClient {
  private static final Logger logger = Logger.getLogger("client.Client");
  public static void main(String[] args) throws InterruptedException{
    int ticket = -1;
    try (Socket socket = new Socket("127.0.0.1", 6379)) {
      logger.info("socket opened : "+socket.isConnected());
      InputStream input = socket.getInputStream();
      OutputStream output = socket.getOutputStream();
      for(int i=0;i<1;i++) {
        String command = "*2\r\n$4\r\nECHO\r\n$3\r\nhey\r\n";
        System.out.println("send pingping");
        output.write(command.getBytes(StandardCharsets.UTF_8));
        byte[] bytes = new byte[1024];
        int result = input.read(bytes);
        if (result == -1) {
          logger.info("server closed");
          break;
        }
        String line = new String(bytes, 0, result);
        logger.info("read value : " + line);
      }
    }catch (IOException e){
      logger.log(Level.INFO,"IOException : {}",e.getMessage());
    }
  }
}
