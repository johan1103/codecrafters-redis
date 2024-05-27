package repository;

import java.util.HashMap;
import java.util.Map;

public class InMemoryRepository implements Repository{
  private final Map<String,String> repository;
  public InMemoryRepository(){
    this.repository=new HashMap<>();
  }

  @Override
  public void set(String key, String value) {
    repository.put(key,value);
  }

  @Override
  public String get(String key) {
    return repository.get(key);
  }
}
