/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FfManagementSuiteTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FuehrerscheinFfDetailComponent } from '../../../../../../main/webapp/app/entities/fuehrerschein/fuehrerschein-ff-detail.component';
import { FuehrerscheinFfService } from '../../../../../../main/webapp/app/entities/fuehrerschein/fuehrerschein-ff.service';
import { FuehrerscheinFf } from '../../../../../../main/webapp/app/entities/fuehrerschein/fuehrerschein-ff.model';

describe('Component Tests', () => {

    describe('FuehrerscheinFf Management Detail Component', () => {
        let comp: FuehrerscheinFfDetailComponent;
        let fixture: ComponentFixture<FuehrerscheinFfDetailComponent>;
        let service: FuehrerscheinFfService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FfManagementSuiteTestModule],
                declarations: [FuehrerscheinFfDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FuehrerscheinFfService,
                    JhiEventManager
                ]
            }).overrideTemplate(FuehrerscheinFfDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FuehrerscheinFfDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FuehrerscheinFfService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FuehrerscheinFf(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.fuehrerschein).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
