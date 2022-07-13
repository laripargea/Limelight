import { Component, OnInit, Inject, QueryList, ViewChildren, OnDestroy } from '@angular/core';
import { Play } from '../../play';
import { PlayService } from '../../services/play.service';
import { FavouritesService } from '../../services/favourites.service';
import { NgbdSortableHeader, SortEvent } from '../../services/sortable.directive';
import { DecimalPipe } from '@angular/common';
import { Observable } from 'rxjs';
import { User } from '../../user';
import { UserService } from '../../services/user.service';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { ToastServiceService } from '../../services/toast-service.service';

@Component({
  selector: 'app-favourites',
  templateUrl: './favourites.component.html',
  styleUrls: ['./favourites.component.scss'],
  providers: [ PlayService, DecimalPipe ]
})
export class FavouritesComponent implements OnInit, OnDestroy {
  plays$: Observable<Play[]>;
  total$: Observable<number>;
  @ViewChildren(NgbdSortableHeader) headers: QueryList<NgbdSortableHeader>;
  public PLAYS = {} as Play[];
  email: string;
  user: User;
  closeResult = '';

  constructor(@Inject(PlayService) private _playService:PlayService, public service: FavouritesService, private modalService: NgbModal, @Inject(UserService) private _userService:UserService, @Inject(ToastServiceService) public toastServiceService: ToastServiceService) {
    this.plays$ = service.plays$;
    this.total$ = service.total$;
  }

  onSort({column, direction}: SortEvent) {
      // resetting other headers
      this.headers.forEach(header => {
        if (header.sortable !== column) {
          header.direction = '';
        }
      });
      this.service.sortColumn = column;
      this.service.sortDirection = direction;
  }

  ngOnInit(): void {
    this.email = localStorage.getItem("User")!;
    this.service.getFavourites(this.email).subscribe((data) => {
      this.PLAYS = data;
    });
    this._userService.getUserByEmail(this.email).subscribe((data: User) => {
      this.user = data;
    });
  }

  deleteFav(title: string) {
    this._playService.deleteFavourite(this.email, title).subscribe((data: any) => {
      console.log(data);
    });
    window.location.reload();
  }

  open(content) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  save() {
    let firstName = (<HTMLInputElement>document.getElementById("idFirstName")!).value;
    let lastName = (<HTMLInputElement>document.getElementById("idLastName")!).value;
    let mobileNumber = (<HTMLInputElement>document.getElementById("idMobileNumber")!).value;
    if (lastName == "")
      lastName = "empty";
    if (firstName == "")
      firstName = "empty";
    if (mobileNumber == "")
      mobileNumber = "empty";
    this._userService.updateUser(this.user.email, firstName, lastName, mobileNumber).subscribe((data: User) => {
      this.user = data;
    });
    this.showStandard('Profile updated successfully.');
  }

  changePass() {
     let password = (<HTMLInputElement>document.getElementById("idPass")!).value;
     this._userService.updatePassword(this.user.email, password).subscribe((data: User) => {
       this.user = data;
     });
     window.location.reload();
  }

  logout() {
    localStorage.clear();
  }

  showStandard(text: string) {
    this.toastServiceService.show(text);
  }

  ngOnDestroy(): void {
    this.toastServiceService.clear();
  }
}
