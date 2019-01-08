import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OdsProcessesComponent } from './ods-processes.component';

describe('OdsProcessesComponent', () => {
  let component: OdsProcessesComponent;
  let fixture: ComponentFixture<OdsProcessesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OdsProcessesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OdsProcessesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
