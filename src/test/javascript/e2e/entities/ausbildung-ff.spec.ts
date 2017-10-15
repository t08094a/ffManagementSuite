import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Ausbildung e2e test', () => {

    let navBarPage: NavBarPage;
    let ausbildungDialogPage: AusbildungDialogPage;
    let ausbildungComponentsPage: AusbildungComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Ausbildungs', () => {
        navBarPage.goToEntity('ausbildung-ff');
        ausbildungComponentsPage = new AusbildungComponentsPage();
        expect(ausbildungComponentsPage.getTitle()).toMatch(/ffManagementSuiteApp.ausbildung.home.title/);

    });

    it('should load create Ausbildung dialog', () => {
        ausbildungComponentsPage.clickOnCreateButton();
        ausbildungDialogPage = new AusbildungDialogPage();
        expect(ausbildungDialogPage.getModalTitle()).toMatch(/ffManagementSuiteApp.ausbildung.home.createOrEditLabel/);
        ausbildungDialogPage.close();
    });

    it('should create and save Ausbildungs', () => {
        ausbildungComponentsPage.clickOnCreateButton();
        ausbildungDialogPage.setBeginnInput('2000-12-31');
        expect(ausbildungDialogPage.getBeginnInput()).toMatch('2000-12-31');
        ausbildungDialogPage.setEndeInput('2000-12-31');
        expect(ausbildungDialogPage.getEndeInput()).toMatch('2000-12-31');
        ausbildungDialogPage.setZeugnisInput(absolutePath);
        ausbildungDialogPage.personSelectLastOption();
        ausbildungDialogPage.lehrgangSelectLastOption();
        ausbildungDialogPage.save();
        expect(ausbildungDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class AusbildungComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-ausbildung-ff div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class AusbildungDialogPage {
    modalTitle = element(by.css('h4#myAusbildungLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    beginnInput = element(by.css('input#field_beginn'));
    endeInput = element(by.css('input#field_ende'));
    zeugnisInput = element(by.css('input#file_zeugnis'));
    personSelect = element(by.css('select#field_person'));
    lehrgangSelect = element(by.css('select#field_lehrgang'));

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

    setZeugnisInput = function (zeugnis) {
        this.zeugnisInput.sendKeys(zeugnis);
    }

    getZeugnisInput = function () {
        return this.zeugnisInput.getAttribute('value');
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

    lehrgangSelectLastOption = function () {
        this.lehrgangSelect.all(by.tagName('option')).last().click();
    }

    lehrgangSelectOption = function (option) {
        this.lehrgangSelect.sendKeys(option);
    }

    getLehrgangSelect = function () {
        return this.lehrgangSelect;
    }

    getLehrgangSelectedOption = function () {
        return this.lehrgangSelect.element(by.css('option:checked')).getText();
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
