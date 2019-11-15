import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PasoComponentsPage, PasoDeleteDialog, PasoUpdatePage } from './paso.page-object';

const expect = chai.expect;

describe('Paso e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let pasoComponentsPage: PasoComponentsPage;
  let pasoUpdatePage: PasoUpdatePage;
  let pasoDeleteDialog: PasoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Pasos', async () => {
    await navBarPage.goToEntity('paso');
    pasoComponentsPage = new PasoComponentsPage();
    await browser.wait(ec.visibilityOf(pasoComponentsPage.title), 5000);
    expect(await pasoComponentsPage.getTitle()).to.eq('Pasos');
  });

  it('should load create Paso page', async () => {
    await pasoComponentsPage.clickOnCreateButton();
    pasoUpdatePage = new PasoUpdatePage();
    expect(await pasoUpdatePage.getPageTitle()).to.eq('Create or edit a Paso');
    await pasoUpdatePage.cancel();
  });

  it('should create and save Pasos', async () => {
    const nbButtonsBeforeCreate = await pasoComponentsPage.countDeleteButtons();

    await pasoComponentsPage.clickOnCreateButton();
    await promise.all([
      pasoUpdatePage.setNameInput('name'),
      pasoUpdatePage.commandSelectLastOption(),
      pasoUpdatePage.setOrigenInput('origen'),
      pasoUpdatePage.setDestinoInput('destino'),
      pasoUpdatePage.setParametroInput('parametro'),
      pasoUpdatePage.instalacionSelectLastOption()
    ]);
    expect(await pasoUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await pasoUpdatePage.getOrigenInput()).to.eq('origen', 'Expected Origen value to be equals to origen');
    expect(await pasoUpdatePage.getDestinoInput()).to.eq('destino', 'Expected Destino value to be equals to destino');
    expect(await pasoUpdatePage.getParametroInput()).to.eq('parametro', 'Expected Parametro value to be equals to parametro');
    await pasoUpdatePage.save();
    expect(await pasoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await pasoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Paso', async () => {
    const nbButtonsBeforeDelete = await pasoComponentsPage.countDeleteButtons();
    await pasoComponentsPage.clickOnLastDeleteButton();

    pasoDeleteDialog = new PasoDeleteDialog();
    expect(await pasoDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Paso?');
    await pasoDeleteDialog.clickOnConfirmButton();

    expect(await pasoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
