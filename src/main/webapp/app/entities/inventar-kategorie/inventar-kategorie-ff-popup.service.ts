import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { InventarKategorieFf } from './inventar-kategorie-ff.model';
import { InventarKategorieFfService } from './inventar-kategorie-ff.service';

@Injectable()
export class InventarKategorieFfPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private inventarKategorieService: InventarKategorieFfService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.inventarKategorieService.find(id).subscribe((inventarKategorie) => {
                    this.ngbModalRef = this.inventarKategorieModalRef(component, inventarKategorie);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.inventarKategorieModalRef(component, new InventarKategorieFf());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    inventarKategorieModalRef(component: Component, inventarKategorie: InventarKategorieFf): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.inventarKategorie = inventarKategorie;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
