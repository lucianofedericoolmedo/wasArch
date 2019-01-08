import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { PaginatedTableComponent } from 'src/app/shared/paginated-table/paginated-table.component';
import { PaginationContext } from 'src/app/shared/pagination-contexts/pagination-context.class';
import { Observable } from 'rxjs';
import { ConciliationService } from 'src/app/providers/http/conciliation.service';
import { DatePipe } from '@angular/common';
import { isEmpty } from 'rxjs/operators';

@Component({
  selector: 'app-generic-conciliation-table',
  templateUrl: './generic-conciliation-table.component.html',
  styleUrls: ['./generic-conciliation-table.component.scss']
})
export class GenericConciliationTableComponent implements OnInit {
  public format: string;
  form: FormGroup;

  tiposSaldos: string[];
  diferencias: string[];
  fechas: string[];
  contenidos: string[];
  entidades: string[];

  constructor(
    private formBuilder: FormBuilder,
    private conciliationService: ConciliationService,
    private datePipe: DatePipe
  ) { }

  @Input() context: PaginationContext<any>;
  @Input() filteringMethod: string;
  @Input() filteringFields: {
    tipoSaldo: boolean,
    diferencias: boolean,
    fecha: boolean,
    contenido: boolean,
    codigoCuentaContable: boolean,
    cuentaContable: boolean,
    centroContable: boolean,
    entidades: boolean
  };
  @Input() tableHeaders: any[];
  @Input() tableData: {displayData?: any[], inputData?: any[]};
  @Input() filtersCssClasses: any;
  @Input() fileDownload: string;
  @Input() isEmpty: boolean;

  @ViewChild(PaginatedTableComponent)
  paginatedTable: PaginatedTableComponent;

  ngOnInit() {
    this.buildForm();
    this.format = 'dd/MM/yyyy';
    this.fetchFilteringValues();
  }

  buildForm() {
    this.form = this.formBuilder.group({});
    Object.keys(this.filteringFields).forEach(field => {
      if (this.filteringFields[field]) {
        this.form.addControl(field, new FormControl('', []));
      }
    });
  }

  search() {
    const queryObject = { ...this.form.value };
    this.paginatedTable.notifyCurrentPage(queryObject);
  }

  fetchFilteringValues() {
    const filteringMethod: () => Observable<any> = this.conciliationService[this.filteringMethod].bind(this.conciliationService);
    filteringMethod()
      .subscribe((data: any) => {
        this.tiposSaldos = data.tiposSaldos;
        this.diferencias = data.diferencias;
        this.fechas      = data.fechas;
        this.contenidos  = data.contenidos;
        this.entidades   = data.entidades;
      });
  }

  getRestCallFunction() {
    return () => this.conciliationService.downloadConciliacion(this.fileDownload, this.form.value);
  }

  getApplicationProductSourceFileName() {
    const formattedDate = this.datePipe.transform(new Date(), this.format);
    return `${this.fileDownload}_${formattedDate}.xlsx`;
  }

  getDownloadedFileType() {
    return 'application/vnd.ms-excel';
  }
  
}
