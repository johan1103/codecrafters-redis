package commands;

public interface Command {
  byte[] process(String[] args);
}
