package es.infolojo.newkeepitdroid.utils

import es.infolojo.newkeepitdroid.domain.data.common.DaysOfWeekModel
import es.infolojo.newkeepitdroid.domain.data.common.MonthModel
import es.infolojo.newkeepitdroid.domain.mappers.toDaysOfWeek
import es.infolojo.newkeepitdroid.domain.mappers.toMonth
import java.util.Calendar

// region public methods
fun Calendar.getCurrentDayOfWeek(): DaysOfWeekModel = get(Calendar.DAY_OF_WEEK).toDaysOfWeek()
fun Calendar.getCurrentMonth(): MonthModel = (get(Calendar.MONTH) + 1).toMonth()
fun Calendar.getCurrentDayOfMonth(): String = this.get(Calendar.DAY_OF_MONTH).toString()
fun Calendar.getCurrentYear(): String = this.get(Calendar.YEAR).toString()
// endregion public methods

