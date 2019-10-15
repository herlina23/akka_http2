package weatherForecast


import play.api.libs.json.Reads._
import play.api.libs.json._

import scala.util.Try

case class Weather(id: Int, main: String, description: String, icon: String)
case class Clouds(all: Int)
case class Wind(speed: Double, degree: Double)
case class Rain(`3h`: Double)
case class Sys(pod: String)
case class Coordinate(latitude: Double, longitude: Double)

case class Forecast(cod: String, message: Double, cnt: Int, weatherList: Seq[WeatherList], city: City)
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
  implicit val jsonFormat: Format[Main1] = new Format[Main1] {
    override def reads(json: JsValue) = Try {
      val temp = (json \ "temp").as[Double]
      val temp_min = (json \ "temp_min").as[Double]
      val temp_max = (json \ "temp_max").as[Double]
      val pressure = (json \ "pressure").as[Double]
      val sea_level =(json \ "sea_level").as[Double]
      val grnd_level =(json \ "grnd_level").as[Double]
      val humidity =(json \ "humidity").as[Int]
      val temp_kf =(json \ "temp_kf").as[Double]
      Main1(temp,temp_min,temp_max,pressure, sea_level, grnd_level,humidity,temp_kf)
    }.fold(err => JsError(err.getMessage), JsSuccess(_))

    override def writes(o: Main1) = Json.obj(
      "temp" -> o.temperature,
      "temp_min" -> o.temperatureMin,
      "temp_max" -> o.temperatureMax,
      "pressure" -> o.pressure,
      "sea_level" -> o.seaLevel,
      "grnd_level" -> o.groundLevel,
      "humidity" -> o.humidity,
      "temp_kf" -> o.tempKf
    )
  }
}

object Weather{
  implicit val jsonFormat: Format[Weather] = new Format[Weather] {
    override def reads(json: JsValue) = Try {
      val id = (json \ "id").as[Int]
      val main = (json \ "main").as[String]
      val description = (json \ "description").as[String]
      val icon = (json \ "icon").as[String]
      Weather(id,main,description,icon)
    }.fold(err => JsError(err.getMessage), JsSuccess(_))

    override def writes(o: Weather) = Json.obj(
      "id" -> o.id,
      "main" -> o.main,
      "description" -> o.description,
      "icon" -> o.icon
    )
  }
}

object Clouds{
  implicit val jsonFormat: Format[Clouds] = new Format[Clouds] {
    override def reads(json: JsValue) = Try {
      val all = (json \ "all").as[Int]
      Clouds(all)
    }.fold(err => JsError(err.getMessage), JsSuccess(_))

    override def writes(o: Clouds) = Json.obj(
      "all" -> o.all
    )
  }
}

object Wind{
  implicit val jsonFormat: Format[Wind] = new Format[Wind] {
    override def reads(json: JsValue) = Try {
      val speed = (json \ "speed").as[Double]
      val deg = (json \ "deg").as[Double]
      Wind(speed,deg)
    }.fold(err => JsError(err.getMessage), JsSuccess(_))

    override def writes(o: Wind) = Json.obj(
      "speed" -> o.speed,
      "deg" -> o.degree
    )
  }
}

object Rain{
  implicit val jsonFormat: Format[Rain] = new Format[Rain] {
    override def reads(json: JsValue) = Try {
      val`3h` = (json \ "3h").as[Int]
      Rain(`3h`)
    }.fold(err => JsError(err.getMessage), JsSuccess(_))

    override def writes(o: Rain) = Json.obj(
      "3h" -> o.`3h`
    )
  }
}

object Sys{
  implicit val jsonFormat: Format[Sys] = new Format[Sys] {
    override def reads(json: JsValue) = Try {
      val pod = (json \ "pod").as[String]
      Sys(pod)
    }.fold(err => JsError(err.getMessage), JsSuccess(_))

    override def writes(o: Sys) = Json.obj(
      "pod" -> o.pod
    )
  }
}

object Coordinate{
  implicit val jsonFormat: Format[Coordinate] = new Format[Coordinate] {
    override def reads(json: JsValue) = Try {
      val lat = (json \ "lat").as[Double]
      val lon = (json \ "lon").as[Double]
      Coordinate(lat,lon)
    }.fold(err => JsError(err.getMessage), JsSuccess(_))

    override def writes(o: Coordinate) = Json.obj(
      "lat" -> o.latitude,
      "lon" -> o.longitude
    )
  }
}

object City{
  implicit val jsonFormat: Format[City] = new Format[City] {
    override def reads(json: JsValue) = Try {
      val id = (json \ "id").as[Int]
      val name = (json \ "name").as[String]
      val coord = (json \ "coord").as[Coordinate]
      val country = (json \ "country").as[String]
      val timezone = (json \ "timezone").as[Int]
      City(id,name,coord,country,timezone)
    }.fold(err => JsError(err.getMessage), JsSuccess(_))

    override def writes(o: City) = Json.obj(
      "id" -> o.id,
      "name" -> o.name,
      "coord" -> o.coordinate,
      "country" -> o.country,
      "timezone" -> o.timezone
    )
  }
}

object WeatherList{
  implicit val jsonFormat: Format[WeatherList] = new Format[WeatherList] {
    override def reads(json: JsValue) = Try {
      val dt = (json \ "dt").as[Int]
      val main1 = (json \ "main").as[Main1]
      val weather = (json \ "weather").as[Seq[Weather]]
      val clouds = (json \ "clouds").as[Clouds]
      val wind = (json \ "wind").as[Wind]
      val rain = (json \ "rain").asOpt[Rain]
      val sys = (json \ "sys").as[Sys]
      val dt_txt = (json \ "dt_txt").as[String]
      WeatherList(dt,main1,weather,clouds,wind,rain,sys,dt_txt)
    }.fold(err => JsError(err.getMessage), JsSuccess(_))

    override def writes(o: WeatherList) = Json.obj(
      "dt" -> o.date,
      "main" -> o.main1,
      "weather" -> o.weather,
      "clouds" -> o.clouds,
      "wind" -> o.wind,
      "rain" -> o.rain,
      "sys" -> o.sys,
      "dt_txt" -> o.dateText
    )
  }
}

object Forecast{
  implicit val jsonFormat: Format[Forecast] = new Format[Forecast] {
    override def reads(json: JsValue) = Try {
      val cod = (json \ "cod").as[String]
      val message = (json \ "message").as[Double]
      val cnt = (json \ "cnt").as[Int]
      val weaterList = (json \ "list").as[Seq[WeatherList]]
      val city = (json \ "city").as[City]
      Forecast(cod,message,cnt,weaterList,city)
    }.fold(err => JsError(err.getMessage), JsSuccess(_))

    override def writes(o: Forecast) = Json.obj(
      "cod" -> o.cod,
      "message" -> o.message,
      "cnt" -> o.cnt,
      "list" -> o.weatherList,
      "city" -> o.city
    )
  }
}