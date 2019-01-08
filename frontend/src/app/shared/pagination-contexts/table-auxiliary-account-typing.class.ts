import { PaginationContext } from './pagination-context.class';
import { Injectable } from '@angular/core';
import { SharedModule } from '../shared.module';
import { PageResponse } from '../../interfaces/page-response.interface';
import { ReportsUser } from 'src/app/interfaces/reports-user.interface';
import { TablesService } from 'src/app/providers/http/table-dimension.service';
import { GenericHttpRequestService } from 'src/app/providers/http/generic.service';

@Injectable({
  providedIn: SharedModule
})
export class TableAuxiliaryAccountTypingContext extends PaginationContext<ReportsUser> {
  public pageSize = 10;

  constructor(public tablesService: TablesService) {
    super();
  }

  service(): GenericHttpRequestService {
    return this.tablesService;
  }

  pageChangeSubscription(pageNumber: number, queryData: any): void {
    this.search(pageNumber, queryData);
  }

  private search(pageNumber: number, queryData: any): void {
    this.tablesService.queryAuxiliaryAccountTyping(pageNumber, this.pageSize, queryData).subscribe((data: PageResponse<ReportsUser>) => {
      this.subject$.next(data);
    });
  }

}
