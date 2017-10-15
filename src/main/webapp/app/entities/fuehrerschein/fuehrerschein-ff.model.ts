import { BaseEntity } from './../../shared';

export class FuehrerscheinFf implements BaseEntity {
    constructor(
        public id?: number,
        public klasse?: string,
    ) {
    }
}
