package com.geobyte.lcmsbe.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.geobyte.lcmsbe.entity.Location;
import com.geobyte.lcmsbe.entity.Route;
import com.geobyte.lcmsbe.service.LocationService;
import com.geobyte.lcmsbe.util.AnalyticsMachine;
import com.geobyte.lcmsbe.util.EntityNotFoundException;
import com.geobyte.lcmsbe.util.EntityNotUpdatedException;
import com.geobyte.lcmsbe.util.RouteAnalyticsReport;
import com.geobyte.lcmsbe.util.RouteDetails;
import com.geobyte.lcmsbe.util.RouteQuery;
import com.geobyte.lcmsbe.util.RoutesTableSetupException;
import com.geobyte.lcmsbe.util.StatusInWord;
import com.geobyte.lcmsbe.util.StatusMessage;

/*
 * NOTE: 
 * For simplicity sake, a table named "routes" is setup.
 * The table contains distance information between any two locations (origin and destination)
 * Given the constraints:
 * 	- delivery can only start if a minimum of 3 locations exist 
 *  - all deliveries between origin and destination must pass through at least 1 intermediate location before getting to the final destination
 *  - all deliveries between origin and destination can only pass through a maximum of 4 intermediate locations before getting to the final destination
 *  - no location is visited more than once while delivering a package etc
 * Therefore, the "routes" table will store data and distances of location. It also eventually stores delivery cost.
 * The data samples possible routes (location combinations) given the constraints above
 * Again,for simplicity sake, the table will contain samples of possible routes for only 6 location.
 * In sum, table "routes" is intended as a mock (and therefore made extremely simple) of a realistic 
 * location data services (e.g., google etc).
 * The route data is adequate to implement and show the operation of the core business logic of interest to Geobyte
 *    
 */

@RestController
@CrossOrigin(origins = "http://localhost:4200")
//@CrossOrigin()
@RequestMapping("/api/v1")
public class LocationRestController {
	private final static String updateErrorMessage = "Specified location not updated, perhaps it doesn't exist";
	private final static String deleteErrorMessage = "Specified location not deleted, perhaps it never existed";
	private final static String retrievalErrorMessage = "Specified location not found";
	
	//Track number of locations added; Only a maximum of 6locations can be added
	private RouteAnalyticsReport routeAnalyticReport;
	private LocationService locationService;
	private AnalyticsMachine analyticsMachine;
	
	@Autowired // auto-inject service object
	public LocationRestController(LocationService locationService, RouteAnalyticsReport routeAnalyticReport, AnalyticsMachine analyticsMachine) {
		this.locationService = locationService;
		this.routeAnalyticReport = routeAnalyticReport;
		this.analyticsMachine = analyticsMachine;
	}
		
	//Retrieve single location from db
	@GetMapping("/locations/{locationId}")
	public Location getLocation(@PathVariable int locationId) {
		Location location = locationService.findById(locationId);
		
		if (location == null) {
			throw new EntityNotFoundException(retrievalErrorMessage);
		}
		
		return location;
	}
	
	// Retrieve all locations from db
	@GetMapping("/locations")
	public List<Location> getAllLocation() {
		List<Location> locations = locationService.findAll();
		
		if (locations == null) {
			return new ArrayList<Location>();
		}
		
		return locations;
	}
	
	// Write location to db
	@PostMapping("/locations")
	public Location createLocation(@RequestBody Location location) {
		location.setId(0); // ensures JPA performs insert and not update	
		
		return locationService.save(location);
	}
	
	
	@PatchMapping("/locations")
	public StatusMessage updateLocation(@RequestBody Location location) {
		long locationId = location.getId();
		
		Location verifiedlocation = locationService.findById(locationId);
		
		if (verifiedlocation == null) {
			throw new EntityNotUpdatedException(updateErrorMessage);
		}
		
		locationService.update(location);
		String msge = "Location with id " + locationId + " updated";
		
		return new StatusMessage(msge, StatusInWord.SUCCESS);
	}
	
	// Deletes location from db
	@DeleteMapping("/locations/{locationId}")
	public StatusMessage deleteLocation(@PathVariable int locationId) {		
		Location location = locationService.findById(locationId);
		
		if (location == null) {
			throw new EntityNotFoundException(deleteErrorMessage);
		}
		
		locationService.deleteById(locationId);
		String msge = "Location with id " + locationId + " deleted";
		
		return new StatusMessage(msge, StatusInWord.SUCCESS);
	}
	
	// expects origin and destination fields in the http request body
	@PostMapping("/routes/navigation-guide")
	public RouteAnalyticsReport performRouteAnalysis(@RequestBody RouteQuery routeQuery) {
		String origin = routeQuery.getOrigin();
		String destination = routeQuery.getDestination();
		
		//compute and write "totalDeliveryCost" to "routes" table
		analyticsMachine.computeTotalDeliveryCostAndUpdateRoutesTable();
		
		System.out.println("analyticsMachine.getRouteTableStatus(): " + analyticsMachine.isRouteTableReady());
		
		if (!analyticsMachine.isRouteTableReady()) {
			throw new RoutesTableSetupException("Route table inexistent or less than 3rows have values for totalDeliveryCost");
		}
		
		//1.
		Route bestRoute = analyticsMachine.getBestRoute(origin, destination);
		String instruction = "Find the recommended navigation guide for making delivery given the origin and destination you specified";
		List<Location> locations = analyticsMachine.getSequenceOfLocationsOnBestRoute(bestRoute.getRouteName());
		RouteDetails routeDetails = new RouteDetails(bestRoute, instruction, locations);
		//2.
		BigDecimal totalDeliveryCostForBestRoute = bestRoute.getTotalDeliveryCost();
		//3.
		Route mostExpensiveRoute = analyticsMachine.getMostExpensiveRoute(origin, destination);
		//4.
		BigDecimal highestDeliveryCost = mostExpensiveRoute.getTotalDeliveryCost();
		
		routeAnalyticReport.setBestRoute(routeDetails);
		routeAnalyticReport.setMostExpensiveRoute(mostExpensiveRoute);
		routeAnalyticReport.setTotalDeliveryCostForBestRoute(totalDeliveryCostForBestRoute);
		routeAnalyticReport.setHighestDeliveryCostForMostExpensiveRoute(highestDeliveryCost);
		
		return routeAnalyticReport;
	}
}
