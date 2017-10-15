import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Email e2e test', () => {

    let navBarPage: NavBarPage;
    let emailDialogPage: EmailDialogPage;
    let emailComponentsPage: EmailComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Emails', () => {
        navBarPage.goToEntity('email-ff');
        emailComponentsPage = new EmailComponentsPage();
        expect(emailComponentsPage.getTitle()).toMatch(/ffManagementSuiteApp.email.home.title/);

    });

    it('should load create Email dialog', () => {
        emailComponentsPage.clickOnCreateButton();
        emailDialogPage = new EmailDialogPage();
        expect(emailDialogPage.getModalTitle()).toMatch(/ffManagementSuiteApp.email.home.createOrEditLabel/);
        emailDialogPage.close();
    });

    it('should create and save Emails', () => {
        emailComponentsPage.clickOnCreateButton();
        emailDialogPage.setTypInput('typ');
        expect(emailDialogPage.getTypInput()).toMatch('typ');
        emailDialogPage.setAdresseInput('adresse');
        expect(emailDialogPage.getAdresseInput()).toMatch('adresse');
        emailDialogPage.personSelectLastOption();
        emailDialogPage.save();
        expect(emailDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class EmailComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-email-ff div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class EmailDialogPage {
    modalTitle = element(by.css('h4#myEmailLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    typInput = element(by.css('input#field_typ'));
    adresseInput = element(by.css('input#field_adresse'));
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

    setAdresseInput = function (adresse) {
        this.adresseInput.sendKeys(adresse);
    }

    getAdresseInput = function () {
        return this.adresseInput.getAttribute('value');
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
