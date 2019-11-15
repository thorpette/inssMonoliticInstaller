import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { InssMonoliticInstallerSharedModule } from 'app/shared/shared.module';

import { JhiConfigurationComponent } from './configuration.component';

import { configurationRoute } from './configuration.route';

@NgModule({
  imports: [InssMonoliticInstallerSharedModule, RouterModule.forChild([configurationRoute])],
  declarations: [JhiConfigurationComponent]
})
export class ConfigurationModule {}
