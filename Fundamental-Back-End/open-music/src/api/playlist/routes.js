export default (handler) => [
  {
    method: "POST",
    path: "/playlists",
    handler: (req, h) => handler.postPlaylistHandler(req, h),
    options: {
      auth: "playlist_strategy",
    },
  },
  {
    method: "GET",
    path: "/playlists",
    handler: (req, h) => handler.getPlaylistsHandler(req, h),
    options: {
      auth: "playlist_strategy",
    },
  },
  {
    method: "DELETE",
    path: "/playlists/{id}",
    handler: (req, h) => handler.deletePlaylistByIdHandler(req, h),
    options: {
      auth: "playlist_strategy",
    },
  },
  {
    method: "POST",
    path: "/playlists/{id}/songs",
    handler: (req, h) => handler.postPlaylistSongHandler(req, h),
    options: {
      auth: "playlist_strategy",
    },
  },
  {
    method: "GET",
    path: "/playlists/{id}/songs",
    handler: (req, h) => handler.getPlaylistSongsHandler(req, h),
    options: {
      auth: "playlist_strategy",
    },
  },
  {
    method: "DELETE",
    path: "/playlists/{id}/songs",
    handler: (req, h) => handler.deletePlaylistSongHandler(req, h),
    options: {
      auth: "playlist_strategy",
    },
  },
  {
    method: "GET",
    path: "/playlists/{id}/activities",
    handler: (req, h) => handler.getPlaylistActivitiesHandler(req, h),
    options: {
      auth: "playlist_strategy",
    },
  },
  {
    method: "POST",
    path: "/collaborations",
    handler: (req, h) => handler.postCollaborationHandler(req, h),
    options: {
      auth: "playlist_strategy",
    },
  },
  {
    method: "DELETE",
    path: "/collaborations",
    handler: (req, h) => handler.deleteCollaborationHandler(req, h),
    options: {
      auth: "playlist_strategy",
    },
  },
];
