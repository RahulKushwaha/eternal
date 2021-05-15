package madtitan.projects.eternaldb;

public class DataFileMetadata {

  private final int keyValueLocationStart;
  private final int keyValueLength;
  private final int statsLocationStart;
  private final int statsLength;
  private final int bloomFilterLocationStart;
  private final int bloomFilterLength;

  private DataFileMetadata(final int keyValueLocationStart,
      final int keyValueLength,
      final int statsLocationStart,
      final int statsLength,
      final int bloomFilterLocationStart,
      final int bloomFilterLength) {
    this.keyValueLocationStart = keyValueLocationStart;
    this.keyValueLength = keyValueLength;
    this.statsLocationStart = statsLocationStart;
    this.statsLength = statsLength;
    this.bloomFilterLocationStart = bloomFilterLocationStart;
    this.bloomFilterLength = bloomFilterLength;
  }

  public int getKeyValueLocationStart() {
    return keyValueLocationStart;
  }

  public int getKeyValueLength() {
    return keyValueLength;
  }

  public int getStatsLocationStart() {
    return statsLocationStart;
  }

  public int getStatsLength() {
    return statsLength;
  }

  public int getBloomFilterLocationStart() {
    return bloomFilterLocationStart;
  }

  public int getBloomFilterLength() {
    return bloomFilterLength;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {

    private int keyValueLocationStart;
    private int keyValueLength;
    private int statsLocationStart;
    private int statsLength;
    private int bloomFilterLocationStart;
    private int bloomFilterLength;

    private Builder() {
    }

    public Builder withKeyValueLocationStart(int keyValueLocationStart) {
      this.keyValueLocationStart = keyValueLocationStart;
      return this;
    }

    public Builder withKeyValueLength(int keyValueLength) {
      this.keyValueLength = keyValueLength;
      return this;
    }

    public Builder withStatsLocationStart(int statsLocationStart) {
      this.statsLocationStart = statsLocationStart;
      return this;
    }

    public Builder withStatsLength(int statsLength) {
      this.statsLength = statsLength;
      return this;
    }

    public Builder withBloomFilterLocationStart(int bloomFilterLocationStart) {
      this.bloomFilterLocationStart = bloomFilterLocationStart;
      return this;
    }

    public Builder withBloomFilterLength(int bloomFilterLength) {
      this.bloomFilterLength = bloomFilterLength;
      return this;
    }

    public DataFileMetadata build() {
      return new DataFileMetadata(keyValueLocationStart, keyValueLength, statsLocationStart,
          statsLength, bloomFilterLocationStart, bloomFilterLength);
    }
  }
}
