export class Exception extends Error {
  constructor(message, statusCode = 500) {
    super(message);
    this.message = message;
    this.statusCode = statusCode;
  }
}

export class NotFoundException extends Exception {
  constructor(message) {
    super(message, 404);
  }
}

export class BadRequestException extends Exception {
  constructor(message) {
    super(message, 400);
  }
}

export class UnauthorizedException extends Exception {
  constructor(message) {
    super(message, 401);
  }
}

export class ForbiddenException extends Exception {
  constructor(message) {
    super(message, 403);
  }
}
