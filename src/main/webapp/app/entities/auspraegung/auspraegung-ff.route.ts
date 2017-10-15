import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AuspraegungFfComponent } from './auspraegung-ff.component';
import { AuspraegungFfDetailComponent } from './auspraegung-ff-detail.component';
import { AuspraegungFfPopupComponent } from './auspraegung-ff-dialog.component';
import { AuspraegungFfDeletePopupComponent } from './auspraegung-ff-delete-dialog.component';

export const auspraegungRoute: Routes = [
    {
        path: 'auspraegung-ff',
        component: AuspraegungFfComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.auspraegung.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'auspraegung-ff/:id',
        component: AuspraegungFfDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.auspraegung.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const auspraegungPopupRoute: Routes = [
    {
        path: 'auspraegung-ff-new',
        component: AuspraegungFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.auspraegung.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'auspraegung-ff/:id/edit',
        component: AuspraegungFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.auspraegung.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'auspraegung-ff/:id/delete',
        component: AuspraegungFfDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.auspraegung.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
