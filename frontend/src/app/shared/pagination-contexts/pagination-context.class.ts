import { Observable, Subject } from 'rxjs';
import { PageResponse } from '../../interfaces/page-response.interface';
import { GenericHttpRequestService } from 'src/app/providers/http/generic.service';

export abstract class PaginationContext<T> {
  protected subject$: Subject<PageResponse<T>>;
  pageChanging$: Observable<{pageNumber: number, queryData: any}>;
  public isEmpty = false; //indica si la tabla inicia vacia

  constructor() {
    this.subject$ = new Subject<PageResponse<T>>();
  }

  abstract pageChangeSubscription(pageNumber: number, queryData: any): void;

  public getObservable(): Observable<PageResponse<T>> {
    return this.subject$.asObservable();
  }

  public setPageChangingObservable(pageChanging$) {
    this.pageChanging$ = pageChanging$;
    this.pageChanging$.subscribe((data: {pageNumber: number, queryData: any}) => {
      this.pageChangeSubscription(data.pageNumber, data.queryData);
    });
  }

}
