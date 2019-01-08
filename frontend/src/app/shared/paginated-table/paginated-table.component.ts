import { Component, OnInit, Input } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { PageResponse } from '../../interfaces/page-response.interface';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-paginated-table',
  templateUrl: './paginated-table.component.html',
  styleUrls: ['./paginated-table.component.scss']
})
export class PaginatedTableComponent implements OnInit {
  page = 1;
  data: any;
  collectionSize: number;
  dataNotification$: Observable<any>;
  pageChanging$: Subject<{pageNumber: number, queryData: any}>;
  paginationContext: any;
  paginatedForm: FormGroup;
  fieldsNames: string[];

  constructor(private formBuilder: FormBuilder) {
    this.pageChanging$ = new Subject<{pageNumber: number, queryData: any}>();

    this.paginatedForm = this.formBuilder.group({
      formRow: this.formBuilder.array([])
    });
  }

  @Input() context: any;
  @Input() columns: any[];
  @Input() fields: {displayData?: any[], inputData?: any};
  @Input() query: any;

  ngOnInit() {
    this.dataNotification$ = this.context.getObservable();
    this.dataNotification$.subscribe((data: PageResponse<any>) => {
      this.data = data.items;
      this.collectionSize = data.totalSize;

      if (!this.data || this.data.length === 0) {
        this.page = 1;
      }

      if (this.fields.inputData) {
        this.fieldsNames = Object.keys(this.fields.inputData);
        this.paginatedForm = this.formBuilder.group({
          formRow: this.formBuilder.array(
            this.data.map(elementInData => {
              const groupObject = this.makeFormGroupObject(elementInData);
              return this.formBuilder.group(groupObject);
            })
          )
        });
      }
    });

    this.context.setPageChangingObservable(this.pageChanging$.asObservable());
    if(!this.context.isEmpty){
      this.onPageChange(this.page);
    }
    
  }

  onPageChange(pageNumberSelected) {
    this.page = pageNumberSelected;
    this.notifyCurrentPage();
  }

  notifyCurrentPage(queryData?: any) {
    this.query = queryData == null ? this.query : queryData;
    const queryDataToSend = queryData ? queryData : this.query;
    this.pageChanging$.next({pageNumber: this.page, queryData: queryDataToSend});
  }

  makeFormGroupObject(dataElement: any) {
    const makingFormGroupObject = {};

    Object.keys(this.fields.inputData).forEach(fieldName => {
      const validation = this.fields.inputData[fieldName].validations || [];
      makingFormGroupObject[fieldName] = [dataElement[fieldName], validation];
    });

    return makingFormGroupObject;
  }

  isValidForm() {
    return this.paginatedForm.valid;
  }

  getValues() {
    return this.paginatedForm.value.formRow;
  }

}
