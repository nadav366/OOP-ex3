import org.junit.runner.RunWith;
import org.junit.runners.Suite;

// Define which test to run
@RunWith(Suite.class)
@Suite.SuiteClasses({
        LongTermTest.class,
        LockerTest.class
})

/**
 * A class that ran the all tests of  SpaceshipDepository software.
 */
public class SpaceshipDepositoryTest {
}
