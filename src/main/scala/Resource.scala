import scala.annotation.StaticAnnotation
import scala.meta._

class get extends StaticAnnotation
class post extends StaticAnnotation
class put extends StaticAnnotation
class patch extends StaticAnnotation

class Resource extends StaticAnnotation {
    inline def apply(defn: Any): Any = meta {
        val (cls, companion) = defn match {
            case q"${cls: Defn.Class}; ${obj:Defn.Object}" => (cls, obj)
            case q"${cls: Defn.Class}" => (cls, q"object ${Term.Name(cls.name.value)}")
        }

        val params = for {
            param <- cls.ctor.paramss.flatten
            modifier <- param.mods
            newParam <- modifier match {
                case mod"@get" | mod"@put" | mod"@post" => Some(param.copy(mods = Nil))
                case mod"@patch" =>   
                    val tpe = param.decltpe.get.asInstanceOf[Type]
                    Some(param"${param.name}: Option[$tpe]=None")
                case _ => None
            }
        } yield modifier -> newParam

        //println(cls, companion)

        val grouped = params.groupBy(_._1.toString).mapValues(_.map(_._2))
        //println(grouped)
        val newClasses = grouped map {
            case (annotation, params) =>
                val className = annotation.stripPrefix("@").capitalize
                q"case class ${Type.Name(className)}(..$params)"
        }

        println(newClasses)

        companion.copy(
            templ = companion.templ.copy(
                stats = Some(companion.templ.stats.getOrElse(Nil) ++ newClasses)
            )
        )
        //defn
    }
}