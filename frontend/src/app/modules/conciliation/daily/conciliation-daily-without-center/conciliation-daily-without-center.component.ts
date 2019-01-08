import { Component, OnInit } from '@angular/core';
import { ConciliationDailyWithoutCenterContext } from 'src/app/shared/pagination-contexts/conciliation-daily-without-center-context.class';

@Component({
  selector: 'app-conciliation-daily-without-center',
  templateUrl: './conciliation-daily-without-center.component.html',
  styleUrls: ['./conciliation-daily-without-center.component.scss']
})
export class ConciliationDailyWithoutCenterComponent implements OnInit {
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
    private conciliationDailyWithoutCenterContext: ConciliationDailyWithoutCenterContext
  ) { }

  ngOnInit() {
    this.format = 'dd/MM/yyyy';

    this.tableHeaders = [
      {value: 'Fecha'},
      {value: 'Moneda'},
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
    return this.conciliationDailyWithoutCenterContext;
  }

  getServiceFilteringMethodName() {
    return 'dailyWithoutCenterFilteringData';
  }

  getFileDownloadName() {
    return 'diaria-sin-centro';
  }

  setAsEmpty(){
    this.getContext().isEmpty = true;
  }

}
