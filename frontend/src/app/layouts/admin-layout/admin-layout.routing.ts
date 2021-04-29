import { Routes } from '@angular/router';

import { DashboardComponent } from '../../dashboard/dashboard.component';
import { TableListComponent } from '../../table-list/table-list.component';
import { TypographyComponent } from '../../typography/typography.component';
import { IconsComponent } from '../../icons/icons.component';
import { MapsComponent } from '../../maps/maps.component';
import { NotificationsComponent } from '../../notifications/notifications.component';
import { UpgradeComponent } from '../../upgrade/upgrade.component';
import { PacComponent } from 'app/pages/pac/pac.component';
import { ActividadesComponent } from 'app/actividades/actividades.component';
import { DocentesComponent } from 'app/docentes/docentes.component';
import { PeriodoComponent } from 'app/periodo/periodo.component';
import { SubactividadesComponent } from 'app/subactividades/subactividades.component';
import { DirectoresComponent } from 'app/directores/directores.component';
import { ForbiddenComponent } from 'app/http-errors/forbidden/forbidden.component';
import { NotFoundComponent } from 'app/http-errors/not-found/not-found.component';
import { RequestErrorComponent } from 'app/http-errors/request-error/request-error.component';
import { ReportComponent } from 'app/report/report.component';
import { VerificarActividadComponent } from 'app/directores/verificar-actividad/verificar-actividad.component';

export const AdminLayoutRoutes: Routes = [
     {
       path: '',
       children: [ {
         path: 'dashboard',
         component: DashboardComponent
     }]}, 
    //{
    // path: '',
    // children: [ {
    //   path: 'userprofile',
    //   component: UserProfileComponent
    // }]
    // }, {
    //   path: '',
    //   children: [ {
    //     path: 'icons',
    //     component: IconsComponent
    //     }]
    // }, {
    //     path: '',
    //     children: [ {
    //         path: 'notifications',
    //         component: NotificationsComponent
    //     }]
    // }, {
    //     path: '',
    //     children: [ {
    //         path: 'maps',
    //         component: MapsComponent
    //     }]
    // }, {
    //     path: '',
    //     children: [ {
    //         path: 'typography',
    //         component: TypographyComponent
    //     }]
    // }, {
    //     path: '',
    //     children: [ {
    //         path: 'upgrade',
    //         component: UpgradeComponent
    //     }]
    // }
   // { path: 'hojaSalida',      component: DashboardComponent },
    { path: 'matriculaPac',   component: PacComponent },
    { path: 'matriculaUte',     component: TableListComponent },
    { path: 'typography',     component: TypographyComponent },
    { path: 'icons',          component: IconsComponent },
    { path: 'maps',           component: MapsComponent },
    { path: 'notifications',  component: NotificationsComponent },
    { path: 'upgrade',        component: UpgradeComponent },
    //{ path: 'actividades/:code_periodo/:pidm/:fechIn', component: ActividadesComponent }
    { path: 'carga-general', component: DocentesComponent},
    { path: 'actividades/:code_periodo/:pidm', component: ActividadesComponent },
    { path: 'subactividades/:pidm/:code_periodo/:code_actividad', component: SubactividadesComponent },
    { path: 'carga-director', component: DirectoresComponent },
    { path: 'forbbiden', component: ForbiddenComponent},
    { path: 'not-found', component: NotFoundComponent},
    { path: 'request-error', component: RequestErrorComponent},
    { path: 'report', component: ReportComponent},
    { path: 'verificarCarga/:pidm', component: VerificarActividadComponent }
];
