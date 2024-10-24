package suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * The AllTestsSuite class is a JUnit test suite that aggregates
 * all test classes, including Model, View and later Controller for the
 * ThreeTriosGame. This suite allows for running all tests together in
 * one single execution.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ViewTestSuite.class,
        ModelTestSuite.class
})
public class AllTestsSuite {
}
