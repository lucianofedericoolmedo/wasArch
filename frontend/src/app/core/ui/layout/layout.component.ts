import { Component, OnInit } from '@angular/core';
import { Subject, Observable } from 'rxjs';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss']
})
export class LayoutComponent implements OnInit {

  toggleSideBarSubj$: Subject<Boolean>;
  toggleSideBarObs$: Observable<Boolean>;
  isCollapsed: boolean;

  constructor() {
    this.toggleSideBarSubj$ = new Subject<Boolean>();
    this.toggleSideBarObs$  = this.toggleSideBarSubj$.asObservable();
  }

  ngOnInit() {
    this.toggleSideBarObs$.subscribe((isCollapsed: boolean) => {
      this.isCollapsed = isCollapsed;
    });
  }

}
