import Joi from "joi";
import { BadRequestException } from "../../utils/exception.js";

export const postUserValidator = (data) => {
  const { error } = Joi.object({
    username: Joi.string().required(),
    password: Joi.string().required(),
    fullname: Joi.string().required(),
  }).validate(data);

  if (error) {
    throw new BadRequestException(error.message);
  }
};

export const postAuthenticationsValidator = (data) => {
  const { error } = Joi.object({
    username: Joi.string().required(),
    password: Joi.string().required(),
  }).validate(data);

  if (error) {
    throw new BadRequestException(error.message);
  }
};

export const putAuthenticationsValidator = (data) => {
  const { error } = Joi.object({
    refreshToken: Joi.string().required(),
  }).validate(data);

  if (error) {
    throw new BadRequestException(error.message);
  }
};

export const deleteAuthenticationsValidator = (data) => {
  const { error } = Joi.object({
    refreshToken: Joi.string().required(),
  }).validate(data);

  if (error) {
    throw new BadRequestException(error.message);
  }
};
