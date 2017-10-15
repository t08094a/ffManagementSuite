import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DurchfuehrungPruefungFfComponent } from './durchfuehrung-pruefung-ff.component';
import { DurchfuehrungPruefungFfDetailComponent } from './durchfuehrung-pruefung-ff-detail.component';
import { DurchfuehrungPruefungFfPopupComponent } from './durchfuehrung-pruefung-ff-dialog.component';
import { DurchfuehrungPruefungFfDeletePopupComponent } from './durchfuehrung-pruefung-ff-delete-dialog.component';

export const durchfuehrungPruefungRoute: Routes = [
    {
        path: 'durchfuehrung-pruefung-ff',
        component: DurchfuehrungPruefungFfComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.durchfuehrungPruefung.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'durchfuehrung-pruefung-ff/:id',
        component: DurchfuehrungPruefungFfDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.durchfuehrungPruefung.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const durchfuehrungPruefungPopupRoute: Routes = [
    {
        path: 'durchfuehrung-pruefung-ff-new',
        component: DurchfuehrungPruefungFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.durchfuehrungPruefung.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'durchfuehrung-pruefung-ff/:id/edit',
        component: DurchfuehrungPruefungFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.durchfuehrungPruefung.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'durchfuehrung-pruefung-ff/:id/delete',
        component: DurchfuehrungPruefungFfDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.durchfuehrungPruefung.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
