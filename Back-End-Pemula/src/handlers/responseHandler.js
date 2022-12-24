/* eslint-disable import/prefer-default-export */
export const successResponse = (data) => {
  return {
    status: 'success',
    data,
  };
};

export const notFoundResponse = (message) => {
  return {
    status: 'fail',
    message,
  };
};

export const internalServerErrorResponse = (message) => {
  return {
    status: 'error',
    message,
  };
};
