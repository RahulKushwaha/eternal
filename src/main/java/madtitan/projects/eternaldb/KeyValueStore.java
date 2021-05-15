package madtitan.projects.eternaldb;

import java.util.LinkedList;

public class KeyValueStore implements Map<byte[], byte[]> {

  private final LinkedList<Map<byte[], byte[]>> lookupChain;

  public KeyValueStore() {
    this.lookupChain = new LinkedList<>();
  }

  @Override
  public byte[] get(byte[] key) {
    var iterator = this.lookupChain.iterator();
    while (iterator.hasNext()) {
      var map = iterator.next();
      byte[] result;
      while ((result = map.get(key)) != null) {
        return result;
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
