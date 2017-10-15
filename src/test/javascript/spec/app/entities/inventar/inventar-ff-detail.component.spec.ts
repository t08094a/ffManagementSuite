/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FfManagementSuiteTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { InventarFfDetailComponent } from '../../../../../../main/webapp/app/entities/inventar/inventar-ff-detail.component';
import { InventarFfService } from '../../../../../../main/webapp/app/entities/inventar/inventar-ff.service';
import { InventarFf } from '../../../../../../main/webapp/app/entities/inventar/inventar-ff.model';

describe('Component Tests', () => {

    describe('InventarFf Management Detail Component', () => {
        let comp: InventarFfDetailComponent;
        let fixture: ComponentFixture<InventarFfDetailComponent>;
        let service: InventarFfService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FfManagementSuiteTestModule],
                declarations: [InventarFfDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    InventarFfService,
                    JhiEventManager
                ]
            }).overrideTemplate(InventarFfDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InventarFfDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InventarFfService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new InventarFf(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.inventar).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
