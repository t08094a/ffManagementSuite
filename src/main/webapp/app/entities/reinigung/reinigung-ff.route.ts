import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ReinigungFfComponent } from './reinigung-ff.component';
import { ReinigungFfDetailComponent } from './reinigung-ff-detail.component';
import { ReinigungFfPopupComponent } from './reinigung-ff-dialog.component';
import { ReinigungFfDeletePopupComponent } from './reinigung-ff-delete-dialog.component';

export const reinigungRoute: Routes = [
    {
        path: 'reinigung-ff',
        component: ReinigungFfComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.reinigung.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'reinigung-ff/:id',
        component: ReinigungFfDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.reinigung.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const reinigungPopupRoute: Routes = [
    {
        path: 'reinigung-ff-new',
        component: ReinigungFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.reinigung.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'reinigung-ff/:id/edit',
        component: ReinigungFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.reinigung.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'reinigung-ff/:id/delete',
        component: ReinigungFfDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.reinigung.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
