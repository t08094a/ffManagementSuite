import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Verfuegbarkeit e2e test', () => {

    let navBarPage: NavBarPage;
    let verfuegbarkeitDialogPage: VerfuegbarkeitDialogPage;
    let verfuegbarkeitComponentsPage: VerfuegbarkeitComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Verfuegbarkeits', () => {
        navBarPage.goToEntity('verfuegbarkeit-ff');
        verfuegbarkeitComponentsPage = new VerfuegbarkeitComponentsPage();
        expect(verfuegbarkeitComponentsPage.getTitle()).toMatch(/ffManagementSuiteApp.verfuegbarkeit.home.title/);

    });

    it('should load create Verfuegbarkeit dialog', () => {
        verfuegbarkeitComponentsPage.clickOnCreateButton();
        verfuegbarkeitDialogPage = new VerfuegbarkeitDialogPage();
        expect(verfuegbarkeitDialogPage.getModalTitle()).toMatch(/ffManagementSuiteApp.verfuegbarkeit.home.createOrEditLabel/);
        verfuegbarkeitDialogPage.close();
    });

    it('should create and save Verfuegbarkeits', () => {
        verfuegbarkeitComponentsPage.clickOnCreateButton();
        verfuegbarkeitDialogPage.setTitelInput('titel');
        expect(verfuegbarkeitDialogPage.getTitelInput()).toMatch('titel');
        verfuegbarkeitDialogPage.wocheneinteilungSelectLastOption();
        verfuegbarkeitDialogPage.tageszeitSelectLastOption();
        verfuegbarkeitDialogPage.save();
        expect(verfuegbarkeitDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class VerfuegbarkeitComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-verfuegbarkeit-ff div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class VerfuegbarkeitDialogPage {
    modalTitle = element(by.css('h4#myVerfuegbarkeitLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    titelInput = element(by.css('input#field_titel'));
    wocheneinteilungSelect = element(by.css('select#field_wocheneinteilung'));
    tageszeitSelect = element(by.css('select#field_tageszeit'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setTitelInput = function (titel) {
        this.titelInput.sendKeys(titel);
    }

    getTitelInput = function () {
        return this.titelInput.getAttribute('value');
    }

    setWocheneinteilungSelect = function (wocheneinteilung) {
        this.wocheneinteilungSelect.sendKeys(wocheneinteilung);
    }

    getWocheneinteilungSelect = function () {
        return this.wocheneinteilungSelect.element(by.css('option:checked')).getText();
    }

    wocheneinteilungSelectLastOption = function () {
        this.wocheneinteilungSelect.all(by.tagName('option')).last().click();
    }
    setTageszeitSelect = function (tageszeit) {
        this.tageszeitSelect.sendKeys(tageszeit);
    }

    getTageszeitSelect = function () {
        return this.tageszeitSelect.element(by.css('option:checked')).getText();
    }

    tageszeitSelectLastOption = function () {
        this.tageszeitSelect.all(by.tagName('option')).last().click();
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
