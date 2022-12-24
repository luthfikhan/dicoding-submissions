import { postExportPlaylistValidator } from "./validator.js";

class Handler {
  constructor(rabbitmqService, playlistPostgres) {
    this._rabbitmqService = rabbitmqService;
    this._playlistPostgres = playlistPostgres;
  }

  async postExportPlaylistHandler(request, h) {
    postExportPlaylistValidator(request.payload);
    const playlistId = request.params.playlistId;
    const { id: userId } = request.auth.credentials;
    const { targetEmail } = request.payload;

    await this._playlistPostgres.verifyPlaylist(playlistId);
    await this._playlistPostgres.verifyPlaylistOwner(playlistId, userId);

    await this._rabbitmqService.sendToQueue("export:playlists", {
      playlistId,
      userId,
      targetEmail,
    });

    return h
      .response({
        status: "success",
        message: "Permintaan Anda sedang kami proses",
      })
      .code(201);
  }
}

export default Handler;
