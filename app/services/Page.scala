package services

import play.api.libs.json.JsSuccess
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsString
import play.api.libs.json.JsValue
import play.api.libs.json.Format
import play.api.libs.json.JsNumber
import play.api.libs.json.JsArray
import play.api.libs.json.Json
import play.api.libs.json.Json.JsValueWrapper
import play.api.libs.json.JsNumber
import java.sql.Date
import java.sql.Timestamp
import play.api.libs.json.JsBoolean

/**
 * @author yangliubing
 */

case class Page[T](data: Option[List[T]] = None, recordsTotal: Int, pageNo: Int, pageSize: Int, totalPage: Int,draw:Int)

object Page {
  implicit val anyMapFormat = new Format[Map[String, Any]] {
    def writes(map: Map[String, Any]): JsValue =
      Json.obj(map.map {
        case (s, o) =>
          val ret: (String, JsValueWrapper) = o match {
            case _: String => s -> JsString(o.asInstanceOf[String])
            case _: Int    => s -> JsNumber(o.asInstanceOf[Int])
            case _: Double => s -> JsNumber(o.asInstanceOf[Double])
            case _: Date   => s -> JsString(o.asInstanceOf[String])
            case _: Timestamp   => s -> JsString(o.toString())
            case _: Long   => s -> JsNumber(o.asInstanceOf[Long])
            case _: Boolean   => s -> JsBoolean(o.asInstanceOf[Boolean])
            case _         => s -> JsArray(o.asInstanceOf[List[String]].map(JsString(_)))
          }
          ret
      }.toSeq: _*)

    def reads(jv: JsValue): JsResult[Map[String, Object]] =
      JsSuccess(jv.as[Map[String, JsValue]].map {
        case (k, v) =>
          k -> (v match {
            case s: JsString => s.as[String]
            case l           => l.as[List[String]]
          })
      })
  }

  implicit object PageFormat extends Format[Page[Map[String, Any]]] {

    def writes(page: Page[Map[String, Any]]): JsValue = {
      val pageSeq = Seq(
        "draw" -> JsNumber(page.draw),
        "recordsTotal" -> JsNumber(page.recordsTotal),
        "pageNo" -> JsNumber(page.pageNo),
        "pageSize" -> JsNumber(page.pageSize),
        "recordsFiltered" -> JsNumber(page.recordsTotal),
        "data" -> Json.toJson(page.data.get))
      JsObject(pageSeq)
    }
    // (i don't need this method; just here to satisfy the api)
    def reads(json: JsValue): JsResult[Page[Map[String, Any]]] = {
      JsSuccess(Page(None, 0, 0, 0, 0,0))
    }
  }
}