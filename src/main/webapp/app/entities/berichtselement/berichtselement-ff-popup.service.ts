import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { BerichtselementFf } from './berichtselement-ff.model';
import { BerichtselementFfService } from './berichtselement-ff.service';

@Injectable()
export class BerichtselementFfPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private berichtselementService: BerichtselementFfService

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
                this.berichtselementService.find(id).subscribe((berichtselement) => {
                    berichtselement.beginn = this.datePipe
                        .transform(berichtselement.beginn, 'yyyy-MM-ddTHH:mm:ss');
                    berichtselement.ende = this.datePipe
                        .transform(berichtselement.ende, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.berichtselementModalRef(component, berichtselement);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.berichtselementModalRef(component, new BerichtselementFf());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    berichtselementModalRef(component: Component, berichtselement: BerichtselementFf): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.berichtselement = berichtselement;
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
