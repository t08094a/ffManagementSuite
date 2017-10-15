import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { FfManagementSuitePersonFfModule } from './person/person-ff.module';
import { FfManagementSuiteErreichbarkeitFfModule } from './erreichbarkeit/erreichbarkeit-ff.module';
import { FfManagementSuiteEmailFfModule } from './email/email-ff.module';
import { FfManagementSuiteAusbildungFfModule } from './ausbildung/ausbildung-ff.module';
import { FfManagementSuiteLehrgangFfModule } from './lehrgang/lehrgang-ff.module';
import { FfManagementSuiteOrganisationsstrukturFfModule } from './organisationsstruktur/organisationsstruktur-ff.module';
import { FfManagementSuiteDienstzeitFfModule } from './dienstzeit/dienstzeit-ff.module';
import { FfManagementSuiteLeistungspruefungFfModule } from './leistungspruefung/leistungspruefung-ff.module';
import { FfManagementSuiteFuehrerscheinFfModule } from './fuehrerschein/fuehrerschein-ff.module';
import { FfManagementSuiteVerfuegbarkeitFfModule } from './verfuegbarkeit/verfuegbarkeit-ff.module';
import { FfManagementSuiteDienststellungFfModule } from './dienststellung/dienststellung-ff.module';
import { FfManagementSuiteInventarKategorieFfModule } from './inventar-kategorie/inventar-kategorie-ff.module';
import { FfManagementSuiteInventarFfModule } from './inventar/inventar-ff.module';
import { FfManagementSuiteSchutzausruestungFfModule } from './schutzausruestung/schutzausruestung-ff.module';
import { FfManagementSuiteAuspraegungFfModule } from './auspraegung/auspraegung-ff.module';
import { FfManagementSuiteFahrzeugFfModule } from './fahrzeug/fahrzeug-ff.module';
import { FfManagementSuiteAtemschutzInventarFfModule } from './atemschutz-inventar/atemschutz-inventar-ff.module';
import { FfManagementSuiteReinigungFfModule } from './reinigung/reinigung-ff.module';
import { FfManagementSuiteWartungFfModule } from './wartung/wartung-ff.module';
import { FfManagementSuitePruefungFfModule } from './pruefung/pruefung-ff.module';
import { FfManagementSuiteDurchfuehrungPruefungFfModule } from './durchfuehrung-pruefung/durchfuehrung-pruefung-ff.module';
import { FfManagementSuiteDurchfuehrungWartungFfModule } from './durchfuehrung-wartung/durchfuehrung-wartung-ff.module';
import { FfManagementSuiteBerichtselementFfModule } from './berichtselement/berichtselement-ff.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        FfManagementSuitePersonFfModule,
        FfManagementSuiteErreichbarkeitFfModule,
        FfManagementSuiteEmailFfModule,
        FfManagementSuiteAusbildungFfModule,
        FfManagementSuiteLehrgangFfModule,
        FfManagementSuiteOrganisationsstrukturFfModule,
        FfManagementSuiteDienstzeitFfModule,
        FfManagementSuiteLeistungspruefungFfModule,
        FfManagementSuiteFuehrerscheinFfModule,
        FfManagementSuiteVerfuegbarkeitFfModule,
        FfManagementSuiteDienststellungFfModule,
        FfManagementSuiteInventarKategorieFfModule,
        FfManagementSuiteInventarFfModule,
        FfManagementSuiteSchutzausruestungFfModule,
        FfManagementSuiteAuspraegungFfModule,
        FfManagementSuiteFahrzeugFfModule,
        FfManagementSuiteAtemschutzInventarFfModule,
        FfManagementSuiteReinigungFfModule,
        FfManagementSuiteWartungFfModule,
        FfManagementSuitePruefungFfModule,
        FfManagementSuiteDurchfuehrungPruefungFfModule,
        FfManagementSuiteDurchfuehrungWartungFfModule,
        FfManagementSuiteBerichtselementFfModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FfManagementSuiteEntityModule {}
