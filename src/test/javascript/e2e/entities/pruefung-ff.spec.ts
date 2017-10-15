import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Pruefung e2e test', () => {

    let navBarPage: NavBarPage;
    let pruefungDialogPage: PruefungDialogPage;
    let pruefungComponentsPage: PruefungComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Pruefungs', () => {
        navBarPage.goToEntity('pruefung-ff');
        pruefungComponentsPage = new PruefungComponentsPage();
        expect(pruefungComponentsPage.getTitle()).toMatch(/ffManagementSuiteApp.pruefung.home.title/);

    });

    it('should load create Pruefung dialog', () => {
        pruefungComponentsPage.clickOnCreateButton();
        pruefungDialogPage = new PruefungDialogPage();
        expect(pruefungDialogPage.getModalTitle()).toMatch(/ffManagementSuiteApp.pruefung.home.createOrEditLabel/);
        pruefungDialogPage.close();
    });

    it('should create and save Pruefungs', () => {
        pruefungComponentsPage.clickOnCreateButton();
        pruefungDialogPage.setBezeichnungInput('bezeichnung');
        expect(pruefungDialogPage.getBezeichnungInput()).toMatch('bezeichnung');
        pruefungDialogPage.setBeginnInput('2000-12-31');
        expect(pruefungDialogPage.getBeginnInput()).toMatch('2000-12-31');
        pruefungDialogPage.setLetztePruefungInput('2000-12-31');
        expect(pruefungDialogPage.getLetztePruefungInput()).toMatch('2000-12-31');
        pruefungDialogPage.setIntervallWertInput('5');
        expect(pruefungDialogPage.getIntervallWertInput()).toMatch('5');
        pruefungDialogPage.intervallEinheitSelectLastOption();
        pruefungDialogPage.setKostenInput('5');
        expect(pruefungDialogPage.getKostenInput()).toMatch('5');
        pruefungDialogPage.inventarKategorieSelectLastOption();
        pruefungDialogPage.save();
        expect(pruefungDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class PruefungComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-pruefung-ff div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class PruefungDialogPage {
    modalTitle = element(by.css('h4#myPruefungLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    bezeichnungInput = element(by.css('input#field_bezeichnung'));
    beginnInput = element(by.css('input#field_beginn'));
    letztePruefungInput = element(by.css('input#field_letztePruefung'));
    intervallWertInput = element(by.css('input#field_intervallWert'));
    intervallEinheitSelect = element(by.css('select#field_intervallEinheit'));
    kostenInput = element(by.css('input#field_kosten'));
    inventarKategorieSelect = element(by.css('select#field_inventarKategorie'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setBezeichnungInput = function (bezeichnung) {
        this.bezeichnungInput.sendKeys(bezeichnung);
    }

    getBezeichnungInput = function () {
        return this.bezeichnungInput.getAttribute('value');
    }

    setBeginnInput = function (beginn) {
        this.beginnInput.sendKeys(beginn);
    }

    getBeginnInput = function () {
        return this.beginnInput.getAttribute('value');
    }

    setLetztePruefungInput = function (letztePruefung) {
        this.letztePruefungInput.sendKeys(letztePruefung);
    }

    getLetztePruefungInput = function () {
        return this.letztePruefungInput.getAttribute('value');
    }

    setIntervallWertInput = function (intervallWert) {
        this.intervallWertInput.sendKeys(intervallWert);
    }

    getIntervallWertInput = function () {
        return this.intervallWertInput.getAttribute('value');
    }

    setIntervallEinheitSelect = function (intervallEinheit) {
        this.intervallEinheitSelect.sendKeys(intervallEinheit);
    }

    getIntervallEinheitSelect = function () {
        return this.intervallEinheitSelect.element(by.css('option:checked')).getText();
    }

    intervallEinheitSelectLastOption = function () {
        this.intervallEinheitSelect.all(by.tagName('option')).last().click();
    }
    setKostenInput = function (kosten) {
        this.kostenInput.sendKeys(kosten);
    }

    getKostenInput = function () {
        return this.kostenInput.getAttribute('value');
    }

    inventarKategorieSelectLastOption = function () {
        this.inventarKategorieSelect.all(by.tagName('option')).last().click();
    }

    inventarKategorieSelectOption = function (option) {
        this.inventarKategorieSelect.sendKeys(option);
    }

    getInventarKategorieSelect = function () {
        return this.inventarKategorieSelect;
    }

    getInventarKategorieSelectedOption = function () {
        return this.inventarKategorieSelect.element(by.css('option:checked')).getText();
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
