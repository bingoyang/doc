package services

import java.util.Date


/**
 * @author yangliubing
 */
case class UserInfoForm(id: Long, openid: String,nickname: String,headicon: String,country: String,province: String,city: String,marktime: Date, weixinno: String,phone: String,healthreport: String,checkaddress: String,milion: String,halfmara: String,fullmara: String,industry: String,workunit: String)

case class UserInfoExtForm(identity: String,gender: Int,height: Double,weight: Double,clothingsize: String,shoesize: Int,tags :String)
