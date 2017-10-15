import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FfManagementSuiteSharedModule } from '../../shared';
import {
    SchutzausruestungFfService,
    SchutzausruestungFfPopupService,
    SchutzausruestungFfComponent,
    SchutzausruestungFfDetailComponent,
    SchutzausruestungFfDialogComponent,
    SchutzausruestungFfPopupComponent,
    SchutzausruestungFfDeletePopupComponent,
    SchutzausruestungFfDeleteDialogComponent,
    schutzausruestungRoute,
    schutzausruestungPopupRoute,
} from './';

const ENTITY_STATES = [
    ...schutzausruestungRoute,
    ...schutzausruestungPopupRoute,
];

@NgModule({
    imports: [
        FfManagementSuiteSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SchutzausruestungFfComponent,
        SchutzausruestungFfDetailComponent,
        SchutzausruestungFfDialogComponent,
        SchutzausruestungFfDeleteDialogComponent,
        SchutzausruestungFfPopupComponent,
        SchutzausruestungFfDeletePopupComponent,
    ],
    entryComponents: [
        SchutzausruestungFfComponent,
        SchutzausruestungFfDialogComponent,
        SchutzausruestungFfPopupComponent,
        SchutzausruestungFfDeleteDialogComponent,
        SchutzausruestungFfDeletePopupComponent,
    ],
    providers: [
        SchutzausruestungFfService,
        SchutzausruestungFfPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FfManagementSuiteSchutzausruestungFfModule {}
