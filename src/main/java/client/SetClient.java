package client;

public class SetClient {
  public static void main(String[] args) throws InterruptedException{
    String command = "*3\r\n$3\r\nSET\r\n$3\r\nfoo\r\n$3\r\nbar\r\n";
    Client client = new Client();
    client.executeQuery(command);
  }
}
