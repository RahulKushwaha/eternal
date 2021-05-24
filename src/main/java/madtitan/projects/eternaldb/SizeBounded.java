package madtitan.projects.eternaldb;

public interface SizeBounded {

  int getSizeThreshold();

  int getSize();

  default boolean exceedsThreshold() {
    return this.getSizeThreshold() <= this.getSize();
  }
}
