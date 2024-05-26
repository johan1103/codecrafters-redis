import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Main {
  public static void main(String[] args){
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.out.println("Logs from your program will appear here!");

    //  Uncomment this block to pass the first stage
    ServerSocket serverSocket = null;
    Socket clientSocket = null;
    int port = 6379;
    try {
      serverSocket = new ServerSocket(port);
      // Since the tester restarts your program quite often, setting SO_REUSEADDR
      // ensures that we don't run into 'Address already in use' errors
      serverSocket.setReuseAddress(true);
      // Wait for connection from client.
      while (true) {
        try{
        clientSocket = serverSocket.accept();
        InputStream input = clientSocket.getInputStream();
        List<String> commands = new ArrayList<>();
        List<Byte> byteList = new ArrayList<>();
        while (true) {
          byte[] readByte = new byte[1024];
          int result = input.read(readByte);
          if (result == -1)
            break;
          String commandLine = new String(readByte, 0, result, StandardCharsets.UTF_8);
          StringBuilder command = new StringBuilder();
          for (int i = 0; i <= commandLine.length(); i++) {
            if (i == commandLine.length() || commandLine.charAt(i) == '\n') {
              commands.add(command.toString());
              command = new StringBuilder();
              continue;
            }
            command.append(commandLine.charAt(i));
          }
          clientSocket.getOutputStream().write("+PONG\r\n".getBytes());
        }
      } catch(IOException e){
        System.out.println("IOException: " + e.getMessage());
      } finally{
        try {
          if (clientSocket != null) {
            clientSocket.close();
          }
        } catch (IOException e) {
          System.out.println("IOException: " + e.getMessage());
        }
      }
    }
    }catch (IOException e){
      System.out.println("IOException: " + e.getMessage());
    }
  }
  public static String byteListToString(List<Byte> l, Charset charset) {
    if (l == null) {
      return "";
    }
    byte[] array = new byte[l.size()];
    int i = 0;
    for (Byte current : l) {
      array[i] = current;
      i++;
    }
    return new String(array, charset);
  }
}
