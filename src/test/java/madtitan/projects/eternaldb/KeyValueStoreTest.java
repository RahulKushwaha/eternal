package madtitan.projects.eternaldb;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.LinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KeyValueStoreTest {

  private KeyValueStore keyValueStore;

  @BeforeEach
  void setUp() {
    InMemoryPrimaryMapImpl inMemoryPrimaryMap = new InMemoryPrimaryMapImpl(2);
    this.keyValueStore = new KeyValueStore(inMemoryPrimaryMap, new LinkedList<>());
  }

  @Test
  void testGetFromEmptyStore() {
    final byte[] key = new byte[]{1, 2};
    assertNull(this.keyValueStore.get(key));
  }

  @Test
  void testGetWhenItemIsInPrimaryMap() {
    final byte[] key = new byte[]{1, 2};
    final byte[] value = new byte[]{2, 3};
    this.keyValueStore.put(key, value);

    byte[] valueResponse = this.keyValueStore.get(key);
    assertNotNull(valueResponse);
    assertEquals(value, valueResponse);
  }

  @Test
  void testPutMoreThanThresholdMovesToLookupChain() {
    for (int i = 0; i < 50; i++) {
      final byte[] key = new byte[]{(byte) i, (byte) (i * 2)};
      final byte[] value = new byte[]{(byte) i, (byte) (i * 2)};

      this.keyValueStore.put(key, value);
    }

    assertEquals(24, this.keyValueStore.getLookupChain().size());
    assertArrayEquals(new byte[]{0, 0}, this.keyValueStore.get(new byte[]{0, 0}));
  }
}
