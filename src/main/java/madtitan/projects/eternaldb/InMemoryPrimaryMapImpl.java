package madtitan.projects.eternaldb;

import com.google.common.primitives.SignedBytes;
import java.util.TreeMap;

public class InMemoryPrimaryMapImpl implements Map<byte[], byte[]> {

  private final TreeMap<byte[], byte[]> map;

  public InMemoryPrimaryMapImpl() {
    this.map = new TreeMap<>(SignedBytes.lexicographicalComparator());
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
