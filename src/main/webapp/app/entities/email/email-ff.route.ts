import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { EmailFfComponent } from './email-ff.component';
import { EmailFfDetailComponent } from './email-ff-detail.component';
import { EmailFfPopupComponent } from './email-ff-dialog.component';
import { EmailFfDeletePopupComponent } from './email-ff-delete-dialog.component';

export const emailRoute: Routes = [
    {
        path: 'email-ff',
        component: EmailFfComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.email.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'email-ff/:id',
        component: EmailFfDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.email.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const emailPopupRoute: Routes = [
    {
        path: 'email-ff-new',
        component: EmailFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.email.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'email-ff/:id/edit',
        component: EmailFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.email.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'email-ff/:id/delete',
        component: EmailFfDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.email.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
