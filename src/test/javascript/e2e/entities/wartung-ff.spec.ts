import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Wartung e2e test', () => {

    let navBarPage: NavBarPage;
    let wartungDialogPage: WartungDialogPage;
    let wartungComponentsPage: WartungComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Wartungs', () => {
        navBarPage.goToEntity('wartung-ff');
        wartungComponentsPage = new WartungComponentsPage();
        expect(wartungComponentsPage.getTitle()).toMatch(/ffManagementSuiteApp.wartung.home.title/);

    });

    it('should load create Wartung dialog', () => {
        wartungComponentsPage.clickOnCreateButton();
        wartungDialogPage = new WartungDialogPage();
        expect(wartungDialogPage.getModalTitle()).toMatch(/ffManagementSuiteApp.wartung.home.createOrEditLabel/);
        wartungDialogPage.close();
    });

    it('should create and save Wartungs', () => {
        wartungComponentsPage.clickOnCreateButton();
        wartungDialogPage.setBezeichnungInput('bezeichnung');
        expect(wartungDialogPage.getBezeichnungInput()).toMatch('bezeichnung');
        wartungDialogPage.setBeginnInput('2000-12-31');
        expect(wartungDialogPage.getBeginnInput()).toMatch('2000-12-31');
        wartungDialogPage.setLetzteWartungInput('2000-12-31');
        expect(wartungDialogPage.getLetzteWartungInput()).toMatch('2000-12-31');
        wartungDialogPage.setIntervallWertInput('5');
        expect(wartungDialogPage.getIntervallWertInput()).toMatch('5');
        wartungDialogPage.intervallEinheitSelectLastOption();
        wartungDialogPage.setKostenInput('5');
        expect(wartungDialogPage.getKostenInput()).toMatch('5');
        wartungDialogPage.inventarKategorieSelectLastOption();
        wartungDialogPage.save();
        expect(wartungDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class WartungComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-wartung-ff div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class WartungDialogPage {
    modalTitle = element(by.css('h4#myWartungLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    bezeichnungInput = element(by.css('input#field_bezeichnung'));
    beginnInput = element(by.css('input#field_beginn'));
    letzteWartungInput = element(by.css('input#field_letzteWartung'));
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

    setLetzteWartungInput = function (letzteWartung) {
        this.letzteWartungInput.sendKeys(letzteWartung);
    }

    getLetzteWartungInput = function () {
        return this.letzteWartungInput.getAttribute('value');
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
