import { BaseEntity } from './../../shared';

export class AuspraegungFf implements BaseEntity {
    constructor(
        public id?: number,
        public bezeichnung?: string,
    ) {
    }
}
