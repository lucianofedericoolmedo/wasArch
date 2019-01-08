import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConciliationDailyWithCenterComponent } from './conciliation-daily-with-center.component';

describe('ConciliationDailyWithCenterComponent', () => {
  let component: ConciliationDailyWithCenterComponent;
  let fixture: ComponentFixture<ConciliationDailyWithCenterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConciliationDailyWithCenterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConciliationDailyWithCenterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
