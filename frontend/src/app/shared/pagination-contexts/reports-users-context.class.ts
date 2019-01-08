import { PaginationContext } from './pagination-context.class';
import { ReportsUser } from 'src/app/interfaces/reports-user.interface';
import { PageResponse } from 'src/app/interfaces/page-response.interface';
import { ReportUsersService } from '../../providers/http/reports-users.service';
import { Injectable } from '@angular/core';
import { SharedModule } from '../shared.module';
import { GenericHttpRequestService } from 'src/app/providers/http/generic.service';

@Injectable({
  providedIn: SharedModule
})
export class ReportUsersContext extends PaginationContext<ReportsUser> {
  public pageSize = 10;

  constructor(public usersService: ReportUsersService) {
    super();
  }

  service(): GenericHttpRequestService {
    return this.usersService;
  }

  pageChangeSubscription(pageNumber: number, queryData: any): void {
    this.search(pageNumber, queryData);
  }

  private search(pageNumber: number, queryData: any): void {
    this.usersService.queryUsers(pageNumber, this.pageSize, queryData).subscribe((data: PageResponse<ReportsUser>) => {
      this.subject$.next(data);
    });
  }

}
