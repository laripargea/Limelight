import { Component, OnInit, TemplateRef, Inject } from '@angular/core';
import { ToastServiceService } from '../../services/toast-service.service';

@Component({
  selector: 'app-toasts',
  template: `
    <ngb-toast
      *ngFor="let toast of toastServiceService.toasts"
      [class]="toast.classname"
      [autohide]="true"
      [delay]="toast.delay || 5000"
      (hidden)="toastServiceService.remove(toast)"
      style="color:black;"
    >
      <ng-template [ngIf]="isTemplate(toast)" [ngIfElse]="text">
        <ng-template [ngTemplateOutlet]="toast.textOrTpl"></ng-template>
      </ng-template>

      <ng-template #text>{{ toast.textOrTpl }}</ng-template>
    </ngb-toast>
  `,
  host: {'class': 'toast-container position-fixed top-0 end-0 p-3', 'style': 'z-index: 1200'}
})
export class ToastsContainerComponent implements OnInit {

  constructor(@Inject(ToastServiceService) public toastServiceService: ToastServiceService) { }

  ngOnInit(): void {
  }

  isTemplate(toast) { return toast.textOrTpl instanceof TemplateRef; }
}
