import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Auspraegung e2e test', () => {

    let navBarPage: NavBarPage;
    let auspraegungDialogPage: AuspraegungDialogPage;
    let auspraegungComponentsPage: AuspraegungComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Auspraegungs', () => {
        navBarPage.goToEntity('auspraegung-ff');
        auspraegungComponentsPage = new AuspraegungComponentsPage();
        expect(auspraegungComponentsPage.getTitle()).toMatch(/ffManagementSuiteApp.auspraegung.home.title/);

    });

    it('should load create Auspraegung dialog', () => {
        auspraegungComponentsPage.clickOnCreateButton();
        auspraegungDialogPage = new AuspraegungDialogPage();
        expect(auspraegungDialogPage.getModalTitle()).toMatch(/ffManagementSuiteApp.auspraegung.home.createOrEditLabel/);
        auspraegungDialogPage.close();
    });

    it('should create and save Auspraegungs', () => {
        auspraegungComponentsPage.clickOnCreateButton();
        auspraegungDialogPage.setBezeichnungInput('bezeichnung');
        expect(auspraegungDialogPage.getBezeichnungInput()).toMatch('bezeichnung');
        auspraegungDialogPage.save();
        expect(auspraegungDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class AuspraegungComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-auspraegung-ff div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class AuspraegungDialogPage {
    modalTitle = element(by.css('h4#myAuspraegungLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    bezeichnungInput = element(by.css('input#field_bezeichnung'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setBezeichnungInput = function (bezeichnung) {
        this.bezeichnungInput.sendKeys(bezeichnung);
    }

    getBezeichnungInput = function () {
        return this.bezeichnungInput.getAttribute('value');
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
