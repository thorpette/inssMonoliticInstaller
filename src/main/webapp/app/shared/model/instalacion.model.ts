import { IPaso } from 'app/shared/model/paso.model';

export interface IInstalacion {
  id?: number;
  name?: string;
  descripcion?: string;
  pasos?: IPaso[];
}

export class Instalacion implements IInstalacion {
  constructor(public id?: number, public name?: string, public descripcion?: string, public pasos?: IPaso[]) {}
}
