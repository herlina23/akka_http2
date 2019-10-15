package new1

import java.nio.file.{Files, Path}
import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._
import play.api.libs.json.{JsError, JsSuccess, Json}
import akka.stream.alpakka.file.scaladsl.Directory
import java.nio.file.{Files, Path}
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._
import play.api.libs.json.{JsError, JsSuccess, Json}
import scala.util.Success
import scala.util.Failure
import akka.NotUsed
import java.nio.file.{Files, Paths}
import akka.stream._
import akka.stream.scaladsl.{FileIO, Sink, Source}
import akka.util.ByteString
import scala.concurrent.Future
import java.io._
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._
import play.api.libs.json.{JsError, JsSuccess, Json}
import scala.util.Success
import scala.util.Failure
import akka.NotUsed
import java.nio.file.{Files, Paths}
import akka.stream._
import akka.stream.scaladsl.{FileIO, Sink, Source}
import akka.util.ByteString
import scala.concurrent.Future
import akka.Done
import akka.stream.alpakka.csv.scaladsl.CsvParsing
import akka.stream.alpakka.csv.scaladsl.CsvToMap
import akka.stream.alpakka.csv.scaladsl.{CsvFormatting, CsvQuotingStyle}
import CsvFormatting._
import scala.collection.immutable
import java.nio.charset.StandardCharsets
import akka.stream.Attributes.Attribute
import scala.util.Random
import akka.stream.alpakka.csv.scaladsl.{ByteOrderMark, CsvFormatting}
import scala.collection.JavaConversions.mapAsScalaMap
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Parsing123 {
  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem("cobaScala")
    implicit val materializer = ActorMaterializer()

    val originDir = "E:\\Project_B\\stream4"
    val resultDir = "E:\\Project_B\\ujicoba123"
    //val objectFilter = args(2)
    val datetime = LocalDateTime.now.format(
      DateTimeFormatter.ofPattern("YYYY.MM.dd_HH.mm.ss")
    )

    val delimiter = SemiColon

    val quoteChar = DoubleQuote
    val escapeChar = Backslash
    val endOfLine = "\r\n"

    val logFile =
      Paths.get(originDir)

    val flow =
      CsvFormatting
        .format(
          delimiter,
          quoteChar,
          escapeChar,
          endOfLine,
          CsvQuotingStyle.Required,
          charset = StandardCharsets.UTF_8,
          byteOrderMark = None
        )

    val streamSource = Directory
      .ls(logFile)
      .flatMapConcat { path =>
        FileIO
          .fromPath(path)
          .via(
            CsvParsing
              .lineScanner(CsvParsing.SemiColon, maximumLineLength = 102400)
          )
          .via(CsvToMap.toMap())
      }
      .map(_.mapValues(_.utf8String))
//      .filter { newrow =>
//        if (newrow
//          .get("object_content")
//          .exists(_.replace("'", "").toLowerCase() == objectFilter)) true
//        else false
//      }
      .groupBy(
        Int.MaxValue,
        a => a.getOrElse("final_sentiment", "not found")
      )
      .prefixAndTail(1)
      .mapAsync(1) {
        case (seq, source) =>
          val headSeq = seq.head

          val identic1 = headSeq.get("final_sentiment").head
          val identic2 = headSeq.get("final_sentiment").head

          val finalName = resultDir + "\\" + datetime + "_" + identic1 + "_" + identic2 + ".txt"

          val listHeader = List(
            "content"
          )

          val afterRmv = source.map { mp =>
            listHeader.map { name =>
              mp.getOrElse(name, "")
            }

          }

          afterRmv
            //.prepend(Source.single(listHeader))
            .via(flow)
            .toMat(FileIO.toPath(Paths.get(finalName)))(Keep right)
            .run()
            .recover {
              case e: Throwable =>
                system.log.error(e, "Error on writing file!")
            }

      }
      //.take(5)
      .mergeSubstreams
      .async
      .runForeach(println)

    val hasil = streamSource.map { coba =>
      println(coba)
    }

    for {
      _ <- hasil.recover {
        case e: Throwable =>
          system.log.error(e, "Error on running main process!")

      }
      _ <- system.terminate()
    } yield {
      sys.exit()
    }
  }

}
