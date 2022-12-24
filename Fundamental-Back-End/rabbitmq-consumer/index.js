import dotenv from 'dotenv'
dotenv.config()

import amqp from 'amqplib'
import { ExportPlaylistListener } from './src/listener/ExportNote.js'
import PlaylistPostgresService from './src/service/postgres/playlist.js'
import { MailService } from './src/service/email/mail.js'

const init = async () => {
  const connection = await amqp.connect(process.env.RABBITMQ_SERVER)
  const channel = await connection.createChannel()
  const playlistPostgres = new PlaylistPostgresService()
  const mailSender = new MailService()
  const exportNoteListener = new ExportPlaylistListener(playlistPostgres, mailSender)
  
  const exportPlaylistQueueName = 'export:playlists'
  await channel.assertQueue(exportPlaylistQueueName, { durable: true })
  channel.consume(exportPlaylistQueueName, (msg) => exportNoteListener.listen(msg), { noAck: true })
}

init()