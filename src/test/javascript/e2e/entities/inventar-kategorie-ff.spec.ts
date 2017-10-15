import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('InventarKategorie e2e test', () => {

    let navBarPage: NavBarPage;
    let inventarKategorieDialogPage: InventarKategorieDialogPage;
    let inventarKategorieComponentsPage: InventarKategorieComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load InventarKategories', () => {
        navBarPage.goToEntity('inventar-kategorie-ff');
        inventarKategorieComponentsPage = new InventarKategorieComponentsPage();
        expect(inventarKategorieComponentsPage.getTitle()).toMatch(/ffManagementSuiteApp.inventarKategorie.home.title/);

    });

    it('should load create InventarKategorie dialog', () => {
        inventarKategorieComponentsPage.clickOnCreateButton();
        inventarKategorieDialogPage = new InventarKategorieDialogPage();
        expect(inventarKategorieDialogPage.getModalTitle()).toMatch(/ffManagementSuiteApp.inventarKategorie.home.createOrEditLabel/);
        inventarKategorieDialogPage.close();
    });

    it('should create and save InventarKategories', () => {
        inventarKategorieComponentsPage.clickOnCreateButton();
        inventarKategorieDialogPage.setTitelInput('titel');
        expect(inventarKategorieDialogPage.getTitelInput()).toMatch('titel');
        inventarKategorieDialogPage.uebergeordneteKategorieSelectLastOption();
        inventarKategorieDialogPage.save();
        expect(inventarKategorieDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class InventarKategorieComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-inventar-kategorie-ff div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class InventarKategorieDialogPage {
    modalTitle = element(by.css('h4#myInventarKategorieLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    titelInput = element(by.css('input#field_titel'));
    uebergeordneteKategorieSelect = element(by.css('select#field_uebergeordneteKategorie'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setTitelInput = function (titel) {
        this.titelInput.sendKeys(titel);
    }

    getTitelInput = function () {
        return this.titelInput.getAttribute('value');
    }

    uebergeordneteKategorieSelectLastOption = function () {
        this.uebergeordneteKategorieSelect.all(by.tagName('option')).last().click();
    }

    uebergeordneteKategorieSelectOption = function (option) {
        this.uebergeordneteKategorieSelect.sendKeys(option);
    }

    getUebergeordneteKategorieSelect = function () {
        return this.uebergeordneteKategorieSelect;
    }

    getUebergeordneteKategorieSelectedOption = function () {
        return this.uebergeordneteKategorieSelect.element(by.css('option:checked')).getText();
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
