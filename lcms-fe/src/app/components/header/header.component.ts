import { Component,   OnInit } from '@angular/core';
import { UiService } from '../../services/ui.service';
//import { Subscription } from 'rxjs';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {
  title = 'Geobyte Logistics and Courier Management System';
  // showAddLocation: boolean = false;
  // sub: Subscription;

  constructor(private uiService: UiService) {
    //this.sub = Subscription.EMPTY;
  }

  ngOnInit(): void {}

  // addLocation() {
  //   this.uiService.toggleAddLocation;
  //   console.log('Location added...');
  // }
}
