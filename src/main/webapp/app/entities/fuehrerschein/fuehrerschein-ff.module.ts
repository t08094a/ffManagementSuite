import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FfManagementSuiteSharedModule } from '../../shared';
import {
    FuehrerscheinFfService,
    FuehrerscheinFfPopupService,
    FuehrerscheinFfComponent,
    FuehrerscheinFfDetailComponent,
    FuehrerscheinFfDialogComponent,
    FuehrerscheinFfPopupComponent,
    FuehrerscheinFfDeletePopupComponent,
    FuehrerscheinFfDeleteDialogComponent,
    fuehrerscheinRoute,
    fuehrerscheinPopupRoute,
} from './';

const ENTITY_STATES = [
    ...fuehrerscheinRoute,
    ...fuehrerscheinPopupRoute,
];

@NgModule({
    imports: [
        FfManagementSuiteSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FuehrerscheinFfComponent,
        FuehrerscheinFfDetailComponent,
        FuehrerscheinFfDialogComponent,
        FuehrerscheinFfDeleteDialogComponent,
        FuehrerscheinFfPopupComponent,
        FuehrerscheinFfDeletePopupComponent,
    ],
    entryComponents: [
        FuehrerscheinFfComponent,
        FuehrerscheinFfDialogComponent,
        FuehrerscheinFfPopupComponent,
        FuehrerscheinFfDeleteDialogComponent,
        FuehrerscheinFfDeletePopupComponent,
    ],
    providers: [
        FuehrerscheinFfService,
        FuehrerscheinFfPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FfManagementSuiteFuehrerscheinFfModule {}
