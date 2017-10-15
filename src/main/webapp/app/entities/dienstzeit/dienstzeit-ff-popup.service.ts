import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DienstzeitFf } from './dienstzeit-ff.model';
import { DienstzeitFfService } from './dienstzeit-ff.service';

@Injectable()
export class DienstzeitFfPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private dienstzeitService: DienstzeitFfService

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
                this.dienstzeitService.find(id).subscribe((dienstzeit) => {
                    if (dienstzeit.beginn) {
                        dienstzeit.beginn = {
                            year: dienstzeit.beginn.getFullYear(),
                            month: dienstzeit.beginn.getMonth() + 1,
                            day: dienstzeit.beginn.getDate()
                        };
                    }
                    if (dienstzeit.ende) {
                        dienstzeit.ende = {
                            year: dienstzeit.ende.getFullYear(),
                            month: dienstzeit.ende.getMonth() + 1,
                            day: dienstzeit.ende.getDate()
                        };
                    }
                    this.ngbModalRef = this.dienstzeitModalRef(component, dienstzeit);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.dienstzeitModalRef(component, new DienstzeitFf());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    dienstzeitModalRef(component: Component, dienstzeit: DienstzeitFf): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.dienstzeit = dienstzeit;
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
