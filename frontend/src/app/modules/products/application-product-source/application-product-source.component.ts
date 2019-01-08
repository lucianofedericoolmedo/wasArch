import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Product } from '../../../interfaces/product.interface';
import { ProductsContext } from 'src/app/shared/pagination-contexts/products-context.class';
import { ProductsService } from 'src/app/providers/http/products.service';
import { DataSharing } from 'src/app/shared/data-sharing.service';
import { PaginatedTableComponent } from '../../../shared/paginated-table/paginated-table.component';
import { Router } from '@angular/router';
import { ApplicationProductsSourceContext } from 'src/app/shared/pagination-contexts/application-products-source-context.class';
import { DatePipe } from '@angular/common';
import { NotificationModal } from 'src/app/shared/notification-modal/notification-modal.service';

@Component({
  selector: 'app-application-product-source',
  templateUrl: './application-product-source.component.html',
  styleUrls: ['./application-product-source.component.scss']
})
export class ApplicationProductSourceComponent implements OnInit {

  public form: FormGroup;
  public products: Product[];
  public format: string;
  productTableDataFieldsName: {displayData?: any[], inputData?: any};
  productTableDataFieldsColumns: any[];
  productosOperacional: any[];
  subproductosOperacional: any[];
  codigosSistemaOrigen: any[];
  productTypeSelected = 'operacional';

  constructor(
    private formBuilder: FormBuilder,
    private applicationProductsSourceContext: ApplicationProductsSourceContext,
    private _router: Router,
    private productsService: ProductsService,
    private datePipe: DatePipe,
    private notificationModal: NotificationModal
  ) { }

  @ViewChild(PaginatedTableComponent)
  paginatedTable: PaginatedTableComponent;

  ngOnInit() {
    this.buildForm();
    this.format = 'dd/MM/yyyy';

    this.productTableDataFieldsName = {
      inputData: {
        aplicativoFuente:             {type: 'select',   validations: [Validators.required]},
        codigoProductoOperacional:    {type: 'display'},
        codigoSubproductoOperacional: {type: 'display'},
        codigoProductoNormalizado:    {type: 'input',    validations: [Validators.required]},
        codigoSubproductoNormalizado: {type: 'input',    validations: [Validators.required]},
        descripcion:                  {type: 'input',    validations: [Validators.required]},
        fechaAlta:                    {type: 'display'},
        fechaBaja:                    {type: 'display'},
        bimonetario:                  {type: 'display'}
      }
    };

    this.productTableDataFieldsColumns = [
      {value: 'Aplicativo Fuente',       style: {minWidth: '250px'}},
      {value: 'Producto Operacional',    style: {width:    '10px'}},
      {value: 'Subproducto Operacional', style: {width:    '115px'}},
      {value: 'Producto Normalizado',    style: {width:    '110px'}},
      {value: 'Subproducto Normalizado', style: {width:    '110px'}},
      {value: 'Descripción',             style: {minWidth: '250px'}},
      {value: 'Fecha Alta'},
      {value: 'Fecha Baja'},
      {value: 'Ind. Biomonetario'}
    ];

    this.getProductosGestion(true);
    this.getAplicativosNormalizados();
  }

  buildForm() {
    this.form = this.formBuilder.group({
      codigoProducto:    ['', []],
      codigoSubproducto: ['', []]
    });
  }

  getProductsPaginationContext() {
    return this.applicationProductsSourceContext;
  }

  search() {
    const queryObject = { ...this.form.value };
    this.paginatedTable.notifyCurrentPage(queryObject);
  }

  getProductosGestion(isOperacional) {
    this.productsService.getProductoGestion(isOperacional).subscribe(
      (data: any[]) => this.productosOperacional = data);
  }

  getSubproductosGestion(codigoProducto) {
    this.productsService.getSubproductoGestion(codigoProducto).subscribe(
      (data: any[]) => this.subproductosOperacional = data);
  }

  getAplicativosNormalizados() {
    this.productsService.getAplicativosNormalizados().subscribe(
      (data: any[]) => this.productTableDataFieldsName.inputData.aplicativoFuente.values = data);
  }

  changedProductoOperacional(value) {
    this.getSubproductosGestion(value);
  }

  getRestCallFunction() {
    return () => this.productsService.getProductoAplicativoFuenteFile(this.form.value);
  }

  getApplicationProductSourceFileName() {
    const formattedDate = this.datePipe.transform(new Date(), this.format);
    return `productos-aplicativo-fuente_${ formattedDate }.xlsx`;
  }

  getDownloadedFileType() {
    return 'application/vnd.ms-excel';
  }

  goBack() {
    this._router.navigate(['/']);
  }

  save() {
    if (this.paginatedTable.isValidForm()) {
      this.createNormalizados(this.paginatedTable.getValues());
    } else {
      console.log('The form values are invalid');
    }
  }

  private createNormalizados(products) {
    products.forEach(function (product) {
      product.codigoSistemaOrigen = product.aplicativoFuente;
    });
    this.productsService.createNormalizados(products)
      .then(() =>  this.notificationModal.success('Se ha realizado la operación con éxito.'))
      .catch((data: any) => this.notificationModal.open(data.error.message));
  }

}
