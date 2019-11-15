import { IInstalacion } from 'app/shared/model/instalacion.model';
import { Command } from 'app/shared/model/enumerations/command.model';

export interface IPaso {
  id?: number;
  name?: string;
  command?: Command;
  origen?: string;
  destino?: string;
  parametro?: string;
  instalacion?: IInstalacion;
}

export class Paso implements IPaso {
  constructor(
    public id?: number,
    public name?: string,
    public command?: Command,
    public origen?: string,
    public destino?: string,
    public parametro?: string,
    public instalacion?: IInstalacion
  ) {}
}
