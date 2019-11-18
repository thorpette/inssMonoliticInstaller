import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Mock } from 'app/shared/model/mock.model';
import { MockService } from './mock.service';
import { MockComponent } from './mock.component';
import { MockDetailComponent } from './mock-detail.component';
import { MockUpdateComponent } from './mock-update.component';
import { MockDeletePopupComponent } from './mock-delete-dialog.component';
import { IMock } from 'app/shared/model/mock.model';

@Injectable({ providedIn: 'root' })
export class MockResolve implements Resolve<IMock> {
  constructor(private service: MockService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMock> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((mock: HttpResponse<Mock>) => mock.body));
    }
    return of(new Mock());
  }
}

export const mockRoute: Routes = [
  {
    path: '',
    component: MockComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Mocks'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MockDetailComponent,
    resolve: {
      mock: MockResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Mocks'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MockUpdateComponent,
    resolve: {
      mock: MockResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Mocks'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MockUpdateComponent,
    resolve: {
      mock: MockResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Mocks'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const mockPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: MockDeletePopupComponent,
    resolve: {
      mock: MockResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Mocks'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
