import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { PaginatedTableComponent } from 'src/app/shared/paginated-table/paginated-table.component';
import { TableAuxiliaryAccountTypingContext } from 'src/app/shared/pagination-contexts/table-auxiliary-account-typing.class';
import { DatePipe } from '@angular/common';
import { Router } from '@angular/router';
import { TablesService } from 'src/app/providers/http/table-dimension.service';
import { Observable, pipe } from 'rxjs';
import { tap } from 'rxjs/operators';

@Component({
  selector: 'app-table-auxiliary-account-typing',
  templateUrl: './table-auxiliary-account-typing.component.html',
  styleUrls: ['./table-auxiliary-account-typing.component.scss']
})
export class TableAuxiliaryAccountTypingComponent implements OnInit {
  form: FormGroup;
  format: string;
  tableColumns: any[];
  tableFields: { inputData?: any };
  queryObject: {};

  constructor(
    private formBuilder: FormBuilder,
    private tableAuxiliaryAccountTypingContext: TableAuxiliaryAccountTypingContext,
    private datePipe: DatePipe,
    private _router: Router,
    private tablesService: TablesService
  ) { }

  @ViewChild(PaginatedTableComponent)
  paginatedTable: PaginatedTableComponent;

  ngOnInit() {
    this.buildForm();
    this.format = 'dd/MM/yyyy';
    this.tableColumns = [
      {value: 'Entidad', style: {minWidth: '168px'}},
      {value: 'Valor', style: {minWidth: '184px'}},
      {value: 'Tipo Valor', style: {minWidth: '168px'}},
      {value: 'Tipo Saldo', style: {minWidth: '300px'}},
      {value: 'Clase Saldo', style: {minWidth: '170px'}},
      {value: 'Estado Saldo', style: {minWidth: '274px'}}
    ];
    this.queryObject = {};
    this.getSelectorsValues();
  }

  buildForm() {
    this.form = this.formBuilder.group({
      valor:   ['', []],
      entidad: ['', []]
    });
  }

  getPaginationContext() {
    return this.tableAuxiliaryAccountTypingContext;
  }

  search() {
    const queryObject = { ...this.queryObject, ...this.form.value };
    this.paginatedTable.notifyCurrentPage(queryObject);
  }

  getSelectorsValues() {
    return this.tablesService.getAuxiliaryAccountTypingSelectorsValues().subscribe((data: any) => {
      this.tableFields = {
        inputData: {
          entidad:     {type: 'select',   validations: [Validators.required], values: data.entidad},
          valor:       {type: 'input',    validations: [Validators.required], values: data.valor},
          tipoValor:   {type: 'select',   validations: [Validators.required], values: data.tipoValor},
          tipoSaldo:   {type: 'select',   validations: [Validators.required], values: data.tipoSaldo,   displayField: 'descripcion', valueField: 'codigo'},
          claseSaldo:  {type: 'select',   validations: [Validators.required], values: data.claseSaldo,  displayField: 'descripcion', valueField: 'codigo'},
          estadoSaldo: {type: 'select',   validations: [Validators.required], values: data.estadoSaldo, displayField: 'descripcion', valueField: 'codigo'}
        }
      };
    });
  }

  getRestCallFunction() {
    return () => {
      const queryObject = { ...this.queryObject, ...this.form.value };
      return this.tablesService.getAuxiliaryAccountTypingFile(queryObject);
    }
  }

  getApplicationProductSourceFileName() {
    const formattedDate = this.datePipe.transform(new Date(), this.format);
    return `tipificacion-cuentas_${ formattedDate }.xlsx`;
  }

  getDownloadedFileType() {
    return 'application/vnd.ms-excel';
  }

  goBack() {
    this._router.navigate(['/']);
  }

}
