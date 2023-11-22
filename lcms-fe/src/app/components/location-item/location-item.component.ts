import { Component, Output, OnInit, Input, EventEmitter } from '@angular/core';
import { Location } from '../../utils/Location';
import { faRemove } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-location-item',
  templateUrl: './location-item.component.html',
  styleUrl: './location-item.component.css'
})
export class LocationItemComponent implements OnInit {
  @Input() location: Location = { name: 'default', clearingCost: 0.0 };
  @Output() onDeleteLocation: EventEmitter<Location> = new EventEmitter<Location>();

  faRemove = faRemove;

  constructor() {}

  ngOnInit(): void {}

  onDelete(location: Location) {
    console.log("location deleted: ", location);
    this.onDeleteLocation.emit(location); //bubble up signal to parent component  
  }

}
