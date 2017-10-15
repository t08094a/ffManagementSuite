import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { VerfuegbarkeitFfComponent } from './verfuegbarkeit-ff.component';
import { VerfuegbarkeitFfDetailComponent } from './verfuegbarkeit-ff-detail.component';
import { VerfuegbarkeitFfPopupComponent } from './verfuegbarkeit-ff-dialog.component';
import { VerfuegbarkeitFfDeletePopupComponent } from './verfuegbarkeit-ff-delete-dialog.component';

export const verfuegbarkeitRoute: Routes = [
    {
        path: 'verfuegbarkeit-ff',
        component: VerfuegbarkeitFfComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.verfuegbarkeit.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'verfuegbarkeit-ff/:id',
        component: VerfuegbarkeitFfDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.verfuegbarkeit.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const verfuegbarkeitPopupRoute: Routes = [
    {
        path: 'verfuegbarkeit-ff-new',
        component: VerfuegbarkeitFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.verfuegbarkeit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'verfuegbarkeit-ff/:id/edit',
        component: VerfuegbarkeitFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.verfuegbarkeit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'verfuegbarkeit-ff/:id/delete',
        component: VerfuegbarkeitFfDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.verfuegbarkeit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
