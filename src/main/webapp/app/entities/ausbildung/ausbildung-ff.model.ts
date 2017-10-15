import { BaseEntity } from './../../shared';

export class AusbildungFf implements BaseEntity {
    constructor(
        public id?: number,
        public beginn?: any,
        public ende?: any,
        public zeugnisContentType?: string,
        public zeugnis?: any,
        public person?: BaseEntity,
        public lehrgang?: BaseEntity,
    ) {
    }
}
