import { element, by, ElementFinder } from 'protractor';

export class PasoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-paso div table .btn-danger'));
  title = element.all(by.css('jhi-paso div h2#page-heading span')).first();

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

export class PasoUpdatePage {
  pageTitle = element(by.id('jhi-paso-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  commandSelect = element(by.id('field_command'));
  origenInput = element(by.id('field_origen'));
  destinoInput = element(by.id('field_destino'));
  parametroInput = element(by.id('field_parametro'));
  instalacionSelect = element(by.id('field_instalacion'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setCommandSelect(command) {
    await this.commandSelect.sendKeys(command);
  }

  async getCommandSelect() {
    return await this.commandSelect.element(by.css('option:checked')).getText();
  }

  async commandSelectLastOption() {
    await this.commandSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setOrigenInput(origen) {
    await this.origenInput.sendKeys(origen);
  }

  async getOrigenInput() {
    return await this.origenInput.getAttribute('value');
  }

  async setDestinoInput(destino) {
    await this.destinoInput.sendKeys(destino);
  }

  async getDestinoInput() {
    return await this.destinoInput.getAttribute('value');
  }

  async setParametroInput(parametro) {
    await this.parametroInput.sendKeys(parametro);
  }

  async getParametroInput() {
    return await this.parametroInput.getAttribute('value');
  }

  async instalacionSelectLastOption() {
    await this.instalacionSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async instalacionSelectOption(option) {
    await this.instalacionSelect.sendKeys(option);
  }

  getInstalacionSelect(): ElementFinder {
    return this.instalacionSelect;
  }

  async getInstalacionSelectedOption() {
    return await this.instalacionSelect.element(by.css('option:checked')).getText();
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

export class PasoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-paso-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-paso'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}
