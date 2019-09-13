

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(monochrome = true,
        features = "src/test/resources/features/",
        dryRun = false,
        glue = "gradle.cucumber")
public class RunCucumber_Test {
}
