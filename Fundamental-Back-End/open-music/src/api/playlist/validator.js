import Joi from "joi";
import { BadRequestException } from "../../utils/exception.js";

export const postPlaylistValidator = (data) => {
  const { error } = Joi.object({
    name: Joi.string().required(),
  }).validate(data);

  if (error) {
    throw new BadRequestException(error.message);
  }
};

export const postPlaylistSongValidator = (data) => {
  const { error } = Joi.object({
    songId: Joi.string().required(),
  }).validate(data);

  if (error) {
    throw new BadRequestException(error.message);
  }
};

export const deletePlaylistSongValidator = (data) => {
  const { error } = Joi.object({
    songId: Joi.string().required(),
  }).validate(data);

  if (error) {
    throw new BadRequestException(error.message);
  }
};

export const postCollaborationValidator = (data) => {
  const { error } = Joi.object({
    playlistId: Joi.string().required(),
    userId: Joi.string().required(),
  }).validate(data);

  if (error) {
    throw new BadRequestException(error.message);
  }
};

export const deleteCollaborationValidator = (data) => {
  const { error } = Joi.object({
    playlistId: Joi.string().required(),
    userId: Joi.string().required(),
  }).validate(data);

  if (error) {
    throw new BadRequestException(error.message);
  }
};
