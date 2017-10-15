import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AtemschutzInventarFf } from './atemschutz-inventar-ff.model';
import { AtemschutzInventarFfService } from './atemschutz-inventar-ff.service';

@Injectable()
export class AtemschutzInventarFfPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private atemschutzInventarService: AtemschutzInventarFfService

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
                this.atemschutzInventarService.find(id).subscribe((atemschutzInventar) => {
                    if (atemschutzInventar.angeschafftAm) {
                        atemschutzInventar.angeschafftAm = {
                            year: atemschutzInventar.angeschafftAm.getFullYear(),
                            month: atemschutzInventar.angeschafftAm.getMonth() + 1,
                            day: atemschutzInventar.angeschafftAm.getDate()
                        };
                    }
                    if (atemschutzInventar.ausgemustertAm) {
                        atemschutzInventar.ausgemustertAm = {
                            year: atemschutzInventar.ausgemustertAm.getFullYear(),
                            month: atemschutzInventar.ausgemustertAm.getMonth() + 1,
                            day: atemschutzInventar.ausgemustertAm.getDate()
                        };
                    }
                    this.ngbModalRef = this.atemschutzInventarModalRef(component, atemschutzInventar);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.atemschutzInventarModalRef(component, new AtemschutzInventarFf());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    atemschutzInventarModalRef(component: Component, atemschutzInventar: AtemschutzInventarFf): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.atemschutzInventar = atemschutzInventar;
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
