import Joi from "joi";
import { BadRequestException } from "../../utils/exception.js";

const albumScheme = Joi.object({
  name: Joi.string().required(),
  year: Joi.number().integer().min(1970).max(2022).required(),
});

export const postAlbumValidator = (data) => {
  const { error } = albumScheme.validate(data);

  if (error) {
    throw new BadRequestException(error.message);
  }
};

export const putAlbumValidator = (data) => {
  const { error } = albumScheme.validate(data);

  if (error) {
    throw new BadRequestException(error.message);
  }
};

export const albumCoverContentTypeValidator = (headers) => {
  const scheme = Joi.object({
    "content-type": Joi.string()
      .valid(
        "image/apng",
        "image/avif",
        "image/gif",
        "image/jpeg",
        "image/png",
        "image/webp",
        "image/svg+xml"
      )
      .required(),
  }).unknown();

  const { error } = scheme.validate(headers);
  if (error) {
    throw new BadRequestException(error.message);
  }
};
