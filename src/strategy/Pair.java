package strategy;

/**
 * Represents a generic pair of two values: a key and a value.
 * This class is immutable, meaning once created, the key and value cannot be changed.
 *
 * @param <K> the type of the key
 * @param <V> the type of the value
 */
public class Pair<K, V> {
  private final K key;
  private final V value;

  /**
   * Constructs a new Pair with the specified key and value.
   *
   * @param key   the key of the pair
   * @param value the value of the pair
   * @throws IllegalArgumentException if key or value is null
   */
  public Pair(K key, V value) {
    if (key == null || value == null) {
      throw new IllegalArgumentException("Key and value must not be null");
    }
    this.key = key;
    this.value = value;
  }

  /**
   * Returns the key of this pair.
   *
   * @return the key of this pair
   */
  public K getKey() {
    return key;
  }

  /**
   * Returns the value of this pair.
   *
   * @return the value of this pair
   */
  public V getValue() {
    return value;
  }

  /**
   * Returns a string representation of this pair in the form "(key, value)".
   *
   * @return a string representation of this pair
   */
  @Override
  public String toString() {
    return "(" + key + ", " + value + ")";
  }

  /**
   * Compares this pair to the specified object for equality. Two pairs are considered
   * equal if they are of the same class and their keys and values are equal.
   *
   * @param o the object to compare to
   * @return {@code true} if this pair is equal to the specified object, {@code false} otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (o == null || getClass() != o.getClass()) { return false; }
    Pair<?, ?> pair = (Pair<?, ?>) o;
    return key.equals(pair.key) && value.equals(pair.value);
  }

  /**
   * Returns the hash code of this pair, which is derived from the hash codes of the key and value.
   *
   * @return the hash code of this pair
   */
  @Override
  public int hashCode() {
    return key.hashCode() + value.hashCode();
  }
}
