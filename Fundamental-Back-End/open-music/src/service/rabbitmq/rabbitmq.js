import amqp from "amqplib";

export class RabbitmqService {
  async sendToQueue(queueName, data) {
    const connection = await amqp.connect(process.env.RABBITMQ_SERVER);
    const channel = await connection.createChannel();
    await channel.assertQueue(queueName, { durable: true });
    channel.sendToQueue(queueName, Buffer.from(JSON.stringify(data)));
    await channel.close();
  }
}
