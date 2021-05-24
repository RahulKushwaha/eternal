package madtitan.projects.eternaldb;

public interface Map<K, V> {

  V get(final K key);

  void put(final K key, final V value);

  V delete(final K key);
}
