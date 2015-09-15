import play.api._
import play.api.mvc._
import scala.concurrent.Future
import play.api.mvc.Results._

object AuthFilter extends Filter {

  override def apply(next: RequestHeader => Future[SimpleResult])(requestHeader: RequestHeader): Future[SimpleResult] = {
    
    requestHeader.path match {
      case p if(p == "/" || p.indexOf("/login")>=0 || p.indexOf("/assets")>=0) =>{
        next(requestHeader)
      } 
      case _ => requestHeader.cookies.get("PLAY2AUTH_SESS_ID").map { user =>
        next(requestHeader)
      }.getOrElse {
        Future.successful(Redirect("/"))
      }
    }
  }
}