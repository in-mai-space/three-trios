package suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import model.GameConfigParserTest;
import model.components.card.ThreeTriosCardTest;
import model.components.enums.AttackValueTest;
import model.components.grid.ThreeTriosGridTest;
import model.components.hand.ThreeTriosHandTest;
import model.components.manager.ThreeTriosGridManagerTest;

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