import { nanoid } from "nanoid";
import {
  albumCoverContentTypeValidator,
  postAlbumValidator,
  putAlbumValidator,
} from "./validator.js";

class AlbumHandler {
  constructor(service, songPostgres, storageService, redisService) {
    this._service = service;
    this._songPostgres = songPostgres;
    this._storageService = storageService;
    this._redisService = redisService;

    this._albumCacheKey = "albums";
  }

  async postAlbumHandler(request, h) {
    postAlbumValidator(request.payload);
    const { name, year } = request.payload;
    const id = nanoid(16);

    const albumId = await this._service.addAlbum({
      name,
      year,
      id: `album-${id}`,
    });

    return h
      .response({
        status: "success",
        message: "Album berhasil ditambahkan",
        data: {
          albumId,
        },
      })
      .code(201);
  }

  async getAlbumsHandler() {
    const albums = await this._service.getAlbums();
    return {
      status: "success",
      data: {
        albums,
      },
    };
  }

  async getAlbumByIdHandler(request) {
    const { id } = request.params;
    const album = await this._service.getAlbumById(id);
    const songs = await this._songPostgres.getSongByAlbumId(id);

    return {
      status: "success",
      data: {
        album: {
          ...album[0],
          songs,
        },
      },
    };
  }

  async putAlbumByIdHandler(request) {
    putAlbumValidator(request.payload);
    const { id } = request.params;

    await this._service.editAlbumById(id, request.payload);

    return {
      status: "success",
      message: "Album berhasil diperbarui",
    };
  }

  async deleteAlbumByIdHandler(request) {
    const { id } = request.params;
    await this._service.deleteAlbumById(id);

    return {
      status: "success",
      message: "Album berhasil dihapus",
    };
  }

  async postCoverAlbumHandler(request, h) {
    const { cover } = request.payload;
    albumCoverContentTypeValidator(cover.hapi.headers);
    const { id } = request.params;

    const filename = await this._storageService.writeFile(cover, cover.hapi);
    const coverurl = `http://localhost:5000/albums/images/${filename}`;
    await this._service.insertCoverAlbumById(id, coverurl);

    return h
      .response({
        status: "success",
        message: "Sampul berhasil diunggah",
      })
      .code(201);
  }

  async postLikeAlbumHandler(request, h) {
    const { id } = request.params;
    const { id: userId } = request.auth.credentials;

    await this._service.getAlbumById(id);
    const isLike = await this._service.likeDislikeAlbum(id, userId);
    await this._redisService.delete(`${this._albumCacheKey}_likes:${id}`);

    return h
      .response({
        status: "success",
        message: isLike
          ? "Album berhasil disukai"
          : "Berhasil membatalkan suka",
      })
      .code(201);
  }

  async getLikeAlbumHandler(request, h) {
    const { id } = request.params;
    const cacheKey = `${this._albumCacheKey}_likes:${id}`;
    await this._service.getAlbumById(id);
    let likeCount = await this._redisService.get(cacheKey);
    let dataSources = "cache";

    if (likeCount === null) {
      likeCount = await this._service.getAlbumLike(id);
      await this._redisService.set(cacheKey, likeCount);
      dataSources = "database";
    }
    const response = h.response({
      status: "success",
      data: {
        likes: Number(likeCount),
      },
    });

    if (dataSources === "cache") {
      response.header("X-Data-Source", "cache");
    }

    return response;
  }
}

export default AlbumHandler;
