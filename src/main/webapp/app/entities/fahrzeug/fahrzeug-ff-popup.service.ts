import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FahrzeugFf } from './fahrzeug-ff.model';
import { FahrzeugFfService } from './fahrzeug-ff.service';

@Injectable()
export class FahrzeugFfPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private fahrzeugService: FahrzeugFfService

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
                this.fahrzeugService.find(id).subscribe((fahrzeug) => {
                    if (fahrzeug.angeschafftAm) {
                        fahrzeug.angeschafftAm = {
                            year: fahrzeug.angeschafftAm.getFullYear(),
                            month: fahrzeug.angeschafftAm.getMonth() + 1,
                            day: fahrzeug.angeschafftAm.getDate()
                        };
                    }
                    if (fahrzeug.ausgemustertAm) {
                        fahrzeug.ausgemustertAm = {
                            year: fahrzeug.ausgemustertAm.getFullYear(),
                            month: fahrzeug.ausgemustertAm.getMonth() + 1,
                            day: fahrzeug.ausgemustertAm.getDate()
                        };
                    }
                    this.ngbModalRef = this.fahrzeugModalRef(component, fahrzeug);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.fahrzeugModalRef(component, new FahrzeugFf());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    fahrzeugModalRef(component: Component, fahrzeug: FahrzeugFf): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.fahrzeug = fahrzeug;
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
