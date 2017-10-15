import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FfManagementSuiteSharedModule } from '../../shared';
import {
    LeistungspruefungFfService,
    LeistungspruefungFfPopupService,
    LeistungspruefungFfComponent,
    LeistungspruefungFfDetailComponent,
    LeistungspruefungFfDialogComponent,
    LeistungspruefungFfPopupComponent,
    LeistungspruefungFfDeletePopupComponent,
    LeistungspruefungFfDeleteDialogComponent,
    leistungspruefungRoute,
    leistungspruefungPopupRoute,
} from './';

const ENTITY_STATES = [
    ...leistungspruefungRoute,
    ...leistungspruefungPopupRoute,
];

@NgModule({
    imports: [
        FfManagementSuiteSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LeistungspruefungFfComponent,
        LeistungspruefungFfDetailComponent,
        LeistungspruefungFfDialogComponent,
        LeistungspruefungFfDeleteDialogComponent,
        LeistungspruefungFfPopupComponent,
        LeistungspruefungFfDeletePopupComponent,
    ],
    entryComponents: [
        LeistungspruefungFfComponent,
        LeistungspruefungFfDialogComponent,
        LeistungspruefungFfPopupComponent,
        LeistungspruefungFfDeleteDialogComponent,
        LeistungspruefungFfDeletePopupComponent,
    ],
    providers: [
        LeistungspruefungFfService,
        LeistungspruefungFfPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FfManagementSuiteLeistungspruefungFfModule {}
