import { Injectable } from '@angular/core';
import { SharedModule } from '../shared.module';
import { PaginationContext } from './pagination-context.class';
import { AccountingService } from 'src/app/providers/http/accounting.service';
import { PageResponse } from 'src/app/interfaces/page-response.interface';
import { GenericHttpRequestService } from 'src/app/providers/http/generic.service';

@Injectable({
  providedIn: SharedModule
})
export class SubsidiaryAccountingFreezeContext extends PaginationContext<any> {

  public pageSize = 10;

  constructor(public accountingService: AccountingService) {
    super();
  }

  service(): GenericHttpRequestService {
    return this.accountingService;
  }

  pageChangeSubscription(pageNumber: number, queryData: any): void {
    this.search(pageNumber, queryData);
  }

  private search(pageNumber: number, queryData: any): void {
    this.accountingService.querySubsidiary(pageNumber, this.pageSize, queryData)
    .subscribe((data: PageResponse<any>) => {
      data.items.map(item => {
        if(item.fechaCongelamiento){
          const date = new Date(item.fechaCongelamiento);
          item.fechaCongelamiento = { day: date.getUTCDay(), month: date.getUTCMonth() + 1, year: date.getUTCFullYear()};
        }
      })
      this.subject$.next(data);
    });
  }

}
