import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FfManagementSuiteSharedModule } from '../../shared';
import {
    InventarFfService,
    InventarFfPopupService,
    InventarFfComponent,
    InventarFfDetailComponent,
    InventarFfDialogComponent,
    InventarFfPopupComponent,
    InventarFfDeletePopupComponent,
    InventarFfDeleteDialogComponent,
    inventarRoute,
    inventarPopupRoute,
} from './';

const ENTITY_STATES = [
    ...inventarRoute,
    ...inventarPopupRoute,
];

@NgModule({
    imports: [
        FfManagementSuiteSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        InventarFfComponent,
        InventarFfDetailComponent,
        InventarFfDialogComponent,
        InventarFfDeleteDialogComponent,
        InventarFfPopupComponent,
        InventarFfDeletePopupComponent,
    ],
    entryComponents: [
        InventarFfComponent,
        InventarFfDialogComponent,
        InventarFfPopupComponent,
        InventarFfDeleteDialogComponent,
        InventarFfDeletePopupComponent,
    ],
    providers: [
        InventarFfService,
        InventarFfPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FfManagementSuiteInventarFfModule {}
