import { Component, OnInit, Inject, OnDestroy } from '@angular/core';
import { Play } from '../../play';
import { User } from '../../user';
import { Ticket } from '../../ticket'
import { Timetable } from '../../timetable';
import { PlayService } from '../../services/play.service';
import { UserService } from '../../services/user.service';
import { ToastServiceService } from '../../services/toast-service.service';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit, OnDestroy {
  plays: Play[];
  closeResult = '';
  email: string;
  user: User;
  timetable: Timetable[];
  roleUser: boolean;
  newTimetable = new Timetable();

  constructor(@Inject(PlayService) private _playService:PlayService, private modalService: NgbModal, @Inject(UserService) private _userService:UserService, @Inject(ToastServiceService) public toastServiceService: ToastServiceService) { }

  ngOnInit(): void {
    this._playService.getPlays().subscribe((data: Play[]) => {
      this.plays = data;
    });
    this._playService.getTimetable().subscribe((data: Timetable[]) => {
      this.timetable = data;
    });
    this.email = localStorage.getItem("User")!;
    if (localStorage.getItem("Role") == "user")
      this.roleUser = true;
    this._userService.getUserByEmail(this.email).subscribe((data: User) => {
      this.user = data;
    });
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

  async like(title: string) {
    this._playService.addToFavourites(this.email, title).subscribe((data: any) => {
      console.log(data);
    });
    this._userService.checkFavourite(this.email, title).subscribe((data: any) => {
      if (data) {
        this.showStandard(title + ' is already in your list of favourite plays.');
      }
      else {
        this.showStandard(title + ' was added successfully to your list of favourite plays.');
      }
    });
    await new Promise(resolve => setTimeout(resolve, 5000));
    window.location.reload();
  }

  book(idTimetable: number) {
    var ticket = {} as Ticket;
    this._playService.bookTicket(ticket, this.email, idTimetable).subscribe((data: any) => {
      console.log(data);
    });
    this.showStandard('Ticket booked successfully. Check your email.');
  }

  logout() {
    localStorage.clear();
  }

  changePass() {
    let password = (<HTMLInputElement>document.getElementById("idPass")!).value;
    this._userService.updatePassword(this.user.email, password).subscribe((data: User) => {
      this.user = data;
    });
    window.location.reload();
  }

  showStandard(text: string) {
    this.toastServiceService.show(text);
  }

  ngOnDestroy(): void {
    this.toastServiceService.clear();
  }

  isDisabled(): boolean {
    if (this.roleUser) {
      return false;
    } else {
      return true;
    }
  }

  addTicket() {
    let titleOfPlay = (<HTMLInputElement>document.getElementById("selectPlay")!).value;
    let dateOfPlay = (<HTMLInputElement>document.getElementById("dateOfBirth")!).value;
    let timeOfPlay = (<HTMLInputElement>document.getElementById("time")!).value;
    this._userService.addTimetable(titleOfPlay, dateOfPlay, timeOfPlay).subscribe(data => {
      console.log(data);
    });
    window.location.reload();
  }
}
