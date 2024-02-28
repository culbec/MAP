package Tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageTask extends Task {
    private Message message;

    public MessageTask(String _id, String _descriere, Message message) {
        super(_id, _descriere);
        this.message = message;
    }

    public Message getMessage() {
        return this.message;
    }

    @Override
    public void execute() {
        System.out.println("Subject: " + this.message.subject + " message: " + this.message.body + " data: " + this.message.date);
    }

    @Override
    public String toString() {
        return "Tasks.MessageTask{" +
                "message=" + message +
                "} ";
    }

    public static class Message {
        private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        private String id;
        private String subject;
        private String body;
        private String from;
        private String to;
        private String date;

        public Message(String id, String subject, String body, String from, String to, LocalDateTime date) {
            this.id = id;
            this.subject = subject;
            this.body = body;
            this.from = from;
            this.to = to;
            this.date = date.format(DATE_FORMATTER);
        }

        @Override
        public String toString() {
            return "Message{" +
                    "id='" + id + '\'' +
                    ", subject='" + subject + '\'' +
                    ", body='" + body + '\'' +
                    ", from='" + from + '\'' +
                    ", to='" + to + '\'' +
                    ", date=" + date +
                    '}';
        }
    }
}
