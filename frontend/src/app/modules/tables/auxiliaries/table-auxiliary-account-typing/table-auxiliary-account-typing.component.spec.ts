import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TableAuxiliaryAccountTypingComponent } from './table-auxiliary-account-typing.component';

describe('TableAuxiliaryAccountTypingComponent', () => {
  let component: TableAuxiliaryAccountTypingComponent;
  let fixture: ComponentFixture<TableAuxiliaryAccountTypingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TableAuxiliaryAccountTypingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TableAuxiliaryAccountTypingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
