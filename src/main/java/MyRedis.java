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
    List<String> args = new ArrayList<>();
    StringTokenizer st = new StringTokenizer(requestMessage, "\\rn");
    String bulkStr=st.nextToken();
    int bulkSize=Integer.parseInt(bulkStr.substring(1,2));
    int commandLen = Integer.parseInt(st.nextToken().substring(1,2));
    String command = st.nextToken().substring(0,commandLen);
    for(int i=1;i<bulkSize;i++){
      int argLen = Integer.parseInt(st.nextToken().substring(1,2));
      String arg = st.nextToken().substring(0,argLen);
      args.add(arg);
    }
    Command commandProcessor = commanders.get(command);
    if(commandProcessor==null)
      throw new MyRedisException("invalid command ["+command+"]");
    return commandProcessor.process(args);
  }
}
