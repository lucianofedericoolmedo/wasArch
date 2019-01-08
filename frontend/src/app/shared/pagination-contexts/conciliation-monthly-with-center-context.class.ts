import { Injectable } from '@angular/core';
import { SharedModule } from '../shared.module';
import { PaginationContext } from './pagination-context.class';
import { PageResponse } from 'src/app/interfaces/page-response.interface';
import { ConciliationService } from 'src/app/providers/http/conciliation.service';
import { GenericHttpRequestService } from 'src/app/providers/http/generic.service';

@Injectable({
  providedIn: SharedModule
})
export class ConciliationMonthlyWithCenterContext extends PaginationContext<any> {

  public pageSize = 10;

  constructor(public conciliationService: ConciliationService) {
    super();
  }

  service(): GenericHttpRequestService {
    return this.conciliationService;
  }

  pageChangeSubscription(pageNumber: number, queryData: any): void {
    this.search(pageNumber, queryData);
  }

  private search(pageNumber: number, queryData: any): void {
    this.conciliationService.queryMensualConCentro(pageNumber, this.pageSize, queryData).subscribe((data: PageResponse<any>) => {
      this.subject$.next(data);
    });
  }

}
