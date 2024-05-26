package commands;

import java.nio.charset.StandardCharsets;

public class Echo implements Command{
  @Override
  public byte[] process(String[] args) {
    if(args.length<1)
      throw new MyRedisException("echo command의 파라미터 길이는 1이상이어야 합니다.");
    return args[0].getBytes(StandardCharsets.UTF_8);
  }
}
