import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Dienststellung e2e test', () => {

    let navBarPage: NavBarPage;
    let dienststellungDialogPage: DienststellungDialogPage;
    let dienststellungComponentsPage: DienststellungComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Dienststellungs', () => {
        navBarPage.goToEntity('dienststellung-ff');
        dienststellungComponentsPage = new DienststellungComponentsPage();
        expect(dienststellungComponentsPage.getTitle()).toMatch(/ffManagementSuiteApp.dienststellung.home.title/);

    });

    it('should load create Dienststellung dialog', () => {
        dienststellungComponentsPage.clickOnCreateButton();
        dienststellungDialogPage = new DienststellungDialogPage();
        expect(dienststellungDialogPage.getModalTitle()).toMatch(/ffManagementSuiteApp.dienststellung.home.createOrEditLabel/);
        dienststellungDialogPage.close();
    });

    it('should create and save Dienststellungs', () => {
        dienststellungComponentsPage.clickOnCreateButton();
        dienststellungDialogPage.setTitelInput('titel');
        expect(dienststellungDialogPage.getTitelInput()).toMatch('titel');
        dienststellungDialogPage.setAbkuerzungInput('abkuerzung');
        expect(dienststellungDialogPage.getAbkuerzungInput()).toMatch('abkuerzung');
        dienststellungDialogPage.save();
        expect(dienststellungDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class DienststellungComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-dienststellung-ff div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class DienststellungDialogPage {
    modalTitle = element(by.css('h4#myDienststellungLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    titelInput = element(by.css('input#field_titel'));
    abkuerzungInput = element(by.css('input#field_abkuerzung'));

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
