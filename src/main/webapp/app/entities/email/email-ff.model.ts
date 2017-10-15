import { BaseEntity } from './../../shared';

export class EmailFf implements BaseEntity {
    constructor(
        public id?: number,
        public typ?: string,
        public adresse?: string,
        public person?: BaseEntity,
    ) {
    }
}
