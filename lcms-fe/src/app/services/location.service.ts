import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders } from '@angular/common/http';
import {Observable} from 'rxjs';
import { Location } from '../utils/Location';



const httpHeaderOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  }),
};

@Injectable({
  providedIn: 'root'
})
export class LocationService {
  private apiUrl = 'http://localhost:8080/api/v1/locations';

  constructor(private httpClient:HttpClient) { }

  //using publish-subscribe kind of model
  getLocations(): Observable<Location[]> {
    return this.httpClient.get<Location[]>(this.apiUrl);
  }

  deleteLocation(location: Location): Observable<Location> {
    const workingUrl = `${this.apiUrl}/${location.id}`;
    return this.httpClient.delete<Location>(workingUrl);
  }

  addLocation(location: Location): Observable<Location> {
    return this.httpClient.post<Location>(this.apiUrl, location, httpHeaderOptions);
  }

  
}
