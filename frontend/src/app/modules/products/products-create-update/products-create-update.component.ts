import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl, AbstractControl, ValidationErrors } from '@angular/forms';
import { ProductsService } from '../../../providers/http/products.service';
import { DataSharing } from 'src/app/shared/data-sharing.service';
import { Product } from 'src/app/interfaces/product.interface';
import { ActivatedRoute, Router } from '@angular/router';
import { NotificationModal } from 'src/app/shared/notification-modal/notification-modal.service';

@Component({
  selector: 'app-products-create-update',
  templateUrl: './products-create-update.component.html',
  styleUrls: ['./products-create-update.component.scss']
})
export class ProductsCreateUpdateComponent implements OnInit {
  public editing: boolean;
  public form: FormGroup;
  private storedProduct: Product;
  fields: string[];
  productType: any;
  codigosSistemaOrigen: any[];

  constructor(
    private formBuilder: FormBuilder,
    private productsService: ProductsService,
    private dataSharing: DataSharing,
    private _route: ActivatedRoute,
    private _router: Router,
    private notificationModal: NotificationModal
  ) {
    this.storedProduct = this.dataSharing.getData('product');
    if (this.storedProduct) {
      this.editing = true;
      this.buildForm(this.storedProduct);
      this.dataSharing.clearData('product');
    } else {
      this.editing = false;
      this.redirectIfEditingAndNotProduct();
      this.buildForm();
    }

    this.getAplicativosNormalizados();
  }

  ngOnInit() {
    this._route.params
      .subscribe(params => this.productType = params.productType);
  }

  private redirectIfEditingAndNotProduct() {
    const currentUrl = this._router.url;
    if (currentUrl.indexOf('edit') !== -1) {
      this._router.navigateByUrl('products');
    }
  }

  private buildForm(values?: any) {
    values = values || {};

    let ngbDateStruct = null;
    if (values && values.fechaAlta) {
      const date = new Date(values.fechaAlta);
      ngbDateStruct = { day: date.getUTCDay(), month: date.getUTCMonth() + 1, year: date.getUTCFullYear()};
    }
    this.form = this.formBuilder.group({
      'codigoProducto':               [values.codigoProducto, [Validators.required]],
      'codigoProductoNormalizado':    [values.codigoProductoNormalizado, []],
      'codigoSubproducto':            [values.codigoSubproducto, [Validators.required]],
      'codigoSubproductoNormalizado': [values.codigoSubproductoNormalizado, []],
      'descripcion':                  [values.descripcion, [Validators.required]],
      'codigoSistemaOrigen':          [values.codigoSistemaOrigen, []],
      'productoBimonetario':          [values.productoBimonetario, []],
      'fechaAlta':                    [ngbDateStruct, [Validators.required]]
    });
  }

  getAplicativosNormalizados() {
    this.productsService.getAplicativosNormalizados().subscribe((data: any[]) => this.codigosSistemaOrigen = data);
  }

  createOrUpdate() {
    if (this.form.valid) {
      const formValues = { ...this.form.value };
      const fechaAlta = formValues.fechaAlta;
      formValues.fechaAlta = `${fechaAlta.year}/${fechaAlta.month}/${fechaAlta.day}`;
      if (this.editing) {
        this.update(formValues);
      } else {
        this.create(formValues);
      }
    } else {
      this.notificationModal.open('Hay errores en el formulario');
    }
  }

  private create(product) {
    this.productsService.createProduct(product)
      .then((data: any) => this.notificationModal.success('Se ha realizado la operación con éxito.'))
      .catch((data: any) => this.notificationModal.open(data.error.message));
  }

  private update(product) {
    this.productsService.update(product)
      .then((data: any) => this.notificationModal.success('Se ha realizado la operación con éxito.'))
      .catch((data: any) => this.notificationModal.open(data.error.message));
  }

}
