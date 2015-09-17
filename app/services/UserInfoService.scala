package services

import scala.slick.jdbc.StaticQuery
import scala.slick.jdbc.StaticQuery._
import play.api.Play.current
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick.DB
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.slick.jdbc._
import java.util.ArrayList
import models._

/**
 * @author yangliubing
 */
object UserInfoService {
  implicit val getMapResult = GetResult[Map[String,Any]](r => {
    val rs = r.rs // <- jdbc result set
    val md = rs.getMetaData();
    val res = (1 to r.numColumns).map{ i=> md.getColumnName(i) -> rs.getObject(i) }.toMap[String,Any]
    r.nextRow // <- use Slick's advance method to avoid endless loop
    res
  })
  
  def findUserInfos(pageSize :Int,pageNo :Int,condition :Condition):Page[Map[String,Any]] = {
      
      DB.withSession { implicit s: Session =>
       val q =  StaticQuery.query[(Int,Int),Map[String,Any]]("""select *  from user_info  limit ?,?""")
       val d = q.list((pageNo - 1)*pageSize,pageSize).toList
       val q2 = StaticQuery.query[(Int,Int),Int]("""select count(id) from user_info limit ?,?""")
       val total = q2.list((pageNo - 1)*pageSize,pageSize).head
       val totalPage = if(total%pageSize > 0) total/pageSize+1 else total/pageSize;
       new Page(Some(d),total,pageSize,pageNo,totalPage)
      }
  }
  
  def insertUserInfo(u: UserInfo) = {
      DB.withSession { implicit s: Session =>
        (sqlu"insert into user_info (open_id,nick_name,head_icon,country,province,city,mark_time,weixin_no,phone,health_report,check_address,milion,half_mara,full_mara,industry,workunit,create_date,identity,gender,height,weight,clothing_size,shoe_size,tags) values("+? u.openid +"," +? u.nickname + "," +? u.headicon + "," +? u.country + "," +? u.province+? u.city +"," +? u.marktime + "," +? u.weixinno + "," +? u.phone + "," +? u.healthreport+? u.checkaddress +"," +? u.milion + "," +? u.halfmara + "," +? u.fullmara + "," +? u.industry+? u.workunit +"," +? u.createdate + "," +? u.identity + "," +? u.gender + "," +? u.height+? u.weight + "," +? u.clothingsize + "," +? u.shoesize + "," +? u.tags +")").execute
      }
    
  }
  
}