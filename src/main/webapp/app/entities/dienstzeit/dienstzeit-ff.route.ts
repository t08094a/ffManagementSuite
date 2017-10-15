import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DienstzeitFfComponent } from './dienstzeit-ff.component';
import { DienstzeitFfDetailComponent } from './dienstzeit-ff-detail.component';
import { DienstzeitFfPopupComponent } from './dienstzeit-ff-dialog.component';
import { DienstzeitFfDeletePopupComponent } from './dienstzeit-ff-delete-dialog.component';

export const dienstzeitRoute: Routes = [
    {
        path: 'dienstzeit-ff',
        component: DienstzeitFfComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.dienstzeit.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'dienstzeit-ff/:id',
        component: DienstzeitFfDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.dienstzeit.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dienstzeitPopupRoute: Routes = [
    {
        path: 'dienstzeit-ff-new',
        component: DienstzeitFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.dienstzeit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dienstzeit-ff/:id/edit',
        component: DienstzeitFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.dienstzeit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dienstzeit-ff/:id/delete',
        component: DienstzeitFfDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.dienstzeit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
