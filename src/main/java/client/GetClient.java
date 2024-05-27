package client;

public class GetClient {
  public static void main(String[] args) throws InterruptedException{
    String command = "*2\r\n$3\r\nGET\r\n$3\r\nfoo\r\n";
    Client client = new Client();
    client.executeQuery(command);
  }
}
