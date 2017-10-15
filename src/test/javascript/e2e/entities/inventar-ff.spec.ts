import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Inventar e2e test', () => {

    let navBarPage: NavBarPage;
    let inventarDialogPage: InventarDialogPage;
    let inventarComponentsPage: InventarComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Inventars', () => {
        navBarPage.goToEntity('inventar-ff');
        inventarComponentsPage = new InventarComponentsPage();
        expect(inventarComponentsPage.getTitle()).toMatch(/ffManagementSuiteApp.inventar.home.title/);

    });

    it('should load create Inventar dialog', () => {
        inventarComponentsPage.clickOnCreateButton();
        inventarDialogPage = new InventarDialogPage();
        expect(inventarDialogPage.getModalTitle()).toMatch(/ffManagementSuiteApp.inventar.home.createOrEditLabel/);
        inventarDialogPage.close();
    });

    it('should create and save Inventars', () => {
        inventarComponentsPage.clickOnCreateButton();
        inventarDialogPage.setNummerInput('5');
        expect(inventarDialogPage.getNummerInput()).toMatch('5');
        inventarDialogPage.setAngeschafftAmInput('2000-12-31');
        expect(inventarDialogPage.getAngeschafftAmInput()).toMatch('2000-12-31');
        inventarDialogPage.setAusgemustertAmInput('2000-12-31');
        expect(inventarDialogPage.getAusgemustertAmInput()).toMatch('2000-12-31');
        inventarDialogPage.kategorieSelectLastOption();
        inventarDialogPage.save();
        expect(inventarDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class InventarComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-inventar-ff div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class InventarDialogPage {
    modalTitle = element(by.css('h4#myInventarLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nummerInput = element(by.css('input#field_nummer'));
    angeschafftAmInput = element(by.css('input#field_angeschafftAm'));
    ausgemustertAmInput = element(by.css('input#field_ausgemustertAm'));
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
