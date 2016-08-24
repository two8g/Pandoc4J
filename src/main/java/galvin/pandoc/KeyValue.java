package galvin.pandoc;

/**
 * Created by two8g on 16-4-25.
 */
public class KeyValue<K, V> {
    K key;
    V value;

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public KeyValue(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
