package commands;

import repository.Repository;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class Set implements Command{
  private final String prefix="+";
  private final String CRLF="\r\n";
  private final Repository redisRepository;
  public Set(Repository repository){
    this.redisRepository=repository;
  }
  @Override
  public byte[] process(List<String> args) {
    if(args.size()<2)
      throw new MyRedisException("SET command의 파라미터 길이는 2이상이어야 합니다.");
    String key = args.get(0);
    String value = args.get(1);
    try {
      redisRepository.set(key,value);
    }catch (Exception e){
      throw new MyRedisException("SET 실행 실패, 에러 메세지 : "+e.getMessage());
    }
    return (prefix+"OK"+CRLF).getBytes(StandardCharsets.UTF_8);
  }
}
