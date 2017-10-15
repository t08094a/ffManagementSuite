import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { LeistungspruefungFf } from './leistungspruefung-ff.model';
import { LeistungspruefungFfService } from './leistungspruefung-ff.service';

@Injectable()
export class LeistungspruefungFfPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private leistungspruefungService: LeistungspruefungFfService

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
                this.leistungspruefungService.find(id).subscribe((leistungspruefung) => {
                    if (leistungspruefung.abgelegtAm) {
                        leistungspruefung.abgelegtAm = {
                            year: leistungspruefung.abgelegtAm.getFullYear(),
                            month: leistungspruefung.abgelegtAm.getMonth() + 1,
                            day: leistungspruefung.abgelegtAm.getDate()
                        };
                    }
                    this.ngbModalRef = this.leistungspruefungModalRef(component, leistungspruefung);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.leistungspruefungModalRef(component, new LeistungspruefungFf());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    leistungspruefungModalRef(component: Component, leistungspruefung: LeistungspruefungFf): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.leistungspruefung = leistungspruefung;
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
