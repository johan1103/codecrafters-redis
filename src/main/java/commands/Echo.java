package commands;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class Echo implements Command{
  private final String prefix="$";
  private final String CRLF="\r\n";
  @Override
  public byte[] process(List<String> args) {
    if(args.size()<1)
      throw new MyRedisException("echo command의 파라미터 길이는 1이상이어야 합니다.");
    String response = prefix+args.get(0).length()+CRLF+args.get(0)+CRLF;
    return response.getBytes(StandardCharsets.UTF_8);
  }
}
