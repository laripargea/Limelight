import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Play } from '../play';
import { Ticket } from '../ticket'
import { Timetable } from '../timetable'
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PlayService {

  constructor(private _http:HttpClient) { }

  getPlays():Observable<Play[]> {
    return this._http.get<Play[]>("http://localhost:4201/plays");
  }

  getActors(title : string):Observable<any> {
    return this._http.get<any>("http://localhost:4201/plays/actors/" + title);
  }

  getRecommendations(email: string):Observable<Play[]> {
     return this._http.get<Play[]>("http://localhost:4201/users/recommendations/" + email);
  }

  getFavourites(email: string):Observable<Play[]> {
     return this._http.get<Play[]>("http://localhost:4201/users/favourites/" + email);
  }

  getTimetable():Observable<Timetable[]> {
     return this._http.get<Timetable[]>("http://localhost:4201/timetable");
  }

  addToFavourites(email: string, title: string):Observable<Play[]> {
     return this._http.get<Play[]>("http://localhost:4201/users/favourite/" + email + "/" + title);
  }

  bookTicket(ticket: Ticket, email: string, idTimetable: number):Observable<Ticket> {
    return this._http.post<Ticket>("http://localhost:4201/tickets/add/" + email + "/" + idTimetable, ticket);
  }

  deleteFavourite(email: string, title: string):Observable<Play[]> {
     return this._http.get<Play[]>("http://localhost:4201/users/favourite/delete/" + email + "/" + title);
  }
}
