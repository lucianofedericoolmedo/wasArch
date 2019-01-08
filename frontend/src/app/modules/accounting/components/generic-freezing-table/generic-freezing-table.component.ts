import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { PaginatedTableComponent } from 'src/app/shared/paginated-table/paginated-table.component';
import { AccountingService } from 'src/app/providers/http/accounting.service';
import { Observable } from 'rxjs';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-generic-freezing-table',
  templateUrl: './generic-freezing-table.component.html',
  styleUrls: ['./generic-freezing-table.component.scss']
})
export class GenericFreezingTableComponent implements OnInit {
  tableHeaders: any[];
  tableInputs: {displayData?: any[], inputData?: any};
  queryForm: FormGroup;
  addForm: FormGroup;
  freezingDates: any[];
  public format: string;

  constructor(
    private formBuilder: FormBuilder,
    private accountingService: AccountingService,
    private datePipe: DatePipe
  ) { }

  @Input() context: any;
  @Input() filteringMethod: string;
  @Input() fileDownload: string;

  @ViewChild(PaginatedTableComponent)
  paginatedTable: PaginatedTableComponent;

  ngOnInit() {
    this.queryForm = this.formBuilder.group({
      'fechaCongelamiento': ['', []]
    });

    this.addForm = this.formBuilder.group({
      'fechaCongelamiento': ['', [Validators.required]]
    });

    this.tableHeaders = [
      {value: 'Fecha Congelamiento'},
      {value: 'Estado Ejecución'}
    ];

    this.tableInputs = {
      inputData: {
        fechaCongelamiento: {type: 'date', validations: [Validators.required]},
        estadoEjecucion:    {type: 'display'}
      }
    };

    this.getFechasCongelamientos();
  }

  search() {
    const queryObject = { ...this.queryForm.value };
    this.paginatedTable.notifyCurrentPage(queryObject);
  }

  fetchFilteringValues() {
    const filteringMethod: () => Observable<any> = this.accountingService[this.filteringMethod].bind(this.accountingService);
    filteringMethod()
      .subscribe((data: any) => {});
  }

  add() {
    console.log('add');
  }

  getFechasCongelamientos() {
    this.accountingService.getFechasCongelamiento().subscribe((data: any[]) => this.freezingDates = data);
  }

  getRestCallFunction() {
    return () => this.accountingService.download(this.fileDownload, this.queryForm.value);
  }

  getApplicationProductSourceFileName() {
    const formattedDate = this.datePipe.transform(new Date(), this.format);
    return `${this.fileDownload}_${formattedDate}.xlsx`;
  }

  getDownloadedFileType() {
    return 'application/vnd.ms-excel';
  }
  
}
