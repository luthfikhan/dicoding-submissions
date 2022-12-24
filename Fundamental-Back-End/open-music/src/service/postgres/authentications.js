import pg from "pg";
import { BadRequestException } from "../../utils/exception.js";

const { Pool } = pg;

class AuthenticationsPostgresService {
  constructor() {
    this._pool = new Pool();
  }

  async insertTokenId(tokenId) {
    const query = {
      text: "INSERT INTO authentications VALUES($1)",
      values: [tokenId],
    };

    await this._pool.query(query);
  }

  async deleteTokenId(tokenId) {
    const query = {
      text: "DELETE FROM authentications WHERE tokenid = $1",
      values: [tokenId],
    };

    await this._pool.query(query);
  }

  async verifyTokenId(tokenId) {
    const query = {
      text: "SELECT * FROM authentications WHERE tokenid = $1",
      values: [tokenId],
    };

    const result = await this._pool.query(query);

    if (result.rowCount === 0) {
      throw new BadRequestException("Refresh token tidak valid");
    }
  }
}

export default AuthenticationsPostgresService;
