package es.infolojo.newkeepitdroid.domain.data.common

/**
 * Represents the days of the week.
 *
 * This enum class provides a type-safe way to represent the seven days of the week.
 * Each day is associated with an integer value (`dayInt`) and a string representation (`dayName`).
 * The integer value starts from 1 for Sunday and increments up to 7 for Saturday.
 *
 * @property dayInt The integer representation of the day (1 for Sunday, 7 for Saturday).
 * @property dayName The string representation of the day (e.g., "SUNDAY", "MONDAY").
 */
enum class DaysOfWeekModel(val dayInt: Int, val dayName: String) {
    SUNDAY(1, "SUNDAY"),
    MONDAY(2, "MONDAY"),
    TUESDAY(3, "TUESDAY"),
    WEDNESDAY(4, "WEDNESDAY"),
    THURSDAY(5, "THURSDAY"),
    FRIDAY(6, "FRIDAY"),
    SATURDAY(7, "SATURDAY")
}

/**
 * Represents the months of the year.
 *
 * Each enum constant holds the numerical representation of the month (1 for January, 12 for December)
 * and the full name of the month as a string.
 *
 * @property monthInt The numerical representation of the month (1-12).
 * @property monthName The full name of the month (e.g., "JANUARY", "FEBRUARY").
 */
enum class MonthModel(val monthInt: Int, val monthName: String) {
    JANUARY(1, "JANUARY"),
    FEBRUARY(2, "FEBRUARY"),
    MARCH(3, "MARCH"),
    APRIL(4, "APRIL"),
    MAY(5, "MAY"),
    JUNE(6, "JUNE"),
    JULY(7, "JULY"),
    AUGUST(8, "AUGUST"),
    SEPTEMBER(9, "SEPTEMBER"),
    OCTOBER(10, "OCTOBER"),
    NOVEMBER(11, "NOVEMBER"),
    DECEMBER(12, "DECEMBER")
}
