import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FfManagementSuiteSharedModule } from '../../shared';
import {
    DurchfuehrungPruefungFfService,
    DurchfuehrungPruefungFfPopupService,
    DurchfuehrungPruefungFfComponent,
    DurchfuehrungPruefungFfDetailComponent,
    DurchfuehrungPruefungFfDialogComponent,
    DurchfuehrungPruefungFfPopupComponent,
    DurchfuehrungPruefungFfDeletePopupComponent,
    DurchfuehrungPruefungFfDeleteDialogComponent,
    durchfuehrungPruefungRoute,
    durchfuehrungPruefungPopupRoute,
} from './';

const ENTITY_STATES = [
    ...durchfuehrungPruefungRoute,
    ...durchfuehrungPruefungPopupRoute,
];

@NgModule({
    imports: [
        FfManagementSuiteSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DurchfuehrungPruefungFfComponent,
        DurchfuehrungPruefungFfDetailComponent,
        DurchfuehrungPruefungFfDialogComponent,
        DurchfuehrungPruefungFfDeleteDialogComponent,
        DurchfuehrungPruefungFfPopupComponent,
        DurchfuehrungPruefungFfDeletePopupComponent,
    ],
    entryComponents: [
        DurchfuehrungPruefungFfComponent,
        DurchfuehrungPruefungFfDialogComponent,
        DurchfuehrungPruefungFfPopupComponent,
        DurchfuehrungPruefungFfDeleteDialogComponent,
        DurchfuehrungPruefungFfDeletePopupComponent,
    ],
    providers: [
        DurchfuehrungPruefungFfService,
        DurchfuehrungPruefungFfPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FfManagementSuiteDurchfuehrungPruefungFfModule {}
