import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InssMonoliticInstallerSharedModule } from 'app/shared/shared.module';
import { MockComponent } from './mock.component';
import { MockDetailComponent } from './mock-detail.component';
import { MockUpdateComponent } from './mock-update.component';
import { MockDeletePopupComponent, MockDeleteDialogComponent } from './mock-delete-dialog.component';
import { mockRoute, mockPopupRoute } from './mock.route';

const ENTITY_STATES = [...mockRoute, ...mockPopupRoute];

@NgModule({
  imports: [InssMonoliticInstallerSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [MockComponent, MockDetailComponent, MockUpdateComponent, MockDeleteDialogComponent, MockDeletePopupComponent],
  entryComponents: [MockDeleteDialogComponent]
})
export class InssMonoliticInstallerMockModule {}
