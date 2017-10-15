import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Fuehrerschein e2e test', () => {

    let navBarPage: NavBarPage;
    let fuehrerscheinDialogPage: FuehrerscheinDialogPage;
    let fuehrerscheinComponentsPage: FuehrerscheinComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Fuehrerscheins', () => {
        navBarPage.goToEntity('fuehrerschein-ff');
        fuehrerscheinComponentsPage = new FuehrerscheinComponentsPage();
        expect(fuehrerscheinComponentsPage.getTitle()).toMatch(/ffManagementSuiteApp.fuehrerschein.home.title/);

    });

    it('should load create Fuehrerschein dialog', () => {
        fuehrerscheinComponentsPage.clickOnCreateButton();
        fuehrerscheinDialogPage = new FuehrerscheinDialogPage();
        expect(fuehrerscheinDialogPage.getModalTitle()).toMatch(/ffManagementSuiteApp.fuehrerschein.home.createOrEditLabel/);
        fuehrerscheinDialogPage.close();
    });

    it('should create and save Fuehrerscheins', () => {
        fuehrerscheinComponentsPage.clickOnCreateButton();
        fuehrerscheinDialogPage.setKlasseInput('klasse');
        expect(fuehrerscheinDialogPage.getKlasseInput()).toMatch('klasse');
        fuehrerscheinDialogPage.save();
        expect(fuehrerscheinDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class FuehrerscheinComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-fuehrerschein-ff div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class FuehrerscheinDialogPage {
    modalTitle = element(by.css('h4#myFuehrerscheinLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    klasseInput = element(by.css('input#field_klasse'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setKlasseInput = function (klasse) {
        this.klasseInput.sendKeys(klasse);
    }

    getKlasseInput = function () {
        return this.klasseInput.getAttribute('value');
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
