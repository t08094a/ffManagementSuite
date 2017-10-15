import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AtemschutzInventarFfComponent } from './atemschutz-inventar-ff.component';
import { AtemschutzInventarFfDetailComponent } from './atemschutz-inventar-ff-detail.component';
import { AtemschutzInventarFfPopupComponent } from './atemschutz-inventar-ff-dialog.component';
import { AtemschutzInventarFfDeletePopupComponent } from './atemschutz-inventar-ff-delete-dialog.component';

export const atemschutzInventarRoute: Routes = [
    {
        path: 'atemschutz-inventar-ff',
        component: AtemschutzInventarFfComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.atemschutzInventar.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'atemschutz-inventar-ff/:id',
        component: AtemschutzInventarFfDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.atemschutzInventar.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const atemschutzInventarPopupRoute: Routes = [
    {
        path: 'atemschutz-inventar-ff-new',
        component: AtemschutzInventarFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.atemschutzInventar.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'atemschutz-inventar-ff/:id/edit',
        component: AtemschutzInventarFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.atemschutzInventar.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'atemschutz-inventar-ff/:id/delete',
        component: AtemschutzInventarFfDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.atemschutzInventar.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
