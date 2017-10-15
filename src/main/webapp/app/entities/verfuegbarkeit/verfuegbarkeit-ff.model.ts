import { BaseEntity } from './../../shared';

export const enum Wocheneinteilung {
    'WOCHENTAG',
    'WOCHENENDE'
}

export const enum Tageszeit {
    'TAG',
    'NACHT'
}

export class VerfuegbarkeitFf implements BaseEntity {
    constructor(
        public id?: number,
        public titel?: string,
        public wocheneinteilung?: Wocheneinteilung,
        public tageszeit?: Tageszeit,
    ) {
    }
}
