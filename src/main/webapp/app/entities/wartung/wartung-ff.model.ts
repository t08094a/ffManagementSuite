import { BaseEntity } from './../../shared';

export const enum IntervallEinheit {
    'MONAT',
    'JAHR'
}

export class WartungFf implements BaseEntity {
    constructor(
        public id?: number,
        public bezeichnung?: string,
        public beginn?: any,
        public letzteWartung?: any,
        public intervallWert?: number,
        public intervallEinheit?: IntervallEinheit,
        public kosten?: number,
        public inventarKategorie?: BaseEntity,
    ) {
    }
}
