import { Component, OnInit } from '@angular/core';
import { AltairAccountingFreezeContext } from 'src/app/shared/pagination-contexts/altair-accounting-freeze-context.class';

@Component({
  selector: 'app-altair-accounting-freeze',
  templateUrl: './altair-accounting-freeze.component.html',
  styleUrls: ['./altair-accounting-freeze.component.scss']
})
export class AltairAccountingFreezeComponent implements OnInit {
  queryObject: any;

  constructor(private altairAccountingFreezeContext: AltairAccountingFreezeContext) { }

  ngOnInit() {
    this.queryObject = {};
  }

  getContext() {
    return this.altairAccountingFreezeContext;
  }

  getServiceFilteringMethodName() {
    return 'altairFilterData';
  }
  
  getFileDownloadName() {
    return 'altair';
  }

}
