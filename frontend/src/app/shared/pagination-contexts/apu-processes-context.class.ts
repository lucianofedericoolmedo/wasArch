import { PaginationContext } from './pagination-context.class';
import { PageResponse } from 'src/app/interfaces/page-response.interface';
import { Injectable } from '@angular/core';
import { SharedModule } from '../shared.module';
import { GenericHttpRequestService } from 'src/app/providers/http/generic.service';
import { ProcessesLogService } from 'src/app/providers/http/processes-log.service';

@Injectable({
  providedIn: SharedModule
})
export class ApuProcessesContext extends PaginationContext<any> {
  public pageSize = 10;

  constructor(public processesLogService: ProcessesLogService) {
    super();
  }

  service(): GenericHttpRequestService {
    return this.processesLogService;
  }

  pageChangeSubscription(pageNumber: number, queryData: any): void {
    this.search(pageNumber, queryData);
  }

  private search(pageNumber: number, queryData: any): void {
    this.processesLogService.queryLogProcessApu(pageNumber, this.pageSize, queryData).subscribe((data: PageResponse<any>) => {
      this.subject$.next(data);
    });
  }

}
