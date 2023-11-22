import { Component, OnInit } from '@angular/core';
import { LocationService } from '../../services/location.service';
import { Location } from '../../utils/Location';

@Component({
  selector: 'app-location',
  templateUrl: './locations.component.html',
  styleUrl: './locations.component.css'
})
export class LocationComponent implements OnInit {
  locations: Location[] = [];


  constructor(private locationService: LocationService) {}
  
  ngOnInit(): void {
    this.locationService.getLocations().subscribe((locations) => {
      this.locations = locations;
    });
  }

  deleteLocation(location: Location) {
    this.locationService.deleteLocation(location).subscribe(
      () => {() => (this.locations = this.locations.filter((loc) => loc.id !== location.id));
    });
  }

  addLocation(location: Location) {
    this.locationService
      .addLocation(location)
      .subscribe((location) => this.locations.push(location));
    
      console.log('Adding new location');
  }

}
