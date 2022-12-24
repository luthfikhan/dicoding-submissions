import pg from "pg";
import {
  ForbiddenException,
  NotFoundException,
} from "../../utils/exception.js";

const { Pool } = pg;

class PlaylistPostgresService {
  constructor() {
    this._pool = new Pool();
  }

  async addPlaylist({ id, name, owner }) {
    const query = {
      text: "INSERT INTO playlists VALUES($1, $2, $3) RETURNING id",
      values: [id, name, owner],
    };

    const result = await this._pool.query(query);
    return result.rows[0].id;
  }

  async getPlaylists(owner) {
    const result = await this._pool.query({
      text: `SELECT playlists.id, playlists.name, users.username FROM playlists
      LEFT JOIN users ON users.id = playlists.owner
      LEFT JOIN playlist_collaborations ON playlist_collaborations.playlist_id = playlists.id
      WHERE playlists.owner = $1 OR playlist_collaborations.user_id = $1`,
      values: [owner],
    });
    console.log(result.rows);

    return result.rows;
  }

  async getPlaylistById(id) {
    const query = {
      text: `SELECT playlists.id, playlists.name, users.username FROM playlists
      LEFT JOIN users ON users.id = playlists.owner
      WHERE playlists.id = $1`,
      values: [id],
    };

    const result = await this._pool.query(query);

    return result.rows[0];
  }

  async deletePlaylistById(id) {
    const query = {
      text: "DELETE FROM playlists WHERE id = $1 RETURNING id",
      values: [id],
    };

    const result = await this._pool.query(query);
    if (result.rowCount === 0) {
      throw new NotFoundException("Playlist tidak ditemukan");
    }
    return result.rows;
  }

  async verifyPlaylistOwner(id, owner) {
    const result = await this._pool.query({
      text: "SELECT * FROM playlists WHERE id = $1",
      values: [id],
    });

    if (result.rowCount === 0) {
      throw new NotFoundException("Resource not found");
    }

    if (result.rows[0].owner !== owner) {
      throw new ForbiddenException(
        "You are not authorized to access this resource"
      );
    }
  }

  async verifyPlaylistMember(id, idUser) {
    const result = await this._pool.query({
      text: "SELECT * FROM playlists WHERE id = $1",
      values: [id],
    });

    if (result.rowCount === 0) {
      throw new NotFoundException("Resource not found");
    }

    if (result.rows[0].owner !== idUser) {
      const result = await this._pool.query({
        text: "SELECT * FROM playlist_collaborations WHERE playlist_id = $1 AND user_id = $2",
        values: [id, idUser],
      });

      if (result.rowCount === 0) {
        throw new ForbiddenException(
          "You are not authorized to access this resource"
        );
      }
    }
  }

  async verifyPlaylist(playlistId) {
    const result = await this._pool.query({
      text: "SELECT * FROM playlists WHERE id = $1",
      values: [playlistId],
    });

    if (result.rowCount === 0) {
      throw new NotFoundException("Resource not found");
    }
  }

  async addSongToPlaylist(playlistId, songId) {
    const query = {
      text: "INSERT INTO playlist_song VALUES($1, $2)",
      values: [playlistId, songId],
    };

    await this._pool.query(query);
  }

  async getPlaylistSongs(playlistId) {
    const query = {
      text: `SELECT songs.id, songs.title, songs.performer FROM songs 
      LEFT JOIN playlist_song ON songs.id = playlist_song.song_id
      WHERE playlist_song.playlist_id = $1`,
      values: [playlistId],
    };

    const result = await this._pool.query(query);

    return result.rows;
  }

  async deleteSongFromPlaylist(playlistId, songId) {
    const query = {
      text: "DELETE FROM playlist_song WHERE playlist_id = $1 AND song_id = $2",
      values: [playlistId, songId],
    };

    await this._pool.query(query);
  }

  async addCollaboratorToPlaylist({ id, playlistId, userId }) {
    const query = {
      text: "INSERT INTO playlist_collaborations VALUES($1, $2, $3) RETURNING id",
      values: [id, playlistId, userId],
    };

    const result = await this._pool.query(query);
    return result.rows[0].id;
  }

  async deleteCollaboratorFromPlaylist({ playlistId, userId }) {
    const query = {
      text: "DELETE FROM playlist_collaborations WHERE playlist_id = $1 AND user_id = $2",
      values: [playlistId, userId],
    };

    await this._pool.query(query);
  }

  async insertPlaylistActivity(playlistId, songId, userId, action) {
    const time = new Date().toISOString();

    const query = {
      text: "INSERT INTO playlist_song_activities VALUES($1, $2, $3, $4, $5)",
      values: [playlistId, songId, userId, action, time],
    };

    await this._pool.query(query);
  }

  async getPlaylistActivities(playlistId) {
    const query = {
      text: `SELECT playlist_song_activities.action, playlist_song_activities.time, users.username, songs.title FROM playlist_song_activities
      LEFT JOIN users ON users.id = playlist_song_activities.user_id
      LEFT JOIN songs ON songs.id = playlist_song_activities.song_id
      WHERE playlist_song_activities.playlist_id = $1`,
      values: [playlistId],
    };

    const result = await this._pool.query(query);

    return result.rows.sort((a, b) => new Date(a.time) - new Date(b.time));
  }
}

export default PlaylistPostgresService;
