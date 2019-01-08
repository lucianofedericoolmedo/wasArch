import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConciliationDailyWithoutCenterComponent } from './conciliation-daily-without-center.component';

describe('ConciliationDailyWithoutCenterComponent', () => {
  let component: ConciliationDailyWithoutCenterComponent;
  let fixture: ComponentFixture<ConciliationDailyWithoutCenterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConciliationDailyWithoutCenterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConciliationDailyWithoutCenterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
