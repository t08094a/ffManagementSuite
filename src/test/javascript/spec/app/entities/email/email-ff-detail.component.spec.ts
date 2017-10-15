/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FfManagementSuiteTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EmailFfDetailComponent } from '../../../../../../main/webapp/app/entities/email/email-ff-detail.component';
import { EmailFfService } from '../../../../../../main/webapp/app/entities/email/email-ff.service';
import { EmailFf } from '../../../../../../main/webapp/app/entities/email/email-ff.model';

describe('Component Tests', () => {

    describe('EmailFf Management Detail Component', () => {
        let comp: EmailFfDetailComponent;
        let fixture: ComponentFixture<EmailFfDetailComponent>;
        let service: EmailFfService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FfManagementSuiteTestModule],
                declarations: [EmailFfDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EmailFfService,
                    JhiEventManager
                ]
            }).overrideTemplate(EmailFfDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmailFfDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmailFfService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new EmailFf(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.email).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
