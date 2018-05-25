import com.typesafe.config.ConfigFactory

object AppConfig {
  private lazy val raw = ConfigFactory.load()
  lazy val config = WebConfig(raw.getString("host"), raw.getInt("port"))
}

final case class WebConfig(host: String, port: Int)
