import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FfManagementSuiteSharedModule } from '../../shared';
import {
    OrganisationsstrukturFfService,
    OrganisationsstrukturFfPopupService,
    OrganisationsstrukturFfComponent,
    OrganisationsstrukturFfDetailComponent,
    OrganisationsstrukturFfDialogComponent,
    OrganisationsstrukturFfPopupComponent,
    OrganisationsstrukturFfDeletePopupComponent,
    OrganisationsstrukturFfDeleteDialogComponent,
    organisationsstrukturRoute,
    organisationsstrukturPopupRoute,
} from './';

const ENTITY_STATES = [
    ...organisationsstrukturRoute,
    ...organisationsstrukturPopupRoute,
];

@NgModule({
    imports: [
        FfManagementSuiteSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        OrganisationsstrukturFfComponent,
        OrganisationsstrukturFfDetailComponent,
        OrganisationsstrukturFfDialogComponent,
        OrganisationsstrukturFfDeleteDialogComponent,
        OrganisationsstrukturFfPopupComponent,
        OrganisationsstrukturFfDeletePopupComponent,
    ],
    entryComponents: [
        OrganisationsstrukturFfComponent,
        OrganisationsstrukturFfDialogComponent,
        OrganisationsstrukturFfPopupComponent,
        OrganisationsstrukturFfDeleteDialogComponent,
        OrganisationsstrukturFfDeletePopupComponent,
    ],
    providers: [
        OrganisationsstrukturFfService,
        OrganisationsstrukturFfPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FfManagementSuiteOrganisationsstrukturFfModule {}
