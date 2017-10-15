/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FfManagementSuiteTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DienstzeitFfDetailComponent } from '../../../../../../main/webapp/app/entities/dienstzeit/dienstzeit-ff-detail.component';
import { DienstzeitFfService } from '../../../../../../main/webapp/app/entities/dienstzeit/dienstzeit-ff.service';
import { DienstzeitFf } from '../../../../../../main/webapp/app/entities/dienstzeit/dienstzeit-ff.model';

describe('Component Tests', () => {

    describe('DienstzeitFf Management Detail Component', () => {
        let comp: DienstzeitFfDetailComponent;
        let fixture: ComponentFixture<DienstzeitFfDetailComponent>;
        let service: DienstzeitFfService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FfManagementSuiteTestModule],
                declarations: [DienstzeitFfDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DienstzeitFfService,
                    JhiEventManager
                ]
            }).overrideTemplate(DienstzeitFfDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DienstzeitFfDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DienstzeitFfService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new DienstzeitFf(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.dienstzeit).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
