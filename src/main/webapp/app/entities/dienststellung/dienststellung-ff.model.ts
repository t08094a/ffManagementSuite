import { BaseEntity } from './../../shared';

export class DienststellungFf implements BaseEntity {
    constructor(
        public id?: number,
        public titel?: string,
        public abkuerzung?: string,
    ) {
    }
}
