import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { WartungFfComponent } from './wartung-ff.component';
import { WartungFfDetailComponent } from './wartung-ff-detail.component';
import { WartungFfPopupComponent } from './wartung-ff-dialog.component';
import { WartungFfDeletePopupComponent } from './wartung-ff-delete-dialog.component';

export const wartungRoute: Routes = [
    {
        path: 'wartung-ff',
        component: WartungFfComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.wartung.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'wartung-ff/:id',
        component: WartungFfDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.wartung.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const wartungPopupRoute: Routes = [
    {
        path: 'wartung-ff-new',
        component: WartungFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.wartung.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'wartung-ff/:id/edit',
        component: WartungFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.wartung.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'wartung-ff/:id/delete',
        component: WartungFfDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.wartung.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
