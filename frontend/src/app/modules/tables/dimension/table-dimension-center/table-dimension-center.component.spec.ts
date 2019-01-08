import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TableDimensionCenterComponent } from './table-dimension-center.component';

describe('TableDimensionCenterComponent', () => {
  let component: TableDimensionCenterComponent;
  let fixture: ComponentFixture<TableDimensionCenterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TableDimensionCenterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TableDimensionCenterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
