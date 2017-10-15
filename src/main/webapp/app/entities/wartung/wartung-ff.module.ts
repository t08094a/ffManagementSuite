import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FfManagementSuiteSharedModule } from '../../shared';
import {
    WartungFfService,
    WartungFfPopupService,
    WartungFfComponent,
    WartungFfDetailComponent,
    WartungFfDialogComponent,
    WartungFfPopupComponent,
    WartungFfDeletePopupComponent,
    WartungFfDeleteDialogComponent,
    wartungRoute,
    wartungPopupRoute,
} from './';

const ENTITY_STATES = [
    ...wartungRoute,
    ...wartungPopupRoute,
];

@NgModule({
    imports: [
        FfManagementSuiteSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        WartungFfComponent,
        WartungFfDetailComponent,
        WartungFfDialogComponent,
        WartungFfDeleteDialogComponent,
        WartungFfPopupComponent,
        WartungFfDeletePopupComponent,
    ],
    entryComponents: [
        WartungFfComponent,
        WartungFfDialogComponent,
        WartungFfPopupComponent,
        WartungFfDeleteDialogComponent,
        WartungFfDeletePopupComponent,
    ],
    providers: [
        WartungFfService,
        WartungFfPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FfManagementSuiteWartungFfModule {}
