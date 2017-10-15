/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FfManagementSuiteTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LehrgangFfDetailComponent } from '../../../../../../main/webapp/app/entities/lehrgang/lehrgang-ff-detail.component';
import { LehrgangFfService } from '../../../../../../main/webapp/app/entities/lehrgang/lehrgang-ff.service';
import { LehrgangFf } from '../../../../../../main/webapp/app/entities/lehrgang/lehrgang-ff.model';

describe('Component Tests', () => {

    describe('LehrgangFf Management Detail Component', () => {
        let comp: LehrgangFfDetailComponent;
        let fixture: ComponentFixture<LehrgangFfDetailComponent>;
        let service: LehrgangFfService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FfManagementSuiteTestModule],
                declarations: [LehrgangFfDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LehrgangFfService,
                    JhiEventManager
                ]
            }).overrideTemplate(LehrgangFfDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LehrgangFfDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LehrgangFfService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new LehrgangFf(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.lehrgang).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
