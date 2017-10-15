import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { InventarKategorieFfComponent } from './inventar-kategorie-ff.component';
import { InventarKategorieFfDetailComponent } from './inventar-kategorie-ff-detail.component';
import { InventarKategorieFfPopupComponent } from './inventar-kategorie-ff-dialog.component';
import { InventarKategorieFfDeletePopupComponent } from './inventar-kategorie-ff-delete-dialog.component';

export const inventarKategorieRoute: Routes = [
    {
        path: 'inventar-kategorie-ff',
        component: InventarKategorieFfComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.inventarKategorie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'inventar-kategorie-ff/:id',
        component: InventarKategorieFfDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.inventarKategorie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const inventarKategoriePopupRoute: Routes = [
    {
        path: 'inventar-kategorie-ff-new',
        component: InventarKategorieFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.inventarKategorie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'inventar-kategorie-ff/:id/edit',
        component: InventarKategorieFfPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.inventarKategorie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'inventar-kategorie-ff/:id/delete',
        component: InventarKategorieFfDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ffManagementSuiteApp.inventarKategorie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
