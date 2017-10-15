import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { InventarFfComponent } from './inventar-ff.component';
import { InventarFfDetailComponent } from './inventar-ff-detail.component';
import { InventarFfPopupComponent } from './inventar-ff-dialog.component';
import { InventarFfDeletePopupComponent } from './inventar-ff-delete-dialog.component';

export const inventarRoute: Routes = [
    {
        path: 'inventar-ff',
        component: InventarFfComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.inventar.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'inventar-ff/:id',
        component: InventarFfDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.inventar.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const inventarPopupRoute: Routes = [
    {
        path: 'inventar-ff-new',
        component: InventarFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.inventar.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'inventar-ff/:id/edit',
        component: InventarFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.inventar.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'inventar-ff/:id/delete',
        component: InventarFfDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.inventar.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
