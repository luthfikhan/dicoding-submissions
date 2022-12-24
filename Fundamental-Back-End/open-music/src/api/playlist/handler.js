import { nanoid } from "nanoid";
import {
  deleteCollaborationValidator,
  deletePlaylistSongValidator,
  postCollaborationValidator,
  postPlaylistSongValidator,
  postPlaylistValidator,
} from "./validator.js";

class Handler {
  constructor(playlistPostgressService, songPostgres, usersPostgres) {
    this._playlistPostgressService = playlistPostgressService;
    this._songPostgres = songPostgres;
    this._usersPostgres = usersPostgres;
  }

  async postPlaylistHandler(request, h) {
    postPlaylistValidator(request.payload);
    const { name } = request.payload;
    const id = nanoid(16);

    const playlistId = await this._playlistPostgressService.addPlaylist({
      name,
      id: `playlist-${id}`,
      owner: request.auth.credentials.id,
    });

    return h
      .response({
        status: "success",
        message: "Playlist berhasil ditambahkan",
        data: {
          playlistId,
        },
      })
      .code(201);
  }

  async getPlaylistsHandler(request) {
    const { id } = request.auth.credentials;

    const playlists = await this._playlistPostgressService.getPlaylists(id);

    return {
      status: "success",
      data: {
        playlists,
      },
    };
  }

  async deletePlaylistByIdHandler(request) {
    const { id } = request.params;
    const { id: owner } = request.auth.credentials;
    await this._playlistPostgressService.verifyPlaylist(id);
    await this._playlistPostgressService.verifyPlaylistOwner(id, owner);

    await this._playlistPostgressService.deletePlaylistById(id);

    return {
      status: "success",
      message: "Playlist berhasil dihapus",
    };
  }

  async postPlaylistSongHandler(request, h) {
    postPlaylistSongValidator(request.payload);

    const { id } = request.params;
    const { id: owner } = request.auth.credentials;
    const { songId } = request.payload;

    await this._playlistPostgressService.verifyPlaylist(id);
    await this._playlistPostgressService.verifyPlaylistMember(id, owner);
    await this._songPostgres.getSongById(songId);
    await this._playlistPostgressService.addSongToPlaylist(id, songId);
    await this._playlistPostgressService.insertPlaylistActivity(
      id,
      songId,
      owner,
      "add"
    );

    return h
      .response({
        status: "success",
        message: "Musik berhasil ditambahkan ke playlist",
      })
      .code(201);
  }

  async getPlaylistSongsHandler(request) {
    const { id } = request.params;
    const { id: owner } = request.auth.credentials;

    await this._playlistPostgressService.verifyPlaylist(id);
    await this._playlistPostgressService.verifyPlaylistMember(id, owner);
    const songs = await this._playlistPostgressService.getPlaylistSongs(id);
    const playlist = await this._playlistPostgressService.getPlaylistById(id);

    return {
      status: "success",
      data: {
        playlist: {
          ...playlist,
          songs,
        },
      },
    };
  }

  async deletePlaylistSongHandler(request) {
    deletePlaylistSongValidator(request.payload);

    const { id } = request.params;
    const { id: owner } = request.auth.credentials;
    const { songId } = request.payload;

    await this._playlistPostgressService.verifyPlaylist(id);
    await this._playlistPostgressService.verifyPlaylistMember(id, owner);
    await this._songPostgres.getSongById(songId);
    await this._playlistPostgressService.deleteSongFromPlaylist(id, songId);
    await this._playlistPostgressService.insertPlaylistActivity(
      id,
      songId,
      owner,
      "delete"
    );

    return {
      status: "success",
      message: "Musik berhasil dihapus dari playlist",
    };
  }

  async getPlaylistActivitiesHandler(request) {
    const { id } = request.params;
    const { id: owner } = request.auth.credentials;

    await this._playlistPostgressService.verifyPlaylist(id);
    await this._playlistPostgressService.verifyPlaylistMember(id, owner);
    const activities =
      await this._playlistPostgressService.getPlaylistActivities(id);

    return {
      status: "success",
      data: {
        playlistId: id,
        activities,
      },
    };
  }

  async postCollaborationHandler(request, h) {
    postCollaborationValidator(request.payload);
    const id = `collab-${nanoid(16)}`;

    const { id: owner } = request.auth.credentials;
    const { playlistId } = request.payload;

    await this._playlistPostgressService.verifyPlaylist(playlistId);
    await this._playlistPostgressService.verifyPlaylistMember(
      playlistId,
      owner
    );
    await this._usersPostgres.verifyExistingUser(request.payload.userId);

    await this._playlistPostgressService.addCollaboratorToPlaylist({
      id,
      ...request.payload,
    });

    return h
      .response({
        status: "success",
        message: "Kolaborasi berhasil ditambahkan",
        data: {
          collaborationId: id,
        },
      })
      .code(201);
  }

  async deleteCollaborationHandler(request) {
    deleteCollaborationValidator(request.payload);
    const { playlistId } = request.payload;
    const { id: owner } = request.auth.credentials;

    await this._playlistPostgressService.verifyPlaylist(playlistId);
    await this._playlistPostgressService.verifyPlaylistOwner(playlistId, owner);

    await this._playlistPostgressService.deleteCollaboratorFromPlaylist(
      request.payload
    );

    return {
      status: "success",
      message: "Kolaborasi berhasil dihapus",
    };
  }
}

export default Handler;
