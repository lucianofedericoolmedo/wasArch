import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LayoutComponent } from './layout/layout.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { SidebarMenuComponent } from './sidebar-menu/sidebar-menu.component';

import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

import { CollapseModule, BsDropdownModule } from 'ngx-bootstrap';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    CommonModule,
    NgbModule,
    CollapseModule.forRoot(),
    BsDropdownModule.forRoot(),
    RouterModule
  ],
  declarations: [LayoutComponent, HeaderComponent, FooterComponent, SidebarMenuComponent],
  exports: [LayoutComponent]
})
export class UiModule { }
