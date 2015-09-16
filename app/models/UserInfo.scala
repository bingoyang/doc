package models

import scala.concurrent.ExecutionContext
import play.api.db.slick.iteratees.SlickPlayIteratees._
import play.api.db.slick.Config.driver
import play.api.db.slick.Config.driver.simple._
import play.api.Logger
import play.api._
import play.api.db.slick.DB
import play.api.Play.current
import java.util.Date
import java.sql.Timestamp

case class UserInfoExt(id: Long,identity: String,gender: Int,height: Double,weight: Double,clothingsize: String,shoesize: Int,tags :String)

class UserInfoExtsTB(tag: Tag) extends Table[UserInfoExt](tag, "user_info_ext") {
  
  def id   = column[Long]("id")
  def gender = column[Int]("gender")
  def height = column[Double]("height")
  def weight = column[Double]("weight")
  def clothingsize = column[String]("clothing_size")
  def shoesize = column[Int]("shoe_size")
  def identity = column[String]("identity")
  def tags = column[String]("tags")
  
  def * = (id,identity,gender,height,weight,clothingsize,shoesize,tags) <> (UserInfoExt.tupled,UserInfoExt.unapply)
  
}

object UserInfoExts {
  /** Base query for the table */
  object userInfoExts extends TableQuery(new UserInfoExtsTB(_))

  def create(userInfoExt:UserInfoExt)(implicit s: Session) = (userInfoExts returning userInfoExts.map(_.id)) += userInfoExt
  
  def findById(id:Long)(implicit s: Session): Option[UserInfoExt] = userInfoExts.filter(_.id === id).list.headOption
  
}

case class UserInfo(id: Long, openid: String,nickname: String,headicon: String,country: String,province: String,city: String,marktime: Date, weixinno: String,phone: String,healthreport: String,checkaddress: String,milion: String,halfmara: String,fullmara: String,industry: String,workunit: String,createdate: Date,updatetime: Date)

/** Mapping of columns to the row object */
class UserInfosTB(tag: Tag) extends Table[UserInfo](tag, "user_info") {
   implicit val JavaUtilDateMapper =
    MappedColumnType .base[java.util.Date, java.sql.Timestamp] (
      d => new java.sql.Timestamp(d.getTime),
      d => new java.util.Date(d.getTime))
  
  def id   = column[Long]("id")
  def openid = column[String]("open_id")
  def nickname = column[String]("nick_name")
  def headicon = column[String]("head_icon")
  def country = column[String]("country")
  def province = column[String]("province")
  def city = column[String]("city")
  def marktime = column[Date]("mark_time")
  def weixinno = column[String]("weixin_no")
  def phone = column[String]("phone")
  def healthreport = column[String]("health_report")
  def checkaddress = column[String]("check_address")
  def milion = column[String]("milion")
  def halfmara = column[String]("half_mara")
  def fullmara = column[String]("full_mara")
  def industry = column[String]("industry")
  def workunit = column[String]("workunit")
  def createdate = column[Date]("create_date")
  def updatetime = column[Date]("update_time")
  
  
  def * = (id,openid,nickname,headicon,country,province,city,marktime, weixinno,phone,healthreport,checkaddress,milion,halfmara,fullmara,industry,workunit,createdate,updatetime) <> (UserInfo.tupled,UserInfo.unapply)
  
}

object UserInfos {
  /** Base query for the table */
  object userInfos extends TableQuery(new UserInfosTB(_))

  def create(userInfo:UserInfo)(implicit s: Session) = (userInfos returning userInfos.map(_.id)) += userInfo
  
  def findById(id:Long)(implicit s: Session): Option[UserInfo] = userInfos.filter(_.id === id).list.headOption
  
}