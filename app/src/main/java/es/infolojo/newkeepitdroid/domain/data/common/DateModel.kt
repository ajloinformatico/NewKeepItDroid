package es.infolojo.newkeepitdroid.domain.data.common

import es.infolojo.newkeepitdroid.utils.getCurrentDayOfMonth
import es.infolojo.newkeepitdroid.utils.getCurrentDayOfWeek
import es.infolojo.newkeepitdroid.utils.getCurrentMonth
import es.infolojo.newkeepitdroid.utils.getCurrentYear
import java.util.Calendar
import java.util.Date

data class DateModel(
    val time: Date,
    val month: MonthModel,
    val dayOfMonth: String,
    val dayOfWeek: DaysOfWeekModel,
    val currentYear: String
) {
    // Builder pattern to get ever the most updated date possible
    companion object {
        private val calendar = Calendar.getInstance()
        fun build(): DateModel = DateModel(
            time = calendar.time,
            month = calendar.getCurrentMonth(),
            dayOfMonth = calendar.getCurrentDayOfMonth(),
            dayOfWeek = calendar.getCurrentDayOfWeek(),
            currentYear = calendar.getCurrentYear()
        )
    }
}
