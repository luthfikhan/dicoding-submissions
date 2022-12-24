import Joi from "joi";
import { BadRequestException } from "../../utils/exception.js";

export const postExportPlaylistValidator = (data) => {
  const { error } = Joi.object({
    targetEmail: Joi.string().email().required(),
  }).validate(data);

  if (error) {
    throw new BadRequestException(error.message);
  }
};
