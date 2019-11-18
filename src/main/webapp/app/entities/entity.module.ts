import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'instalacion',
        loadChildren: () => import('./instalacion/instalacion.module').then(m => m.InssMonoliticInstallerInstalacionModule)
      },
      {
        path: 'paso',
        loadChildren: () => import('./paso/paso.module').then(m => m.InssMonoliticInstallerPasoModule)
      },
      {
        path: 'mock',
        loadChildren: () => import('./mock/mock.module').then(m => m.InssMonoliticInstallerMockModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class InssMonoliticInstallerEntityModule {}
