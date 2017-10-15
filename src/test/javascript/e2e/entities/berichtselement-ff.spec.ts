import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Berichtselement e2e test', () => {

    let navBarPage: NavBarPage;
    let berichtselementDialogPage: BerichtselementDialogPage;
    let berichtselementComponentsPage: BerichtselementComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Berichtselements', () => {
        navBarPage.goToEntity('berichtselement-ff');
        berichtselementComponentsPage = new BerichtselementComponentsPage();
        expect(berichtselementComponentsPage.getTitle()).toMatch(/ffManagementSuiteApp.berichtselement.home.title/);

    });

    it('should load create Berichtselement dialog', () => {
        berichtselementComponentsPage.clickOnCreateButton();
        berichtselementDialogPage = new BerichtselementDialogPage();
        expect(berichtselementDialogPage.getModalTitle()).toMatch(/ffManagementSuiteApp.berichtselement.home.createOrEditLabel/);
        berichtselementDialogPage.close();
    });

    it('should create and save Berichtselements', () => {
        berichtselementComponentsPage.clickOnCreateButton();
        berichtselementDialogPage.setBeginnInput(12310020012301);
        expect(berichtselementDialogPage.getBeginnInput()).toMatch('2001-12-31T02:30');
        berichtselementDialogPage.setEndeInput(12310020012301);
        expect(berichtselementDialogPage.getEndeInput()).toMatch('2001-12-31T02:30');
        berichtselementDialogPage.setTitelInput('titel');
        expect(berichtselementDialogPage.getTitelInput()).toMatch('titel');
        berichtselementDialogPage.setBeschreibungInput('beschreibung');
        expect(berichtselementDialogPage.getBeschreibungInput()).toMatch('beschreibung');
        berichtselementDialogPage.setStundenInput('5');
        expect(berichtselementDialogPage.getStundenInput()).toMatch('5');
        berichtselementDialogPage.kategorieSelectLastOption();
        berichtselementDialogPage.save();
        expect(berichtselementDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class BerichtselementComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-berichtselement-ff div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class BerichtselementDialogPage {
    modalTitle = element(by.css('h4#myBerichtselementLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    beginnInput = element(by.css('input#field_beginn'));
    endeInput = element(by.css('input#field_ende'));
    titelInput = element(by.css('input#field_titel'));
    beschreibungInput = element(by.css('input#field_beschreibung'));
    stundenInput = element(by.css('input#field_stunden'));
    kategorieSelect = element(by.css('select#field_kategorie'));

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

    setTitelInput = function (titel) {
        this.titelInput.sendKeys(titel);
    }

    getTitelInput = function () {
        return this.titelInput.getAttribute('value');
    }

    setBeschreibungInput = function (beschreibung) {
        this.beschreibungInput.sendKeys(beschreibung);
    }

    getBeschreibungInput = function () {
        return this.beschreibungInput.getAttribute('value');
    }

    setStundenInput = function (stunden) {
        this.stundenInput.sendKeys(stunden);
    }

    getStundenInput = function () {
        return this.stundenInput.getAttribute('value');
    }

    setKategorieSelect = function (kategorie) {
        this.kategorieSelect.sendKeys(kategorie);
    }

    getKategorieSelect = function () {
        return this.kategorieSelect.element(by.css('option:checked')).getText();
    }

    kategorieSelectLastOption = function () {
        this.kategorieSelect.all(by.tagName('option')).last().click();
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
