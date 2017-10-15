import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Leistungspruefung e2e test', () => {

    let navBarPage: NavBarPage;
    let leistungspruefungDialogPage: LeistungspruefungDialogPage;
    let leistungspruefungComponentsPage: LeistungspruefungComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Leistungspruefungs', () => {
        navBarPage.goToEntity('leistungspruefung-ff');
        leistungspruefungComponentsPage = new LeistungspruefungComponentsPage();
        expect(leistungspruefungComponentsPage.getTitle()).toMatch(/ffManagementSuiteApp.leistungspruefung.home.title/);

    });

    it('should load create Leistungspruefung dialog', () => {
        leistungspruefungComponentsPage.clickOnCreateButton();
        leistungspruefungDialogPage = new LeistungspruefungDialogPage();
        expect(leistungspruefungDialogPage.getModalTitle()).toMatch(/ffManagementSuiteApp.leistungspruefung.home.createOrEditLabel/);
        leistungspruefungDialogPage.close();
    });

    it('should create and save Leistungspruefungs', () => {
        leistungspruefungComponentsPage.clickOnCreateButton();
        leistungspruefungDialogPage.typSelectLastOption();
        leistungspruefungDialogPage.setStufeInput('5');
        expect(leistungspruefungDialogPage.getStufeInput()).toMatch('5');
        leistungspruefungDialogPage.setAbgelegtAmInput('2000-12-31');
        expect(leistungspruefungDialogPage.getAbgelegtAmInput()).toMatch('2000-12-31');
        // leistungspruefungDialogPage.teilnehmerSelectLastOption();
        leistungspruefungDialogPage.save();
        expect(leistungspruefungDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class LeistungspruefungComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-leistungspruefung-ff div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class LeistungspruefungDialogPage {
    modalTitle = element(by.css('h4#myLeistungspruefungLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    typSelect = element(by.css('select#field_typ'));
    stufeInput = element(by.css('input#field_stufe'));
    abgelegtAmInput = element(by.css('input#field_abgelegtAm'));
    teilnehmerSelect = element(by.css('select#field_teilnehmer'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setTypSelect = function (typ) {
        this.typSelect.sendKeys(typ);
    }

    getTypSelect = function () {
        return this.typSelect.element(by.css('option:checked')).getText();
    }

    typSelectLastOption = function () {
        this.typSelect.all(by.tagName('option')).last().click();
    }
    setStufeInput = function (stufe) {
        this.stufeInput.sendKeys(stufe);
    }

    getStufeInput = function () {
        return this.stufeInput.getAttribute('value');
    }

    setAbgelegtAmInput = function (abgelegtAm) {
        this.abgelegtAmInput.sendKeys(abgelegtAm);
    }

    getAbgelegtAmInput = function () {
        return this.abgelegtAmInput.getAttribute('value');
    }

    teilnehmerSelectLastOption = function () {
        this.teilnehmerSelect.all(by.tagName('option')).last().click();
    }

    teilnehmerSelectOption = function (option) {
        this.teilnehmerSelect.sendKeys(option);
    }

    getTeilnehmerSelect = function () {
        return this.teilnehmerSelect;
    }

    getTeilnehmerSelectedOption = function () {
        return this.teilnehmerSelect.element(by.css('option:checked')).getText();
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
