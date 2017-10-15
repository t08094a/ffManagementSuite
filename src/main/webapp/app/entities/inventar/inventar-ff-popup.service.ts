import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { InventarFf } from './inventar-ff.model';
import { InventarFfService } from './inventar-ff.service';

@Injectable()
export class InventarFfPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private inventarService: InventarFfService

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
                this.inventarService.find(id).subscribe((inventar) => {
                    if (inventar.angeschafftAm) {
                        inventar.angeschafftAm = {
                            year: inventar.angeschafftAm.getFullYear(),
                            month: inventar.angeschafftAm.getMonth() + 1,
                            day: inventar.angeschafftAm.getDate()
                        };
                    }
                    if (inventar.ausgemustertAm) {
                        inventar.ausgemustertAm = {
                            year: inventar.ausgemustertAm.getFullYear(),
                            month: inventar.ausgemustertAm.getMonth() + 1,
                            day: inventar.ausgemustertAm.getDate()
                        };
                    }
                    this.ngbModalRef = this.inventarModalRef(component, inventar);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.inventarModalRef(component, new InventarFf());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    inventarModalRef(component: Component, inventar: InventarFf): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.inventar = inventar;
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
