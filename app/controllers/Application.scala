package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.UserInfo
import play.api.libs.json.Json
import services.UserInfoBean
import services.UserInfoForm
import java.sql.Date
import services.UserInfoExtForm
import play.api.data.format.Formats._
import services.UserInfoBean
import services.Condition
import services.Condition
import services.UserInfoService
import play.api.libs.json.JsString

object Application extends Controller {
    
  val userInfoForm:Form[UserInfoBean] = Form(
      mapping(
            "userinfoform" -> mapping(
             "id" -> of(longFormat),
            "openid" -> text,
            "nickname" -> text,
            "headicon" -> text,
            "country" -> text,
            "province" -> text,
            "city" -> text,
            "marktime" -> date,
            "weixinno" -> text,
            "phone" -> text,
            "healthreport" -> text,
            "checkaddress" -> text,
            "milion" -> text,
            "halfmara" -> text,
            "fullmara" -> text,
            "industry" -> text,
            "workunit" -> text
          )(UserInfoForm.apply)(UserInfoForm.unapply _)          
          ,
          "userinfoextform" -> mapping(
            "identity" -> text,
            "gender" -> number,
            "height" -> of(doubleFormat),
            "weight" -> of(doubleFormat),
            "clothingsize" -> text,
            "shoesize" -> number,
            "tags" -> text
          )(UserInfoExtForm.apply)(UserInfoExtForm.unapply _)   
      ){
            (userinfoform,userinfoextform) => new UserInfoBean(userinfoform.id,userinfoform.openid,userinfoform.nickname,userinfoform.headicon,userinfoform.country,userinfoform.province,userinfoform.city,new Date(userinfoform.marktime.getTime),userinfoform.weixinno,userinfoform.phone,userinfoform.healthreport,userinfoform.checkaddress,userinfoform.milion,userinfoform.halfmara,userinfoform.fullmara,userinfoform.industry,userinfoform.workunit,userinfoextform.identity,userinfoextform.gender,userinfoextform.height,userinfoextform.weight,userinfoextform.clothingsize,userinfoextform.shoesize,userinfoextform.tags,new Date(System.currentTimeMillis()),new Date(System.currentTimeMillis()))
      } {
            u => Some(UserInfoForm(u.id, u.openid,u.nickname,u.headicon,u.country,u.province,u.city,u.marktime, u.weixinno,u.phone,u.healthreport,u.checkaddress,u.milion,u.halfmara,u.fullmara,u.industry,u.workunit),UserInfoExtForm(u.identity,u.gender,u.height,u.weight,u.clothingsize,u.shoesize,u.tags))
      }   
  )
  
  val conditionForm:Form[Condition] = Form{
    mapping(
        "nickname" -> text,
        "country" -> text,
        "province" -> text,
        "city" -> text,
        "weixinno" -> text,
        "phone" -> text,
        "draw" -> number,
        "start" -> number,
        "userid" -> of(longFormat)
    )(Condition.apply)(Condition.unapply)
  }
  
  def index = Action {
    Ok(views.html.index())
  }
  def touserinfo = Action {
    Ok(views.html.userinfo("用户信息管理"))
  }
  
  def userinfolist = Action {
     implicit request => {
         val start: Int = request.getQueryString("start").get.toInt;
         val length: Int = request.getQueryString("length").get.toInt;
         val page = UserInfoService.findUserInfos(length,start,Condition("","","","","","",length,request.getQueryString("start").get.toInt,0l))
         Ok(Json.toJson(page))
    }
  }
  
  def adduserinfo = Action {
    implicit request => {
         userInfoForm.bindFromRequest.fold(
             errors =>  Ok(Json.toJson(Map("status" -> "9999", "msg" -> "参数异常！"))),
             u => {
                    try{
                      UserInfoService.insertUserInfo(u)
                      Ok(views.html.userinfo("用户信息管理"))
                    } catch{
                      case e:Exception => Ok(Json.toJson(Map("status" -> "9999", "msg" -> "服务异常！")))
                    }
             }
         )
    }
  }
  
  def deleteuserinfo = Action {
    implicit request => {
         userInfoForm.bindFromRequest.fold(
             errors =>  Ok(Json.toJson(Map("status" -> "9999", "msg" -> "参数异常！"))),
             u => {
                    try{
                      UserInfoService.insertUserInfo(u)
                      Ok(views.html.userinfo("用户信息管理"))
                    } catch{
                      case e:Exception => Ok(Json.toJson(Map("status" -> "9999", "msg" -> "服务异常！")))
                    }
             }
         )
    }
  }
  
  def updateuserinfo = Action {
    implicit request => {
         userInfoForm.bindFromRequest.fold(
             errors =>  Ok(Json.toJson(Map("status" -> "9999", "msg" -> "参数异常！"))),
             u => {
                    Ok(views.html.userinfo("用户信息管理"))
             }
         )
    }
  }

  def upload = Action(parse.multipartFormData) { request =>
    request.body.file("excel").map { excel =>
      import java.io.File
      val filename = excel.filename
      val contentType = excel.contentType
      excel.ref.moveTo(new File("/tmp/picture"))
      
      Ok("File uploaded")
    }.getOrElse {
      Redirect(routes.Application.index).flashing("error" -> "Missing file")
    }
  }
}