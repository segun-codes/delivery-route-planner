import { Injectable } from '@angular/core';
import { Subject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UiService {
  private displayAddLocation: boolean = false;

  // utility to help propagate state change from one component to multiple components
  private subject = new Subject<any>(); 

  constructor() { }

  // invoked once we click "Add" button
  // toggleAddLocation(): void {
  //   this.displayAddLocation = !this.displayAddLocation;
  //   this.subject.next(this.displayAddLocation);
  // }

  // this method is triggered once we we toggle (i.e., once toggleAddTask is invoked)
  // the present state (clicked/not clicked) of the "Add" button is made available to other components
  // and can be accessed through subscription 
  // OnToggle(): Observable<any> {
  //   return this.subject.asObservable(); // other component can subscribe to the event now turned into an Observable one
  // }
}
