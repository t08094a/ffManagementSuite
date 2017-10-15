import { BaseEntity } from './../../shared';

export const enum Mitgliedsstatus {
    'AKTIV',
    'PASSIV',
    'FOERDERND'
}

export class PersonFf implements BaseEntity {
    constructor(
        public id?: number,
        public vorname?: string,
        public nachname?: string,
        public geburtsdatum?: any,
        public strasse?: string,
        public hausnummer?: number,
        public appendix?: string,
        public postleitzahl?: string,
        public ort?: string,
        public status?: Mitgliedsstatus,
        public ehrung25Jahre?: any,
        public ehrung40Jahre?: any,
        public zugehoerigkeit?: BaseEntity,
        public erreichbarkeitens?: BaseEntity[],
        public emails?: BaseEntity[],
        public dienstzeitens?: BaseEntity[],
        public ausbildungens?: BaseEntity[],
        public schutzausruestungs?: BaseEntity[],
        public inDienststellung?: BaseEntity,
        public fuehrerscheines?: BaseEntity[],
        public verfuegbarkeitens?: BaseEntity[],
        public leistungspruefungens?: BaseEntity[],
    ) {
    }
}
