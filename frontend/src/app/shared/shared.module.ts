import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PaginatedTableComponent } from './paginated-table/paginated-table.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ReactiveFormsModule } from '@angular/forms';
import { NotificationModalComponent } from './notification-modal/notification-modal.component';
import { FileDownloaderComponent } from './file-downloader/file-downloader.component';
import { TooltipModule, AlertModule } from 'ngx-bootstrap';

@NgModule({
  imports: [
    CommonModule, NgbModule, ReactiveFormsModule, TooltipModule.forRoot(), AlertModule.forRoot()
  ],
  declarations: [PaginatedTableComponent, NotificationModalComponent, FileDownloaderComponent],
  exports: [PaginatedTableComponent, FileDownloaderComponent],
  entryComponents: [
    NotificationModalComponent
  ]
})
export class SharedModule { }
