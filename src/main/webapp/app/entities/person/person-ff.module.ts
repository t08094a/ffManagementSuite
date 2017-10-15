import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FfManagementSuiteSharedModule } from '../../shared';
import {
    PersonFfService,
    PersonFfPopupService,
    PersonFfComponent,
    PersonFfDetailComponent,
    PersonFfDialogComponent,
    PersonFfPopupComponent,
    PersonFfDeletePopupComponent,
    PersonFfDeleteDialogComponent,
    personRoute,
    personPopupRoute,
} from './';

const ENTITY_STATES = [
    ...personRoute,
    ...personPopupRoute,
];

@NgModule({
    imports: [
        FfManagementSuiteSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PersonFfComponent,
        PersonFfDetailComponent,
        PersonFfDialogComponent,
        PersonFfDeleteDialogComponent,
        PersonFfPopupComponent,
        PersonFfDeletePopupComponent,
    ],
    entryComponents: [
        PersonFfComponent,
        PersonFfDialogComponent,
        PersonFfPopupComponent,
        PersonFfDeleteDialogComponent,
        PersonFfDeletePopupComponent,
    ],
    providers: [
        PersonFfService,
        PersonFfPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FfManagementSuitePersonFfModule {}
