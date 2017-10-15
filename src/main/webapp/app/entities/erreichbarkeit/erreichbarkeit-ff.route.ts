import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ErreichbarkeitFfComponent } from './erreichbarkeit-ff.component';
import { ErreichbarkeitFfDetailComponent } from './erreichbarkeit-ff-detail.component';
import { ErreichbarkeitFfPopupComponent } from './erreichbarkeit-ff-dialog.component';
import { ErreichbarkeitFfDeletePopupComponent } from './erreichbarkeit-ff-delete-dialog.component';

export const erreichbarkeitRoute: Routes = [
    {
        path: 'erreichbarkeit-ff',
        component: ErreichbarkeitFfComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.erreichbarkeit.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'erreichbarkeit-ff/:id',
        component: ErreichbarkeitFfDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.erreichbarkeit.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const erreichbarkeitPopupRoute: Routes = [
    {
        path: 'erreichbarkeit-ff-new',
        component: ErreichbarkeitFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.erreichbarkeit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'erreichbarkeit-ff/:id/edit',
        component: ErreichbarkeitFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.erreichbarkeit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'erreichbarkeit-ff/:id/delete',
        component: ErreichbarkeitFfDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.erreichbarkeit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
