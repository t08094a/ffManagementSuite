import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Erreichbarkeit e2e test', () => {

    let navBarPage: NavBarPage;
    let erreichbarkeitDialogPage: ErreichbarkeitDialogPage;
    let erreichbarkeitComponentsPage: ErreichbarkeitComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Erreichbarkeits', () => {
        navBarPage.goToEntity('erreichbarkeit-ff');
        erreichbarkeitComponentsPage = new ErreichbarkeitComponentsPage();
        expect(erreichbarkeitComponentsPage.getTitle()).toMatch(/ffManagementSuiteApp.erreichbarkeit.home.title/);

    });

    it('should load create Erreichbarkeit dialog', () => {
        erreichbarkeitComponentsPage.clickOnCreateButton();
        erreichbarkeitDialogPage = new ErreichbarkeitDialogPage();
        expect(erreichbarkeitDialogPage.getModalTitle()).toMatch(/ffManagementSuiteApp.erreichbarkeit.home.createOrEditLabel/);
        erreichbarkeitDialogPage.close();
    });

    it('should create and save Erreichbarkeits', () => {
        erreichbarkeitComponentsPage.clickOnCreateButton();
        erreichbarkeitDialogPage.setTypInput('typ');
        expect(erreichbarkeitDialogPage.getTypInput()).toMatch('typ');
        erreichbarkeitDialogPage.setVorwahlInput('vorwahl');
        expect(erreichbarkeitDialogPage.getVorwahlInput()).toMatch('vorwahl');
        erreichbarkeitDialogPage.setRufnummerInput('rufnummer');
        expect(erreichbarkeitDialogPage.getRufnummerInput()).toMatch('rufnummer');
        erreichbarkeitDialogPage.personSelectLastOption();
        erreichbarkeitDialogPage.save();
        expect(erreichbarkeitDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ErreichbarkeitComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-erreichbarkeit-ff div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ErreichbarkeitDialogPage {
    modalTitle = element(by.css('h4#myErreichbarkeitLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    typInput = element(by.css('input#field_typ'));
    vorwahlInput = element(by.css('input#field_vorwahl'));
    rufnummerInput = element(by.css('input#field_rufnummer'));
    personSelect = element(by.css('select#field_person'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setTypInput = function (typ) {
        this.typInput.sendKeys(typ);
    }

    getTypInput = function () {
        return this.typInput.getAttribute('value');
    }

    setVorwahlInput = function (vorwahl) {
        this.vorwahlInput.sendKeys(vorwahl);
    }

    getVorwahlInput = function () {
        return this.vorwahlInput.getAttribute('value');
    }

    setRufnummerInput = function (rufnummer) {
        this.rufnummerInput.sendKeys(rufnummer);
    }

    getRufnummerInput = function () {
        return this.rufnummerInput.getAttribute('value');
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
