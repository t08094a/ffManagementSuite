/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FfManagementSuiteTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { VerfuegbarkeitFfDetailComponent } from '../../../../../../main/webapp/app/entities/verfuegbarkeit/verfuegbarkeit-ff-detail.component';
import { VerfuegbarkeitFfService } from '../../../../../../main/webapp/app/entities/verfuegbarkeit/verfuegbarkeit-ff.service';
import { VerfuegbarkeitFf } from '../../../../../../main/webapp/app/entities/verfuegbarkeit/verfuegbarkeit-ff.model';

describe('Component Tests', () => {

    describe('VerfuegbarkeitFf Management Detail Component', () => {
        let comp: VerfuegbarkeitFfDetailComponent;
        let fixture: ComponentFixture<VerfuegbarkeitFfDetailComponent>;
        let service: VerfuegbarkeitFfService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FfManagementSuiteTestModule],
                declarations: [VerfuegbarkeitFfDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    VerfuegbarkeitFfService,
                    JhiEventManager
                ]
            }).overrideTemplate(VerfuegbarkeitFfDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VerfuegbarkeitFfDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VerfuegbarkeitFfService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new VerfuegbarkeitFf(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.verfuegbarkeit).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
