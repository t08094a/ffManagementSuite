import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Reinigung e2e test', () => {

    let navBarPage: NavBarPage;
    let reinigungDialogPage: ReinigungDialogPage;
    let reinigungComponentsPage: ReinigungComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Reinigungs', () => {
        navBarPage.goToEntity('reinigung-ff');
        reinigungComponentsPage = new ReinigungComponentsPage();
        expect(reinigungComponentsPage.getTitle()).toMatch(/ffManagementSuiteApp.reinigung.home.title/);

    });

    it('should load create Reinigung dialog', () => {
        reinigungComponentsPage.clickOnCreateButton();
        reinigungDialogPage = new ReinigungDialogPage();
        expect(reinigungDialogPage.getModalTitle()).toMatch(/ffManagementSuiteApp.reinigung.home.createOrEditLabel/);
        reinigungDialogPage.close();
    });

    it('should create and save Reinigungs', () => {
        reinigungComponentsPage.clickOnCreateButton();
        reinigungDialogPage.setDurchfuehrungInput('2000-12-31');
        expect(reinigungDialogPage.getDurchfuehrungInput()).toMatch('2000-12-31');
        reinigungDialogPage.save();
        expect(reinigungDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ReinigungComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-reinigung-ff div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ReinigungDialogPage {
    modalTitle = element(by.css('h4#myReinigungLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    durchfuehrungInput = element(by.css('input#field_durchfuehrung'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setDurchfuehrungInput = function (durchfuehrung) {
        this.durchfuehrungInput.sendKeys(durchfuehrung);
    }

    getDurchfuehrungInput = function () {
        return this.durchfuehrungInput.getAttribute('value');
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
