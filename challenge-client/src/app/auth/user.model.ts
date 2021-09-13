export class User {
  constructor(
    public username: String,
    public id: number,
    private _token: string,
    public tokenType: string,
    private _tokenExpirationDate: Date
  ) {}

  get token() {
    if (!this._tokenExpirationDate || new Date() > this._tokenExpirationDate)
        return null;
    return this._token;
  }
}
