import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MockComponentsPage, MockDeleteDialog, MockUpdatePage } from './mock.page-object';

const expect = chai.expect;

describe('Mock e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let mockComponentsPage: MockComponentsPage;
  let mockUpdatePage: MockUpdatePage;
  let mockDeleteDialog: MockDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Mocks', async () => {
    await navBarPage.goToEntity('mock');
    mockComponentsPage = new MockComponentsPage();
    await browser.wait(ec.visibilityOf(mockComponentsPage.title), 5000);
    expect(await mockComponentsPage.getTitle()).to.eq('Mocks');
  });

  it('should load create Mock page', async () => {
    await mockComponentsPage.clickOnCreateButton();
    mockUpdatePage = new MockUpdatePage();
    expect(await mockUpdatePage.getPageTitle()).to.eq('Create or edit a Mock');
    await mockUpdatePage.cancel();
  });

  it('should create and save Mocks', async () => {
    const nbButtonsBeforeCreate = await mockComponentsPage.countDeleteButtons();

    await mockComponentsPage.clickOnCreateButton();
    await promise.all([
      mockUpdatePage.setNameInput('name'),
      mockUpdatePage.setInputInput('input'),
      mockUpdatePage.setOutputInput('output'),
      mockUpdatePage.setUrlInput('url')
    ]);
    expect(await mockUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await mockUpdatePage.getInputInput()).to.eq('input', 'Expected Input value to be equals to input');
    expect(await mockUpdatePage.getOutputInput()).to.eq('output', 'Expected Output value to be equals to output');
    expect(await mockUpdatePage.getUrlInput()).to.eq('url', 'Expected Url value to be equals to url');
    await mockUpdatePage.save();
    expect(await mockUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await mockComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Mock', async () => {
    const nbButtonsBeforeDelete = await mockComponentsPage.countDeleteButtons();
    await mockComponentsPage.clickOnLastDeleteButton();

    mockDeleteDialog = new MockDeleteDialog();
    expect(await mockDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Mock?');
    await mockDeleteDialog.clickOnConfirmButton();

    expect(await mockComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
