package madtitan.projects.eternaldb;

import java.util.LinkedList;

public class KeyValueStore implements Map<byte[], byte[]> {

  private volatile LinkedList<Map<byte[], byte[]>> lookupChain;
  private volatile InMemoryPrimaryMapImpl inMemoryPrimaryMap;

  public KeyValueStore(final InMemoryPrimaryMapImpl inMemoryPrimaryMap,
      final LinkedList<Map<byte[], byte[]>> lookupChain) {
    this.lookupChain = lookupChain;
    this.inMemoryPrimaryMap = inMemoryPrimaryMap;
  }

  @Override
  public byte[] get(byte[] key) {
    byte[] value;
    synchronized (this) {
      if ((value = this.inMemoryPrimaryMap.get(key)) != null) {
        return value;
      }
    }

    var iterator = this.lookupChain.iterator();
    while (iterator.hasNext()) {
      if ((value = iterator.next().get(key)) != null) {
        return value;
      }
    }

    return null;
  }

  @Override
  public void put(byte[] key, byte[] value) {
    synchronized (this) {
      if (this.inMemoryPrimaryMap.exceedsThreshold()) {
        // Add the current InMemoryPrimaryMap to the LinkedList
        // Create a new InMemoryPrimaryMap.
        // Replace the current reference to InMemoryPrimaryMap with the new one.
        final LinkedList<Map<byte[], byte[]>> clone = (LinkedList<Map<byte[], byte[]>>) lookupChain.clone();
        clone.addFirst(this.inMemoryPrimaryMap);

        this.lookupChain = clone;
        this.inMemoryPrimaryMap = new InMemoryPrimaryMapImpl(2);
      }

      this.inMemoryPrimaryMap.put(key, value);
    }
  }

  @Override
  public byte[] delete(byte[] key) {
    return new byte[0];
  }

  LinkedList<Map<byte[], byte[]>> getLookupChain() {
    return this.lookupChain;
  }
}
