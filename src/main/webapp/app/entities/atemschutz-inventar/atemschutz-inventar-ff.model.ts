import { BaseEntity } from './../../shared';

export class AtemschutzInventarFf implements BaseEntity {
    constructor(
        public id?: number,
        public nummer?: number,
        public angeschafftAm?: any,
        public ausgemustertAm?: any,
        public kategorie?: BaseEntity,
        public durchgefuehrteWartungens?: BaseEntity[],
        public durchgefuehrtePruefungens?: BaseEntity[],
    ) {
    }
}
