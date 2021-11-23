/**
 * VVS by Rodrigo Prestes Machado
 * 
 * VVS is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
 *
*/
package junit;

import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Exemplo Selenium/Chrome Driver com Junit.
 */
class WebTest {
    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        WebTest.driver = new ChromeDriver();
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    //https://stackoverflow.com/questions/38764125/sonarqube-issue-add-at-least-one-assertion-to-this-test-case-for-unit-test-wit
    @Test
    @SuppressWarnings("squid:S2699")
    void web() {
        WebTest.driver.get("https://ifrs.edu.br/");
        WebTest.driver.manage().window().setSize(new Dimension(1440, 877));
        WebTest.driver.findElement(By.linkText("Editais")).click();

        List<WebElement> elements = WebTest.driver.findElements(By.cssSelector(".editais__title"));
        assert elements.size() > 0;
    }
}
