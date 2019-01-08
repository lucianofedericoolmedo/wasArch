import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GenericFreezingTableComponent } from './generic-freezing-table.component';

describe('GenericFreezingTableComponent', () => {
  let component: GenericFreezingTableComponent;
  let fixture: ComponentFixture<GenericFreezingTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GenericFreezingTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GenericFreezingTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
