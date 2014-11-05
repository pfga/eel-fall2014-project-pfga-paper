package FuzzyTimeSeries

case class AnnualRecord(timeSlot: String, events: Int,
                        var fuzzySet: String = "", var flrgLH: String = "",
                        var flrgRH: String = "", var fcEvents:Int = 0)
