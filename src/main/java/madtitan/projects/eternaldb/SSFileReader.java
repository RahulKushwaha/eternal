package madtitan.projects.eternaldb;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;

public class SSFileReader {

  public static final String READ_MODE = "r";
  private final RandomAccessFile randomAccessFile;
  private final DataFileMetadata dataFileMetadata;

  public SSFileReader(final String filePath) throws IOException {
    this.randomAccessFile = new RandomAccessFile(filePath, READ_MODE);
    this.dataFileMetadata = this.parseDataFileMetadata();
  }

  private DataFileMetadata parseDataFileMetadata() throws IOException {
    var statsLocationStart = this.randomAccessFile.readLong();
    var bloomFilterLocationStart = this.randomAccessFile.readLong();
    var keyValueLocationStart = this.randomAccessFile.readLong();

    return DataFileMetadata.builder()
        .statsLocationStart(statsLocationStart)
        .bloomFilterLocationStart(bloomFilterLocationStart)
        .keyValueLocationStart(keyValueLocationStart).build();
  }

  public DataFileMetadata getDataFileMetadata() {
    return dataFileMetadata;
  }

  public byte[] getValue(final long location) throws IOException {
    this.randomAccessFile.seek(location);
    final int valueLength = this.randomAccessFile.readInt();
    final byte[] value = new byte[valueLength];
    this.randomAccessFile.readFully(value);
    return value;
  }

  public SSKeyValueIterator getIterator() throws IOException {
    return new SSKeyValueIterator(this.dataFileMetadata.keyValueLocationStart(),
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

    private final long location;
    private final RandomAccessFile randomAccessFile;

    public SSKeyValueIterator(final long location, final RandomAccessFile randomAccessFile)
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
      final int keyLength;
      try {
        keyLength = this.randomAccessFile.readInt();
        final byte[] key = new byte[keyLength];
        final int valueLocation = this.randomAccessFile.readInt();
        return KeyValueLocation.of(key, valueLocation);
      } catch (IOException e) {
        e.printStackTrace();
      }
      return null;
    }
  }
}
