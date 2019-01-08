import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Product } from '../../interfaces/product.interface';
import { ProductsContext } from 'src/app/shared/pagination-contexts/products-context.class';
import { RejectionsJoinService } from 'src/app/providers/http/rejections-join.service';
import { DataSharing } from 'src/app/shared/data-sharing.service';
import { PaginatedTableComponent } from '../../shared/paginated-table/paginated-table.component';
import { Router } from '@angular/router';
import { RejectionsJoinContext } from 'src/app/shared/pagination-contexts/rejections-join-context.class';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-rejections-join',
  templateUrl: './rejections-join.component.html',
  styleUrls: ['./rejections-join.component.scss']
})
export class RejectionsJoinComponent implements OnInit {

  public form: FormGroup;
  public products: Product[];
  public format: string;
  rejectonsJoinTableDataFieldsName: {displayData?: any[], inputData?: any};
  rejectionsJoinTableDataFieldsColumns: any[];
  queryObject: any;
  dates: any[];
  contents: any[];
  codigosSistemaOrigen: any[];

  constructor(
    private formBuilder: FormBuilder,
    private rejectionsJoinContext: RejectionsJoinContext,
    private _router: Router,
    private rejectionsJoinService: RejectionsJoinService,
    private dataSharing: DataSharing,
    private datePipe: DatePipe) { }

  @ViewChild(PaginatedTableComponent)
  paginatedTable: PaginatedTableComponent;

  ngOnInit() {
    this.buildForm();
    this.format = 'dd/MM/yyyy';

    this.rejectonsJoinTableDataFieldsName = {
      displayData: [
        {fieldName:   'fecha'},
        {fieldName:   'descripcionContenido'},
        {fieldName:   'descripcionSistemaOrigen'},
        {fieldName:   'descripcionPais'},
        {fieldName:   'nombreTarget'},
        {fieldName:   'registrosAfectados'},
        {class: 'fa fa-eye', buttonAction: () => {}}
      ]
    };

    this.rejectionsJoinTableDataFieldsColumns = [
      {value: 'Fecha'},
      {value: 'Contenido'},
      {value: 'Sistema Origen'},
      {value: 'País'},
      {value: 'Nombre Target'},
      {value: 'Registros Afectados'},
      {value: ''},
    ];

    this.queryObject = {
    };

    this.getFilters();
  }

  buildForm() {
    this.form = this.formBuilder.group({
      cod_sist_origen: ['', []],
      date: ['', []],
      content: ['', []]
    });
  }

  getRejectionJoinPaginationContext() {
    return this.rejectionsJoinContext;
  }

  search() {
    const queryObject = { ...this.queryObject, ...this.form.value };
    this.paginatedTable.notifyCurrentPage(queryObject);
  }

  getFilters() {
    this.rejectionsJoinService.getFilters().subscribe(
      (data: any) => {this.dates = data.fechas;
        this.contents = data.contenidos;
        this.codigosSistemaOrigen = data.origen;
      });
  }

  getRestCallFunction() {
    return this.rejectionsJoinService.getExcelReport.bind(this.rejectionsJoinService);
  }

  getRejectionJoinFileName() {
    const formattedDate = this.datePipe.transform(new Date(), this.format);
    return `rejection-join_${ formattedDate }.xlsx`;
  }

  getDownloadedFileType() {
    return 'application/vnd.ms-excel';
  }

  goBack() {
    this._router.navigate(['/']);
  }

}
