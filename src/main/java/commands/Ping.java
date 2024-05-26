package commands;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class Ping implements Command{
  private final String prefix="+";
  private final String CRLF="\r\n";
  @Override
  public byte[] process(List<String> args) {
    return (prefix+"PONG"+CRLF).getBytes(StandardCharsets.UTF_8);
  }
}
