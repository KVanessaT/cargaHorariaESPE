import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewGeneralReportComponent } from './view-general-report.component';

describe('ViewGeneralReportComponent', () => {
  let component: ViewGeneralReportComponent;
  let fixture: ComponentFixture<ViewGeneralReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewGeneralReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewGeneralReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
