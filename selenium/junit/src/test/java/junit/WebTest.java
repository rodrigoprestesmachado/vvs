/**
 * VVS by Rodrigo Prestes Machado
 * VVS is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
 *
*/

package junit;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Exemplo Selenium/Chrome Driver com Junit.
 */
public class WebTest {
    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        //Rodar no Linux: Usar os arguments no-sandbox
        //options.addArguments("--no-sandbox"); // Bypass OS security model
        //options.addArguments("- disable-dev-shm-usage"); // overcome limited resource problems

        WebTest.driver = new ChromeDriver(options);
    }

    @AfterAll
    public static void tearDown() {

        if (WebTest.driver != null) {

            WebTest.driver.quit();
            WebTest.driver = null;
        }

    }

    @Test
    @SuppressWarnings("checkstyle:magicnumber")
    public void web() {

        WebTest.driver.get("https://ifrs.edu.br/");
        WebTest.driver.findElement(By.xpath("//li[@id='menu-item-844']/a")).click();

        List<WebElement> elements = WebTest.driver.findElements(By.cssSelector(".editais__title"));
        assert elements.size() > 0;
    }
}
