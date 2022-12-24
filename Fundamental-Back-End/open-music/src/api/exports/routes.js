export default (handler) => [
  {
    method: "POST",
    path: "/export/playlists/{playlistId}",
    handler: (req, h) => handler.postExportPlaylistHandler(req, h),
    options: {
      auth: "playlist_strategy",
    },
  },
];
