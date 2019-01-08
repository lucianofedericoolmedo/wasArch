import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PaginatedTableComponent } from 'src/app/shared/paginated-table/paginated-table.component';
import { TableDimensionCenterContext } from 'src/app/shared/pagination-contexts/table-dimension-center.class';

@Component({
  selector: 'app-table-dimension-center',
  templateUrl: './table-dimension-center.component.html',
  styleUrls: ['./table-dimension-center.component.scss']
})
export class TableDimensionCenterComponent implements OnInit {
  form: FormGroup;
  format: string;
  tableFields: { displayData: any[]; };
  tableColumns: any[];
  queryObject: {};

  constructor(
    private formBuilder: FormBuilder,
    private tableDimensionCenterContext: TableDimensionCenterContext
  ) { }

  @ViewChild(PaginatedTableComponent)
  paginatedTable: PaginatedTableComponent;

  ngOnInit() {
    this.buildForm();
    this.format = 'dd/MM/yyyy';
    this.tableColumns = [
      {value: 'Cód. Centro'},
      {value: 'Des. Centro'},
      {value: 'Cód. Zona'},
      {value: 'Cód. Plaza'},
      {value: 'Cód. Localidad'},
      {value: 'Cód. Región'},
      {value: 'Cód. Camara'},
      {value: 'Ind. Baja'},
      {value: 'Tip. Oficina'},
      {value: 'Ind. Oficina contable'},
      {value: 'Centro Operante'}
    ];
    this.tableFields = {
      displayData: [
        {fieldName:   'codigoCentro'},
        {fieldName:   'descripcionCentro'},
        {fieldName:   'codigoZona'},
        {fieldName:   'codigoPlaza'},
        {fieldName:   'codigoLocalidad'},
        {fieldName:   'codigoRegion'},
        {fieldName:   'codigoCamara'},
        {fieldName:   'indBaja'},
        {fieldName:   'tipOficina'},
        {fieldName:   'infOficinaContable'},
        {fieldName:   'centroOperante'}
      ]
    };
    this.queryObject = {};
    this.search();
  }

  buildForm() {
    this.form = this.formBuilder.group({
      codigoCentro: ['', []]
    });
  }

  getPaginationContext() {
    return this.tableDimensionCenterContext;
  }

  search() {
    const queryObject = { ...this.queryObject, ...this.form.value };
    this.paginatedTable.notifyCurrentPage(queryObject);
  }

}
