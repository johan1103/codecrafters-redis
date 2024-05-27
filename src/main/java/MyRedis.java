import commands.Command;
import commands.Echo;
import commands.MyRedisException;
import commands.Ping;

import java.util.*;

public class MyRedis {
  private final Map<String, Command> commanders;
  public MyRedis(){
    commanders=new HashMap<>();
    commanders.put("ECHO",new Echo());
    commanders.put("PING",new Ping());
  }
  public byte[] request(String requestMessage){
    Request parsedRequest = new Request(requestMessage);
    Command commandProcessor = commanders.get(parsedRequest.getCommand());
    if(commandProcessor==null)
      throw new MyRedisException("invalid command ["+parsedRequest.getCommand()+"]");
    return commandProcessor.process(parsedRequest.getArgs());
  }
}
