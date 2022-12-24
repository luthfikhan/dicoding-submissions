import bcrypt from "bcrypt";
import { nanoid } from "nanoid";
import { UnauthorizedException } from "../../utils/exception.js";
import { generateToken, verifyRefreshToken } from "../../utils/jwt-helper.js";
import {
  deleteAuthenticationsValidator,
  postAuthenticationsValidator,
  postUserValidator,
  putAuthenticationsValidator,
} from "./validator.js";

class Handler {
  constructor(usersPostgres, authenticationsPostgresService) {
    this._usersPostgres = usersPostgres;
    this._authenticationsPostgresService = authenticationsPostgresService;
  }

  async postUserHandler(request, h) {
    postUserValidator(request.payload);
    await this._usersPostgres.checkUsername(request.payload.username);
    const id = nanoid(16);

    const hashedPassword = await bcrypt.hash(request.payload.password, 10);

    const userId = await this._usersPostgres.addUser({
      ...request.payload,
      password: hashedPassword,
      id: `user-${id}`,
    });

    return h
      .response({
        status: "success",
        message: "Pendaftaran berhasil",
        data: {
          userId,
        },
      })
      .code(201);
  }

  async postAuthenticationsHandler({ payload }, h) {
    postAuthenticationsValidator(payload);
    const tokenId = `token-${nanoid(16)}`;

    const { username, password, id } =
      await this._usersPostgres.getUserByUsername(payload.username);

    const match = await bcrypt.compare(payload.password, password);

    if (!match) {
      throw new UnauthorizedException("Username atau password salah");
    }

    await this._authenticationsPostgresService.insertTokenId(tokenId);

    return h
      .response({
        status: "success",
        data: generateToken({ username, tokenId, id }),
      })
      .code(201);
  }

  async putAuthenticationsHandler(request) {
    putAuthenticationsValidator(request.payload);

    const { username, tokenId, id } = verifyRefreshToken(
      request.payload.refreshToken
    );
    await this._authenticationsPostgresService.verifyTokenId(tokenId);

    return {
      status: "success",
      data: generateToken({ username, tokenId, id }),
    };
  }

  async deleteAuthenticationsHandler(request) {
    deleteAuthenticationsValidator(request.payload);

    const { tokenId } = verifyRefreshToken(request.payload.refreshToken);

    await this._authenticationsPostgresService.deleteTokenId(tokenId);

    return {
      status: "success",
      message: "Logout berhasil",
    };
  }
}

export default Handler;
