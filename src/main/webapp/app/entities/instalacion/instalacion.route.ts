import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Instalacion } from 'app/shared/model/instalacion.model';
import { InstalacionService } from './instalacion.service';
import { InstalacionComponent } from './instalacion.component';
import { InstalacionDetailComponent } from './instalacion-detail.component';
import { InstalacionUpdateComponent } from './instalacion-update.component';
import { InstalacionDeletePopupComponent } from './instalacion-delete-dialog.component';
import { IInstalacion } from 'app/shared/model/instalacion.model';

@Injectable({ providedIn: 'root' })
export class InstalacionResolve implements Resolve<IInstalacion> {
  constructor(private service: InstalacionService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInstalacion> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((instalacion: HttpResponse<Instalacion>) => instalacion.body));
    }
    return of(new Instalacion());
  }
}

export const instalacionRoute: Routes = [
  {
    path: '',
    component: InstalacionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Instalacions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InstalacionDetailComponent,
    resolve: {
      instalacion: InstalacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Instalacions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InstalacionUpdateComponent,
    resolve: {
      instalacion: InstalacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Instalacions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InstalacionUpdateComponent,
    resolve: {
      instalacion: InstalacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Instalacions'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const instalacionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: InstalacionDeletePopupComponent,
    resolve: {
      instalacion: InstalacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Instalacions'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
