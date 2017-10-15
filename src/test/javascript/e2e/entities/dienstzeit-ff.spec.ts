import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Dienstzeit e2e test', () => {

    let navBarPage: NavBarPage;
    let dienstzeitDialogPage: DienstzeitDialogPage;
    let dienstzeitComponentsPage: DienstzeitComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Dienstzeits', () => {
        navBarPage.goToEntity('dienstzeit-ff');
        dienstzeitComponentsPage = new DienstzeitComponentsPage();
        expect(dienstzeitComponentsPage.getTitle()).toMatch(/ffManagementSuiteApp.dienstzeit.home.title/);

    });

    it('should load create Dienstzeit dialog', () => {
        dienstzeitComponentsPage.clickOnCreateButton();
        dienstzeitDialogPage = new DienstzeitDialogPage();
        expect(dienstzeitDialogPage.getModalTitle()).toMatch(/ffManagementSuiteApp.dienstzeit.home.createOrEditLabel/);
        dienstzeitDialogPage.close();
    });

    it('should create and save Dienstzeits', () => {
        dienstzeitComponentsPage.clickOnCreateButton();
        dienstzeitDialogPage.setBeginnInput('2000-12-31');
        expect(dienstzeitDialogPage.getBeginnInput()).toMatch('2000-12-31');
        dienstzeitDialogPage.setEndeInput('2000-12-31');
        expect(dienstzeitDialogPage.getEndeInput()).toMatch('2000-12-31');
        dienstzeitDialogPage.personSelectLastOption();
        dienstzeitDialogPage.save();
        expect(dienstzeitDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class DienstzeitComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-dienstzeit-ff div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class DienstzeitDialogPage {
    modalTitle = element(by.css('h4#myDienstzeitLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    beginnInput = element(by.css('input#field_beginn'));
    endeInput = element(by.css('input#field_ende'));
    personSelect = element(by.css('select#field_person'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setBeginnInput = function (beginn) {
        this.beginnInput.sendKeys(beginn);
    }

    getBeginnInput = function () {
        return this.beginnInput.getAttribute('value');
    }

    setEndeInput = function (ende) {
        this.endeInput.sendKeys(ende);
    }

    getEndeInput = function () {
        return this.endeInput.getAttribute('value');
    }

    personSelectLastOption = function () {
        this.personSelect.all(by.tagName('option')).last().click();
    }

    personSelectOption = function (option) {
        this.personSelect.sendKeys(option);
    }

    getPersonSelect = function () {
        return this.personSelect;
    }

    getPersonSelectedOption = function () {
        return this.personSelect.element(by.css('option:checked')).getText();
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
