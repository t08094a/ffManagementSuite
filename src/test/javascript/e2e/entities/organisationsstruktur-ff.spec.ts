import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Organisationsstruktur e2e test', () => {

    let navBarPage: NavBarPage;
    let organisationsstrukturDialogPage: OrganisationsstrukturDialogPage;
    let organisationsstrukturComponentsPage: OrganisationsstrukturComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Organisationsstrukturs', () => {
        navBarPage.goToEntity('organisationsstruktur-ff');
        organisationsstrukturComponentsPage = new OrganisationsstrukturComponentsPage();
        expect(organisationsstrukturComponentsPage.getTitle()).toMatch(/ffManagementSuiteApp.organisationsstruktur.home.title/);

    });

    it('should load create Organisationsstruktur dialog', () => {
        organisationsstrukturComponentsPage.clickOnCreateButton();
        organisationsstrukturDialogPage = new OrganisationsstrukturDialogPage();
        expect(organisationsstrukturDialogPage.getModalTitle()).toMatch(/ffManagementSuiteApp.organisationsstruktur.home.createOrEditLabel/);
        organisationsstrukturDialogPage.close();
    });

    it('should create and save Organisationsstrukturs', () => {
        organisationsstrukturComponentsPage.clickOnCreateButton();
        organisationsstrukturDialogPage.setNameInput('name');
        expect(organisationsstrukturDialogPage.getNameInput()).toMatch('name');
        organisationsstrukturDialogPage.parentSelectLastOption();
        organisationsstrukturDialogPage.save();
        expect(organisationsstrukturDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class OrganisationsstrukturComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-organisationsstruktur-ff div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class OrganisationsstrukturDialogPage {
    modalTitle = element(by.css('h4#myOrganisationsstrukturLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    parentSelect = element(by.css('select#field_parent'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function (name) {
        this.nameInput.sendKeys(name);
    }

    getNameInput = function () {
        return this.nameInput.getAttribute('value');
    }

    parentSelectLastOption = function () {
        this.parentSelect.all(by.tagName('option')).last().click();
    }

    parentSelectOption = function (option) {
        this.parentSelect.sendKeys(option);
    }

    getParentSelect = function () {
        return this.parentSelect;
    }

    getParentSelectedOption = function () {
        return this.parentSelect.element(by.css('option:checked')).getText();
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
