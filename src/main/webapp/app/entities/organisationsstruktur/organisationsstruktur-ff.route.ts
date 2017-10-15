import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { OrganisationsstrukturFfComponent } from './organisationsstruktur-ff.component';
import { OrganisationsstrukturFfDetailComponent } from './organisationsstruktur-ff-detail.component';
import { OrganisationsstrukturFfPopupComponent } from './organisationsstruktur-ff-dialog.component';
import { OrganisationsstrukturFfDeletePopupComponent } from './organisationsstruktur-ff-delete-dialog.component';

export const organisationsstrukturRoute: Routes = [
    {
        path: 'organisationsstruktur-ff',
        component: OrganisationsstrukturFfComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.organisationsstruktur.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'organisationsstruktur-ff/:id',
        component: OrganisationsstrukturFfDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.organisationsstruktur.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const organisationsstrukturPopupRoute: Routes = [
    {
        path: 'organisationsstruktur-ff-new',
        component: OrganisationsstrukturFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.organisationsstruktur.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'organisationsstruktur-ff/:id/edit',
        component: OrganisationsstrukturFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.organisationsstruktur.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'organisationsstruktur-ff/:id/delete',
        component: OrganisationsstrukturFfDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.organisationsstruktur.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
