import { PaginationContext } from './pagination-context.class';
import { RejectionJoin } from 'src/app/interfaces/rejections-join.interface';
import { PageResponse } from 'src/app/interfaces/page-response.interface';
import { RejectionsJoinService } from '../../providers/http/rejections-join.service';
import { Injectable } from '@angular/core';
import { SharedModule } from '../shared.module';
import { GenericHttpRequestService } from 'src/app/providers/http/generic.service';

@Injectable({
  providedIn: SharedModule
})
export class RejectionsJoinContext extends PaginationContext<RejectionJoin> {
  public pageSize = 10;

  constructor(public rejectionsJoinService: RejectionsJoinService) {
    super();
  }

  service(): GenericHttpRequestService {
    return this.rejectionsJoinService;
  }

  pageChangeSubscription(pageNumber: number, queryData: any): void {
    this.search(pageNumber, queryData);
  }

  private search(pageNumber: number, queryData: any): void {
    this.rejectionsJoinService.queryRejected(pageNumber, this.pageSize, queryData).subscribe((data: PageResponse<RejectionJoin>) => {
      this.subject$.next(data);
    });
  }

}
