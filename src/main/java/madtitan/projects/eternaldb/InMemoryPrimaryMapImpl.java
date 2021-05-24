package madtitan.projects.eternaldb;

import com.google.common.primitives.SignedBytes;
import java.util.TreeMap;

public class InMemoryPrimaryMapImpl implements Map<byte[], byte[]>, SizeBounded {

  private final TreeMap<byte[], byte[]> map;
  private final int itemsThreshold;

  private static final int DEFAULT_ITEMS_THRESHOLD = 1000;

  public InMemoryPrimaryMapImpl() {
    this(DEFAULT_ITEMS_THRESHOLD);
  }

  public InMemoryPrimaryMapImpl(final int itemsThreshold) {
    this.map = new TreeMap<>(SignedBytes.lexicographicalComparator());
    this.itemsThreshold = itemsThreshold;
  }

  @Override
  public byte[] get(byte[] key) {
    return this.map.get(key);
  }

  @Override
  public void put(byte[] key, byte[] value) {
    this.map.put(key, value);
  }

  @Override
  public byte[] delete(byte[] key) {
    return new byte[0];
  }

  @Override
  public int getSizeThreshold() {
    return this.itemsThreshold;
  }

  @Override
  public int getSize() {
    return this.map.size();
  }
}
