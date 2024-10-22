package suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import model.components.card.ThreeTriosCardTest;
import model.components.enums.AttackValueTest;
import model.components.grid.ThreeTriosGridTest;
import model.components.hand.ThreeTriosHandTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ThreeTriosCardTest.class,
        AttackValueTest.class,
        ThreeTriosHandTest.class,
        ThreeTriosGridTest.class
})

public class ModelTestSuite {
}