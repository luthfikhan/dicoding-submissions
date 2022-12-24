export default (handler) => [
  {
    method: "POST",
    path: "/users",
    handler: (req, h) => handler.postUserHandler(req, h),
  },
  {
    method: "POST",
    path: "/authentications",
    handler: (req, h) => handler.postAuthenticationsHandler(req, h),
  },
  {
    method: "PUT",
    path: "/authentications",
    handler: (req, h) => handler.putAuthenticationsHandler(req, h),
  },
  {
    method: "DELETE",
    path: "/authentications",
    handler: (req, h) => handler.deleteAuthenticationsHandler(req, h),
  },
];
