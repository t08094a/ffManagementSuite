import { BaseEntity } from './../../shared';

export class SchutzausruestungFf implements BaseEntity {
    constructor(
        public id?: number,
        public nummer?: number,
        public angeschafftAm?: any,
        public ausgemustertAm?: any,
        public groesse?: string,
        public kategorie?: BaseEntity,
        public auspraegung?: BaseEntity,
        public durchgefuehrteWartungens?: BaseEntity[],
        public durchgefuehrtePruefungens?: BaseEntity[],
        public besitzer?: BaseEntity,
    ) {
    }
}
