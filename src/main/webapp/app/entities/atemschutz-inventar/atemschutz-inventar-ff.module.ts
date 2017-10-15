import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FfManagementSuiteSharedModule } from '../../shared';
import {
    AtemschutzInventarFfService,
    AtemschutzInventarFfPopupService,
    AtemschutzInventarFfComponent,
    AtemschutzInventarFfDetailComponent,
    AtemschutzInventarFfDialogComponent,
    AtemschutzInventarFfPopupComponent,
    AtemschutzInventarFfDeletePopupComponent,
    AtemschutzInventarFfDeleteDialogComponent,
    atemschutzInventarRoute,
    atemschutzInventarPopupRoute,
} from './';

const ENTITY_STATES = [
    ...atemschutzInventarRoute,
    ...atemschutzInventarPopupRoute,
];

@NgModule({
    imports: [
        FfManagementSuiteSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AtemschutzInventarFfComponent,
        AtemschutzInventarFfDetailComponent,
        AtemschutzInventarFfDialogComponent,
        AtemschutzInventarFfDeleteDialogComponent,
        AtemschutzInventarFfPopupComponent,
        AtemschutzInventarFfDeletePopupComponent,
    ],
    entryComponents: [
        AtemschutzInventarFfComponent,
        AtemschutzInventarFfDialogComponent,
        AtemschutzInventarFfPopupComponent,
        AtemschutzInventarFfDeleteDialogComponent,
        AtemschutzInventarFfDeletePopupComponent,
    ],
    providers: [
        AtemschutzInventarFfService,
        AtemschutzInventarFfPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FfManagementSuiteAtemschutzInventarFfModule {}
