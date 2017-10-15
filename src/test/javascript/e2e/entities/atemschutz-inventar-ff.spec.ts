import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('AtemschutzInventar e2e test', () => {

    let navBarPage: NavBarPage;
    let atemschutzInventarDialogPage: AtemschutzInventarDialogPage;
    let atemschutzInventarComponentsPage: AtemschutzInventarComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load AtemschutzInventars', () => {
        navBarPage.goToEntity('atemschutz-inventar-ff');
        atemschutzInventarComponentsPage = new AtemschutzInventarComponentsPage();
        expect(atemschutzInventarComponentsPage.getTitle()).toMatch(/ffManagementSuiteApp.atemschutzInventar.home.title/);

    });

    it('should load create AtemschutzInventar dialog', () => {
        atemschutzInventarComponentsPage.clickOnCreateButton();
        atemschutzInventarDialogPage = new AtemschutzInventarDialogPage();
        expect(atemschutzInventarDialogPage.getModalTitle()).toMatch(/ffManagementSuiteApp.atemschutzInventar.home.createOrEditLabel/);
        atemschutzInventarDialogPage.close();
    });

    it('should create and save AtemschutzInventars', () => {
        atemschutzInventarComponentsPage.clickOnCreateButton();
        atemschutzInventarDialogPage.setNummerInput('5');
        expect(atemschutzInventarDialogPage.getNummerInput()).toMatch('5');
        atemschutzInventarDialogPage.setAngeschafftAmInput('2000-12-31');
        expect(atemschutzInventarDialogPage.getAngeschafftAmInput()).toMatch('2000-12-31');
        atemschutzInventarDialogPage.setAusgemustertAmInput('2000-12-31');
        expect(atemschutzInventarDialogPage.getAusgemustertAmInput()).toMatch('2000-12-31');
        atemschutzInventarDialogPage.kategorieSelectLastOption();
        atemschutzInventarDialogPage.save();
        expect(atemschutzInventarDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class AtemschutzInventarComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-atemschutz-inventar-ff div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class AtemschutzInventarDialogPage {
    modalTitle = element(by.css('h4#myAtemschutzInventarLabel'));
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
