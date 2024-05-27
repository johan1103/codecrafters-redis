import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Request {
  private String command;
  private final List<String> args;
  public Request(String requestMessage){
    args=new ArrayList<>();
    StringTokenizer tokenizedMessage = new StringTokenizer(requestMessage,"\r\n");
    int querySize=parseQuerySize(tokenizedMessage);
    parseCommand(tokenizedMessage);
    parseArguments(tokenizedMessage,querySize);
  }
  private int parseQuerySize(StringTokenizer st){
    String bulkStr=st.nextToken();
    return Integer.parseInt(bulkStr.substring(1,2));
  }
  private void parseCommand(StringTokenizer st){
    int commandLen = Integer.parseInt(st.nextToken().substring(1,2));
    this.command = st.nextToken().substring(0,commandLen);
  }
  private void parseArguments(StringTokenizer st,int querySize){
    for(int i=1;i<querySize;i++){
      int argLen = Integer.parseInt(st.nextToken().substring(1,2));
      String arg = st.nextToken().substring(0,argLen);
      this.args.add(arg);
    }
  }
  public String getCommand(){
    return this.command;
  }
  public List<String> getArgs(){
    return this.args;
  }
}
