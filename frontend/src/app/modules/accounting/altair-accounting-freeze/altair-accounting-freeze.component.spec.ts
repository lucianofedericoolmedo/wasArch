import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AltairAccountingFreezeComponent } from './altair-accounting-freeze.component';

describe('AltairAccountingFreezeComponent', () => {
  let component: AltairAccountingFreezeComponent;
  let fixture: ComponentFixture<AltairAccountingFreezeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AltairAccountingFreezeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AltairAccountingFreezeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
