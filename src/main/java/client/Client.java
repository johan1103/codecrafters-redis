package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {
  public void executeQuery(String command)throws InterruptedException{
    try (Socket socket = new Socket("127.0.0.1", 6379)) {
      System.out.println("socket opened : "+socket.isConnected());
      InputStream input = socket.getInputStream();
      OutputStream output = socket.getOutputStream();
      for(int i=0;i<1;i++) {
        System.out.println("send "+command);
        output.write(command.getBytes(StandardCharsets.UTF_8));
        byte[] bytes = new byte[1024];
        int result = input.read(bytes);
        if (result == -1) {
          System.out.println("server closed");
          break;
        }
        String line = new String(bytes, 0, result);
        System.out.println("read value : " + line);
      }
    }catch (IOException e){
      System.out.println("IOException : " + e.getMessage());
    }
  }
}
