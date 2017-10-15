import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Schutzausruestung e2e test', () => {

    let navBarPage: NavBarPage;
    let schutzausruestungDialogPage: SchutzausruestungDialogPage;
    let schutzausruestungComponentsPage: SchutzausruestungComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Schutzausruestungs', () => {
        navBarPage.goToEntity('schutzausruestung-ff');
        schutzausruestungComponentsPage = new SchutzausruestungComponentsPage();
        expect(schutzausruestungComponentsPage.getTitle()).toMatch(/ffManagementSuiteApp.schutzausruestung.home.title/);

    });

    it('should load create Schutzausruestung dialog', () => {
        schutzausruestungComponentsPage.clickOnCreateButton();
        schutzausruestungDialogPage = new SchutzausruestungDialogPage();
        expect(schutzausruestungDialogPage.getModalTitle()).toMatch(/ffManagementSuiteApp.schutzausruestung.home.createOrEditLabel/);
        schutzausruestungDialogPage.close();
    });

    it('should create and save Schutzausruestungs', () => {
        schutzausruestungComponentsPage.clickOnCreateButton();
        schutzausruestungDialogPage.setNummerInput('5');
        expect(schutzausruestungDialogPage.getNummerInput()).toMatch('5');
        schutzausruestungDialogPage.setAngeschafftAmInput('2000-12-31');
        expect(schutzausruestungDialogPage.getAngeschafftAmInput()).toMatch('2000-12-31');
        schutzausruestungDialogPage.setAusgemustertAmInput('2000-12-31');
        expect(schutzausruestungDialogPage.getAusgemustertAmInput()).toMatch('2000-12-31');
        schutzausruestungDialogPage.setGroesseInput('groesse');
        expect(schutzausruestungDialogPage.getGroesseInput()).toMatch('groesse');
        schutzausruestungDialogPage.kategorieSelectLastOption();
        schutzausruestungDialogPage.auspraegungSelectLastOption();
        schutzausruestungDialogPage.besitzerSelectLastOption();
        schutzausruestungDialogPage.save();
        expect(schutzausruestungDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class SchutzausruestungComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-schutzausruestung-ff div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class SchutzausruestungDialogPage {
    modalTitle = element(by.css('h4#mySchutzausruestungLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nummerInput = element(by.css('input#field_nummer'));
    angeschafftAmInput = element(by.css('input#field_angeschafftAm'));
    ausgemustertAmInput = element(by.css('input#field_ausgemustertAm'));
    groesseInput = element(by.css('input#field_groesse'));
    kategorieSelect = element(by.css('select#field_kategorie'));
    auspraegungSelect = element(by.css('select#field_auspraegung'));
    besitzerSelect = element(by.css('select#field_besitzer'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNummerInput = function (nummer) {
        this.nummerInput.sendKeys(nummer);
    }

    getNummerInput = function () {
        return this.nummerInput.getAttribute('value');
    }

    setAngeschafftAmInput = function (angeschafftAm) {
        this.angeschafftAmInput.sendKeys(angeschafftAm);
    }

    getAngeschafftAmInput = function () {
        return this.angeschafftAmInput.getAttribute('value');
    }

    setAusgemustertAmInput = function (ausgemustertAm) {
        this.ausgemustertAmInput.sendKeys(ausgemustertAm);
    }

    getAusgemustertAmInput = function () {
        return this.ausgemustertAmInput.getAttribute('value');
    }

    setGroesseInput = function (groesse) {
        this.groesseInput.sendKeys(groesse);
    }

    getGroesseInput = function () {
        return this.groesseInput.getAttribute('value');
    }

    kategorieSelectLastOption = function () {
        this.kategorieSelect.all(by.tagName('option')).last().click();
    }

    kategorieSelectOption = function (option) {
        this.kategorieSelect.sendKeys(option);
    }

    getKategorieSelect = function () {
        return this.kategorieSelect;
    }

    getKategorieSelectedOption = function () {
        return this.kategorieSelect.element(by.css('option:checked')).getText();
    }

    auspraegungSelectLastOption = function () {
        this.auspraegungSelect.all(by.tagName('option')).last().click();
    }

    auspraegungSelectOption = function (option) {
        this.auspraegungSelect.sendKeys(option);
    }

    getAuspraegungSelect = function () {
        return this.auspraegungSelect;
    }

    getAuspraegungSelectedOption = function () {
        return this.auspraegungSelect.element(by.css('option:checked')).getText();
    }

    besitzerSelectLastOption = function () {
        this.besitzerSelect.all(by.tagName('option')).last().click();
    }

    besitzerSelectOption = function (option) {
        this.besitzerSelect.sendKeys(option);
    }

    getBesitzerSelect = function () {
        return this.besitzerSelect;
    }

    getBesitzerSelectedOption = function () {
        return this.besitzerSelect.element(by.css('option:checked')).getText();
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
