import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { LeistungspruefungFfComponent } from './leistungspruefung-ff.component';
import { LeistungspruefungFfDetailComponent } from './leistungspruefung-ff-detail.component';
import { LeistungspruefungFfPopupComponent } from './leistungspruefung-ff-dialog.component';
import { LeistungspruefungFfDeletePopupComponent } from './leistungspruefung-ff-delete-dialog.component';

export const leistungspruefungRoute: Routes = [
    {
        path: 'leistungspruefung-ff',
        component: LeistungspruefungFfComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.leistungspruefung.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'leistungspruefung-ff/:id',
        component: LeistungspruefungFfDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.leistungspruefung.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const leistungspruefungPopupRoute: Routes = [
    {
        path: 'leistungspruefung-ff-new',
        component: LeistungspruefungFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.leistungspruefung.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'leistungspruefung-ff/:id/edit',
        component: LeistungspruefungFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.leistungspruefung.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'leistungspruefung-ff/:id/delete',
        component: LeistungspruefungFfDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.leistungspruefung.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
