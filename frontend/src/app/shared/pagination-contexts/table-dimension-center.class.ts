import { PaginationContext } from './pagination-context.class';
import { ReportsUser } from 'src/app/interfaces/reports-user.interface';
import { PageResponse } from 'src/app/interfaces/page-response.interface';
import { Injectable } from '@angular/core';
import { SharedModule } from '../shared.module';
import { GenericHttpRequestService } from 'src/app/providers/http/generic.service';
import { TablesService } from 'src/app/providers/http/table-dimension.service';

@Injectable({
  providedIn: SharedModule
})
export class TableDimensionCenterContext extends PaginationContext<ReportsUser> {
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
    this.tablesService.queryDimensionCenter(pageNumber, this.pageSize, queryData).subscribe((data: PageResponse<ReportsUser>) => {
      this.subject$.next(data);
    });
  }

}
