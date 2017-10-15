import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FfManagementSuiteSharedModule } from '../../shared';
import {
    AuspraegungFfService,
    AuspraegungFfPopupService,
    AuspraegungFfComponent,
    AuspraegungFfDetailComponent,
    AuspraegungFfDialogComponent,
    AuspraegungFfPopupComponent,
    AuspraegungFfDeletePopupComponent,
    AuspraegungFfDeleteDialogComponent,
    auspraegungRoute,
    auspraegungPopupRoute,
} from './';

const ENTITY_STATES = [
    ...auspraegungRoute,
    ...auspraegungPopupRoute,
];

@NgModule({
    imports: [
        FfManagementSuiteSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AuspraegungFfComponent,
        AuspraegungFfDetailComponent,
        AuspraegungFfDialogComponent,
        AuspraegungFfDeleteDialogComponent,
        AuspraegungFfPopupComponent,
        AuspraegungFfDeletePopupComponent,
    ],
    entryComponents: [
        AuspraegungFfComponent,
        AuspraegungFfDialogComponent,
        AuspraegungFfPopupComponent,
        AuspraegungFfDeleteDialogComponent,
        AuspraegungFfDeletePopupComponent,
    ],
    providers: [
        AuspraegungFfService,
        AuspraegungFfPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FfManagementSuiteAuspraegungFfModule {}
