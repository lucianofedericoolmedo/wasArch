import { Component, OnInit } from '@angular/core';
import { ConciliationMonthlyWithCenterContext } from 'src/app/shared/pagination-contexts/conciliation-monthly-with-center-context.class';

@Component({
  selector: 'app-conciliation-monthly-with-center',
  templateUrl: './conciliation-monthly-with-center.component.html',
  styleUrls: ['./conciliation-monthly-with-center.component.scss']
})
export class ConciliationMonthlyWithCenterComponent implements OnInit {
  tableHeaders: any[];
  tableData: {displayData?: any[], inputData?: any[]};
  format: string;
  filteringFields: {
    tipoSaldo: boolean,
    diferencias: boolean,
    fecha: boolean,
    contenido: boolean,
    codigoCuentaContable: boolean,
    cuentaContable: boolean,
    centroContable: boolean,
    entidades: boolean
  };
  filterCssClasses: { fecha: string[]; contenido: string[]; cuentaContable: string[]; centroContable: string[]; };

  constructor(
    private conciliationMonthlyWithCenterContext: ConciliationMonthlyWithCenterContext
  ) { }

  ngOnInit() {
    this.format = 'dd/MM/yyyy';

    this.tableHeaders = [
      {value: 'Fecha'},
      {value: 'Código Divisa'},
      {value: 'Código Contenido'},
      {value: 'Entidad'},
      {value: 'Código Cta. Contable'},
      {value: 'Cta. Contable'},
      {value: 'Saldo Contable'},
      {value: 'Saldo Operacional'},
      {value: 'Imp Desv MIS'},
      {value: 'Imp Desv COC'}
    ];

    this.tableData = {
      displayData: [
        {fieldName: 'fecha', format: this.format},
        {fieldName: 'codigoDivisa'},
        {fieldName: 'codigoContenido'},
        {fieldName: 'entidad'},
        {fieldName: 'codigoCuentaContable'},
        {fieldName: 'descripcionCuentaContable'},
        {fieldName: 'saldoContable'},
        {fieldName: 'saldoOperacional'},
        {fieldName: 'importeDesviacionMis'},
        {fieldName: 'importeDesviacionCoc'}
      ]
    };

    this.filteringFields = {
      tipoSaldo: true,
      diferencias: true,
      fecha: true,
      contenido: true,
      codigoCuentaContable: false,
      cuentaContable: true,
      centroContable: true,
      entidades: true
    };

    this.filterCssClasses = {
      fecha: ['col-md-6 col-lg-3'],
      contenido: ['col-md-6 col-lg-3'],
      cuentaContable: ['col-md-6 col-lg-3'],
      centroContable: ['col-md-6 col-lg-3']
    };
  }

  getContext() {
    return this.conciliationMonthlyWithCenterContext;
  }

  getServiceFilteringMethodName() {
    return 'monthlyWithCenterFilteringData';
  }

  getFileDownloadName() {
    return 'mensual-con-centro';
  }

  setAsEmpty(){
    this.getContext().isEmpty = true;
  }

}
