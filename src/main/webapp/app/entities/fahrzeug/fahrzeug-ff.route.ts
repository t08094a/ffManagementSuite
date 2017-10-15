import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { FahrzeugFfComponent } from './fahrzeug-ff.component';
import { FahrzeugFfDetailComponent } from './fahrzeug-ff-detail.component';
import { FahrzeugFfPopupComponent } from './fahrzeug-ff-dialog.component';
import { FahrzeugFfDeletePopupComponent } from './fahrzeug-ff-delete-dialog.component';

export const fahrzeugRoute: Routes = [
    {
        path: 'fahrzeug-ff',
        component: FahrzeugFfComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.fahrzeug.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'fahrzeug-ff/:id',
        component: FahrzeugFfDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.fahrzeug.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fahrzeugPopupRoute: Routes = [
    {
        path: 'fahrzeug-ff-new',
        component: FahrzeugFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.fahrzeug.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'fahrzeug-ff/:id/edit',
        component: FahrzeugFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.fahrzeug.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'fahrzeug-ff/:id/delete',
        component: FahrzeugFfDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.fahrzeug.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
