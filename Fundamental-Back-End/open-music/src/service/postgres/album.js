import pg from "pg";
import { NotFoundException } from "../../utils/exception.js";

const { Pool } = pg;

class AlbumPostgresService {
  constructor() {
    this._pool = new Pool();
  }

  async addAlbum({ id, name, year }) {
    const query = {
      text: "INSERT INTO albums VALUES($1, $2, $3) RETURNING id",
      values: [id, name, year],
    };

    const result = await this._pool.query(query);
    return result.rows[0].id;
  }

  async getAlbums() {
    const result = await this._pool.query("SELECT * FROM albums");
    return result.rows;
  }

  async getAlbumById(id) {
    const query = {
      text: "SELECT * FROM albums WHERE id = $1",
      values: [id],
    };

    const result = await this._pool.query(query);

    if (result.rowCount === 0) {
      throw new NotFoundException("Album tidak ditemukan");
    }

    return result.rows.map((album) => ({
      ...album,
      coverUrl: album.coverurl,
      coverurl: undefined,
    }));
  }

  async editAlbumById(id, { name, year }) {
    const query = {
      text: "UPDATE albums SET name = $1, year = $2 WHERE id = $3 RETURNING id",
      values: [name, year, id],
    };

    const result = await this._pool.query(query);
    if (result.rowCount === 0) {
      throw new NotFoundException("Album tidak ditemukan");
    }

    return result.rows;
  }

  async deleteAlbumById(id) {
    const query = {
      text: "DELETE FROM albums WHERE id = $1 RETURNING id",
      values: [id],
    };

    const result = await this._pool.query(query);
    if (result.rowCount === 0) {
      throw new NotFoundException("Album tidak ditemukan");
    }
    return result.rows;
  }

  async insertCoverAlbumById(id, coverurl) {
    const query = {
      text: "UPDATE albums SET coverurl = $1 WHERE id = $2 RETURNING id",
      values: [coverurl, id],
    };

    const result = await this._pool.query(query);
    if (result.rowCount === 0) {
      throw new NotFoundException("Album tidak ditemukan");
    }

    return result.rows;
  }

  async likeDislikeAlbum(albumId, userId) {
    const query = {
      text: "SELECT * FROM user_album_like WHERE album_id = $1 AND user_id = $2",
      values: [albumId, userId],
    };

    const result = await this._pool.query(query);

    if (result.rowCount === 0) {
      await this._pool.query({
        text: "INSERT INTO user_album_like VALUES($1, $2)",
        values: [userId, albumId],
      });

      return true;
    } else {
      await this._pool.query({
        text: "DELETE FROM user_album_like WHERE album_id = $1 AND user_id = $2",
        values: [albumId, userId],
      });
      return false;
    }
  }

  async getAlbumLike(albumId) {
    const query = {
      text: "SELECT * FROM user_album_like WHERE album_id = $1",
      values: [albumId],
    };

    const result = await this._pool.query(query);

    return result.rowCount;
  }
}

export default AlbumPostgresService;
