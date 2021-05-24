package madtitan.projects.eternaldb;

import java.io.IOException;
import java.util.HashMap;
import madtitan.projects.eternaldb.SSFileReader.SSKeyValueIterator;

public class SSTableMap implements Map<byte[], byte[]> {

  private final SSFileReader ssFileReader;
  private final HashMap<byte[], Integer> lookup;

  public SSTableMap(final SSFileReader ssFileReader) throws IOException {
    this.ssFileReader = ssFileReader;
    this.lookup = new HashMap<>();

    final SSKeyValueIterator iterator = this.ssFileReader.getIterator();
    while (iterator.hasNext()) {
      var next = iterator.next();
      this.lookup.put(next.key, next.valueLocation);
    }
  }

  @Override
  public byte[] get(byte[] key) {
    return new byte[0];
  }

  @Override
  public void put(byte[] key, byte[] value) {

  }

  @Override
  public byte[] delete(byte[] key) {
    return new byte[0];
  }
}
