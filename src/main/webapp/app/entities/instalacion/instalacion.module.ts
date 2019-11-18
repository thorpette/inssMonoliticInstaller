import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InssMonoliticInstallerSharedModule } from 'app/shared/shared.module';
import { InstalacionComponent } from './instalacion.component';
import { InstalacionDetailComponent } from './instalacion-detail.component';
import { InstalacionUpdateComponent } from './instalacion-update.component';
import { InstalacionDeletePopupComponent, InstalacionDeleteDialogComponent } from './instalacion-delete-dialog.component';
import { instalacionRoute, instalacionPopupRoute } from './instalacion.route';

const ENTITY_STATES = [...instalacionRoute, ...instalacionPopupRoute];

@NgModule({
  imports: [InssMonoliticInstallerSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    InstalacionComponent,
    InstalacionDetailComponent,
    InstalacionUpdateComponent,
    InstalacionDeleteDialogComponent,
    InstalacionDeletePopupComponent
  ],
  entryComponents: [InstalacionDeleteDialogComponent]
})
export class InssMonoliticInstallerInstalacionModule {}
