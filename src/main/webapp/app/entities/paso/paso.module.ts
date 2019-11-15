import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InssMonoliticInstallerSharedModule } from 'app/shared/shared.module';
import { PasoComponent } from './paso.component';
import { PasoDetailComponent } from './paso-detail.component';
import { PasoUpdateComponent } from './paso-update.component';
import { PasoDeletePopupComponent, PasoDeleteDialogComponent } from './paso-delete-dialog.component';
import { pasoRoute, pasoPopupRoute } from './paso.route';

const ENTITY_STATES = [...pasoRoute, ...pasoPopupRoute];

@NgModule({
  imports: [InssMonoliticInstallerSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [PasoComponent, PasoDetailComponent, PasoUpdateComponent, PasoDeleteDialogComponent, PasoDeletePopupComponent],
  entryComponents: [PasoDeleteDialogComponent]
})
export class InssMonoliticInstallerPasoModule {}
