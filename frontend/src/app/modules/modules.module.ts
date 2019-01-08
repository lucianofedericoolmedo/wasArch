import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HomeComponent } from './home/home.component';
import { ReportsComponent } from './reports/reports.component';
import { ReportsMantainerComponent } from './reports/reports-mantainer/reports-mantainer.component';
import { ProductsComponent } from './products/products.component';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ProductsCreateUpdateComponent } from './products/products-create-update/products-create-update.component';
import { RouterModule } from '@angular/router';
import { SharedModule } from '../shared/shared.module';

import { NgbDatepickerModule } from '@ng-bootstrap/ng-bootstrap';
import { ApplicationProductSourceComponent } from './products/application-product-source/application-product-source.component';
import { GenericFreezingTableComponent } from './accounting/components/generic-freezing-table/generic-freezing-table.component';
import { SubsidiaryAccountingFreezeComponent } from './accounting/subsidiary-accounting-freeze/subsidiary-accounting-freeze.component';
import { AltairAccountingFreezeComponent } from './accounting/altair-accounting-freeze/altair-accounting-freeze.component';
import { GlobalGroupsFiltersComponent } from './hierarchies/components/global-groups-filters/global-groups-filters.component';
// tslint:disable-next-line:max-line-length
import { GlobalGroupsCreateUpdateComponent } from './hierarchies/components/global-groups-create-update/global-groups-create-update.component';
import { GlobalGroupsComponent } from './hierarchies/global-groups/global-groups.component';
// tslint:disable-next-line:max-line-length
import { ConciliationMonthlyWithCenterComponent } from './conciliation/monthly/conciliation-monthly-with-center/conciliation-monthly-with-center.component';
// tslint:disable-next-line:max-line-length
import { ConciliationMonthlyWithoutCenterComponent } from './conciliation/monthly/conciliation-monthly-without-center/conciliation-monthly-without-center.component';
// tslint:disable-next-line:max-line-length
import { GenericConciliationTableComponent } from './conciliation/components/generic-conciliation-table/generic-conciliation-table.component';
// tslint:disable-next-line:max-line-length
import { ConciliationDailyWithCenterComponent } from './conciliation/daily/conciliation-daily-with-center/conciliation-daily-with-center.component';
// tslint:disable-next-line:max-line-length
import { ConciliationDailyWithoutCenterComponent } from './conciliation/daily/conciliation-daily-without-center/conciliation-daily-without-center.component';
import { DatePipe } from '@angular/common';
import { ApuProcessesComponent } from './processes-log/apu-processes/apu-processes.component';
import { OdsProcessesComponent } from './processes-log/ods-processes/ods-processes.component';
import { RejectionsJoinComponent } from './rejections-join/rejections-join.component';
import { ReportsUsersComponent } from './reports/reports-users/reports-users.component';
import { TableDimensionCenterComponent } from './tables/dimension/table-dimension-center/table-dimension-center.component';
// tslint:disable-next-line:max-line-length
import { TableAuxiliaryAccountTypingComponent } from './tables/auxiliaries/table-auxiliary-account-typing/table-auxiliary-account-typing.component';

@NgModule({
  declarations: [
    HomeComponent,
    ReportsComponent,
    ReportsMantainerComponent,
    ProductsComponent,
    ProductsCreateUpdateComponent,
    ApplicationProductSourceComponent,
    GenericFreezingTableComponent,
    SubsidiaryAccountingFreezeComponent,
    AltairAccountingFreezeComponent,
    GlobalGroupsFiltersComponent,
    GlobalGroupsCreateUpdateComponent,
    GlobalGroupsComponent,
    GenericConciliationTableComponent,
    ConciliationMonthlyWithCenterComponent,
    ConciliationMonthlyWithoutCenterComponent,
    ConciliationDailyWithCenterComponent,
    ConciliationDailyWithoutCenterComponent,
    ApuProcessesComponent,
    OdsProcessesComponent,
    RejectionsJoinComponent,
    ReportsUsersComponent,
    TableDimensionCenterComponent,
    TableAuxiliaryAccountTypingComponent
  ],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    BrowserModule,
    RouterModule,
    SharedModule,
    NgbDatepickerModule
  ],
  providers: [
    DatePipe
  ]
})
export class ModulesModule { }
