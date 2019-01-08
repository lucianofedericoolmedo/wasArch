import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConciliationMonthlyWithCenterComponent } from './conciliation-monthly-with-center.component';

describe('ConciliationMonthlyWithCenterComponent', () => {
  let component: ConciliationMonthlyWithCenterComponent;
  let fixture: ComponentFixture<ConciliationMonthlyWithCenterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConciliationMonthlyWithCenterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConciliationMonthlyWithCenterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
