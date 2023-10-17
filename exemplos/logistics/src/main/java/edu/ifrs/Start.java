/**
 * VVS by Rodrigo Prestes Machado
 *
 * VVS is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/
package edu.ifrs;

import java.util.ArrayList;
import java.util.List;

import edu.ifrs.business.Load;
import edu.ifrs.business.Vehicle;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/logistics")
public class Start {

    /** List of vehicles. */
    private List<Vehicle> vehicles = new ArrayList();

    /**
     * Inicializes the application with some vehicles and loads.
     */
    public Start() {
        Vehicle vehicle1 = new Vehicle(200);
        Load load1 = new Load(50);
        Load load2 = new Load(50);
        vehicle1.addWeight(load1);
        vehicle1.addWeight(load2);
        this.vehicles.add(vehicle1);

        Vehicle vehicle2 = new Vehicle(400);
        Load load3 = new Load(150);
        Load load4 = new Load(200);
        vehicle2.addWeight(load3);
        vehicle2.addWeight(load4);
        this.vehicles.add(vehicle2);
    }

    /**
     * Gets a vehicle.
     * @param index index of the vehicle
     * @return the vehicle
     */
    @GET
    @Path("/getVehicle/{index}")
    @Produces(MediaType.APPLICATION_JSON)
    public Vehicle getVehicle(@PathParam("index") int index) {
        return this.vehicles.get(index);
    }

    /**
     * Gets a list of vehicles.
     *
     * @return the list of vehicles
     */
    @GET
    @Path("/getVehicles")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vehicle> getVehicles() {
        return this.vehicles;
    }

}
