import { BaseEntity } from './../../shared';

export class InventarKategorieFf implements BaseEntity {
    constructor(
        public id?: number,
        public titel?: string,
        public unterkategories?: BaseEntity[],
        public wartungens?: BaseEntity[],
        public pruefungens?: BaseEntity[],
        public uebergeordneteKategorie?: BaseEntity,
    ) {
    }
}
