package new3

import play.api.libs.json.{JsError, JsSuccess, Json}
import weatherForecast.Forecast

import play.api.libs.json.{JsError, JsSuccess, Json}
import weatherForecast.Forecast

import scala.util.Success
import scala.util.Failure
import java.io._

object Stream6 {
  def main(args: Array[String]): Unit = {

    //load from source
    //change source to weather forecast
    val hdr = List("Temperature", "Temperature Min", "Temperature Max", "Pressure", "Sea Level", "Ground Level", "Humidity", "Description", "Wind Speed", "Wind Degree", "Date", "City")
    val url = "http://laundry-microservice-users.herokuapp.com/api/v1/users"
    val jsonStr = io.Source.fromURL(url).getLines.mkString
    val jsonValue = Json.parse(jsonStr)
    //val forecastOpt = jsonValue.asOpt[Forecast]
    val forecastOpt = jsonValue.asOpt[Forecast]
    println(jsonValue)
  }
}
