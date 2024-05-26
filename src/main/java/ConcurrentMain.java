import java.io.IOException;

public class ConcurrentMain {
  public static void main(String[] args) throws IOException {
    RedisNioServer server = new RedisNioServer();
    server.run();
  }
}
