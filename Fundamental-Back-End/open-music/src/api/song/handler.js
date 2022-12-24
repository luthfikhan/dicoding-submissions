import { nanoid } from "nanoid";
import { postSongValidator, putSongValidator } from "./validator.js";

class SongHandler {
  constructor(service) {
    this._service = service;
  }

  async postSongHandler(request, h) {
    postSongValidator(request.payload);
    const id = nanoid(16);

    const songId = await this._service.addSong({
      ...request.payload,
      id: `song-${id}`,
    });

    return h
      .response({
        status: "success",
        message: "Musik berhasil ditambahkan",
        data: {
          songId,
        },
      })
      .code(201);
  }

  async getSongHandler(request) {
    const songs = await this._service.getSongs(request.query);
    return {
      status: "success",
      data: {
        songs,
      },
    };
  }

  async getSongByIdHandler(request) {
    const { id } = request.params;
    const song = await this._service.getSongById(id);

    return {
      status: "success",
      data: {
        song: song[0],
      },
    };
  }

  async putSongByIdHandler(request) {
    putSongValidator(request.payload);
    const { id } = request.params;

    await this._service.editSongById(id, request.payload);

    return {
      status: "success",
      message: "Musik berhasil diperbarui",
    };
  }

  async deleteSongByIdHandler(request) {
    const { id } = request.params;
    await this._service.deleteSongById(id);

    return {
      status: "success",
      message: "Musik berhasil dihapus",
    };
  }
}

export default SongHandler;
