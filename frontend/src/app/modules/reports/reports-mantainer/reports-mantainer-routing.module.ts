import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ReportsMantainerComponent } from './reports-mantainer.component';

const routes: Routes = [
    {
        path: 'reports-mantainer',
        component: ReportsMantainerComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ReportsMantainerRoutingModule {}
