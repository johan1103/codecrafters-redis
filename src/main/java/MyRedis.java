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
    String command = parseCommand(requestMessage);
    String[] args = parseArguments(requestMessage);
    Command commandProcessor = commanders.get(command);
    if(commandProcessor==null)
      throw new MyRedisException("invalid command ["+command+"]");
    return commandProcessor.process(args);
  }
  private String parseCommand(String message){
    StringTokenizer st = new StringTokenizer(message," ");
    return st.nextToken();
  }
  private String[] parseArguments(String message){
    StringTokenizer st = new StringTokenizer(message, " ");
    st.nextToken();
    List<String> argsList = new ArrayList<>();
    while(st.hasMoreTokens()){
      argsList.add(st.nextToken());
    }
    String[] args = new String[argsList.size()];
    for(int i=0;i<args.length;i++)
      args[i]=argsList.get(i);
    return args;
  }
}
