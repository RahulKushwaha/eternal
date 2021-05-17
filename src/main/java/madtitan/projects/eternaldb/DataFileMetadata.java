package madtitan.projects.eternaldb;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class DataFileMetadata {

  public abstract long keyValueLocationStart();
  public abstract long statsLocationStart();
  public abstract long bloomFilterLocationStart();

  public static Builder builder() {
    return new AutoValue_DataFileMetadata.Builder();
  }


  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder keyValueLocationStart(long keyValueLocationStart);

    public abstract Builder statsLocationStart(long statsLocationStart);

    public abstract Builder bloomFilterLocationStart(long bloomFilterLocationStart);

    public abstract DataFileMetadata build();
  }
}
