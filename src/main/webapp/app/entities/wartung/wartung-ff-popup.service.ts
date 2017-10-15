import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { WartungFf } from './wartung-ff.model';
import { WartungFfService } from './wartung-ff.service';

@Injectable()
export class WartungFfPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private wartungService: WartungFfService

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
                this.wartungService.find(id).subscribe((wartung) => {
                    if (wartung.beginn) {
                        wartung.beginn = {
                            year: wartung.beginn.getFullYear(),
                            month: wartung.beginn.getMonth() + 1,
                            day: wartung.beginn.getDate()
                        };
                    }
                    if (wartung.letzteWartung) {
                        wartung.letzteWartung = {
                            year: wartung.letzteWartung.getFullYear(),
                            month: wartung.letzteWartung.getMonth() + 1,
                            day: wartung.letzteWartung.getDate()
                        };
                    }
                    this.ngbModalRef = this.wartungModalRef(component, wartung);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.wartungModalRef(component, new WartungFf());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    wartungModalRef(component: Component, wartung: WartungFf): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.wartung = wartung;
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
