import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SchutzausruestungFf } from './schutzausruestung-ff.model';
import { SchutzausruestungFfService } from './schutzausruestung-ff.service';

@Injectable()
export class SchutzausruestungFfPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private schutzausruestungService: SchutzausruestungFfService

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
                this.schutzausruestungService.find(id).subscribe((schutzausruestung) => {
                    if (schutzausruestung.angeschafftAm) {
                        schutzausruestung.angeschafftAm = {
                            year: schutzausruestung.angeschafftAm.getFullYear(),
                            month: schutzausruestung.angeschafftAm.getMonth() + 1,
                            day: schutzausruestung.angeschafftAm.getDate()
                        };
                    }
                    if (schutzausruestung.ausgemustertAm) {
                        schutzausruestung.ausgemustertAm = {
                            year: schutzausruestung.ausgemustertAm.getFullYear(),
                            month: schutzausruestung.ausgemustertAm.getMonth() + 1,
                            day: schutzausruestung.ausgemustertAm.getDate()
                        };
                    }
                    this.ngbModalRef = this.schutzausruestungModalRef(component, schutzausruestung);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.schutzausruestungModalRef(component, new SchutzausruestungFf());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    schutzausruestungModalRef(component: Component, schutzausruestung: SchutzausruestungFf): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.schutzausruestung = schutzausruestung;
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
