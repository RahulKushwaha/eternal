package madtitan.projects.eternaldb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class will be used to write the {@link TreeMap} to a file.
 */
public class SSFileWriter {

  public static final String WRITE_MODE = "w";

  private final RandomAccessFile randomAccessFile;

  public SSFileWriter(RandomAccessFile randomAccessFile) {
    this.randomAccessFile = randomAccessFile;
  }

  /**
   * Api to write a data {@link TreeMap} to the file.
   *
   */
  public void writeToFile(final byte[] stats,
      final byte[] bloomfilterData,
      final TreeMap<byte[], byte[]> map,
      final String filePath)
      throws FileNotFoundException {

    final RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, WRITE_MODE);
    final Map<byte[], Long> keyOffSetMap = new HashMap<>();
    try {
      final long statLocation = randomAccessFile.getFilePointer();
      randomAccessFile.write(stats.length);
      randomAccessFile.write(stats);

      final long bloomFilterLocation = randomAccessFile.getFilePointer();
      randomAccessFile.write(bloomfilterData.length);
      randomAccessFile.write(bloomfilterData);
      final long mapStartPosition = randomAccessFile.getFilePointer();

      for (Map.Entry<byte[], byte[]> entry : map.entrySet()) {
        long filePosition = randomAccessFile.getFilePointer();
        int valueLength = entry.getValue().length;
        randomAccessFile.write(valueLength);
        randomAccessFile.write(entry.getValue());
        keyOffSetMap.put(entry.getKey(), filePosition);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
