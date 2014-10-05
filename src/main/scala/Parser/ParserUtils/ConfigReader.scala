package Parser.ParserUtils

import Parser.ParserUtils.ConfigKeyNames._
import org.apache.commons.configuration.PropertiesConfiguration

/**
 * Created by preethu on 9/22/14.
 */
object ConfigReader {

  def containsKey(config: PropertiesConfiguration, colName: String) = {
    if (!config.containsKey(colName)) {
      println(s"'${colName}' property not found!")
      config.clear()
    }
  }

  def validateConfig(config: PropertiesConfiguration) = {
    containsKey(config, schema)
    containsKey(config, loadDate)
    containsKey(config, reduceColumn)
    containsKey(config, delimiterStr)
  }

  def getConfig(configName: String) = {
    val config = new PropertiesConfiguration(configName)
    validateConfig(config)
    config
  }
}