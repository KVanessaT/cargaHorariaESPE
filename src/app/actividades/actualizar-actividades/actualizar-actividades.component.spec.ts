import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ActualizarActividadesComponent } from './actualizar-actividades.component';

describe('ActualizarActividadesComponent', () => {
  let component: ActualizarActividadesComponent;
  let fixture: ComponentFixture<ActualizarActividadesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ActualizarActividadesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActualizarActividadesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
