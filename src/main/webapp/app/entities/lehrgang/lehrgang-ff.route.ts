import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { LehrgangFfComponent } from './lehrgang-ff.component';
import { LehrgangFfDetailComponent } from './lehrgang-ff-detail.component';
import { LehrgangFfPopupComponent } from './lehrgang-ff-dialog.component';
import { LehrgangFfDeletePopupComponent } from './lehrgang-ff-delete-dialog.component';

export const lehrgangRoute: Routes = [
    {
        path: 'lehrgang-ff',
        component: LehrgangFfComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.lehrgang.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'lehrgang-ff/:id',
        component: LehrgangFfDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.lehrgang.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lehrgangPopupRoute: Routes = [
    {
        path: 'lehrgang-ff-new',
        component: LehrgangFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.lehrgang.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lehrgang-ff/:id/edit',
        component: LehrgangFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.lehrgang.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lehrgang-ff/:id/delete',
        component: LehrgangFfDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.lehrgang.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
