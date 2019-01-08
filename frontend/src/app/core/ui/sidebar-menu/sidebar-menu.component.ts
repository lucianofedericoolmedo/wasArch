import { Component, OnInit, Input , ElementRef } from '@angular/core';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';
import { trigger, state, style, animate, transition } from '@angular/animations';

@Component({
  selector: 'app-sidebar-menu',
  templateUrl: './sidebar-menu.component.html',
  styleUrls: ['./sidebar-menu.component.scss'],
  animations: [
    trigger('expandCollapse', [
      state('open', style({
      })),
      state('closed', style({
        position: 'relative',
        height: 0,
        overflow: 'hidden',
        transition: 'height 0.35s ease'
      })),
      transition('*=>*', animate('150ms'))
    ]),
  ]
})
export class SidebarMenuComponent implements OnInit {

  isCollapsed = true;
  public showMenu = '';
  public showMenuInner = '';

  constructor(private router: Router) {
    this.router.events.subscribe((val) => this.setIsCollapsed(true));
  }

  @Input() toggleSideMenuSubject: Subject<Boolean>;

  ngOnInit() {

  }

  toogleIsCollapsed() {
    this.setIsCollapsed(!this.isCollapsed);
  }

  setIsCollapsed(collapse: boolean) {
    this.isCollapsed = collapse;
    this.notifySideBarCollapsed(collapse);
  }


  notifySideBarCollapsed(collapsed: boolean) {
    this.toggleSideMenuSubject.next(collapsed);
  }

  clickedMenuItem(collapse: boolean, element: any) {
    this.setIsCollapsed(collapse);
    this.toggleShowProp(element);
  }

  toggleShowProp(myProp) {
    if (this.showMenu !== myProp) {
      this.showMenu = myProp;
      this.showMenuInner = '';
    } else {
      this.showMenu = '';
    }
  }

  toggleShowPropInner(myPropInner) {
    if (this.showMenuInner !== myPropInner) {
      this.showMenuInner = myPropInner;
    } else {
      this.showMenuInner = '';
    }
  }

}
