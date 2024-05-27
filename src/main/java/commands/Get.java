package commands;

import repository.Repository;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class Get implements Command{
  private final String prefix="$";
  private final String CRLF="\r\n";
  private final Repository redisRepository;
  public Get(Repository repository){
    this.redisRepository=repository;
  }
  @Override
  public byte[] process(List<String> args) {
    if(args.size()<1)
      throw new MyRedisException("GET command의 파라미터 길이는 1이상이어야 합니다.");
    String key = args.get(0);
    String value = redisRepository.get(key);
    if(value==null)
      return (prefix+"-1"+CRLF).getBytes(StandardCharsets.UTF_8);
    return (prefix+value.length()+CRLF+value+CRLF).getBytes(StandardCharsets.UTF_8);
  }
}
