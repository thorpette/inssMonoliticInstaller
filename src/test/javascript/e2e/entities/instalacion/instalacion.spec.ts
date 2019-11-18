import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { InstalacionComponentsPage, InstalacionDeleteDialog, InstalacionUpdatePage } from './instalacion.page-object';

const expect = chai.expect;

describe('Instalacion e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let instalacionComponentsPage: InstalacionComponentsPage;
  let instalacionUpdatePage: InstalacionUpdatePage;
  let instalacionDeleteDialog: InstalacionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Instalacions', async () => {
    await navBarPage.goToEntity('instalacion');
    instalacionComponentsPage = new InstalacionComponentsPage();
    await browser.wait(ec.visibilityOf(instalacionComponentsPage.title), 5000);
    expect(await instalacionComponentsPage.getTitle()).to.eq('Instalacions');
  });

  it('should load create Instalacion page', async () => {
    await instalacionComponentsPage.clickOnCreateButton();
    instalacionUpdatePage = new InstalacionUpdatePage();
    expect(await instalacionUpdatePage.getPageTitle()).to.eq('Create or edit a Instalacion');
    await instalacionUpdatePage.cancel();
  });

  it('should create and save Instalacions', async () => {
    const nbButtonsBeforeCreate = await instalacionComponentsPage.countDeleteButtons();

    await instalacionComponentsPage.clickOnCreateButton();
    await promise.all([instalacionUpdatePage.setNameInput('name'), instalacionUpdatePage.setDescripcionInput('descripcion')]);
    expect(await instalacionUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await instalacionUpdatePage.getDescripcionInput()).to.eq(
      'descripcion',
      'Expected Descripcion value to be equals to descripcion'
    );
    await instalacionUpdatePage.save();
    expect(await instalacionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await instalacionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Instalacion', async () => {
    const nbButtonsBeforeDelete = await instalacionComponentsPage.countDeleteButtons();
    await instalacionComponentsPage.clickOnLastDeleteButton();

    instalacionDeleteDialog = new InstalacionDeleteDialog();
    expect(await instalacionDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Instalacion?');
    await instalacionDeleteDialog.clickOnConfirmButton();

    expect(await instalacionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
