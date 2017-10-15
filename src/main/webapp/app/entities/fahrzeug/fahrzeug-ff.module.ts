import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FfManagementSuiteSharedModule } from '../../shared';
import {
    FahrzeugFfService,
    FahrzeugFfPopupService,
    FahrzeugFfComponent,
    FahrzeugFfDetailComponent,
    FahrzeugFfDialogComponent,
    FahrzeugFfPopupComponent,
    FahrzeugFfDeletePopupComponent,
    FahrzeugFfDeleteDialogComponent,
    fahrzeugRoute,
    fahrzeugPopupRoute,
} from './';

const ENTITY_STATES = [
    ...fahrzeugRoute,
    ...fahrzeugPopupRoute,
];

@NgModule({
    imports: [
        FfManagementSuiteSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FahrzeugFfComponent,
        FahrzeugFfDetailComponent,
        FahrzeugFfDialogComponent,
        FahrzeugFfDeleteDialogComponent,
        FahrzeugFfPopupComponent,
        FahrzeugFfDeletePopupComponent,
    ],
    entryComponents: [
        FahrzeugFfComponent,
        FahrzeugFfDialogComponent,
        FahrzeugFfPopupComponent,
        FahrzeugFfDeleteDialogComponent,
        FahrzeugFfDeletePopupComponent,
    ],
    providers: [
        FahrzeugFfService,
        FahrzeugFfPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FfManagementSuiteFahrzeugFfModule {}
