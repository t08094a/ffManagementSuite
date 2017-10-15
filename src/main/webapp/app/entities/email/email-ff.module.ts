import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FfManagementSuiteSharedModule } from '../../shared';
import {
    EmailFfService,
    EmailFfPopupService,
    EmailFfComponent,
    EmailFfDetailComponent,
    EmailFfDialogComponent,
    EmailFfPopupComponent,
    EmailFfDeletePopupComponent,
    EmailFfDeleteDialogComponent,
    emailRoute,
    emailPopupRoute,
} from './';

const ENTITY_STATES = [
    ...emailRoute,
    ...emailPopupRoute,
];

@NgModule({
    imports: [
        FfManagementSuiteSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EmailFfComponent,
        EmailFfDetailComponent,
        EmailFfDialogComponent,
        EmailFfDeleteDialogComponent,
        EmailFfPopupComponent,
        EmailFfDeletePopupComponent,
    ],
    entryComponents: [
        EmailFfComponent,
        EmailFfDialogComponent,
        EmailFfPopupComponent,
        EmailFfDeleteDialogComponent,
        EmailFfDeletePopupComponent,
    ],
    providers: [
        EmailFfService,
        EmailFfPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FfManagementSuiteEmailFfModule {}
