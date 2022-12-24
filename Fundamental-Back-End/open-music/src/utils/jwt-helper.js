import Jwt from "@hapi/jwt";
import { BadRequestException } from "./exception.js";

export const generateToken = (payload) => {
  const accessToken = Jwt.token.generate(
    payload,
    process.env.ACCESS_TOKEN_KEY,
    {
      expiresIn: process.env.ACCESS_TOKEN_AGES,
    }
  );
  const refreshToken = Jwt.token.generate(
    payload,
    process.env.REFRESH_TOKEN_KEY,
    {
      expiresIn: process.env.REFRESH_TOKEN_KEY,
    }
  );

  return {
    accessToken,
    refreshToken,
  };
};

export const verifyRefreshToken = (refreshToken) => {
  try {
    const artifacts = Jwt.token.decode(refreshToken);
    Jwt.token.verifySignature(artifacts, process.env.REFRESH_TOKEN_KEY);
    const { payload } = artifacts.decoded;

    return payload;
  } catch (error) {
    throw new BadRequestException("Refresh token tidak valid");
  }
};
