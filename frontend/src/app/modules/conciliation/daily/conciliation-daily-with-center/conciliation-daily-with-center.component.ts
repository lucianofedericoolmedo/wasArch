import { Component, OnInit, Input } from '@angular/core';
import { ConciliationDailyWithCenterContext } from 'src/app/shared/pagination-contexts/conciliation-daily-with-center-context.class';

@Component({
  selector: 'app-conciliation-daily-with-center',
  templateUrl: './conciliation-daily-with-center.component.html',
  styleUrls: ['./conciliation-daily-with-center.component.scss']
})
export class ConciliationDailyWithCenterComponent implements OnInit {
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
    private conciliationDailyWithCenterContext: ConciliationDailyWithCenterContext
  ) { }

  ngOnInit() {
    this.format = 'dd/MM/yyyy';

    this.tableHeaders = [
      {value: 'Fecha'},
      {value: 'Moneda'},
      {value: 'Contenido'},
      {value: 'Sis. Origen'},
      {value: 'Entidad'},
      {value: 'Centro Contable'},
      {value: 'Cuenta Contable'},
      {value: 'Descripción Cuenta Contable'},
      {value: 'Saldo Contable'},
      {value: 'Saldo Operacional'}
    ];

    this.tableData = {
      displayData: [
        {fieldName: 'fecha', format: this.format},
        {fieldName: 'codigoDivisa'},
        {fieldName: 'codigoContenido'},
        {fieldName: 'codigoSistemaOrigen'},
        {fieldName: 'entidad'},
        {fieldName: 'codigoCentroContable'},
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
    return this.conciliationDailyWithCenterContext;
  }

  getServiceFilteringMethodName() {
    return 'dailyWithCenterFilteringData';
  }

  getFileDownloadName() {
    return 'diaria-con-centro';
  }

  setAsEmpty(){
    this.getContext().isEmpty = true;
  }
  
}
