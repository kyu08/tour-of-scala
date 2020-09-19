object TourOfScala extends App {
  println("===================")
  abstract class Notification
  case class Email(sender: String, title: String, body: String) extends Notification
  case class SMS(sender: String, title: String, body: String) extends Notification
  case class VoiceRecording(sender: String, title: String, body: String) extends Notification

  def show(notification: Notification): String = {
    notification match {
      case email: Email =>
        s"from $email.sender title: $email.title"
      case sms: SMS =>
        s"from ${sms.sender} title: ${sms.title}"
      case voice: VoiceRecording =>
        s"from $voice.sender title: $voice.title"
    }
  }

  val sms = SMS("kyu", "hello", "i am a cat")
  println(show(sms))

  println("===================")
}