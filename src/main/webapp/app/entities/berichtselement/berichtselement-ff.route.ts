import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BerichtselementFfComponent } from './berichtselement-ff.component';
import { BerichtselementFfDetailComponent } from './berichtselement-ff-detail.component';
import { BerichtselementFfPopupComponent } from './berichtselement-ff-dialog.component';
import { BerichtselementFfDeletePopupComponent } from './berichtselement-ff-delete-dialog.component';

export const berichtselementRoute: Routes = [
    {
        path: 'berichtselement-ff',
        component: BerichtselementFfComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.berichtselement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'berichtselement-ff/:id',
        component: BerichtselementFfDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.berichtselement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const berichtselementPopupRoute: Routes = [
    {
        path: 'berichtselement-ff-new',
        component: BerichtselementFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.berichtselement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'berichtselement-ff/:id/edit',
        component: BerichtselementFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.berichtselement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'berichtselement-ff/:id/delete',
        component: BerichtselementFfDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.berichtselement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
