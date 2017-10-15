import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FfManagementSuiteSharedModule } from '../../shared';
import {
    ReinigungFfService,
    ReinigungFfPopupService,
    ReinigungFfComponent,
    ReinigungFfDetailComponent,
    ReinigungFfDialogComponent,
    ReinigungFfPopupComponent,
    ReinigungFfDeletePopupComponent,
    ReinigungFfDeleteDialogComponent,
    reinigungRoute,
    reinigungPopupRoute,
} from './';

const ENTITY_STATES = [
    ...reinigungRoute,
    ...reinigungPopupRoute,
];

@NgModule({
    imports: [
        FfManagementSuiteSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ReinigungFfComponent,
        ReinigungFfDetailComponent,
        ReinigungFfDialogComponent,
        ReinigungFfDeleteDialogComponent,
        ReinigungFfPopupComponent,
        ReinigungFfDeletePopupComponent,
    ],
    entryComponents: [
        ReinigungFfComponent,
        ReinigungFfDialogComponent,
        ReinigungFfPopupComponent,
        ReinigungFfDeleteDialogComponent,
        ReinigungFfDeletePopupComponent,
    ],
    providers: [
        ReinigungFfService,
        ReinigungFfPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FfManagementSuiteReinigungFfModule {}
