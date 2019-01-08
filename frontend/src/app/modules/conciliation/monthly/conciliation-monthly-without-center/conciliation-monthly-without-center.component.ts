import { Component, OnInit } from '@angular/core';
// tslint:disable-next-line:max-line-length
import { ConciliationMonthlyWithoutCenterContext } from 'src/app/shared/pagination-contexts/conciliation-monthly-without-center-context.class';

@Component({
  selector: 'app-conciliation-monthly-without-center',
  templateUrl: './conciliation-monthly-without-center.component.html',
  styleUrls: ['./conciliation-monthly-without-center.component.scss']
})
export class ConciliationMonthlyWithoutCenterComponent implements OnInit {
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
  filterCssClasses: { fecha: string[]; contenido: string[]; codigoCuentaContable: string[]; };

  constructor(
    private conciliationMonthlyWithoutCenterContext: ConciliationMonthlyWithoutCenterContext
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
      {value: 'Saldo Operacional'}
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
        {fieldName: 'saldoOperacional'}
      ]
    };

    this.filteringFields = {
      tipoSaldo: true,
      diferencias: true,
      fecha: true,
      contenido: true,
      codigoCuentaContable: true,
      cuentaContable: false,
      centroContable: false,
      entidades: true
    };

    this.filterCssClasses = {
      fecha: ['col-md-6 col-lg-4'],
      contenido: ['col-md-6 col-lg-4'],
      codigoCuentaContable: ['col-lg-4']
    };
  }

  getContext() {
    return this.conciliationMonthlyWithoutCenterContext;
  }

  getServiceFilteringMethodName() {
    return 'monthlyWithoutCenterFilteringData';
  }

  getFileDownloadName() {
    return 'mensual-sin-centro';
  }

  setAsEmpty(){
    this.getContext().isEmpty = true;
  }

}
