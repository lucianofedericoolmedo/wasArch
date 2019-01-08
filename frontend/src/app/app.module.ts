import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UiModule } from './core/ui/ui.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { SharedModule } from './shared/shared.module';
import { NgbModule, NgbDateParserFormatter, NgbDatepickerI18n } from '@ng-bootstrap/ng-bootstrap';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { ModulesRoutingModule } from './modules/modules.routing';
import { ApiPrefixInterceptor } from './core/interceptors/api-prefix.interceptor';
import { NgbDateCustomParserFormatter } from './core/config/datepicker-format.class';
import { NgbDateCustomLanguage } from './core/config/datepicker-language.class';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { LOCALE_ID } from '@angular/core';
import { TooltipModule } from 'ngx-bootstrap';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    UiModule,
    SharedModule,
    NgbModule,
    ModulesRoutingModule,
    HttpClientModule,
    TooltipModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ApiPrefixInterceptor,
      multi: true
    },
    {
      provide: NgbDateParserFormatter,
      useClass: NgbDateCustomParserFormatter
    },
    {
      provide: NgbDatepickerI18n,
      useClass: NgbDateCustomLanguage
    },
    {
      provide: LocationStrategy,
      useClass: HashLocationStrategy
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
