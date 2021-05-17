package madtitan.projects.eternaldb;

import java.util.LinkedList;

public class KeyValueStore implements Map<byte[], byte[]> {

  private final LinkedList<Map<byte[], byte[]>> lookupChain;

  public KeyValueStore() {
    this.lookupChain = new LinkedList<>();
  }

  @Override
  public byte[] get(byte[] key) {
    for (Map<byte[], byte[]> map : this.lookupChain) {
      if ((map.get(key)) != null) {
        return map.get(key);
      }
    }

    return null;
  }

  @Override
  public byte[] put(byte[] key, byte[] value) {

    return this.lookupChain.get(0).put(key, value);
  }

  @Override
  public byte[] delete(byte[] key) {

    return new byte[0];
  }
}
