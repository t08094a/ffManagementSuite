import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FfManagementSuiteSharedModule } from '../../shared';
import {
    PruefungFfService,
    PruefungFfPopupService,
    PruefungFfComponent,
    PruefungFfDetailComponent,
    PruefungFfDialogComponent,
    PruefungFfPopupComponent,
    PruefungFfDeletePopupComponent,
    PruefungFfDeleteDialogComponent,
    pruefungRoute,
    pruefungPopupRoute,
} from './';

const ENTITY_STATES = [
    ...pruefungRoute,
    ...pruefungPopupRoute,
];

@NgModule({
    imports: [
        FfManagementSuiteSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PruefungFfComponent,
        PruefungFfDetailComponent,
        PruefungFfDialogComponent,
        PruefungFfDeleteDialogComponent,
        PruefungFfPopupComponent,
        PruefungFfDeletePopupComponent,
    ],
    entryComponents: [
        PruefungFfComponent,
        PruefungFfDialogComponent,
        PruefungFfPopupComponent,
        PruefungFfDeleteDialogComponent,
        PruefungFfDeletePopupComponent,
    ],
    providers: [
        PruefungFfService,
        PruefungFfPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FfManagementSuitePruefungFfModule {}
