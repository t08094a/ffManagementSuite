import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FfManagementSuiteSharedModule } from '../../shared';
import {
    ErreichbarkeitFfService,
    ErreichbarkeitFfPopupService,
    ErreichbarkeitFfComponent,
    ErreichbarkeitFfDetailComponent,
    ErreichbarkeitFfDialogComponent,
    ErreichbarkeitFfPopupComponent,
    ErreichbarkeitFfDeletePopupComponent,
    ErreichbarkeitFfDeleteDialogComponent,
    erreichbarkeitRoute,
    erreichbarkeitPopupRoute,
} from './';

const ENTITY_STATES = [
    ...erreichbarkeitRoute,
    ...erreichbarkeitPopupRoute,
];

@NgModule({
    imports: [
        FfManagementSuiteSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ErreichbarkeitFfComponent,
        ErreichbarkeitFfDetailComponent,
        ErreichbarkeitFfDialogComponent,
        ErreichbarkeitFfDeleteDialogComponent,
        ErreichbarkeitFfPopupComponent,
        ErreichbarkeitFfDeletePopupComponent,
    ],
    entryComponents: [
        ErreichbarkeitFfComponent,
        ErreichbarkeitFfDialogComponent,
        ErreichbarkeitFfPopupComponent,
        ErreichbarkeitFfDeleteDialogComponent,
        ErreichbarkeitFfDeletePopupComponent,
    ],
    providers: [
        ErreichbarkeitFfService,
        ErreichbarkeitFfPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FfManagementSuiteErreichbarkeitFfModule {}
