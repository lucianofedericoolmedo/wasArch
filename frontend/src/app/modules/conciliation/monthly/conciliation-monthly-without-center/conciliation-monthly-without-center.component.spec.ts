import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConciliationMonthlyWithoutCenterComponent } from './conciliation-monthly-without-center.component';

describe('ConciliationMonthlyWithoutCenterComponent', () => {
  let component: ConciliationMonthlyWithoutCenterComponent;
  let fixture: ComponentFixture<ConciliationMonthlyWithoutCenterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConciliationMonthlyWithoutCenterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConciliationMonthlyWithoutCenterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
