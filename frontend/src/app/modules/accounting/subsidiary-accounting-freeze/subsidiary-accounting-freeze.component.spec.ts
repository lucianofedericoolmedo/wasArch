import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubsidiaryAccountingFreezeComponent } from './subsidiary-accounting-freeze.component';

describe('SubsidiaryAccountingFreezeComponent', () => {
  let component: SubsidiaryAccountingFreezeComponent;
  let fixture: ComponentFixture<SubsidiaryAccountingFreezeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubsidiaryAccountingFreezeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubsidiaryAccountingFreezeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
