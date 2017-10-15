import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FfManagementSuiteSharedModule } from '../../shared';
import {
    LehrgangFfService,
    LehrgangFfPopupService,
    LehrgangFfComponent,
    LehrgangFfDetailComponent,
    LehrgangFfDialogComponent,
    LehrgangFfPopupComponent,
    LehrgangFfDeletePopupComponent,
    LehrgangFfDeleteDialogComponent,
    lehrgangRoute,
    lehrgangPopupRoute,
} from './';

const ENTITY_STATES = [
    ...lehrgangRoute,
    ...lehrgangPopupRoute,
];

@NgModule({
    imports: [
        FfManagementSuiteSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LehrgangFfComponent,
        LehrgangFfDetailComponent,
        LehrgangFfDialogComponent,
        LehrgangFfDeleteDialogComponent,
        LehrgangFfPopupComponent,
        LehrgangFfDeletePopupComponent,
    ],
    entryComponents: [
        LehrgangFfComponent,
        LehrgangFfDialogComponent,
        LehrgangFfPopupComponent,
        LehrgangFfDeleteDialogComponent,
        LehrgangFfDeletePopupComponent,
    ],
    providers: [
        LehrgangFfService,
        LehrgangFfPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FfManagementSuiteLehrgangFfModule {}
