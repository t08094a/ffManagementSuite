import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SchutzausruestungFfComponent } from './schutzausruestung-ff.component';
import { SchutzausruestungFfDetailComponent } from './schutzausruestung-ff-detail.component';
import { SchutzausruestungFfPopupComponent } from './schutzausruestung-ff-dialog.component';
import { SchutzausruestungFfDeletePopupComponent } from './schutzausruestung-ff-delete-dialog.component';

export const schutzausruestungRoute: Routes = [
    {
        path: 'schutzausruestung-ff',
        component: SchutzausruestungFfComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.schutzausruestung.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'schutzausruestung-ff/:id',
        component: SchutzausruestungFfDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.schutzausruestung.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const schutzausruestungPopupRoute: Routes = [
    {
        path: 'schutzausruestung-ff-new',
        component: SchutzausruestungFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.schutzausruestung.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'schutzausruestung-ff/:id/edit',
        component: SchutzausruestungFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.schutzausruestung.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'schutzausruestung-ff/:id/delete',
        component: SchutzausruestungFfDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.schutzausruestung.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
