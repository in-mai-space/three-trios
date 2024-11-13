package strategy;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Represent tests for Pair.
 */
public class PairTest {

  @Test(expected = IllegalArgumentException.class)
  public void testNullKey() {
    new Pair<>(null, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullValue() {
    new Pair<>("Hello", null);
  }

  @Test
  public void testGetKey() {
    Pair<String, Integer> pair = new Pair<>("Key1", 100);
    assertEquals("Key1", pair.getKey());
  }

  @Test
  public void testGetValue() {
    Pair<String, Integer> pair = new Pair<>("Key1", 100);
    assertEquals((Integer) 100, pair.getValue());
  }

  @Test
  public void testToString() {
    Pair<String, Integer> pair = new Pair<>("Key1", 100);
    assertEquals("(Key1, 100)", pair.toString());
  }

  @Test
  public void testEqualsSameContent() {
    Pair<String, Integer> pair1 = new Pair<>("Key1", 100);
    Pair<String, Integer> pair2 = new Pair<>("Key1", 100);
    assertTrue(pair1.equals(pair2));
  }

  @Test
  public void testEqualsDifferentKey() {
    Pair<String, Integer> pair1 = new Pair<>("Key1", 100);
    Pair<String, Integer> pair2 = new Pair<>("Key2", 100);
    assertFalse(pair1.equals(pair2));
  }

  @Test
  public void testEqualsDifferentValue() {
    Pair<String, Integer> pair1 = new Pair<>("Key1", 100);
    Pair<String, Integer> pair2 = new Pair<>("Key1", 200);
    assertFalse(pair1.equals(pair2));
  }

  @Test
  public void testEqualsDifferentObject() {
    Pair<String, Integer> pair = new Pair<>("Key1", 100);
    assertFalse(pair.equals("Some String"));
  }

  @Test
  public void testEqualsNull() {
    Pair<String, Integer> pair = new Pair<>("Key1", 100);
    assertFalse(pair.equals(null));
  }

  @Test
  public void testHashCodeSameContent() {
    Pair<String, Integer> pair1 = new Pair<>("Key1", 100);
    Pair<String, Integer> pair2 = new Pair<>("Key1", 100);
    assertEquals(pair1.hashCode(), pair2.hashCode());
  }

  @Test
  public void testHashCodeDifferentContent() {
    Pair<String, Integer> pair1 = new Pair<>("Key1", 100);
    Pair<String, Integer> pair2 = new Pair<>("Key1", 200);
    assertNotEquals(pair1.hashCode(), pair2.hashCode());
  }
}
