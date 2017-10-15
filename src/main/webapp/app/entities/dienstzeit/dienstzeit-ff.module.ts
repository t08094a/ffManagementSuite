import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FfManagementSuiteSharedModule } from '../../shared';
import {
    DienstzeitFfService,
    DienstzeitFfPopupService,
    DienstzeitFfComponent,
    DienstzeitFfDetailComponent,
    DienstzeitFfDialogComponent,
    DienstzeitFfPopupComponent,
    DienstzeitFfDeletePopupComponent,
    DienstzeitFfDeleteDialogComponent,
    dienstzeitRoute,
    dienstzeitPopupRoute,
} from './';

const ENTITY_STATES = [
    ...dienstzeitRoute,
    ...dienstzeitPopupRoute,
];

@NgModule({
    imports: [
        FfManagementSuiteSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DienstzeitFfComponent,
        DienstzeitFfDetailComponent,
        DienstzeitFfDialogComponent,
        DienstzeitFfDeleteDialogComponent,
        DienstzeitFfPopupComponent,
        DienstzeitFfDeletePopupComponent,
    ],
    entryComponents: [
        DienstzeitFfComponent,
        DienstzeitFfDialogComponent,
        DienstzeitFfPopupComponent,
        DienstzeitFfDeleteDialogComponent,
        DienstzeitFfDeletePopupComponent,
    ],
    providers: [
        DienstzeitFfService,
        DienstzeitFfPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FfManagementSuiteDienstzeitFfModule {}
