import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GenericConciliationTableComponent } from './generic-conciliation-table.component';

describe('GenericConciliationTableComponent', () => {
  let component: GenericConciliationTableComponent;
  let fixture: ComponentFixture<GenericConciliationTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GenericConciliationTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GenericConciliationTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
