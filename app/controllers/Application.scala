package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.UserInfo
import play.api.libs.json.Json

object Application extends Controller {
  
//  val userInfoForm = Form {
//    mapping("openid" -> text,
//            "nickname" -> text,
//            "openid" -> text,
//            "country" -> text,
//            "province" -> text,
//            "city" -> text,
//            "marktime" -> text,
//            "weixinno" -> text,
//            "phone" -> text,
//            "healthreport" -> text,
//            "checkaddress" -> text,
//            "milion" -> text,
//            "halfmara" -> text,
//            "fullmara" -> text,
//            "industry" -> text,
//            "workunit" -> text,
//            "identity" -> text,
//            "gender" -> number,
//            "height" -> number,
//            "weight" -> number,
//            "clothingsize" -> text,
//            "shoesize" -> number,
//            "tags" -> text)(UserInfo.getUserInfo)(_.map(u => (u.username, "")))
//  }

  def index = Action {
    Ok(views.html.index())
  }
  def touserinfo = Action {
    Ok(views.html.userinfo("用户信息管理"))
  }
  
  def userinfolist = Action {implicit request =>
    Ok(views.html.userinfo("用户信息管理"))
  }
  
  
}