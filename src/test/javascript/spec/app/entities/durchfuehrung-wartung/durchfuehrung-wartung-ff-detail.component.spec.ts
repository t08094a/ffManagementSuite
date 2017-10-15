/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FfManagementSuiteTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DurchfuehrungWartungFfDetailComponent } from '../../../../../../main/webapp/app/entities/durchfuehrung-wartung/durchfuehrung-wartung-ff-detail.component';
import { DurchfuehrungWartungFfService } from '../../../../../../main/webapp/app/entities/durchfuehrung-wartung/durchfuehrung-wartung-ff.service';
import { DurchfuehrungWartungFf } from '../../../../../../main/webapp/app/entities/durchfuehrung-wartung/durchfuehrung-wartung-ff.model';

describe('Component Tests', () => {

    describe('DurchfuehrungWartungFf Management Detail Component', () => {
        let comp: DurchfuehrungWartungFfDetailComponent;
        let fixture: ComponentFixture<DurchfuehrungWartungFfDetailComponent>;
        let service: DurchfuehrungWartungFfService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FfManagementSuiteTestModule],
                declarations: [DurchfuehrungWartungFfDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DurchfuehrungWartungFfService,
                    JhiEventManager
                ]
            }).overrideTemplate(DurchfuehrungWartungFfDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DurchfuehrungWartungFfDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DurchfuehrungWartungFfService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new DurchfuehrungWartungFf(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.durchfuehrungWartung).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
