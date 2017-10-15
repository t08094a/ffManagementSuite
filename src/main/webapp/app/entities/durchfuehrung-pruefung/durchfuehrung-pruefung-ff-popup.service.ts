import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DurchfuehrungPruefungFf } from './durchfuehrung-pruefung-ff.model';
import { DurchfuehrungPruefungFfService } from './durchfuehrung-pruefung-ff.service';

@Injectable()
export class DurchfuehrungPruefungFfPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private durchfuehrungPruefungService: DurchfuehrungPruefungFfService

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
                this.durchfuehrungPruefungService.find(id).subscribe((durchfuehrungPruefung) => {
                    if (durchfuehrungPruefung.datum) {
                        durchfuehrungPruefung.datum = {
                            year: durchfuehrungPruefung.datum.getFullYear(),
                            month: durchfuehrungPruefung.datum.getMonth() + 1,
                            day: durchfuehrungPruefung.datum.getDate()
                        };
                    }
                    this.ngbModalRef = this.durchfuehrungPruefungModalRef(component, durchfuehrungPruefung);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.durchfuehrungPruefungModalRef(component, new DurchfuehrungPruefungFf());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    durchfuehrungPruefungModalRef(component: Component, durchfuehrungPruefung: DurchfuehrungPruefungFf): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.durchfuehrungPruefung = durchfuehrungPruefung;
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
