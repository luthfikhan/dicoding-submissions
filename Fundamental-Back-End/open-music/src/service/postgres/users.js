import pg from "pg";
import {
  BadRequestException,
  NotFoundException,
  UnauthorizedException,
} from "../../utils/exception.js";

const { Pool } = pg;

class UsersPostgresService {
  constructor() {
    this._pool = new Pool();
  }

  async addUser({ id, username, password, fullname }) {
    const query = {
      text: "INSERT INTO users VALUES($1, $2, $3, $4) RETURNING id",
      values: [id, username, password, fullname],
    };

    const result = await this._pool.query(query);
    return result.rows[0].id;
  }

  async checkUsername(id) {
    const query = {
      text: "SELECT * FROM users WHERE username = $1",
      values: [id],
    };

    const result = await this._pool.query(query);

    if (result.rowCount > 0) {
      throw new BadRequestException("Username sudah digunakan");
    }
  }
  async getUserByUsername(id) {
    const query = {
      text: "SELECT * FROM users WHERE username = $1",
      values: [id],
    };

    const result = await this._pool.query(query);

    if (result.rowCount === 0) {
      throw new UnauthorizedException("Username atau password salah");
    }

    return result.rows[0];
  }

  async verifyExistingUser(id) {
    const query = {
      text: "SELECT * FROM users WHERE id = $1",
      values: [id],
    };

    const result = await this._pool.query(query);

    if (result.rowCount === 0) {
      throw new NotFoundException("User tidak ditemukan");
    }
  }
}

export default UsersPostgresService;
