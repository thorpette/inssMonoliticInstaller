import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { InssMonoliticInstallerSharedModule } from 'app/shared/shared.module';
import { InssMonoliticInstallerCoreModule } from 'app/core/core.module';
import { InssMonoliticInstallerAppRoutingModule } from './app-routing.module';
import { InssMonoliticInstallerHomeModule } from './home/home.module';
import { InssMonoliticInstallerEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    InssMonoliticInstallerSharedModule,
    InssMonoliticInstallerCoreModule,
    InssMonoliticInstallerHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    InssMonoliticInstallerEntityModule,
    InssMonoliticInstallerAppRoutingModule
  ],
  declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [JhiMainComponent]
})
export class InssMonoliticInstallerAppModule {}
