import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../user';
import { Play } from '../play'
import { Observable } from 'rxjs';
import { Timetable } from '../timetable';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private _http:HttpClient) { }

  getUserByEmail(email: string):Observable<User> {
    return this._http.get<User>("http://localhost:4201/users/"+ email);
  }

  updateUser(email: string, firstName: string, lastName:string, mobileNumber: string):Observable<User> {
    return this._http.get<User>("http://localhost:4201/users/update/" + email + "/" + firstName + "/" + lastName + "/" + mobileNumber);
  }

  updatePassword(email: string, password: string):Observable<User> {
    return this._http.get<User>("http://localhost:4201/users/update/" + email + "/" + password);
  }

  checkFavourite(email: string, title: string):Observable<boolean> {
     return this._http.get<boolean>("http://localhost:4201/users/isFavourite/" + email + "/" + title);
  }

  addTimetable(title: string, datePlay: any, timePlay: any):Observable<Timetable> {
    return this._http.get<Timetable>("http://localhost:4201/timetable/addByTitle/" + title + "/" + datePlay + "/" + timePlay);
  }
}
