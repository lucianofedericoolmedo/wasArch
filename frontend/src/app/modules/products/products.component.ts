import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Product } from '../../interfaces/product.interface';
import { ProductsContext } from 'src/app/shared/pagination-contexts/products-context.class';
import { PaginatedTableComponent } from '../../shared/paginated-table/paginated-table.component';
import { Router } from '@angular/router';
import { DataSharing } from 'src/app/shared/data-sharing.service';
import { ProductsService } from 'src/app/providers/http/products.service';
import { NotificationModal } from 'src/app/shared/notification-modal/notification-modal.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss']
})
export class ProductsComponent implements OnInit {

  public form: FormGroup;
  public products: Product[];
  public format: string;
  isOperacional: boolean;
  productTableDataFieldsName: {displayData?: any[], inputData?: any[]};
  productTableDataFieldsColumns: any[];
  queryObject: any;
  productosOperacional: any[];
  subproductosOperacional: any[];
  codigosSistemaOrigen: any[];

  constructor(
    private formBuilder: FormBuilder,
    private productsContext: ProductsContext,
    private _router: Router,
    private dataSharing: DataSharing,
    private productsService: ProductsService,
    private datePipe: DatePipe
  ) { }

  @ViewChild(PaginatedTableComponent)
  paginatedTable: PaginatedTableComponent;

  @Input() fileDownload: string;

  ngOnInit() {
    this.buildForm();
    this.format = 'dd/MM/yyyy';

    this.productTableDataFieldsName = {
      displayData: [
        {fieldName:   'codigoSistemaOrigen'},
        {fieldName:   'codigoProducto'},
        {fieldName:   'codigoSubproducto'},
        {fieldName:   'codigoProductoNormalizado'},
        {fieldName:   'codigoSubproductoNormalizado'},
        {fieldDate:   'fechaAlta', format: this.format},
        {fieldDate:   'fechaBaja', format: this.format},
        {fieldName:   'descripcion'},
        {fieldName:   'productoDestinatario'},
        {class: 'fa fa-pencil', buttonAction: this.editProduct.bind(this)}
      ]
    };

    this.productTableDataFieldsColumns = [
      {value: 'Cód. Sist. Origen'},
      {value: 'Cód. Prod. Operacional'},
      {value: 'Cód. Subprod. Operacional'},
      {value: 'Cód. Prod. Normalizado'},
      {value: 'Cód. Subprod. Normalizado'},
      {value: 'Fecha Alta'},
      {value: 'Fecha Baja'},
      {value: 'Desc. Producto'},
      {value: 'Producto Bimonetario'},
      {value: ''}
    ];

    this.queryObject = {
      isOperacional: true
    };

    this.getProductosGestion(this.queryObject.isOperacional);
    this.getAplicativosNormalizados();
  }

  buildForm() {
    this.form = this.formBuilder.group({
      isOperacional:      ['false', [Validators.required]],
      codSistemaOrigen:   ['', []],
      prodOperacional:    ['', []],
      subprodOperacional: ['', []]
    });
  }

  editProduct(product) {
    this.dataSharing.store('product', product);
    this._router.navigate(['/products/edit']);
  }

  search() {
    const queryObject = { ...this.queryObject, ...this.form.value };
    this.paginatedTable.notifyCurrentPage(queryObject);
  }

  changedIsOperacional(value) {
    this.getProductosGestion(value);
  }

  getProductsPaginationContext() {
    return this.productsContext;
  }

  getProductosGestion(isOperacional) {
    this.productsService.getProductoGestion(isOperacional).subscribe((data: any[]) => this.productosOperacional = data);
  }

  getSubproductosGestion(codigoProducto) {
    this.productsService.getSubproductoGestion(codigoProducto).subscribe((data: any[]) => this.subproductosOperacional = data);
  }

  getAplicativosNormalizados() {
    this.productsService.getAplicativosNormalizados().subscribe((data: any[]) => this.codigosSistemaOrigen = data);
  }

  changedProductoOperacional(value) {
    this.getSubproductosGestion(value);
  }

  getRestCallFunction() {
    return () => this.productsService.download(this.form.value);
  }

  getApplicationProductSourceFileName() {
    const formattedDate = this.datePipe.transform(new Date(), this.format);
    return `${this.fileDownload}_${formattedDate}.xlsx`;
  }

  getDownloadedFileType() {
    return 'application/vnd.ms-excel';
  }

  getFileDownloadName() {
    return 'filial';
  }

}
