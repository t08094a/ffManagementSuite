import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DurchfuehrungWartungFf } from './durchfuehrung-wartung-ff.model';
import { DurchfuehrungWartungFfService } from './durchfuehrung-wartung-ff.service';

@Injectable()
export class DurchfuehrungWartungFfPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private durchfuehrungWartungService: DurchfuehrungWartungFfService

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
                this.durchfuehrungWartungService.find(id).subscribe((durchfuehrungWartung) => {
                    if (durchfuehrungWartung.datum) {
                        durchfuehrungWartung.datum = {
                            year: durchfuehrungWartung.datum.getFullYear(),
                            month: durchfuehrungWartung.datum.getMonth() + 1,
                            day: durchfuehrungWartung.datum.getDate()
                        };
                    }
                    this.ngbModalRef = this.durchfuehrungWartungModalRef(component, durchfuehrungWartung);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.durchfuehrungWartungModalRef(component, new DurchfuehrungWartungFf());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    durchfuehrungWartungModalRef(component: Component, durchfuehrungWartung: DurchfuehrungWartungFf): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.durchfuehrungWartung = durchfuehrungWartung;
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
