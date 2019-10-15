package wf



import play.api.libs.json.{Format, Json}

case class Weather(id: Int, main: String, description: String, icon: String)
case class Clouds(all: Int)
case class Wind(speed: Double, degree: Double)
case class Rain(`3h`: Double)
case class Sys(pod: String)
case class Coordinate(latitude: Double, longitude: Double)
case class Forecast123(cod: String, message: Double, cnt: Int, weatherList: Seq[WeatherList], city: City)
case class WeatherList(date: Int, main1: Main1, weather: Seq[Weather], clouds: Clouds, wind: Wind, rain: Option[Rain], sys: Sys, dateText: String)
case class City(id: Int, name: String, coordinate: Coordinate, country: String, timezone: Int)
case class Main1
(
  temperature: Double,
  temperatureMin: Double,
  temperatureMax: Double,
  pressure: Double,
  seaLevel: Double,
  groundLevel: Double,
  humidity: Int,
  tempKf: Double
)

object Main1 {
  implicit val formatter:Format[Main1]=Json.format[Main1]
}

object Weather{
  implicit val formatter:Format[Weather]=Json.format[Weather]
}

object Clouds{
  implicit val formatter:Format[Clouds]=Json.format[Clouds]
}

object Wind{
  implicit val formatter:Format[Wind]=Json.format[Wind]
}

object Rain{
  implicit val formatter:Format[Rain]=Json.format[Rain]
}

object Sys{
  implicit val formatter:Format[Sys]=Json.format[Sys]
}

object Coordinate{
  implicit val formatter:Format[Coordinate]=Json.format[Coordinate]
}

object City{
  implicit val formatter:Format[City]=Json.format[City]
}

object WeatherList{
  implicit val formatter:Format[WeatherList]=Json.format[WeatherList]
}

object Forecast123{
  implicit val formatter:Format[Forecast123]=Json.format[Forecast123]
}



