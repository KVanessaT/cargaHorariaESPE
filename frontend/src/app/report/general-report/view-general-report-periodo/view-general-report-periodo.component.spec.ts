import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewGeneralReportPeriodoComponent } from './view-general-report-periodo.component';

describe('ViewGeneralReportPeriodoComponent', () => {
  let component: ViewGeneralReportPeriodoComponent;
  let fixture: ComponentFixture<ViewGeneralReportPeriodoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewGeneralReportPeriodoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewGeneralReportPeriodoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
