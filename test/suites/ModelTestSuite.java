package suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import model.GameConfigParserTest;
import model.components.card.ThreeTriosCardTest;
import model.components.enums.AttackValueTest;
import model.components.grid.ThreeTriosGridTest;
import model.components.hand.ThreeTriosHandTest;
import model.components.manager.ThreeTriosGridManagerTest;

/**
 * The ModelTestSuite class is a JUnit test suite that aggregates
 * multiple test classes for the model components of the Three Trios
 * card game. This suite allows for running all model-related tests
 * together in a single execution.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ThreeTriosCardTest.class,
        AttackValueTest.class,
        ThreeTriosHandTest.class,
        ThreeTriosGridTest.class,
        GameConfigParserTest.class,
        ThreeTriosGridManagerTest.class
})
public class ModelTestSuite {
}