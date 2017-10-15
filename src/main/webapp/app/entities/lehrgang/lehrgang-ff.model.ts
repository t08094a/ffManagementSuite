import { BaseEntity } from './../../shared';

export class LehrgangFf implements BaseEntity {
    constructor(
        public id?: number,
        public titel?: string,
        public abkuerzung?: string,
        public lehrgang?: BaseEntity,
        public voraussetzungens?: BaseEntity[],
    ) {
    }
}
