import { element, by, ElementFinder } from 'protractor';

export class MockComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-mock div table .btn-danger'));
  title = element.all(by.css('jhi-mock div h2#page-heading span')).first();

  async clickOnCreateButton() {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton() {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getText();
  }
}

export class MockUpdatePage {
  pageTitle = element(by.id('jhi-mock-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  inputInput = element(by.id('field_input'));
  outputInput = element(by.id('field_output'));
  urlInput = element(by.id('field_url'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setInputInput(input) {
    await this.inputInput.sendKeys(input);
  }

  async getInputInput() {
    return await this.inputInput.getAttribute('value');
  }

  async setOutputInput(output) {
    await this.outputInput.sendKeys(output);
  }

  async getOutputInput() {
    return await this.outputInput.getAttribute('value');
  }

  async setUrlInput(url) {
    await this.urlInput.sendKeys(url);
  }

  async getUrlInput() {
    return await this.urlInput.getAttribute('value');
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class MockDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-mock-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-mock'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
