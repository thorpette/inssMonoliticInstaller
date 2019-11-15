import { NgModule } from '@angular/core';
import { InssMonoliticInstallerSharedLibsModule } from './shared-libs.module';
import { JhiAlertComponent } from './alert/alert.component';
import { JhiAlertErrorComponent } from './alert/alert-error.component';
import { JhiLoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';

@NgModule({
  imports: [InssMonoliticInstallerSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent, JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [
    InssMonoliticInstallerSharedLibsModule,
    JhiAlertComponent,
    JhiAlertErrorComponent,
    JhiLoginModalComponent,
    HasAnyAuthorityDirective
  ]
})
export class InssMonoliticInstallerSharedModule {}
