package all;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/resources/Uses_Cases"},
        plugin = {"html:target/cucumber/wikipedia3.html"},
        monochrome = true,
        snippets = SnippetType.CAMELCASE,
        glue = {"all"}

)
public class TestRunner {


}
