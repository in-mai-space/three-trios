package view.console;

/**
 * The GameView interface defines a generic contract for rendering
 * different types of game views. Implementing classes should provide
 * their own way of displaying or updating the game interface.
 *
 * @param <T> the type of the rendered view (can be any type,
 *            e.g., String, GUI component, etc.)
 */
public interface GameView<T> {
  /**
   * Renders the game view and returns the representation of the view.
   *
   * @return the rendered view of type T. This could be a
   *         String representation, a GUI component, or any other
   *         type depending on the implementation.
   */
  T render();
}
