import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Person e2e test', () => {

    let navBarPage: NavBarPage;
    let personDialogPage: PersonDialogPage;
    let personComponentsPage: PersonComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load People', () => {
        navBarPage.goToEntity('person-ff');
        personComponentsPage = new PersonComponentsPage();
        expect(personComponentsPage.getTitle()).toMatch(/ffManagementSuiteApp.person.home.title/);

    });

    it('should load create Person dialog', () => {
        personComponentsPage.clickOnCreateButton();
        personDialogPage = new PersonDialogPage();
        expect(personDialogPage.getModalTitle()).toMatch(/ffManagementSuiteApp.person.home.createOrEditLabel/);
        personDialogPage.close();
    });

    it('should create and save People', () => {
        personComponentsPage.clickOnCreateButton();
        personDialogPage.setVornameInput('vorname');
        expect(personDialogPage.getVornameInput()).toMatch('vorname');
        personDialogPage.setNachnameInput('nachname');
        expect(personDialogPage.getNachnameInput()).toMatch('nachname');
        personDialogPage.setGeburtsdatumInput('2000-12-31');
        expect(personDialogPage.getGeburtsdatumInput()).toMatch('2000-12-31');
        personDialogPage.setStrasseInput('strasse');
        expect(personDialogPage.getStrasseInput()).toMatch('strasse');
        personDialogPage.setHausnummerInput('5');
        expect(personDialogPage.getHausnummerInput()).toMatch('5');
        personDialogPage.setAppendixInput('appendix');
        expect(personDialogPage.getAppendixInput()).toMatch('appendix');
        personDialogPage.setPostleitzahlInput('postleitzahl');
        expect(personDialogPage.getPostleitzahlInput()).toMatch('postleitzahl');
        personDialogPage.setOrtInput('ort');
        expect(personDialogPage.getOrtInput()).toMatch('ort');
        personDialogPage.statusSelectLastOption();
        personDialogPage.setEhrung25JahreInput('2000-12-31');
        expect(personDialogPage.getEhrung25JahreInput()).toMatch('2000-12-31');
        personDialogPage.setEhrung40JahreInput('2000-12-31');
        expect(personDialogPage.getEhrung40JahreInput()).toMatch('2000-12-31');
        personDialogPage.zugehoerigkeitSelectLastOption();
        personDialogPage.inDienststellungSelectLastOption();
        // personDialogPage.fuehrerscheineSelectLastOption();
        // personDialogPage.verfuegbarkeitenSelectLastOption();
        personDialogPage.save();
        expect(personDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class PersonComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-person-ff div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class PersonDialogPage {
    modalTitle = element(by.css('h4#myPersonLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    vornameInput = element(by.css('input#field_vorname'));
    nachnameInput = element(by.css('input#field_nachname'));
    geburtsdatumInput = element(by.css('input#field_geburtsdatum'));
    strasseInput = element(by.css('input#field_strasse'));
    hausnummerInput = element(by.css('input#field_hausnummer'));
    appendixInput = element(by.css('input#field_appendix'));
    postleitzahlInput = element(by.css('input#field_postleitzahl'));
    ortInput = element(by.css('input#field_ort'));
    statusSelect = element(by.css('select#field_status'));
    ehrung25JahreInput = element(by.css('input#field_ehrung25Jahre'));
    ehrung40JahreInput = element(by.css('input#field_ehrung40Jahre'));
    zugehoerigkeitSelect = element(by.css('select#field_zugehoerigkeit'));
    inDienststellungSelect = element(by.css('select#field_inDienststellung'));
    fuehrerscheineSelect = element(by.css('select#field_fuehrerscheine'));
    verfuegbarkeitenSelect = element(by.css('select#field_verfuegbarkeiten'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setVornameInput = function (vorname) {
        this.vornameInput.sendKeys(vorname);
    }

    getVornameInput = function () {
        return this.vornameInput.getAttribute('value');
    }

    setNachnameInput = function (nachname) {
        this.nachnameInput.sendKeys(nachname);
    }

    getNachnameInput = function () {
        return this.nachnameInput.getAttribute('value');
    }

    setGeburtsdatumInput = function (geburtsdatum) {
        this.geburtsdatumInput.sendKeys(geburtsdatum);
    }

    getGeburtsdatumInput = function () {
        return this.geburtsdatumInput.getAttribute('value');
    }

    setStrasseInput = function (strasse) {
        this.strasseInput.sendKeys(strasse);
    }

    getStrasseInput = function () {
        return this.strasseInput.getAttribute('value');
    }

    setHausnummerInput = function (hausnummer) {
        this.hausnummerInput.sendKeys(hausnummer);
    }

    getHausnummerInput = function () {
        return this.hausnummerInput.getAttribute('value');
    }

    setAppendixInput = function (appendix) {
        this.appendixInput.sendKeys(appendix);
    }

    getAppendixInput = function () {
        return this.appendixInput.getAttribute('value');
    }

    setPostleitzahlInput = function (postleitzahl) {
        this.postleitzahlInput.sendKeys(postleitzahl);
    }

    getPostleitzahlInput = function () {
        return this.postleitzahlInput.getAttribute('value');
    }

    setOrtInput = function (ort) {
        this.ortInput.sendKeys(ort);
    }

    getOrtInput = function () {
        return this.ortInput.getAttribute('value');
    }

    setStatusSelect = function (status) {
        this.statusSelect.sendKeys(status);
    }

    getStatusSelect = function () {
        return this.statusSelect.element(by.css('option:checked')).getText();
    }

    statusSelectLastOption = function () {
        this.statusSelect.all(by.tagName('option')).last().click();
    }
    setEhrung25JahreInput = function (ehrung25Jahre) {
        this.ehrung25JahreInput.sendKeys(ehrung25Jahre);
    }

    getEhrung25JahreInput = function () {
        return this.ehrung25JahreInput.getAttribute('value');
    }

    setEhrung40JahreInput = function (ehrung40Jahre) {
        this.ehrung40JahreInput.sendKeys(ehrung40Jahre);
    }

    getEhrung40JahreInput = function () {
        return this.ehrung40JahreInput.getAttribute('value');
    }

    zugehoerigkeitSelectLastOption = function () {
        this.zugehoerigkeitSelect.all(by.tagName('option')).last().click();
    }

    zugehoerigkeitSelectOption = function (option) {
        this.zugehoerigkeitSelect.sendKeys(option);
    }

    getZugehoerigkeitSelect = function () {
        return this.zugehoerigkeitSelect;
    }

    getZugehoerigkeitSelectedOption = function () {
        return this.zugehoerigkeitSelect.element(by.css('option:checked')).getText();
    }

    inDienststellungSelectLastOption = function () {
        this.inDienststellungSelect.all(by.tagName('option')).last().click();
    }

    inDienststellungSelectOption = function (option) {
        this.inDienststellungSelect.sendKeys(option);
    }

    getInDienststellungSelect = function () {
        return this.inDienststellungSelect;
    }

    getInDienststellungSelectedOption = function () {
        return this.inDienststellungSelect.element(by.css('option:checked')).getText();
    }

    fuehrerscheineSelectLastOption = function () {
        this.fuehrerscheineSelect.all(by.tagName('option')).last().click();
    }

    fuehrerscheineSelectOption = function (option) {
        this.fuehrerscheineSelect.sendKeys(option);
    }

    getFuehrerscheineSelect = function () {
        return this.fuehrerscheineSelect;
    }

    getFuehrerscheineSelectedOption = function () {
        return this.fuehrerscheineSelect.element(by.css('option:checked')).getText();
    }

    verfuegbarkeitenSelectLastOption = function () {
        this.verfuegbarkeitenSelect.all(by.tagName('option')).last().click();
    }

    verfuegbarkeitenSelectOption = function (option) {
        this.verfuegbarkeitenSelect.sendKeys(option);
    }

    getVerfuegbarkeitenSelect = function () {
        return this.verfuegbarkeitenSelect;
    }

    getVerfuegbarkeitenSelectedOption = function () {
        return this.verfuegbarkeitenSelect.element(by.css('option:checked')).getText();
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
