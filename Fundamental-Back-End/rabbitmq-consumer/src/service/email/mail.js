import nodemailer from 'nodemailer';

export class MailService {
  constructor() {
    this._transporter = nodemailer.createTransport({
      host: process.env.MAIL_HOST,
      port: process.env.MAIL_PORT,
      secure: false,
      auth: {
        user: process.env.MAIL_ADDRESS,
        pass: process.env.MAIL_PASSWORD,
      },
    });
  }

  sendEmail(message) {

    return this._transporter.sendMail(message);
  }
}
