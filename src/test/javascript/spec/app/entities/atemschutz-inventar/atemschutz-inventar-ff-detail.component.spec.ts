/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FfManagementSuiteTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AtemschutzInventarFfDetailComponent } from '../../../../../../main/webapp/app/entities/atemschutz-inventar/atemschutz-inventar-ff-detail.component';
import { AtemschutzInventarFfService } from '../../../../../../main/webapp/app/entities/atemschutz-inventar/atemschutz-inventar-ff.service';
import { AtemschutzInventarFf } from '../../../../../../main/webapp/app/entities/atemschutz-inventar/atemschutz-inventar-ff.model';

describe('Component Tests', () => {

    describe('AtemschutzInventarFf Management Detail Component', () => {
        let comp: AtemschutzInventarFfDetailComponent;
        let fixture: ComponentFixture<AtemschutzInventarFfDetailComponent>;
        let service: AtemschutzInventarFfService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FfManagementSuiteTestModule],
                declarations: [AtemschutzInventarFfDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AtemschutzInventarFfService,
                    JhiEventManager
                ]
            }).overrideTemplate(AtemschutzInventarFfDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AtemschutzInventarFfDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AtemschutzInventarFfService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AtemschutzInventarFf(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.atemschutzInventar).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
