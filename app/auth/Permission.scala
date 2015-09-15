package auth

import java.util.Date

import com.mongodb.casbah.Imports._
import com.novus.salat._
import com.novus.salat.dao._

import mongoContext._
import play.api.Play.current
import play.api.libs.functional.syntax._
import play.api.libs.json._
import se.radley.plugin.salat._
import se.radley.plugin.salat.Binders._

case class Permission(
  id: ObjectId = new ObjectId,
  pname: String ,
  pmark: String ,
  added: Date = new Date()
)

object Permission extends  ModelCompanion[Permission, ObjectId] with PermissionJson {
  
  def collection = mongoCollection("permissions")
  
  val dao = new SalatDAO[Permission, ObjectId](collection) {}
  
  // Queries
  def findOneByName(pname: String): Option[Permission] = dao.findOne(MongoDBObject("pname" -> pname))
  
  def create(p:Permission) = dao.insert(t = p)
  
}

/**
 * Trait used to convert to and from json
 */
trait PermissionJson {

  implicit val permissionJsonWrite = new Writes[Permission] {
    def writes(u: Permission): JsValue = {
      Json.obj(
        "id" -> u.id,
        "pname" -> u.pname,
        "pmark"  -> u.pmark,
        "added" -> u.added
        )
    }
  }
  implicit val permissionJsonRead = (
    (__ \ 'id).read[ObjectId] ~
    (__ \ 'pname).read[String] ~
    (__ \ 'pmark).read[String] ~
    (__ \ 'added).read[Date] 
  )(Permission.apply _)
}
