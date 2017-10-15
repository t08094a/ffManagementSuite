/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FfManagementSuiteTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PersonFfDetailComponent } from '../../../../../../main/webapp/app/entities/person/person-ff-detail.component';
import { PersonFfService } from '../../../../../../main/webapp/app/entities/person/person-ff.service';
import { PersonFf } from '../../../../../../main/webapp/app/entities/person/person-ff.model';

describe('Component Tests', () => {

    describe('PersonFf Management Detail Component', () => {
        let comp: PersonFfDetailComponent;
        let fixture: ComponentFixture<PersonFfDetailComponent>;
        let service: PersonFfService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FfManagementSuiteTestModule],
                declarations: [PersonFfDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PersonFfService,
                    JhiEventManager
                ]
            }).overrideTemplate(PersonFfDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PersonFfDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonFfService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PersonFf(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.person).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
