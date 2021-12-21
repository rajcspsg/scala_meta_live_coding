import org.scalatest._
import flatspec._
import matchers._

class ResourceTest extends AnyFlatSpec with should.Matchers {
    "scala meta" should "generate classes for annotation" in {
        @Resource case class User(
        @get id: Int,
        @get @post @patch name: String,
        @get @post email: String,
        registeredOn: Long
    )

    val userGet = User.Get(id = 0, name = "Rick", email="test@gmail.com")
    """User.Get(id = 0, name = "Rick", email="test@gmail.com")""" should compile
    """User.Post(name = "Rick", email="test@gmail.com")""" should compile
    """User.Put()""" shouldNot compile
    """User.Patch(name = Some("Rick"))""" should compile
    """User.Patch()""" should compile
    }
}