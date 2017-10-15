/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FfManagementSuiteTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FahrzeugFfDetailComponent } from '../../../../../../main/webapp/app/entities/fahrzeug/fahrzeug-ff-detail.component';
import { FahrzeugFfService } from '../../../../../../main/webapp/app/entities/fahrzeug/fahrzeug-ff.service';
import { FahrzeugFf } from '../../../../../../main/webapp/app/entities/fahrzeug/fahrzeug-ff.model';

describe('Component Tests', () => {

    describe('FahrzeugFf Management Detail Component', () => {
        let comp: FahrzeugFfDetailComponent;
        let fixture: ComponentFixture<FahrzeugFfDetailComponent>;
        let service: FahrzeugFfService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FfManagementSuiteTestModule],
                declarations: [FahrzeugFfDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FahrzeugFfService,
                    JhiEventManager
                ]
            }).overrideTemplate(FahrzeugFfDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FahrzeugFfDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FahrzeugFfService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FahrzeugFf(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.fahrzeug).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
