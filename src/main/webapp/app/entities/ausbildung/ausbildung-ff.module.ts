import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FfManagementSuiteSharedModule } from '../../shared';
import {
    AusbildungFfService,
    AusbildungFfPopupService,
    AusbildungFfComponent,
    AusbildungFfDetailComponent,
    AusbildungFfDialogComponent,
    AusbildungFfPopupComponent,
    AusbildungFfDeletePopupComponent,
    AusbildungFfDeleteDialogComponent,
    ausbildungRoute,
    ausbildungPopupRoute,
} from './';

const ENTITY_STATES = [
    ...ausbildungRoute,
    ...ausbildungPopupRoute,
];

@NgModule({
    imports: [
        FfManagementSuiteSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AusbildungFfComponent,
        AusbildungFfDetailComponent,
        AusbildungFfDialogComponent,
        AusbildungFfDeleteDialogComponent,
        AusbildungFfPopupComponent,
        AusbildungFfDeletePopupComponent,
    ],
    entryComponents: [
        AusbildungFfComponent,
        AusbildungFfDialogComponent,
        AusbildungFfPopupComponent,
        AusbildungFfDeleteDialogComponent,
        AusbildungFfDeletePopupComponent,
    ],
    providers: [
        AusbildungFfService,
        AusbildungFfPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FfManagementSuiteAusbildungFfModule {}
