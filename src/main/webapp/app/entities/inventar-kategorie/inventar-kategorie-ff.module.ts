import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FfManagementSuiteSharedModule } from '../../shared';
import {
    InventarKategorieFfService,
    InventarKategorieFfPopupService,
    InventarKategorieFfComponent,
    InventarKategorieFfDetailComponent,
    InventarKategorieFfDialogComponent,
    InventarKategorieFfPopupComponent,
    InventarKategorieFfDeletePopupComponent,
    InventarKategorieFfDeleteDialogComponent,
    inventarKategorieRoute,
    inventarKategoriePopupRoute,
} from './';

const ENTITY_STATES = [
    ...inventarKategorieRoute,
    ...inventarKategoriePopupRoute,
];

@NgModule({
    imports: [
        FfManagementSuiteSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        InventarKategorieFfComponent,
        InventarKategorieFfDetailComponent,
        InventarKategorieFfDialogComponent,
        InventarKategorieFfDeleteDialogComponent,
        InventarKategorieFfPopupComponent,
        InventarKategorieFfDeletePopupComponent,
    ],
    entryComponents: [
        InventarKategorieFfComponent,
        InventarKategorieFfDialogComponent,
        InventarKategorieFfPopupComponent,
        InventarKategorieFfDeleteDialogComponent,
        InventarKategorieFfDeletePopupComponent,
    ],
    providers: [
        InventarKategorieFfService,
        InventarKategorieFfPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FfManagementSuiteInventarKategorieFfModule {}
