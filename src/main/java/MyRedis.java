import commands.*;
import commands.Set;
import repository.InMemoryRepository;
import repository.Repository;

import java.util.*;

public class MyRedis {
  private final Map<String, Command> commanders;
  public MyRedis(){
    Repository repository = new InMemoryRepository();
    commanders=new HashMap<>();
    commanders.put("ECHO",new Echo());
    commanders.put("PING",new Ping());
    commanders.put("GET",new Get(repository));
    commanders.put("SET",new Set(repository));
  }
  public byte[] request(String requestMessage){
    Request parsedRequest = new Request(requestMessage);
    Command commandProcessor = commanders.get(parsedRequest.getCommand());
    if(commandProcessor==null)
      throw new MyRedisException("invalid command ["+parsedRequest.getCommand()+"]");
    return commandProcessor.process(parsedRequest.getArgs());
  }
}
