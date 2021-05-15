package madtitan.projects.eternaldb;

import java.util.HashMap;

public class InMemoryPrimaryMapImpl implements Map<byte[], byte[]> {

  private final HashMap<byte[], byte[]> map;

  public InMemoryPrimaryMapImpl() {
    this.map = new HashMap<>();
  }

  @Override
  public byte[] get(byte[] key) {
    return this.map.get(key);
  }

  @Override
  public byte[] put(byte[] key, byte[] value) {
    return this.map.put(key, value);
  }

  @Override
  public byte[] delete(byte[] key) {
    return new byte[0];
  }
}
