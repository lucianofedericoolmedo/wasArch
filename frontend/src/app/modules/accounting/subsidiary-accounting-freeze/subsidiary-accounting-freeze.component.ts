import { Component, OnInit } from '@angular/core';
import { SubsidiaryAccountingFreezeContext } from 'src/app/shared/pagination-contexts/subsidiary-accounting-freeze-context.class';

@Component({
  selector: 'app-subsidiary-accounting-freeze',
  templateUrl: './subsidiary-accounting-freeze.component.html',
  styleUrls: ['./subsidiary-accounting-freeze.component.scss']
})
export class SubsidiaryAccountingFreezeComponent implements OnInit {

  constructor(private subsidiaryAccountingFreezeContext: SubsidiaryAccountingFreezeContext) { }

  ngOnInit() {
  }

  getContext() {
    return this.subsidiaryAccountingFreezeContext;
  }

  getServiceFilteringMethodName() {
    return 'susidiaryFilterData';
  }

  getFileDownloadName() {
    return 'filial';
  }
}
