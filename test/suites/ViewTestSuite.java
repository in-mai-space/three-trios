package suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import view.ThreeTriosTextViewTest;

/**
 * The ViewTestSuite class is a JUnit test suite that aggregates
 * multiple test classes for the view components of the Three Trios
 * card game. This suite allows for running all view-related tests
 * together in a single execution.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ThreeTriosTextViewTest.class
})
public class ViewTestSuite {
}
