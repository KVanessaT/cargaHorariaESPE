import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AdminLayoutRoutes } from './admin-layout.routing';
import { DashboardComponent } from '../../dashboard/dashboard.component';
import { TableListComponent } from '../../table-list/table-list.component';
import { TypographyComponent } from '../../typography/typography.component';
import { IconsComponent } from '../../icons/icons.component';
import { MapsComponent } from '../../maps/maps.component';
import { NotificationsComponent } from '../../notifications/notifications.component';
import { UpgradeComponent } from '../../upgrade/upgrade.component';
import {TableModule} from 'primeng/table';
import {DialogModule} from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import {DropdownModule} from 'primeng/dropdown';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {ProgressBarModule} from 'primeng/progressbar';
import {ChartModule} from 'primeng/chart';
import {MatTabsModule} from '@angular/material/tabs';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import {MatAutocompleteModule} from '@angular/material/autocomplete';

import {
  MatButtonModule,
  MatInputModule,
  MatRippleModule,
  MatFormFieldModule,
  MatTooltipModule,
  MatSelectModule,
  MatIconModule,
  MatDialogModule,
  MatBadgeModule,
  MatRadioModule
} from '@angular/material';
import { MatSelectFilterModule } from 'mat-select-filter';

import { PacComponent } from 'app/pages/pac/pac.component';
import { DatosPersonalesComponent } from 'app/pages/secciones/datos-personales/datos-personales.component';
import { DatosAcademicosComponent } from 'app/pages/secciones/datos-academicos/datos-academicos.component';
import { DocentesComponent } from 'app/docentes/docentes.component';
import { ActividadesComponent } from 'app/actividades/actividades.component';
import { SubactividadesComponent } from 'app/subactividades/subactividades.component';
import { AddActividadesComponent } from 'app/actividades/add-actividades/add-actividades.component';
import { EditActividadesComponent } from 'app/actividades/edit-actividades/edit-actividades.component';
import { DeleteActividadesComponent } from 'app/actividades/delete-actividades/delete-actividades.component';
import { AddSubactividadesComponent } from 'app/subactividades/add-subactividades/add-subactividades.component';
import { EditSubactividadesComponent } from 'app/subactividades/edit-subactividades/edit-subactividades.component';
import { DeleteSubactividadesComponent } from 'app/subactividades/delete-subactividades/delete-subactividades.component';
import { PeriodoComponent } from 'app/periodo/periodo.component';
import { ScrollingModule } from '@angular/cdk/scrolling';
import { DirectoresComponent } from 'app/directores/directores.component';
import { ActualizarActividadesComponent } from 'app/actividades/actualizar-actividades/actualizar-actividades.component';
import { NotFoundComponent } from 'app/http-errors/not-found/not-found.component';
import { ForbiddenComponent } from 'app/http-errors/forbidden/forbidden.component';
import { RequestErrorComponent } from 'app/http-errors/request-error/request-error.component';
import { ReportPdfComponent } from 'app/report-pdf/report-pdf.component';
import { ReportComponent } from 'app/report/report.component';
import { GeneralReportComponent } from '../../report/general-report/general-report.component';
import { ViewGeneralReportComponent } from '../../report/general-report/view-general-report/view-general-report.component';
import { ViewGeneralReportPeriodoComponent } from 'app/report/general-report/view-general-report-periodo/view-general-report-periodo.component';
import { ReportByDocenteComponent } from 'app/report/report-by-docente/report-by-docente.component';
import { ViewReportByDocenteComponent } from 'app/report/report-by-docente/view-report-by-docente/view-report-by-docente.component';
import { VerificarActividadComponent } from 'app/directores/verificar-actividad/verificar-actividad.component';
import { ReporteHorarioComponent } from 'app/directores/reporte-horario/reporte-horario.component';
import { PeriodosAsignadosComponent } from 'app/directores/periodos-asignados/periodos-asignados.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(AdminLayoutRoutes),
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatRippleModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatTooltipModule,
    MatSelectFilterModule,
    TableModule,
    MatIconModule,
    DialogModule,
    ButtonModule,
    DropdownModule,
    MatDialogModule,
    ScrollingModule,
    MatProgressBarModule,
    ProgressBarModule,
    ChartModule,
    MatTabsModule,
    PdfViewerModule,
    MatAutocompleteModule,
    MatBadgeModule,
    MatRadioModule

  ],
  declarations: [
    DashboardComponent,
    PacComponent,
    DatosPersonalesComponent,
    DatosAcademicosComponent,
    TableListComponent,
    TypographyComponent,
    IconsComponent,
    MapsComponent,
    NotificationsComponent,
    UpgradeComponent,
    DocentesComponent,
    ActividadesComponent,
    SubactividadesComponent,
    AddActividadesComponent,
    EditActividadesComponent,
    DeleteActividadesComponent,
    AddSubactividadesComponent,
    EditSubactividadesComponent,
    DeleteSubactividadesComponent,
    PeriodoComponent,
    DirectoresComponent,
    ActualizarActividadesComponent,
    NotFoundComponent,
    ForbiddenComponent,
    RequestErrorComponent,
    ReportPdfComponent,
    ReportComponent,
    GeneralReportComponent,
    ViewGeneralReportComponent,
    ViewGeneralReportPeriodoComponent,
    ReportByDocenteComponent,
    ViewReportByDocenteComponent,
    VerificarActividadComponent,
    ReporteHorarioComponent,
    PeriodosAsignadosComponent

  ],
  entryComponents: [
    AddActividadesComponent,
    EditActividadesComponent,
    DeleteActividadesComponent,
    AddSubactividadesComponent,
    EditSubactividadesComponent,
    DeleteSubactividadesComponent,
    PeriodoComponent,
    SubactividadesComponent,
    ActividadesComponent,
    ActualizarActividadesComponent,
    ReportPdfComponent,
    ViewGeneralReportComponent,
    ViewGeneralReportPeriodoComponent,
    ViewReportByDocenteComponent,
    ReporteHorarioComponent,
    PeriodosAsignadosComponent

  ]
})

export class AdminLayoutModule {}
