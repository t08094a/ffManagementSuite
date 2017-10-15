import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { FuehrerscheinFfComponent } from './fuehrerschein-ff.component';
import { FuehrerscheinFfDetailComponent } from './fuehrerschein-ff-detail.component';
import { FuehrerscheinFfPopupComponent } from './fuehrerschein-ff-dialog.component';
import { FuehrerscheinFfDeletePopupComponent } from './fuehrerschein-ff-delete-dialog.component';

export const fuehrerscheinRoute: Routes = [
    {
        path: 'fuehrerschein-ff',
        component: FuehrerscheinFfComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.fuehrerschein.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'fuehrerschein-ff/:id',
        component: FuehrerscheinFfDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.fuehrerschein.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fuehrerscheinPopupRoute: Routes = [
    {
        path: 'fuehrerschein-ff-new',
        component: FuehrerscheinFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.fuehrerschein.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'fuehrerschein-ff/:id/edit',
        component: FuehrerscheinFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.fuehrerschein.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'fuehrerschein-ff/:id/delete',
        component: FuehrerscheinFfDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.fuehrerschein.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
