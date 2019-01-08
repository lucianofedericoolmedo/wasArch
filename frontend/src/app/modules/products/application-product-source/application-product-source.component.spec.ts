import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicationProductSourceComponent } from './application-product-source.component';

describe('ApplicationProductSourceComponent', () => {
  let component: ApplicationProductSourceComponent;
  let fixture: ComponentFixture<ApplicationProductSourceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApplicationProductSourceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplicationProductSourceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
