import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { InssMonoliticInstallerSharedModule } from 'app/shared/shared.module';

import { JhiTrackerComponent } from './tracker.component';

import { trackerRoute } from './tracker.route';

@NgModule({
  imports: [InssMonoliticInstallerSharedModule, RouterModule.forChild([trackerRoute])],
  declarations: [JhiTrackerComponent]
})
export class TrackerModule {}
