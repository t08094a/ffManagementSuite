import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('DurchfuehrungWartung e2e test', () => {

    let navBarPage: NavBarPage;
    let durchfuehrungWartungDialogPage: DurchfuehrungWartungDialogPage;
    let durchfuehrungWartungComponentsPage: DurchfuehrungWartungComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load DurchfuehrungWartungs', () => {
        navBarPage.goToEntity('durchfuehrung-wartung-ff');
        durchfuehrungWartungComponentsPage = new DurchfuehrungWartungComponentsPage();
        expect(durchfuehrungWartungComponentsPage.getTitle()).toMatch(/ffManagementSuiteApp.durchfuehrungWartung.home.title/);

    });

    it('should load create DurchfuehrungWartung dialog', () => {
        durchfuehrungWartungComponentsPage.clickOnCreateButton();
        durchfuehrungWartungDialogPage = new DurchfuehrungWartungDialogPage();
        expect(durchfuehrungWartungDialogPage.getModalTitle()).toMatch(/ffManagementSuiteApp.durchfuehrungWartung.home.createOrEditLabel/);
        durchfuehrungWartungDialogPage.close();
    });

    it('should create and save DurchfuehrungWartungs', () => {
        durchfuehrungWartungComponentsPage.clickOnCreateButton();
        durchfuehrungWartungDialogPage.setDatumInput('2000-12-31');
        expect(durchfuehrungWartungDialogPage.getDatumInput()).toMatch('2000-12-31');
        durchfuehrungWartungDialogPage.setKostenInput('5');
        expect(durchfuehrungWartungDialogPage.getKostenInput()).toMatch('5');
        durchfuehrungWartungDialogPage.inventarSelectLastOption();
        durchfuehrungWartungDialogPage.schutzausruestungSelectLastOption();
        durchfuehrungWartungDialogPage.fahrzeugSelectLastOption();
        durchfuehrungWartungDialogPage.atemschutzInventarSelectLastOption();
        durchfuehrungWartungDialogPage.definitionSelectLastOption();
        durchfuehrungWartungDialogPage.save();
        expect(durchfuehrungWartungDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class DurchfuehrungWartungComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-durchfuehrung-wartung-ff div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class DurchfuehrungWartungDialogPage {
    modalTitle = element(by.css('h4#myDurchfuehrungWartungLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    datumInput = element(by.css('input#field_datum'));
    kostenInput = element(by.css('input#field_kosten'));
    inventarSelect = element(by.css('select#field_inventar'));
    schutzausruestungSelect = element(by.css('select#field_schutzausruestung'));
    fahrzeugSelect = element(by.css('select#field_fahrzeug'));
    atemschutzInventarSelect = element(by.css('select#field_atemschutzInventar'));
    definitionSelect = element(by.css('select#field_definition'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setDatumInput = function (datum) {
        this.datumInput.sendKeys(datum);
    }

    getDatumInput = function () {
        return this.datumInput.getAttribute('value');
    }

    setKostenInput = function (kosten) {
        this.kostenInput.sendKeys(kosten);
    }

    getKostenInput = function () {
        return this.kostenInput.getAttribute('value');
    }

    inventarSelectLastOption = function () {
        this.inventarSelect.all(by.tagName('option')).last().click();
    }

    inventarSelectOption = function (option) {
        this.inventarSelect.sendKeys(option);
    }

    getInventarSelect = function () {
        return this.inventarSelect;
    }

    getInventarSelectedOption = function () {
        return this.inventarSelect.element(by.css('option:checked')).getText();
    }

    schutzausruestungSelectLastOption = function () {
        this.schutzausruestungSelect.all(by.tagName('option')).last().click();
    }

    schutzausruestungSelectOption = function (option) {
        this.schutzausruestungSelect.sendKeys(option);
    }

    getSchutzausruestungSelect = function () {
        return this.schutzausruestungSelect;
    }

    getSchutzausruestungSelectedOption = function () {
        return this.schutzausruestungSelect.element(by.css('option:checked')).getText();
    }

    fahrzeugSelectLastOption = function () {
        this.fahrzeugSelect.all(by.tagName('option')).last().click();
    }

    fahrzeugSelectOption = function (option) {
        this.fahrzeugSelect.sendKeys(option);
    }

    getFahrzeugSelect = function () {
        return this.fahrzeugSelect;
    }

    getFahrzeugSelectedOption = function () {
        return this.fahrzeugSelect.element(by.css('option:checked')).getText();
    }

    atemschutzInventarSelectLastOption = function () {
        this.atemschutzInventarSelect.all(by.tagName('option')).last().click();
    }

    atemschutzInventarSelectOption = function (option) {
        this.atemschutzInventarSelect.sendKeys(option);
    }

    getAtemschutzInventarSelect = function () {
        return this.atemschutzInventarSelect;
    }

    getAtemschutzInventarSelectedOption = function () {
        return this.atemschutzInventarSelect.element(by.css('option:checked')).getText();
    }

    definitionSelectLastOption = function () {
        this.definitionSelect.all(by.tagName('option')).last().click();
    }

    definitionSelectOption = function (option) {
        this.definitionSelect.sendKeys(option);
    }

    getDefinitionSelect = function () {
        return this.definitionSelect;
    }

    getDefinitionSelectedOption = function () {
        return this.definitionSelect.element(by.css('option:checked')).getText();
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
