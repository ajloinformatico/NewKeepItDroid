package es.infolojo.newkeepitdroid.domain.mappers

import es.infolojo.newkeepitdroid.domain.data.common.DaysOfWeekModel
import es.infolojo.newkeepitdroid.domain.data.common.MonthModel

fun Int.toDaysOfWeek(): DaysOfWeekModel = when(this) {
    DaysOfWeekModel.SUNDAY.dayInt -> DaysOfWeekModel.SUNDAY
    DaysOfWeekModel.MONDAY.dayInt -> DaysOfWeekModel.MONDAY
    DaysOfWeekModel.TUESDAY.dayInt -> DaysOfWeekModel.TUESDAY
    DaysOfWeekModel.WEDNESDAY.dayInt -> DaysOfWeekModel.WEDNESDAY
    DaysOfWeekModel.THURSDAY.dayInt -> DaysOfWeekModel.THURSDAY
    DaysOfWeekModel.FRIDAY.dayInt -> DaysOfWeekModel.FRIDAY
    DaysOfWeekModel.SATURDAY.dayInt -> DaysOfWeekModel.SATURDAY
    else -> DaysOfWeekModel.SUNDAY
}

fun String.toDaysOfWeek(): DaysOfWeekModel = when (this) {
    DaysOfWeekModel.SUNDAY.dayName -> DaysOfWeekModel.SUNDAY
    DaysOfWeekModel.MONDAY.dayName -> DaysOfWeekModel.MONDAY
    DaysOfWeekModel.TUESDAY.dayName -> DaysOfWeekModel.TUESDAY
    DaysOfWeekModel.WEDNESDAY.dayName -> DaysOfWeekModel.WEDNESDAY
    DaysOfWeekModel.THURSDAY.dayName -> DaysOfWeekModel.THURSDAY
    DaysOfWeekModel.FRIDAY.dayName -> DaysOfWeekModel.FRIDAY
    DaysOfWeekModel.SATURDAY.dayName -> DaysOfWeekModel.SATURDAY
    else -> DaysOfWeekModel.SUNDAY
}

fun String.toMonth(): MonthModel = when (this) {
    MonthModel.JANUARY.monthName -> MonthModel.JANUARY
    MonthModel.FEBRUARY.monthName -> MonthModel.FEBRUARY
    MonthModel.MARCH.monthName -> MonthModel.MARCH
    MonthModel.APRIL.monthName -> MonthModel.APRIL
    MonthModel.MAY.monthName -> MonthModel.MAY
    MonthModel.JUNE.monthName -> MonthModel.JUNE
    MonthModel.JULY.monthName -> MonthModel.JULY
    MonthModel.AUGUST.monthName -> MonthModel.AUGUST
    MonthModel.SEPTEMBER.monthName -> MonthModel.SEPTEMBER
    MonthModel.OCTOBER.monthName -> MonthModel.OCTOBER
    MonthModel.NOVEMBER.monthName -> MonthModel.NOVEMBER
    MonthModel.DECEMBER.monthName -> MonthModel.DECEMBER
    else -> MonthModel.JANUARY
}

fun Int.toMonth(): MonthModel = when (this) {
    MonthModel.JANUARY.monthInt -> MonthModel.JANUARY
    MonthModel.FEBRUARY.monthInt -> MonthModel.FEBRUARY
    MonthModel.MARCH.monthInt -> MonthModel.MARCH
    MonthModel.APRIL.monthInt -> MonthModel.APRIL
    MonthModel.MAY.monthInt -> MonthModel.MAY
    MonthModel.JUNE.monthInt -> MonthModel.JUNE
    MonthModel.JULY.monthInt -> MonthModel.JULY
    MonthModel.AUGUST.monthInt -> MonthModel.AUGUST
    MonthModel.SEPTEMBER.monthInt -> MonthModel.SEPTEMBER
    MonthModel.OCTOBER.monthInt -> MonthModel.OCTOBER
    MonthModel.NOVEMBER.monthInt -> MonthModel.NOVEMBER
    MonthModel.DECEMBER.monthInt -> MonthModel.DECEMBER
    else -> MonthModel.JANUARY
}
