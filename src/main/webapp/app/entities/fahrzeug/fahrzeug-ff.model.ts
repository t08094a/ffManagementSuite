import { BaseEntity } from './../../shared';

export class FahrzeugFf implements BaseEntity {
    constructor(
        public id?: number,
        public nummer?: number,
        public angeschafftAm?: any,
        public ausgemustertAm?: any,
        public nummernschild?: string,
        public funkrufname?: string,
        public kategorie?: BaseEntity,
        public durchgefuehrteWartungens?: BaseEntity[],
        public durchgefuehrtePruefungens?: BaseEntity[],
    ) {
    }
}
