package com.geobyte.lcmsbe.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.geobyte.lcmsbe.entity.Location;
import com.geobyte.lcmsbe.entity.Route;
import com.geobyte.lcmsbe.service.LocationService;
import com.geobyte.lcmsbe.service.RouteService;



@Component
public class AnalyticsMachine {
	private static final int minRequiredLocationCount = 3;
	private static final int deliveryCostPerPackagePerKm = 1; //i.e., $1.00
	
	private RouteService routeService;
	private LocationService locationService;
	
	@Autowired
	public AnalyticsMachine(RouteService routeService, LocationService locationService) {
		this.routeService = routeService;
		this.locationService = locationService;
	}

	public Route getBestRoute(String origin, String destination) {
		// fill-up the "totalDeliveryCost" field in Route table
		computeTotalDeliveryCostAndUpdateRoutesTable();
		 
		Route bestRoute = routeService.getBestRoute(origin, destination);
			
		return bestRoute;
	}

	public Route getMostExpensiveRoute(String origin, String destination) {
		return routeService.getMostExpensiveRoute(origin, destination);
	}

	public BigDecimal getTotalDeliveryCostForBestRoute(String origin, String destination) {
		
		return null;
	}

	public BigDecimal getHighestDeliveryCostForMostExpensiveRoute(String origin, String destination) {
		
		return null;
	}

	/**
	 * Route table is ready when value for column "totalDeliveryCost"
	 * if found in at least three rows
	 * Of course, DB administrator must have setup (ahead of time) the Route using the provided sql script
	 */
	public boolean isRouteTableReady() {
		boolean routeTableReady = false; 
		
		List<Route> routesWithDeliveryCost = this.routeService.findAllRoutesWithDeliveryCost();
		
		System.out.println("routesWithDeliveryCost.size(): " + routesWithDeliveryCost.size());
		//System.out.println("routesWithDeliveryCost: " + routesWithDeliveryCost);
		
		if (routesWithDeliveryCost.size() >= 3) {
			routeTableReady = true;
		}
		
		System.out.println("routeTableReady: " + routeTableReady);
		
		return routeTableReady;
	}
	
	public void computeTotalDeliveryCostAndUpdateRoutesTable() {
		List<Route> updatedRoutes = new ArrayList<>();
		
		//System.out.println("locationService.getCountOfLocationsInTable(): " + locationService.getCountOfLocationById());
		
		if (locationService.getCountOfLocationById() >= minRequiredLocationCount) {
			List<Route> routes = routeService.findAll();
			List<Location> locations = locationService.findAll();
			
			//System.out.println("routes: " + routes.size());
			System.out.println("locations: " + locations);
			
			//cycle through the return routes and locations and perfoorm the computation
			for (Route route : routes) {
				BigDecimal totalClearingCost = new BigDecimal("0.0").setScale(2, RoundingMode.CEILING);
				
				System.out.println("----routeNam: " + route.getRouteName()+ "--------------------");
				for(Location location : locations) {
					BigDecimal clearingCost = new BigDecimal("0.0").setScale(2, RoundingMode.CEILING);
					String routeName = route.getRouteName();
					String locationName = location.getName();
					
					System.out.println("LocationName: " + locationName);
					// check if location name is in the route string (e.g., location name "A", in route string "ABC", answer is "true" in this case)
					if(routeName.contains(locationName)) {
						//check if location name is at beginning of routeName, if yes, ignore the clearingCost
						if (!routeName.startsWith(locationName)) {
							//get the clearing costs of each location and sum them
							clearingCost = locationService.getClearingCostById(location.getId());
							System.out.println(route.getRouteName() + " - clearingCost: " + clearingCost);
						}
					}
					
					//compute total clearing cost incrementally
					totalClearingCost = totalClearingCost.add(clearingCost);
					System.out.println(route.getRouteName() + " - totalClearingCost: " + totalClearingCost);
				}
				
				//get distance of the present route and compute total distance cost
				double distance = routeService.getDistanceById(route.getRouteId());
				double totalDistanceCost = distance * deliveryCostPerPackagePerKm;
				BigDecimal totalDistanceCostInBD = new BigDecimal(totalDistanceCost).setScale(2, RoundingMode.CEILING);
				System.out.println("totalDistanceCostInBD: " + totalDistanceCostInBD);
				
				//compute total delivery cost for present route (i.e., totalDistanceCostInBD + totalClearingCost)
				BigDecimal totalDeliveryCost = totalClearingCost.add(totalDistanceCostInBD).setScale(2); 
				
				//System.out.println("totalDeliveryCost: " + totalDeliveryCost);
				//write totalDeliveryCost for present route to its "totalDeliveryCost" column in routes table
				route.setTotalDeliveryCost(totalDeliveryCost);
				Route updatedRoute = writeTotalDeliveryCostToRoutesTable(route); // may need this object shortly
			}	
		}
	}
	
	private Route writeTotalDeliveryCostToRoutesTable(Route route) {
		Route updatedRoute = routeService.update(route);
		
		return updatedRoute;
	}
	
	public List<Location> getSequenceOfLocationsOnBestRoute(String routeName) {
		//break route into individual location and get each location
		String[] locationNames = routeName.split("-");
		
		List<Location> locations = new ArrayList<>();
		for(String locationName : locationNames) {
			Location location = locationService.getLocationByName(locationName);
			locations.add(location);
		}
		
		System.out.println("LocationsInSequence: " + locations);
		
		return locations;
	}
}
