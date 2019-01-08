import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportsUsersComponent } from './reports-users.component';

describe('ReportsUsersComponent', () => {
  let component: ReportsUsersComponent;
  let fixture: ComponentFixture<ReportsUsersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportsUsersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportsUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
