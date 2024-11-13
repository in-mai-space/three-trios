package suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import strategy.CornerStrategyTest;
import strategy.FlipCardsStrategyTest;
import strategy.MoveTest;
import strategy.PairTest;
import strategy.UpperLeftStrategyTest;

/**
 * The StrategyTestSuite class is a JUnit test suite that aggregates
 * multiple test classes for the model components of the Three Trios
 * strategy. This suite allows for running all strategy-related tests
 * together in a single execution.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        UpperLeftStrategyTest.class,
        FlipCardsStrategyTest.class,
        CornerStrategyTest.class,
        MoveTest.class,
        PairTest.class
})
public class StrategyTestSuite {
}