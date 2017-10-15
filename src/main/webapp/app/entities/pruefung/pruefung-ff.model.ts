import { BaseEntity } from './../../shared';

export const enum IntervallEinheit {
    'MONAT',
    'JAHR'
}

export class PruefungFf implements BaseEntity {
    constructor(
        public id?: number,
        public bezeichnung?: string,
        public beginn?: any,
        public letztePruefung?: any,
        public intervallWert?: number,
        public intervallEinheit?: IntervallEinheit,
        public kosten?: number,
        public inventarKategorie?: BaseEntity,
    ) {
    }
}
