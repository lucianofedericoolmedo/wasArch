import { Injectable } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NotificationModalComponent } from './notification-modal.component';

@Injectable({
    providedIn: 'root'
})
export class NotificationModal {

    constructor(
        private modalService: NgbModal
    ) {

    }

    public open(message: string) {
        const modalRef = this.modalService.open(NotificationModalComponent, {windowClass: 'notification-modal', centered: true});
        modalRef.componentInstance.message = message;
        modalRef.componentInstance.type = 'error';
        return modalRef;
    }

    public success(message: string) {
        const modalRef = this.modalService.open(NotificationModalComponent, {windowClass: 'notification-modal', centered: true});
        modalRef.componentInstance.message = message;
        modalRef.componentInstance.type = 'success';
        return modalRef;
    }

}
