/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FfManagementSuiteTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { InventarKategorieFfDetailComponent } from '../../../../../../main/webapp/app/entities/inventar-kategorie/inventar-kategorie-ff-detail.component';
import { InventarKategorieFfService } from '../../../../../../main/webapp/app/entities/inventar-kategorie/inventar-kategorie-ff.service';
import { InventarKategorieFf } from '../../../../../../main/webapp/app/entities/inventar-kategorie/inventar-kategorie-ff.model';

describe('Component Tests', () => {

    describe('InventarKategorieFf Management Detail Component', () => {
        let comp: InventarKategorieFfDetailComponent;
        let fixture: ComponentFixture<InventarKategorieFfDetailComponent>;
        let service: InventarKategorieFfService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FfManagementSuiteTestModule],
                declarations: [InventarKategorieFfDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    InventarKategorieFfService,
                    JhiEventManager
                ]
            }).overrideTemplate(InventarKategorieFfDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InventarKategorieFfDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InventarKategorieFfService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new InventarKategorieFf(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.inventarKategorie).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
