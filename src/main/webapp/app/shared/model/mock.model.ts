export interface IMock {
  id?: number;
  name?: string;
  input?: string;
  output?: string;
  url?: string;
}

export class Mock implements IMock {
  constructor(public id?: number, public name?: string, public input?: string, public output?: string, public url?: string) {}
}
