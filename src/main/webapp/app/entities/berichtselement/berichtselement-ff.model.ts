import { BaseEntity } from './../../shared';

export const enum Berichtskategorie {
    'UEBUNG',
    'EINSATZ',
    'WEHR',
    'AUSBILDUNG',
    'JUGEND',
    'LEISTUNGSPRUEFUNG',
    'VEREIN'
}

export class BerichtselementFf implements BaseEntity {
    constructor(
        public id?: number,
        public beginn?: any,
        public ende?: any,
        public titel?: string,
        public beschreibung?: string,
        public stunden?: number,
        public kategorie?: Berichtskategorie,
    ) {
    }
}
