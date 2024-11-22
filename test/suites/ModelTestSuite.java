package suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import model.implementation.ThreeTriosModelTest;
import model.implementation.ThreeTriosCellTest;
import model.implementation.ThreeTriosGridTest;
import model.implementation.ThreeTriosHandTest;
import model.implementation.ThreeTriosGridManagerTest;

/**
 * The ModelTestSuite class is a JUnit test suite that aggregates
 * multiple test classes for the model components of the Three Trios
 * card game. This suite allows for running all model-related tests
 * together in a single execution.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ThreeTriosCellTest.class,
        ThreeTriosHandTest.class,
        ThreeTriosGridTest.class,
        ThreeTriosGridManagerTest.class,
        ThreeTriosModelTest.class,
})
public class ModelTestSuite {
}