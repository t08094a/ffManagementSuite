import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PruefungFf } from './pruefung-ff.model';
import { PruefungFfService } from './pruefung-ff.service';

@Injectable()
export class PruefungFfPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private pruefungService: PruefungFfService

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
                this.pruefungService.find(id).subscribe((pruefung) => {
                    if (pruefung.beginn) {
                        pruefung.beginn = {
                            year: pruefung.beginn.getFullYear(),
                            month: pruefung.beginn.getMonth() + 1,
                            day: pruefung.beginn.getDate()
                        };
                    }
                    if (pruefung.letztePruefung) {
                        pruefung.letztePruefung = {
                            year: pruefung.letztePruefung.getFullYear(),
                            month: pruefung.letztePruefung.getMonth() + 1,
                            day: pruefung.letztePruefung.getDate()
                        };
                    }
                    this.ngbModalRef = this.pruefungModalRef(component, pruefung);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.pruefungModalRef(component, new PruefungFf());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    pruefungModalRef(component: Component, pruefung: PruefungFf): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.pruefung = pruefung;
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
