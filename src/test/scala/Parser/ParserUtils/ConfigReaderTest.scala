package Parser.ParserUtils

import org.scalatest.FlatSpec

/**
 * Created by preethu on 9/22/14.
 *
 */
class ConfigReaderTest extends FlatSpec {
  "Config" should "be empty is file is empty" in {
    val config = ConfigReader.getConf("test.properties")
    assert(config.size() == 0)
  }

  "Config with all properties" should "be valid" in {
    val config = ConfigReader.getConf("parse-config.properties")
    assert(config.size() != 0)
  }
}
