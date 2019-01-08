import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ModulesModule } from './modules.module';
import { HomeComponent } from './home/home.component';
import { ReportsMantainerComponent } from './reports/reports-mantainer/reports-mantainer.component';
import { ProductsComponent } from './products/products.component';
import { ProductsCreateUpdateComponent } from './products/products-create-update/products-create-update.component';
import { ApplicationProductSourceComponent } from './products/application-product-source/application-product-source.component';
import { SubsidiaryAccountingFreezeComponent } from './accounting/subsidiary-accounting-freeze/subsidiary-accounting-freeze.component';
import { AltairAccountingFreezeComponent } from './accounting/altair-accounting-freeze/altair-accounting-freeze.component';
import { GlobalGroupsComponent } from './hierarchies/global-groups/global-groups.component';
// tslint:disable-next-line:max-line-length
import { GlobalGroupsCreateUpdateComponent } from './hierarchies/components/global-groups-create-update/global-groups-create-update.component';
// tslint:disable-next-line:max-line-length
import { ConciliationMonthlyWithCenterComponent } from './conciliation/monthly/conciliation-monthly-with-center/conciliation-monthly-with-center.component';
// tslint:disable-next-line:max-line-length
import { ConciliationMonthlyWithoutCenterComponent } from './conciliation/monthly/conciliation-monthly-without-center/conciliation-monthly-without-center.component';
// tslint:disable-next-line:max-line-length
import { ConciliationDailyWithCenterComponent } from './conciliation/daily/conciliation-daily-with-center/conciliation-daily-with-center.component';
// tslint:disable-next-line:max-line-length
import { ConciliationDailyWithoutCenterComponent } from './conciliation/daily/conciliation-daily-without-center/conciliation-daily-without-center.component';
import { ApuProcessesComponent } from './processes-log/apu-processes/apu-processes.component';
import { OdsProcessesComponent } from './processes-log/ods-processes/ods-processes.component';
import { RejectionsJoinComponent } from './rejections-join/rejections-join.component';
import { ReportsUsersComponent } from './reports/reports-users/reports-users.component';
import { TableDimensionCenterComponent } from './tables/dimension/table-dimension-center/table-dimension-center.component';
// tslint:disable-next-line:max-line-length
import { TableAuxiliaryAccountTypingComponent } from './tables/auxiliaries/table-auxiliary-account-typing/table-auxiliary-account-typing.component';

const routes: Routes = [
    {
        path: '', component: HomeComponent
    },
    {
        path: '', pathMatch: 'prefix', redirectTo: 'home'
    },
    {
        path: 'home', component: HomeComponent
    },
    {
        path: 'reports-mantainer', component: ReportsMantainerComponent
    },
    {
        path: 'products', component: ProductsComponent
    },
    {
        path: 'products/create', component: ProductsCreateUpdateComponent
    },
    {
        path: 'products/edit', component: ProductsCreateUpdateComponent
    },
    {
        path: 'products/application', component: ApplicationProductSourceComponent
    },
    {
        path: 'accounting/subsidiary', component: SubsidiaryAccountingFreezeComponent
    },
    {
        path: 'accounting/altair', component: AltairAccountingFreezeComponent
    },
    {
        path: 'hierarchies/global-groups', component: GlobalGroupsComponent
    },
    {
        path: 'hierarchies/global-groups/create', component: GlobalGroupsCreateUpdateComponent
    },
    {
        path: 'conciliations/monthly/with-center', component: ConciliationMonthlyWithCenterComponent
    },
    {
        path: 'conciliations/monthly/without-center', component: ConciliationMonthlyWithoutCenterComponent
    },
    {
        path: 'conciliations/daily/with-center', component: ConciliationDailyWithCenterComponent
    },
    {
        path: 'conciliations/daily/without-center', component: ConciliationDailyWithoutCenterComponent
    },
    {
        path: 'processes-log/apu', component: ApuProcessesComponent
    },
    {
        path: 'processes-log/ods', component: OdsProcessesComponent
    },
    {
        path: 'rejections/join', component: RejectionsJoinComponent
    },
    {
        path: 'reports/users', component: ReportsUsersComponent
    },
    {
        path: 'tables/dimension/center', component: TableDimensionCenterComponent
    },
    {
        path: 'tables/auxiliaries/account-typing', component: TableAuxiliaryAccountTypingComponent
    }


];

@NgModule({
    imports: [RouterModule.forChild(routes), ModulesModule],
    exports: [RouterModule]
})
export class ModulesRoutingModule {}
