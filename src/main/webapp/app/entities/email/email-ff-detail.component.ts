import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { EmailFf } from './email-ff.model';
import { EmailFfService } from './email-ff.service';

@Component({
    selector: 'jhi-email-ff-detail',
    templateUrl: './email-ff-detail.component.html'
})
export class EmailFfDetailComponent implements OnInit, OnDestroy {

    email: EmailFf;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private emailService: EmailFfService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEmails();
    }

    load(id) {
        this.emailService.find(id).subscribe((email) => {
            this.email = email;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEmails() {
        this.eventSubscriber = this.eventManager.subscribe(
            'emailListModification',
            (response) => this.load(this.email.id)
        );
    }
}
