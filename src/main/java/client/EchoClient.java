package client;

public class EchoClient{
  public static void main(String[] args) throws InterruptedException{
    String command = "*2\r\n$4\r\nECHO\r\n$3\r\nhey\r\n";
    Client client = new Client();
    client.executeQuery(command);
  }
}
