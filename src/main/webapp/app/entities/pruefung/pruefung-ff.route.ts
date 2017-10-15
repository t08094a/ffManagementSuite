import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PruefungFfComponent } from './pruefung-ff.component';
import { PruefungFfDetailComponent } from './pruefung-ff-detail.component';
import { PruefungFfPopupComponent } from './pruefung-ff-dialog.component';
import { PruefungFfDeletePopupComponent } from './pruefung-ff-delete-dialog.component';

export const pruefungRoute: Routes = [
    {
        path: 'pruefung-ff',
        component: PruefungFfComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.pruefung.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pruefung-ff/:id',
        component: PruefungFfDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.pruefung.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pruefungPopupRoute: Routes = [
    {
        path: 'pruefung-ff-new',
        component: PruefungFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.pruefung.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pruefung-ff/:id/edit',
        component: PruefungFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.pruefung.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pruefung-ff/:id/delete',
        component: PruefungFfDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.pruefung.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
