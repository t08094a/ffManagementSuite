import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PersonFf } from './person-ff.model';
import { PersonFfService } from './person-ff.service';

@Injectable()
export class PersonFfPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private personService: PersonFfService

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
                this.personService.find(id).subscribe((person) => {
                    if (person.geburtsdatum) {
                        person.geburtsdatum = {
                            year: person.geburtsdatum.getFullYear(),
                            month: person.geburtsdatum.getMonth() + 1,
                            day: person.geburtsdatum.getDate()
                        };
                    }
                    if (person.ehrung25Jahre) {
                        person.ehrung25Jahre = {
                            year: person.ehrung25Jahre.getFullYear(),
                            month: person.ehrung25Jahre.getMonth() + 1,
                            day: person.ehrung25Jahre.getDate()
                        };
                    }
                    if (person.ehrung40Jahre) {
                        person.ehrung40Jahre = {
                            year: person.ehrung40Jahre.getFullYear(),
                            month: person.ehrung40Jahre.getMonth() + 1,
                            day: person.ehrung40Jahre.getDate()
                        };
                    }
                    this.ngbModalRef = this.personModalRef(component, person);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.personModalRef(component, new PersonFf());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    personModalRef(component: Component, person: PersonFf): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.person = person;
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
