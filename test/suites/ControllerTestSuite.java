package suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import controller.ControllerToModelTest;
import controller.ControllerToPlayerTest;
import controller.ControllerToViewTest;
import controller.GameConfigParserTest;
import controller.IntegrationTest;
import controller.ModelToControllerTest;
import controller.PlayerToControllerTest;

/**
 * The ControllerTestSuite class is a JUnit test suite that aggregates
 * test for the controller for the ThreeTriosGame. This suite allows for
 * running all tests together in one single execution.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ControllerToPlayerTest.class,
        PlayerToControllerTest.class,
        ControllerToViewTest.class,
        GameConfigParserTest.class,
        IntegrationTest.class,
        ControllerToModelTest.class,
        ModelToControllerTest.class
})
public class ControllerTestSuite {
}
