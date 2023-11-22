import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Location } from './../../utils/Location';


@Component({
  selector: 'app-add-location',
  templateUrl: './add-location.component.html',
  styleUrl: './add-location.component.css'
})
export class AddLocationComponent implements OnInit {
  @Output() onAddLocation: EventEmitter<Location> = new EventEmitter();
  location: string = '';
  clearingCost: string = '';

  constructor() {}

  ngOnInit(): void {}

  onSubmit() {
    console.log("Submitting...");
    if (!this.location || !this.clearingCost) {
      alert('Please add missing location or clearing cost');
    }

    const newLocation = {
      name: this.location,
      clearingCost: parseInt(this.clearingCost)
    }

     //propagate signal to parent component
    this.onAddLocation.emit(newLocation)
        
    // reset the form
    this.location = '';
    this.clearingCost = '';    
  }
}
