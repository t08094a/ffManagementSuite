import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ReinigungFf } from './reinigung-ff.model';
import { ReinigungFfService } from './reinigung-ff.service';

@Injectable()
export class ReinigungFfPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private reinigungService: ReinigungFfService

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
                this.reinigungService.find(id).subscribe((reinigung) => {
                    if (reinigung.durchfuehrung) {
                        reinigung.durchfuehrung = {
                            year: reinigung.durchfuehrung.getFullYear(),
                            month: reinigung.durchfuehrung.getMonth() + 1,
                            day: reinigung.durchfuehrung.getDate()
                        };
                    }
                    this.ngbModalRef = this.reinigungModalRef(component, reinigung);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.reinigungModalRef(component, new ReinigungFf());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    reinigungModalRef(component: Component, reinigung: ReinigungFf): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.reinigung = reinigung;
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
