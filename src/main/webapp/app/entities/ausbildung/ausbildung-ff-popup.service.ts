import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AusbildungFf } from './ausbildung-ff.model';
import { AusbildungFfService } from './ausbildung-ff.service';

@Injectable()
export class AusbildungFfPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private ausbildungService: AusbildungFfService

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
                this.ausbildungService.find(id).subscribe((ausbildung) => {
                    if (ausbildung.beginn) {
                        ausbildung.beginn = {
                            year: ausbildung.beginn.getFullYear(),
                            month: ausbildung.beginn.getMonth() + 1,
                            day: ausbildung.beginn.getDate()
                        };
                    }
                    if (ausbildung.ende) {
                        ausbildung.ende = {
                            year: ausbildung.ende.getFullYear(),
                            month: ausbildung.ende.getMonth() + 1,
                            day: ausbildung.ende.getDate()
                        };
                    }
                    this.ngbModalRef = this.ausbildungModalRef(component, ausbildung);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.ausbildungModalRef(component, new AusbildungFf());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    ausbildungModalRef(component: Component, ausbildung: AusbildungFf): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.ausbildung = ausbildung;
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
