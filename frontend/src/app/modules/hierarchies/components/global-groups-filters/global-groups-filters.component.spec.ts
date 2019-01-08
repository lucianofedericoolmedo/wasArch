import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GlobalGroupsFiltersComponent } from './global-groups-filters.component';

describe('GlobalGroupsFiltersComponent', () => {
  let component: GlobalGroupsFiltersComponent;
  let fixture: ComponentFixture<GlobalGroupsFiltersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GlobalGroupsFiltersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GlobalGroupsFiltersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
