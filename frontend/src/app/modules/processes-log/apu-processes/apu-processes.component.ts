import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { ProcessesLogService } from 'src/app/providers/http/processes-log.service';
import { DatePipe } from '@angular/common';
import { ApuProcessesContext } from 'src/app/shared/pagination-contexts/apu-processes-context.class';
import { PaginatedTableComponent } from 'src/app/shared/paginated-table/paginated-table.component';

@Component({
  selector: 'app-apu-processes',
  templateUrl: './apu-processes.component.html',
  styleUrls: ['./apu-processes.component.scss']
})
export class ApuProcessesComponent implements OnInit {
  format: any;
  public form: FormGroup;
  tableFields: {displayData?: any[], inputData?: any[]};
  tableColumns: any[];
  queryObject: any;
  filters: { fechas: any[]; contenidos: any[]; sistemaOrigen: any[]; tabla: any[]; };

  constructor(
    private formBuilder: FormBuilder,
    private processesLogService: ProcessesLogService,
    private apuProcessesContext: ApuProcessesContext,
    private datePipe: DatePipe
  ) { }

  @ViewChild(PaginatedTableComponent)
  paginatedTable: PaginatedTableComponent;

  ngOnInit() {
    this.format = 'dd/MM/yyyy';
    this.buildForm();
    this.filters = {
      fechas: [],
      contenidos: [],
      sistemaOrigen: [],
      tabla: []
    };
    this.getFilteringValues();
    this.tableColumns = [
      {value: 'Fec Data'},
      {value: 'Tabla'},
      {value: 'Desc. Tabla', style: {minWidth: '210px'}},
      {value: 'Cód.Contenido'},
      {value: 'Contenido', style: {minWidth: '284px'}},
      {value: 'Cód. Sist. Origen'},
      {value: 'Sistema Origen', style: {minWidth: '196px'}},
      {value: 'Estado', style: {minWidth: '115px'}},
      {value: 'Inicio', style: {minWidth: '178px'}},
      {value: 'Fin', style: {minWidth: '178px'}},
      {value: 'Duracion (min)'},
      {value: 'Periodo'},
      {value: 'Cód. Proceso'}
    ];
    this.tableFields = {
      displayData: [
        {fieldName:   'fecha'},
        {fieldName:   'tabla'},
        {fieldName:   'descripcionTabla'},
        {fieldName:   'codigoContenido'},
        {fieldName:   'contenido'},
        {fieldName:   'codigoSistemaOrigen'},
        {fieldName:   'sistemaOrigen'},
        {fieldName:   'estado'},
        {fieldName:   'inicio'},
        {fieldName:   'fin'},
        {fieldName:   'duracion'},
        {fieldName:   'periodo'},
        {fieldName:   'codigoProceso'}
      ]
    };
    this.queryObject = { };
    this.search();
  }

  buildForm() {
    this.form = this.formBuilder.group({
      fecha:         ['', []],
      contenido:     ['', []],
      sistemaOrigen: ['', []],
      tabla:         ['', []]
    });
  }

  getPaginationContext() {
    return this.apuProcessesContext;
  }

  search() {
    const queryObject = { ...this.queryObject, ...this.form.value };
    this.paginatedTable.notifyCurrentPage(queryObject);
  }

  getFilteringValues() {
    this.processesLogService.getProcesosApuFiltros()
      .subscribe((data: any) =>
        this.filters = {
          fechas: data.fechas,
          contenidos: data.contenidos,
          sistemaOrigen: data.sistemasOrigen,
          tabla: data.tabla
        }
      );
  }

  getRestCallFunction() {
    return this.processesLogService.getApuProcessExcelReport.bind(this.processesLogService);
  }

  getFileName() {
    const formattedDate = this.datePipe.transform(new Date(), this.format);
    return `procesos-apu_${ formattedDate }.xlsx`;
  }

  getDownloadedFileType() {
    return 'application/vnd.ms-excel';
  }

}
