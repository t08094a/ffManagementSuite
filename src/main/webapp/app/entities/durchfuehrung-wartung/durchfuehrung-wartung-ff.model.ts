import { BaseEntity } from './../../shared';

export class DurchfuehrungWartungFf implements BaseEntity {
    constructor(
        public id?: number,
        public datum?: any,
        public kosten?: number,
        public inventar?: BaseEntity,
        public schutzausruestung?: BaseEntity,
        public fahrzeug?: BaseEntity,
        public atemschutzInventar?: BaseEntity,
        public definition?: BaseEntity,
    ) {
    }
}
