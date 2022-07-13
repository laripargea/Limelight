import { Injectable, PipeTransform } from '@angular/core';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { Play } from '../play';
import { DecimalPipe } from '@angular/common';
import { debounceTime, delay, switchMap, tap } from 'rxjs/operators';
import { SortColumn, SortDirection } from './sortable.directive';
import { HttpClient } from '@angular/common/http';

interface SearchResult {
  plays: Play[];
  total: number;
}

interface State {
  page: number;
  pageSize: number;
  searchTerm: string;
  sortColumn: SortColumn;
  sortDirection: SortDirection;
}

const compare = (v1: string | number, v2: string | number) => v1 < v2 ? -1 : v1 > v2 ? 1 : 0;

function sort(plays: Play[], column: SortColumn, direction: string): Play[] {
  if (direction === '' || column === '') {
    return plays;
  } else {
    return [...plays].sort((a, b) => {
      const res = compare(a[column], b[column]);
      return direction === 'asc' ? res : -res;
    });
  }
}

function matches(play: Play, term: string, pipe: PipeTransform) {
  return play.title.toLowerCase().includes(term.toLowerCase())
    || play.genre.toLowerCase().includes(term.toLowerCase())
    || play.description.toLowerCase().includes(term.toLowerCase());
}

@Injectable({
  providedIn: 'root'
})
export class FavouritesService {
  email: string;
  PLAYS = {} as Play[];
  private _loading$ = new BehaviorSubject<boolean>(true);
  private _search$ = new Subject<void>();
  private _plays$ = new BehaviorSubject<Play[]>([]);
  private _total$ = new BehaviorSubject<number>(0);

  private _state: State = {
    page: 1,
    pageSize: 4,
    searchTerm: '',
    sortColumn: '',
    sortDirection: ''
  };

  constructor(private pipe: DecimalPipe, private _http:HttpClient) {
    this._search$.pipe(
      tap(() => this._loading$.next(true)),
      debounceTime(200),
      switchMap(() => this._search()),
      delay(200),
      tap(() => this._loading$.next(false))
    ).subscribe(result => {
      this._plays$.next(result.plays);
      this._total$.next(result.total);
    });
    this._search$.next();
    this.email = localStorage.getItem("User")!;
    this.getFavourites(this.email).subscribe((data) => this.PLAYS = data);
  }

  get plays$() { return this._plays$.asObservable(); }
  get total$() { return this._total$.asObservable(); }
  get loading$() { return this._loading$.asObservable(); }
  get page() { return this._state.page; }
  get pageSize() { return this._state.pageSize; }
  get searchTerm() { return this._state.searchTerm; }

  set page(page: number) { this._set({page}); }
  set pageSize(pageSize: number) { this._set({pageSize}); }
  set searchTerm(searchTerm: string) { this._set({searchTerm}); }
  set sortColumn(sortColumn: SortColumn) { this._set({sortColumn}); }
  set sortDirection(sortDirection: SortDirection) { this._set({sortDirection}); }

  private _set(patch: Partial<State>) {
    Object.assign(this._state, patch);
    this._search$.next();
  }

  private _search(): Observable<SearchResult> {
    const {sortColumn, sortDirection, pageSize, page, searchTerm} = this._state;

    // 1. sort
    let plays = sort(this.PLAYS, sortColumn, sortDirection);

    // 2. filter
    plays = plays.filter(play => matches(play, searchTerm, this.pipe));
    const total = plays.length;

    // 3. paginate
    plays = plays.slice((page - 1) * pageSize, (page - 1) * pageSize + pageSize);
    return of({plays, total});
  }

  getFavourites(email: string):Observable<Play[]> {
     return this._http.get<Play[]>("http://localhost:4201/users/favourites/" + email);
  }
}
