import { BaseEntity } from './../../shared';

export class OrganisationsstrukturFf implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public gegliedertIns?: BaseEntity[],
        public parent?: BaseEntity,
    ) {
    }
}
