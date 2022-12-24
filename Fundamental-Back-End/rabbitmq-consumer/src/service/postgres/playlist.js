import pg from "pg";

const { Pool } = pg;

class PlaylistPostgresService {
  constructor() {
    this._pool = new Pool();
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

  async getPlaylistById(id) {
    const query = {
      text: `SELECT playlists.id, playlists.name FROM playlists
      LEFT JOIN users ON users.id = playlists.owner
      WHERE playlists.id = $1`,
      values: [id],
    };

    const result = await this._pool.query(query);

    return result.rows[0];
  }
}

export default PlaylistPostgresService;
