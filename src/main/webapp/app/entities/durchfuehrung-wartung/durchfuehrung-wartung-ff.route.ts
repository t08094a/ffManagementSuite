import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DurchfuehrungWartungFfComponent } from './durchfuehrung-wartung-ff.component';
import { DurchfuehrungWartungFfDetailComponent } from './durchfuehrung-wartung-ff-detail.component';
import { DurchfuehrungWartungFfPopupComponent } from './durchfuehrung-wartung-ff-dialog.component';
import { DurchfuehrungWartungFfDeletePopupComponent } from './durchfuehrung-wartung-ff-delete-dialog.component';

export const durchfuehrungWartungRoute: Routes = [
    {
        path: 'durchfuehrung-wartung-ff',
        component: DurchfuehrungWartungFfComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.durchfuehrungWartung.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'durchfuehrung-wartung-ff/:id',
        component: DurchfuehrungWartungFfDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.durchfuehrungWartung.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const durchfuehrungWartungPopupRoute: Routes = [
    {
        path: 'durchfuehrung-wartung-ff-new',
        component: DurchfuehrungWartungFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.durchfuehrungWartung.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'durchfuehrung-wartung-ff/:id/edit',
        component: DurchfuehrungWartungFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.durchfuehrungWartung.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'durchfuehrung-wartung-ff/:id/delete',
        component: DurchfuehrungWartungFfDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.durchfuehrungWartung.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
