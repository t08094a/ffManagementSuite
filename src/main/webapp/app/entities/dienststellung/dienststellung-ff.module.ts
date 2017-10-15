import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FfManagementSuiteSharedModule } from '../../shared';
import {
    DienststellungFfService,
    DienststellungFfPopupService,
    DienststellungFfComponent,
    DienststellungFfDetailComponent,
    DienststellungFfDialogComponent,
    DienststellungFfPopupComponent,
    DienststellungFfDeletePopupComponent,
    DienststellungFfDeleteDialogComponent,
    dienststellungRoute,
    dienststellungPopupRoute,
} from './';

const ENTITY_STATES = [
    ...dienststellungRoute,
    ...dienststellungPopupRoute,
];

@NgModule({
    imports: [
        FfManagementSuiteSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DienststellungFfComponent,
        DienststellungFfDetailComponent,
        DienststellungFfDialogComponent,
        DienststellungFfDeleteDialogComponent,
        DienststellungFfPopupComponent,
        DienststellungFfDeletePopupComponent,
    ],
    entryComponents: [
        DienststellungFfComponent,
        DienststellungFfDialogComponent,
        DienststellungFfPopupComponent,
        DienststellungFfDeleteDialogComponent,
        DienststellungFfDeletePopupComponent,
    ],
    providers: [
        DienststellungFfService,
        DienststellungFfPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FfManagementSuiteDienststellungFfModule {}
