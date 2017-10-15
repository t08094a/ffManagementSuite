import { BaseEntity } from './../../shared';

export const enum LeistungspruefungTyp {
    'LOESCHANGRIFF',
    'THL'
}

export class LeistungspruefungFf implements BaseEntity {
    constructor(
        public id?: number,
        public typ?: LeistungspruefungTyp,
        public stufe?: number,
        public abgelegtAm?: any,
        public teilnehmers?: BaseEntity[],
    ) {
    }
}
