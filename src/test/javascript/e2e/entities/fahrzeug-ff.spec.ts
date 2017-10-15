import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Fahrzeug e2e test', () => {

    let navBarPage: NavBarPage;
    let fahrzeugDialogPage: FahrzeugDialogPage;
    let fahrzeugComponentsPage: FahrzeugComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Fahrzeugs', () => {
        navBarPage.goToEntity('fahrzeug-ff');
        fahrzeugComponentsPage = new FahrzeugComponentsPage();
        expect(fahrzeugComponentsPage.getTitle()).toMatch(/ffManagementSuiteApp.fahrzeug.home.title/);

    });

    it('should load create Fahrzeug dialog', () => {
        fahrzeugComponentsPage.clickOnCreateButton();
        fahrzeugDialogPage = new FahrzeugDialogPage();
        expect(fahrzeugDialogPage.getModalTitle()).toMatch(/ffManagementSuiteApp.fahrzeug.home.createOrEditLabel/);
        fahrzeugDialogPage.close();
    });

    it('should create and save Fahrzeugs', () => {
        fahrzeugComponentsPage.clickOnCreateButton();
        fahrzeugDialogPage.setNummerInput('5');
        expect(fahrzeugDialogPage.getNummerInput()).toMatch('5');
        fahrzeugDialogPage.setAngeschafftAmInput('2000-12-31');
        expect(fahrzeugDialogPage.getAngeschafftAmInput()).toMatch('2000-12-31');
        fahrzeugDialogPage.setAusgemustertAmInput('2000-12-31');
        expect(fahrzeugDialogPage.getAusgemustertAmInput()).toMatch('2000-12-31');
        fahrzeugDialogPage.setNummernschildInput('nummernschild');
        expect(fahrzeugDialogPage.getNummernschildInput()).toMatch('nummernschild');
        fahrzeugDialogPage.setFunkrufnameInput('funkrufname');
        expect(fahrzeugDialogPage.getFunkrufnameInput()).toMatch('funkrufname');
        fahrzeugDialogPage.kategorieSelectLastOption();
        fahrzeugDialogPage.save();
        expect(fahrzeugDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class FahrzeugComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-fahrzeug-ff div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class FahrzeugDialogPage {
    modalTitle = element(by.css('h4#myFahrzeugLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nummerInput = element(by.css('input#field_nummer'));
    angeschafftAmInput = element(by.css('input#field_angeschafftAm'));
    ausgemustertAmInput = element(by.css('input#field_ausgemustertAm'));
    nummernschildInput = element(by.css('input#field_nummernschild'));
    funkrufnameInput = element(by.css('input#field_funkrufname'));
    kategorieSelect = element(by.css('select#field_kategorie'));

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

    setNummernschildInput = function (nummernschild) {
        this.nummernschildInput.sendKeys(nummernschild);
    }

    getNummernschildInput = function () {
        return this.nummernschildInput.getAttribute('value');
    }

    setFunkrufnameInput = function (funkrufname) {
        this.funkrufnameInput.sendKeys(funkrufname);
    }

    getFunkrufnameInput = function () {
        return this.funkrufnameInput.getAttribute('value');
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
