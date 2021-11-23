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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        // System.out.println("Hello World!");
        // https://www.geeksforgeeks.org/logger-log-method-in-java-with-examples/
        Logger logger = Logger.getLogger(App.class.getName());
        logger.log(Level.INFO, "Hello World!");
    }
}
