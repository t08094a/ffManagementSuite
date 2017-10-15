import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FfManagementSuiteSharedModule } from '../../shared';
import {
    DurchfuehrungWartungFfService,
    DurchfuehrungWartungFfPopupService,
    DurchfuehrungWartungFfComponent,
    DurchfuehrungWartungFfDetailComponent,
    DurchfuehrungWartungFfDialogComponent,
    DurchfuehrungWartungFfPopupComponent,
    DurchfuehrungWartungFfDeletePopupComponent,
    DurchfuehrungWartungFfDeleteDialogComponent,
    durchfuehrungWartungRoute,
    durchfuehrungWartungPopupRoute,
} from './';

const ENTITY_STATES = [
    ...durchfuehrungWartungRoute,
    ...durchfuehrungWartungPopupRoute,
];

@NgModule({
    imports: [
        FfManagementSuiteSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DurchfuehrungWartungFfComponent,
        DurchfuehrungWartungFfDetailComponent,
        DurchfuehrungWartungFfDialogComponent,
        DurchfuehrungWartungFfDeleteDialogComponent,
        DurchfuehrungWartungFfPopupComponent,
        DurchfuehrungWartungFfDeletePopupComponent,
    ],
    entryComponents: [
        DurchfuehrungWartungFfComponent,
        DurchfuehrungWartungFfDialogComponent,
        DurchfuehrungWartungFfPopupComponent,
        DurchfuehrungWartungFfDeleteDialogComponent,
        DurchfuehrungWartungFfDeletePopupComponent,
    ],
    providers: [
        DurchfuehrungWartungFfService,
        DurchfuehrungWartungFfPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FfManagementSuiteDurchfuehrungWartungFfModule {}
