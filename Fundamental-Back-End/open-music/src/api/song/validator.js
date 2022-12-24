import Joi from "joi";
import { BadRequestException } from "../../utils/exception.js";

const songScheme = Joi.object({
  title: Joi.string().required(),
  year: Joi.number().integer().min(1970).max(2022).required(),
  performer: Joi.string().required(),
  genre: Joi.string().required(),
  duration: Joi.number().integer().min(1),
  albumId: Joi.string(),
});

export const postSongValidator = (data) => {
  const { error } = songScheme.validate(data);

  if (error) {
    throw new BadRequestException(error.message);
  }
};

export const putSongValidator = (data) => {
  const { error } = songScheme.validate(data);

  if (error) {
    throw new BadRequestException(error.message);
  }
};
