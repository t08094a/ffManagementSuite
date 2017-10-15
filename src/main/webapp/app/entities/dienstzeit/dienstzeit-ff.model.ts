import { BaseEntity } from './../../shared';

export class DienstzeitFf implements BaseEntity {
    constructor(
        public id?: number,
        public beginn?: any,
        public ende?: any,
        public person?: BaseEntity,
    ) {
    }
}
