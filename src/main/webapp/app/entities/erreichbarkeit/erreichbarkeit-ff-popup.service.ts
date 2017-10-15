import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ErreichbarkeitFf } from './erreichbarkeit-ff.model';
import { ErreichbarkeitFfService } from './erreichbarkeit-ff.service';

@Injectable()
export class ErreichbarkeitFfPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private erreichbarkeitService: ErreichbarkeitFfService

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
                this.erreichbarkeitService.find(id).subscribe((erreichbarkeit) => {
                    this.ngbModalRef = this.erreichbarkeitModalRef(component, erreichbarkeit);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.erreichbarkeitModalRef(component, new ErreichbarkeitFf());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    erreichbarkeitModalRef(component: Component, erreichbarkeit: ErreichbarkeitFf): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.erreichbarkeit = erreichbarkeit;
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
