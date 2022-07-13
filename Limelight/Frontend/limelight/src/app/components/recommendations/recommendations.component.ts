import { Component, OnInit, Inject, Input, OnDestroy } from '@angular/core';
import { Play } from '../../play';
import { User } from '../../user';
import { PlayService } from '../../services/play.service';
import { UserService } from '../../services/user.service';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { ToastServiceService } from '../../services/toast-service.service';

@Component({
  selector: 'app-recommendations',
  templateUrl: './recommendations.component.html',
  styleUrls: ['./recommendations.component.scss']
})
export class RecommendationsComponent implements OnInit, OnDestroy {
  plays: Play[];
  email: string;
  user: User;
  closeResult = '';

  constructor(@Inject(PlayService) private _playService:PlayService, private modalService: NgbModal, @Inject(UserService) private _userService:UserService, @Inject(ToastServiceService) public toastServiceService: ToastServiceService) { }

  ngOnInit(): void {
    this.email = localStorage.getItem("User")!;
    this._playService.getRecommendations(this.email).subscribe((data: Play[]) => {
      this.plays = data;
    });
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
