import pg from "pg";
import { NotFoundException } from "../../utils/exception.js";

const { Pool } = pg;

class SongPostgresService {
  constructor() {
    this._pool = new Pool();
  }

  async addSong({ id, title, year, performer, genre, duration, albumId }) {
    const query = {
      text: "INSERT INTO songs VALUES($1, $2, $3, $4, $5, $6, $7) RETURNING id",
      values: [id, title, year, performer, genre, duration, albumId],
    };

    const result = await this._pool.query(query);
    return result.rows[0].id;
  }

  async getSongs({ title, performer }) {
    const query = {};

    if (title && performer) {
      query.text = `SELECT songs.id, songs.title, songs.performer FROM songs WHERE LOWER(songs.title) LIKE LOWER($1) AND LOWER(songs.performer) LIKE LOWER($2)`;
      query.values = [`%${title}%`, `%${performer}%`];
    } else if (title) {
      query.text = `SELECT songs.id, songs.title, songs.performer FROM songs WHERE LOWER(songs.title) LIKE LOWER($1)`;
      query.values = [`%${title}%`];
    } else if (performer) {
      query.text = `SELECT songs.id, songs.title, songs.performer FROM songs WHERE LOWER(songs.performer) LIKE LOWER($1)`;
      query.values = [`%${performer}%`];
    } else {
      query.text = `SELECT songs.id, songs.title, songs.performer FROM songs`;
    }

    const result = await this._pool.query(query);
    return result.rows;
  }

  async getSongById(id) {
    const query = {
      text: "SELECT * FROM songs WHERE id = $1",
      values: [id],
    };

    const result = await this._pool.query(query);

    if (result.rowCount === 0) {
      throw new NotFoundException("Musik tidak ditemukan");
    }

    return result.rows;
  }

  async editSongById(id, { title, year, performer, genre, duration, albumId }) {
    const query = {
      text: "UPDATE songs SET title = $1, year = $2, performer = $3, genre = $4, duration = $5, album_id = $6 WHERE id = $7 RETURNING id",
      values: [title, year, performer, genre, duration, albumId, id],
    };

    const result = await this._pool.query(query);

    if (result.rowCount === 0) {
      throw new NotFoundException("Musik tidak ditemukan");
    }

    return result.rows;
  }

  async deleteSongById(id) {
    const query = {
      text: "DELETE FROM songs WHERE id = $1 RETURNING id",
      values: [id],
    };

    const result = await this._pool.query(query);

    if (result.rowCount === 0) {
      throw new NotFoundException("Musik tidak ditemukan");
    }

    return result.rows;
  }

  async getSongByAlbumId(id) {
    const query = {
      text: "SELECT * FROM songs WHERE album_id = $1",
      values: [id],
    };

    const result = await this._pool.query(query);
    return result.rows;
  }
}

export default SongPostgresService;
