import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AusbildungFfComponent } from './ausbildung-ff.component';
import { AusbildungFfDetailComponent } from './ausbildung-ff-detail.component';
import { AusbildungFfPopupComponent } from './ausbildung-ff-dialog.component';
import { AusbildungFfDeletePopupComponent } from './ausbildung-ff-delete-dialog.component';

export const ausbildungRoute: Routes = [
    {
        path: 'ausbildung-ff',
        component: AusbildungFfComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.ausbildung.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ausbildung-ff/:id',
        component: AusbildungFfDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.ausbildung.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ausbildungPopupRoute: Routes = [
    {
        path: 'ausbildung-ff-new',
        component: AusbildungFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.ausbildung.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ausbildung-ff/:id/edit',
        component: AusbildungFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.ausbildung.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ausbildung-ff/:id/delete',
        component: AusbildungFfDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.ausbildung.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
