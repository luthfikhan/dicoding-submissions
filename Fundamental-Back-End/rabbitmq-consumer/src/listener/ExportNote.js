export class ExportPlaylistListener {
  constructor(playlistPostgres, mailSender) {
    this._playlistPostgres = playlistPostgres;
    this._mailSender = mailSender;
  }

  async listen(message) {
    try {
      const { playlistId, targetEmail } = JSON.parse(
        message.content.toString()
      );

      const songs = await this._playlistPostgres.getPlaylistSongs(playlistId);
      const playlist = await this._playlistPostgres.getPlaylistById(playlistId);

      const data = {
        playlist: {
          ...playlist,
          songs,
        },
      };
      const emailMessage = {
        from: "Open Music",
        to: targetEmail,
        subject: "Ekspor Playlist",
        text: `Terlampir hasil dari ekspor playlist ${playlist.name}`,
        attachments: [
          {
            filename: "playlist.json",
            content: JSON.stringify(data),
          },
        ],
      };

      const status = await this._mailSender.sendEmail(emailMessage);
      console.log(status);
    } catch (error) {
      console.error(error);
    }
  }
}
