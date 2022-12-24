import routes from "./routes.js";
import Handler from "./handler.js";

export default {
  name: "album",
  version: "1.0.0",
  register: async (
    server,
    { albumPostgres, songPostgres, storageService, redisService }
  ) => {
    const albumHandler = new Handler(
      albumPostgres,
      songPostgres,
      storageService,
      redisService
    );

    server.route(routes(albumHandler));
  },
};
