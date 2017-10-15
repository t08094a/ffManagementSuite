import { BaseEntity } from './../../shared';

export class ErreichbarkeitFf implements BaseEntity {
    constructor(
        public id?: number,
        public typ?: string,
        public vorwahl?: string,
        public rufnummer?: string,
        public person?: BaseEntity,
    ) {
    }
}
