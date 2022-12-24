import { dirname, resolve } from "path";
import { fileURLToPath } from "url";

const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);

export default (handler) => [
  {
    method: "POST",
    path: "/albums",
    handler: (req, h) => handler.postAlbumHandler(req, h),
  },
  {
    method: "GET",
    path: "/albums",
    handler: (req, h) => handler.getAlbumsHandler(req, h),
  },
  {
    method: "GET",
    path: "/albums/{id}",
    handler: (req, h) => handler.getAlbumByIdHandler(req, h),
  },
  {
    method: "PUT",
    path: "/albums/{id}",
    handler: (req, h) => handler.putAlbumByIdHandler(req, h),
  },
  {
    method: "DELETE",
    path: "/albums/{id}",
    handler: (req, h) => handler.deleteAlbumByIdHandler(req, h),
  },
  {
    method: "POST",
    path: "/albums/{id}/covers",
    handler: (req, h) => handler.postCoverAlbumHandler(req, h),
    options: {
      payload: {
        allow: "multipart/form-data",
        multipart: true,
        output: "stream",
        maxBytes: 512000,
      },
    },
  },
  {
    method: "GET",
    path: "/albums/images/{file*}",
    handler: {
      directory: {
        path: resolve(__dirname, "../../../upload/images"),
      },
    },
  },
  {
    method: "POST",
    path: "/albums/{id}/likes",
    handler: (req, h) => handler.postLikeAlbumHandler(req, h),
    options: {
      auth: "playlist_strategy",
    },
  },
  {
    method: "GET",
    path: "/albums/{id}/likes",
    handler: (req, h) => handler.getLikeAlbumHandler(req, h),
  },
];
