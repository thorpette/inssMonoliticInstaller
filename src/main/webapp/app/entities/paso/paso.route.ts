import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Paso } from 'app/shared/model/paso.model';
import { PasoService } from './paso.service';
import { PasoComponent } from './paso.component';
import { PasoDetailComponent } from './paso-detail.component';
import { PasoUpdateComponent } from './paso-update.component';
import { PasoDeletePopupComponent } from './paso-delete-dialog.component';
import { IPaso } from 'app/shared/model/paso.model';

@Injectable({ providedIn: 'root' })
export class PasoResolve implements Resolve<IPaso> {
  constructor(private service: PasoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaso> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((paso: HttpResponse<Paso>) => paso.body));
    }
    return of(new Paso());
  }
}

export const pasoRoute: Routes = [
  {
    path: '',
    component: PasoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Pasos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PasoDetailComponent,
    resolve: {
      paso: PasoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Pasos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PasoUpdateComponent,
    resolve: {
      paso: PasoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Pasos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PasoUpdateComponent,
    resolve: {
      paso: PasoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Pasos'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pasoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PasoDeletePopupComponent,
    resolve: {
      paso: PasoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Pasos'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
