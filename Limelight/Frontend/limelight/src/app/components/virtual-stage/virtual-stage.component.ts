import { Component, OnInit, Inject, OnDestroy } from '@angular/core';
import { User } from '../../user';
import { UserService } from '../../services/user.service';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { ToastServiceService } from '../../services/toast-service.service';

@Component({
  selector: 'app-virtual-stage',
  templateUrl: './virtual-stage.component.html',
  styleUrls: ['./virtual-stage.component.scss']
})
export class VirtualStageComponent implements OnInit, OnDestroy {
  user: User;
  closeResult = '';
  email: string;
  roleUser: boolean;

  constructor(private modalService: NgbModal, @Inject(UserService) private _userService:UserService, @Inject(ToastServiceService) public toastServiceService: ToastServiceService) { }

  ngOnInit(): void {
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
