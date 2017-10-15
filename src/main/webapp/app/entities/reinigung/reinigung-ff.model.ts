import { BaseEntity } from './../../shared';

export class ReinigungFf implements BaseEntity {
    constructor(
        public id?: number,
        public durchfuehrung?: any,
    ) {
    }
}
