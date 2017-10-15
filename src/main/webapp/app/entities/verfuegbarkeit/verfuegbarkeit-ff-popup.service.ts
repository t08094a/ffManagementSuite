import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { VerfuegbarkeitFf } from './verfuegbarkeit-ff.model';
import { VerfuegbarkeitFfService } from './verfuegbarkeit-ff.service';

@Injectable()
export class VerfuegbarkeitFfPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private verfuegbarkeitService: VerfuegbarkeitFfService

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
                this.verfuegbarkeitService.find(id).subscribe((verfuegbarkeit) => {
                    this.ngbModalRef = this.verfuegbarkeitModalRef(component, verfuegbarkeit);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.verfuegbarkeitModalRef(component, new VerfuegbarkeitFf());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    verfuegbarkeitModalRef(component: Component, verfuegbarkeit: VerfuegbarkeitFf): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.verfuegbarkeit = verfuegbarkeit;
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
