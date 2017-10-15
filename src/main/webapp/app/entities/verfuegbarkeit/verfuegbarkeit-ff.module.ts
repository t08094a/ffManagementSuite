import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FfManagementSuiteSharedModule } from '../../shared';
import {
    VerfuegbarkeitFfService,
    VerfuegbarkeitFfPopupService,
    VerfuegbarkeitFfComponent,
    VerfuegbarkeitFfDetailComponent,
    VerfuegbarkeitFfDialogComponent,
    VerfuegbarkeitFfPopupComponent,
    VerfuegbarkeitFfDeletePopupComponent,
    VerfuegbarkeitFfDeleteDialogComponent,
    verfuegbarkeitRoute,
    verfuegbarkeitPopupRoute,
} from './';

const ENTITY_STATES = [
    ...verfuegbarkeitRoute,
    ...verfuegbarkeitPopupRoute,
];

@NgModule({
    imports: [
        FfManagementSuiteSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        VerfuegbarkeitFfComponent,
        VerfuegbarkeitFfDetailComponent,
        VerfuegbarkeitFfDialogComponent,
        VerfuegbarkeitFfDeleteDialogComponent,
        VerfuegbarkeitFfPopupComponent,
        VerfuegbarkeitFfDeletePopupComponent,
    ],
    entryComponents: [
        VerfuegbarkeitFfComponent,
        VerfuegbarkeitFfDialogComponent,
        VerfuegbarkeitFfPopupComponent,
        VerfuegbarkeitFfDeleteDialogComponent,
        VerfuegbarkeitFfDeletePopupComponent,
    ],
    providers: [
        VerfuegbarkeitFfService,
        VerfuegbarkeitFfPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FfManagementSuiteVerfuegbarkeitFfModule {}
