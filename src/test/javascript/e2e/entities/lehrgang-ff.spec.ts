import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Lehrgang e2e test', () => {

    let navBarPage: NavBarPage;
    let lehrgangDialogPage: LehrgangDialogPage;
    let lehrgangComponentsPage: LehrgangComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Lehrgangs', () => {
        navBarPage.goToEntity('lehrgang-ff');
        lehrgangComponentsPage = new LehrgangComponentsPage();
        expect(lehrgangComponentsPage.getTitle()).toMatch(/ffManagementSuiteApp.lehrgang.home.title/);

    });

    it('should load create Lehrgang dialog', () => {
        lehrgangComponentsPage.clickOnCreateButton();
        lehrgangDialogPage = new LehrgangDialogPage();
        expect(lehrgangDialogPage.getModalTitle()).toMatch(/ffManagementSuiteApp.lehrgang.home.createOrEditLabel/);
        lehrgangDialogPage.close();
    });

    it('should create and save Lehrgangs', () => {
        lehrgangComponentsPage.clickOnCreateButton();
        lehrgangDialogPage.setTitelInput('titel');
        expect(lehrgangDialogPage.getTitelInput()).toMatch('titel');
        lehrgangDialogPage.setAbkuerzungInput('abkuerzung');
        expect(lehrgangDialogPage.getAbkuerzungInput()).toMatch('abkuerzung');
        lehrgangDialogPage.lehrgangSelectLastOption();
        lehrgangDialogPage.save();
        expect(lehrgangDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class LehrgangComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-lehrgang-ff div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class LehrgangDialogPage {
    modalTitle = element(by.css('h4#myLehrgangLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    titelInput = element(by.css('input#field_titel'));
    abkuerzungInput = element(by.css('input#field_abkuerzung'));
    lehrgangSelect = element(by.css('select#field_lehrgang'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setTitelInput = function (titel) {
        this.titelInput.sendKeys(titel);
    }

    getTitelInput = function () {
        return this.titelInput.getAttribute('value');
    }

    setAbkuerzungInput = function (abkuerzung) {
        this.abkuerzungInput.sendKeys(abkuerzung);
    }

    getAbkuerzungInput = function () {
        return this.abkuerzungInput.getAttribute('value');
    }

    lehrgangSelectLastOption = function () {
        this.lehrgangSelect.all(by.tagName('option')).last().click();
    }

    lehrgangSelectOption = function (option) {
        this.lehrgangSelect.sendKeys(option);
    }

    getLehrgangSelect = function () {
        return this.lehrgangSelect;
    }

    getLehrgangSelectedOption = function () {
        return this.lehrgangSelect.element(by.css('option:checked')).getText();
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
