import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GlobalGroupsCreateUpdateComponent } from './global-groups-create-update.component';

describe('GlobalGroupsCreateUpdateComponent', () => {
  let component: GlobalGroupsCreateUpdateComponent;
  let fixture: ComponentFixture<GlobalGroupsCreateUpdateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GlobalGroupsCreateUpdateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GlobalGroupsCreateUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
