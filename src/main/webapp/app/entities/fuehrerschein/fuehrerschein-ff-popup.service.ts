import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FuehrerscheinFf } from './fuehrerschein-ff.model';
import { FuehrerscheinFfService } from './fuehrerschein-ff.service';

@Injectable()
export class FuehrerscheinFfPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private fuehrerscheinService: FuehrerscheinFfService

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
                this.fuehrerscheinService.find(id).subscribe((fuehrerschein) => {
                    this.ngbModalRef = this.fuehrerscheinModalRef(component, fuehrerschein);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.fuehrerscheinModalRef(component, new FuehrerscheinFf());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    fuehrerscheinModalRef(component: Component, fuehrerschein: FuehrerscheinFf): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.fuehrerschein = fuehrerschein;
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
