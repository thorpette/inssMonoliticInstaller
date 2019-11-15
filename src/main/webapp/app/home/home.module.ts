import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InssMonoliticInstallerSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [InssMonoliticInstallerSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent]
})
export class InssMonoliticInstallerHomeModule {}
