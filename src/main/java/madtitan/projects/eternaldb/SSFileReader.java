package madtitan.projects.eternaldb;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;

public class SSFileReader {

  private final RandomAccessFile randomAccessFile;
  private final DataFileMetadata dataFileMetadata;

  public SSFileReader(final String filePath) throws IOException {
    this.randomAccessFile = new RandomAccessFile(filePath, "r");
    this.dataFileMetadata = this.parseDataFileMetadata();
  }

  private DataFileMetadata parseDataFileMetadata() throws IOException {
    var statsLocationStart = this.randomAccessFile.readInt();
    var statsLocationLength = this.randomAccessFile.readInt();
    var bloomFilterLocationStart = this.randomAccessFile.readInt();
    var bloomFilterLength = this.randomAccessFile.readInt();
    var keyValueLocationStart = this.randomAccessFile.readInt();
    var keyValueLength = this.randomAccessFile.readInt();

    return DataFileMetadata.builder()
        .withStatsLocationStart(statsLocationStart)
        .withStatsLength(statsLocationLength)
        .withBloomFilterLocationStart(bloomFilterLocationStart)
        .withBloomFilterLength(bloomFilterLength)
        .withKeyValueLocationStart(keyValueLocationStart)
        .withKeyValueLength(keyValueLength)
        .build();
  }

  public DataFileMetadata getDataFileMetadata() {
    return dataFileMetadata;
  }

  public byte[] getValue(final int location) throws IOException {
    this.randomAccessFile.seek(location);
    final int valueLength = this.randomAccessFile.readInt();
    final byte[] value = new byte[valueLength];
    this.randomAccessFile.readFully(value);
    return value;
  }

  public SSKeyValueIterator getIterator() throws IOException {
    return new SSKeyValueIterator(this.dataFileMetadata.getKeyValueLocationStart(),
        this.randomAccessFile);
  }

  static final class KeyValueLocation {

    final byte[] key;
    final int valueLocation;

    public KeyValueLocation(byte[] key, int valueLocation) {
      this.key = key;
      this.valueLocation = valueLocation;
    }

    public static KeyValueLocation of(final byte[] key, final int valueLocation) {
      return new KeyValueLocation(key, valueLocation);
    }
  }

  static final class SSKeyValueIterator implements Iterator<KeyValueLocation> {

    private final int location;
    private final RandomAccessFile randomAccessFile;

    public SSKeyValueIterator(final int location, final RandomAccessFile randomAccessFile)
        throws IOException {
      this.location = location;
      this.randomAccessFile = randomAccessFile;
      this.randomAccessFile.seek(location);
    }

    @Override
    public boolean hasNext() {
      try {
        if (this.location >= randomAccessFile.length()) {
          return false;
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

      return true;
    }

    @Override
    public KeyValueLocation next() {
      final int keyLength = this.randomAccessFile.readInt();
      final byte[] key = new byte[keyLength];
      final int valueLocation = this.randomAccessFile.readInt();
      return KeyValueLocation.of(key, valueLocation);
    }
  }
}
