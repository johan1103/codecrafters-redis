package commands;

import java.nio.charset.StandardCharsets;

public class Ping implements Command{
  @Override
  public byte[] process(String[] args) {
    return "PONG".getBytes(StandardCharsets.UTF_8);
  }
}
