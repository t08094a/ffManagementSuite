import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FfManagementSuiteSharedModule } from '../../shared';
import {
    BerichtselementFfService,
    BerichtselementFfPopupService,
    BerichtselementFfComponent,
    BerichtselementFfDetailComponent,
    BerichtselementFfDialogComponent,
    BerichtselementFfPopupComponent,
    BerichtselementFfDeletePopupComponent,
    BerichtselementFfDeleteDialogComponent,
    berichtselementRoute,
    berichtselementPopupRoute,
} from './';

const ENTITY_STATES = [
    ...berichtselementRoute,
    ...berichtselementPopupRoute,
];

@NgModule({
    imports: [
        FfManagementSuiteSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BerichtselementFfComponent,
        BerichtselementFfDetailComponent,
        BerichtselementFfDialogComponent,
        BerichtselementFfDeleteDialogComponent,
        BerichtselementFfPopupComponent,
        BerichtselementFfDeletePopupComponent,
    ],
    entryComponents: [
        BerichtselementFfComponent,
        BerichtselementFfDialogComponent,
        BerichtselementFfPopupComponent,
        BerichtselementFfDeleteDialogComponent,
        BerichtselementFfDeletePopupComponent,
    ],
    providers: [
        BerichtselementFfService,
        BerichtselementFfPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FfManagementSuiteBerichtselementFfModule {}
