package Parser

import org.apache.commons.configuration.PropertiesConfiguration
import org.scalatest.FlatSpec
import ConfigKeyNames._

/**
 * Created by preethu on 9/22/14.
 */
class ConfigReaderTest extends FlatSpec {
  "Config" should "be empty is file is empty" in {
    val config = ConfigReader.getConfig("test.properties")
    assert(config.isEmpty)
  }

  "Config with all properties" should "be valid" in {
    val config = new PropertiesConfiguration()
    config.addProperty(schema,"")
    config.addProperty(loadDate,"")
    config.addProperty(reduceColumn,"")
    config.addProperty(delimiterStr,"")
    assert(!config.isEmpty)
  }
}