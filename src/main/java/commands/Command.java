package commands;

import java.util.List;

public interface Command {
  byte[] process(List<String> args);
}
