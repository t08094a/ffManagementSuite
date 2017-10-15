import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DienststellungFfComponent } from './dienststellung-ff.component';
import { DienststellungFfDetailComponent } from './dienststellung-ff-detail.component';
import { DienststellungFfPopupComponent } from './dienststellung-ff-dialog.component';
import { DienststellungFfDeletePopupComponent } from './dienststellung-ff-delete-dialog.component';

export const dienststellungRoute: Routes = [
    {
        path: 'dienststellung-ff',
        component: DienststellungFfComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.dienststellung.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'dienststellung-ff/:id',
        component: DienststellungFfDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.dienststellung.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dienststellungPopupRoute: Routes = [
    {
        path: 'dienststellung-ff-new',
        component: DienststellungFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.dienststellung.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dienststellung-ff/:id/edit',
        component: DienststellungFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.dienststellung.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dienststellung-ff/:id/delete',
        component: DienststellungFfDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.dienststellung.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
