import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProcessesLogService } from 'src/app/providers/http/processes-log.service';
import { OdsProcessesContext } from 'src/app/shared/pagination-contexts/ods-processes-context.class';
import { DataSharing } from 'src/app/shared/data-sharing.service';
import { PaginatedTableComponent } from '../../../shared/paginated-table/paginated-table.component';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-ods-processes',
  templateUrl: './ods-processes.component.html',
  styleUrls: ['./ods-processes.component.scss']
})
export class OdsProcessesComponent implements OnInit {
  filters: { fechas: any[]; contenidos: any[]; periodicidad: any[]; };
  form: FormGroup;
  format: any;
  tableColumns: any[];
  tableFields: {displayData?: any[], inputData?: any[]};
  queryObject: any;

  constructor(
    private formBuilder: FormBuilder,
    private processesLogService: ProcessesLogService,
    private datePipe: DatePipe,
    private odsProcessesContext: OdsProcessesContext
  ) { }

  @ViewChild(PaginatedTableComponent)
  paginatedTable: PaginatedTableComponent;

  ngOnInit() {
    this.format = 'dd/MM/yyyy';
    this.buildForm();
    this.filters = {
      fechas: [],
      contenidos: [],
      periodicidad: []
    };
    this.getFilteringValues();
    this.tableColumns = [
      {value: 'Fec Data'},
      {value: 'Código Contenido'},
      {value: 'Contenido', style: {minWidth: '225px'}},
      {value: 'Periodicidad'},
      {value: 'Código Estado Extracción'},
      {value: 'Estado Extracción', style: {minWidth: '208px'}},
      {value: 'Indicador Extracción Finalizada'},
      {value: 'Fecha Inicio Extracción', style: {minWidth: '190px'}},
      {value: 'Fecha Fin Extracción', style: {minWidth: '190px'}},
      {value: 'Fecha Fin Extracción Anterior', style: {minWidth: '190px'}},
      {value: 'Indicador Extracción Manual'},
      {value: 'Inicio', style: {minWidth: '178px'}},
      {value: 'Fin', style: {minWidth: '178px'}},
      {value: 'Duración (Min)'},
      {value: 'Código País'}
    ];
    this.tableFields = {
      displayData: [
        {fieldName:   'fecha'},
        {fieldName:   'codigoContenido'},
        {fieldName:   'descripcionContenido'},
        {fieldName:   'codigoPeriodicidad'},
        {fieldName:   'codigoEstadoExtraccion'},
        {fieldName:   'descripcionEstadoExtraccion'},
        {fieldName:   'indExtraccionFinalizada'},
        {fieldName:   'fechaInicioExtraccion'},
        {fieldName:   'fechaFinExtraccion'},
        {fieldName:   'fechaFinExtraccionAnterior'},
        {fieldName:   'indExtraccionManual'},
        {fieldName:   'inicio'},
        {fieldName:   'fin'},
        {fieldName:   'duracion'},
        {fieldName:   'codigoPais'}
      ]
    };
    this.queryObject = { };
  }

  buildForm() {
    this.form = this.formBuilder.group({
      fecha:        ['', []],
      contenido:    ['', []],
      periodicidad: ['', []]
    });
  }

  getPaginationContext() {
    return this.odsProcessesContext;
  }

  search() {
    const queryObject = { ...this.queryObject, ...this.form.value };
    this.paginatedTable.notifyCurrentPage(queryObject);
  }

  getFilteringValues() {
    this.processesLogService.getProcesosOdsFiltros()
      .subscribe((data: any) =>
        this.filters = {
          fechas: data.fechas,
          contenidos: data.contenidos,
          periodicidad: data.periodicidad
        }
      );
  }

  getRestCallFunction() {
    return this.processesLogService.getOdsProcessExcelReport.bind(this.processesLogService);
  }

  getFileName() {
    const formattedDate = this.datePipe.transform(new Date(), this.format);
    return `procesos-ods_${ formattedDate }.xlsx`;
  }

  getDownloadedFileType() {
    return 'application/vnd.ms-excel';
  }

}
