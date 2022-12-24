import redis from "redis";

export default class RedisService {
  constructor() {
    this._client = redis.createClient({ url: process.env.REDIS_SERVER });
    this._client.on("error", (error) => {
      console.error(error);
    });
    this._client.connect();
  }

  async set(key, value, expirationInSecond = 30 * 60) {
    this._client.set(key, value, { ex: expirationInSecond });
  }

  async get(key) {
    const result = await this._client.get(key);

    return result;
  }

  async delete(key) {
    return await this._client.del(key);
  }
}
