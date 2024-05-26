package commands;

public class MyRedisException extends RuntimeException{
  public MyRedisException(String message){
    super(message);
  }
}
