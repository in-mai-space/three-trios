package suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import strategy.CornerInfallibleStrategyTest;
import strategy.FlipCardsInfallibleStrategyTest;
import strategy.UpperLeftInfallibleStrategyTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UpperLeftInfallibleStrategyTest.class,
        FlipCardsInfallibleStrategyTest.class,
        CornerInfallibleStrategyTest.class
})
public class StrategyTestSuite {
}