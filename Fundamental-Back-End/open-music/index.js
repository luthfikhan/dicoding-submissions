import * as dotenv from "dotenv";
dotenv.config();

import Hapi from "@hapi/hapi";
import Jwt from "@hapi/jwt";
import Inert from "@hapi/inert";
import path, { dirname } from "path";
import { fileURLToPath } from "url";
import album from "./src/api/album/index.js";
import AlbumPostgresService from "./src/service/postgres/album.js";
import song from "./src/api/song/index.js";
import SongPostgresService from "./src/service/postgres/song.js";
import auth from "./src/api/auth/index.js";
import UsersPostgresService from "./src/service/postgres/users.js";
import { Exception } from "./src/utils/exception.js";
import AuthenticationsPostgresService from "./src/service/postgres/authentications.js";
import playlist from "./src/api/playlist/index.js";
import PlaylistPostgresService from "./src/service/postgres/playlist.js";
import { RabbitmqService } from "./src/service/rabbitmq/rabbitmq.js";
import _exports from "./src/api/exports/index.js";
import { StorageService } from "./src/service/storage/storage.js";
import RedisService from "./src/service/storage/redis.js";

const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);

const songPostgres = new SongPostgresService();
const albumPostgres = new AlbumPostgresService();
const usersPostgres = new UsersPostgresService();
const authenticationsPostgresService = new AuthenticationsPostgresService();
const playlistPostgres = new PlaylistPostgresService();
const rabbitmqService = new RabbitmqService();
const storageService = new StorageService(
  path.resolve(__dirname, "upload/images")
);
const redisService = new RedisService();

const init = async () => {
  const server = Hapi.server({
    port: 5000,
    host: "localhost",
    debug: {
      request: ["*"],
    },
  });
  await server.register([Jwt, Inert]);

  server.auth.strategy("playlist_strategy", "jwt", {
    keys: process.env.ACCESS_TOKEN_KEY,
    verify: {
      aud: false,
      iss: false,
      sub: false,
      maxAgeSec: process.env.ACCESS_TOKEN_AGE,
    },
    validate: async (artifacts) => {
      await authenticationsPostgresService.verifyTokenId(
        artifacts.decoded.payload.tokenId
      );

      return {
        isValid: true,
        credentials: {
          id: artifacts.decoded.payload.id,
          username: artifacts.decoded.payload.username,
        },
      };
    },
  });

  await server.register({
    plugin: album,
    options: {
      albumPostgres,
      songPostgres,
      storageService,
      redisService,
    },
  });
  await server.register({
    plugin: song,
    options: {
      service: songPostgres,
    },
  });
  await server.register({
    plugin: auth,
    options: {
      usersPostgres,
      authenticationsPostgresService,
    },
  });
  await server.register({
    plugin: playlist,
    options: {
      playlistPostgres,
      songPostgres,
      usersPostgres,
    },
  });
  await server.register({
    plugin: _exports,
    options: {
      rabbitmqService,
      playlistPostgres,
    },
  });

  server.ext("onPreResponse", async (request, h) => {
    const response = request.response;
    console.log(
      JSON.stringify({
        path: request.url.pathname,
        method: request.method,
        query: request.query,
        // payload: request.payload,
        statusCode: response.statusCode,
        message: response.message,
        auth: request.auth.credentials,
      })
    );

    if (response instanceof Exception) {
      return h
        .response({
          status: [500].includes(response.statusCode) ? "error" : "fail",
          message: response.message,
        })
        .code(response.statusCode);
    }

    if (response.isBoom) {
      const { statusCode, payload } = response.output;
      const { message } = payload;

      return h
        .response({
          status: [500].includes(statusCode) ? "error" : "fail",
          message,
        })
        .code(statusCode);
    }

    return h.continue;
  });

  await server.start();
  console.log("Server running on %s", server.info.uri);
};

process.on("unhandledRejection", (err) => {
  console.log(err);
  process.exit(1);
});

init();
