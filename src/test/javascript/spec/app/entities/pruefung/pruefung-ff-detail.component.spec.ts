/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FfManagementSuiteTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PruefungFfDetailComponent } from '../../../../../../main/webapp/app/entities/pruefung/pruefung-ff-detail.component';
import { PruefungFfService } from '../../../../../../main/webapp/app/entities/pruefung/pruefung-ff.service';
import { PruefungFf } from '../../../../../../main/webapp/app/entities/pruefung/pruefung-ff.model';

describe('Component Tests', () => {

    describe('PruefungFf Management Detail Component', () => {
        let comp: PruefungFfDetailComponent;
        let fixture: ComponentFixture<PruefungFfDetailComponent>;
        let service: PruefungFfService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FfManagementSuiteTestModule],
                declarations: [PruefungFfDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PruefungFfService,
                    JhiEventManager
                ]
            }).overrideTemplate(PruefungFfDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PruefungFfDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PruefungFfService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PruefungFf(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pruefung).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
