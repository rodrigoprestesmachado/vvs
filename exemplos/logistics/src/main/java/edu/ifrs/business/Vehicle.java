/**
 * VVS by Rodrigo Prestes Machado
 *
 * VVS is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/
package edu.ifrs.business;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a vehicle that can carry loads up to a maximum weight limit.
 */
public class Vehicle {

    /**
     * Represents the maximum weight limit of the vehicle.
     */
    private int maximumWeightLimit;

    /**
     * Represents a vehicle that can transport loads.
     */
    private List<Load> loads = new ArrayList<>();

    /**
     * Constructs a new Vehicle object with the specified weight limit.
     *
     * @param weightLimit the maximum weight limit of the vehicle
     */
    public Vehicle(int weightLimit) {
        this.maximumWeightLimit = weightLimit;
    }

    /**
     * Adds a load to the vehicle.
     *
     * @param weight the load to be added
     */
    public void addWeight(Load weight) {
        loads.add(weight);
    }

    /**
     * Checks if the total weight of the loads in the vehicle is within the
     * maximum weight limit.
     *
     * @return true if the weight limit is not exceeded, false otherwise
     */
    public boolean checkWeightLimit() {
        int total = 0;
        for (Load load : loads) {
            total += load.getWeight();
        }
        return total <= maximumWeightLimit;
    }

}
