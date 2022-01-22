package com.vanpra.composematerialdialogs.datetime

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import kotlin.math.cos
import kotlin.math.sin

// Screen Configuration

internal fun Float.getOffset(angle: Double): Offset =
    Offset((this * cos(angle)).toFloat(), (this * sin(angle)).toFloat())

internal class ScreenConfiguration(val screenWidthDp: Int, val screenHeightDp: Int)

@Composable
internal expect fun rememberScreenConfiguration(): ScreenConfiguration

@Composable
internal expect fun isSmallDevice(): Boolean

@Composable
internal expect fun isLargeDevice(): Boolean

// Canvas Functions

internal expect fun Canvas.drawText(
    text: String,
    x: Float,
    y: Float,
    color: Color,
    textSize: Float,
    angle: Float,
    radius: Float,
    isCenter: Boolean?,
    alpha: Int,
)

// Platform LocalDate And LocalTime

expect class PlatformLocalDate {
    val year: Int
    val month: Int
    val dayOfMonth: Int
    val monthValue: Int
    val dayOfYear : Int
    val dayOfWeekValue : Int
    val isLeapYear : Boolean


    fun withDayOfMonth(dayOfMonth: Int): PlatformLocalDate
    fun getMonthShortLocalName(): String
    fun getDayOfWeekShortLocalName(): String
    fun getMonthDisplayName(): String
    internal fun getFirstDayOfMonth(): Int
    internal fun getNumDays(): Int

    companion object {
        val MIN: PlatformLocalDate
        val MAX: PlatformLocalDate
        fun now(): PlatformLocalDate
        fun of(year: Int, month: Int, dayOfMonth: Int): PlatformLocalDate
    }
}

expect class PlatformLocalTime : Comparable<PlatformLocalTime> {
    val isAM: Boolean
    val hour: Int
    val minute: Int
    val second : Int
    val simpleHour : Int
    val nano : Int

    companion object {
        val MIN: PlatformLocalTime
        val MAX: PlatformLocalTime
        fun now(): PlatformLocalTime
        fun of(hour: Int, minute: Int): PlatformLocalTime
        fun of(hour: Int, minute: Int, second: Int): PlatformLocalTime
        fun of(hour: Int, minute: Int, second: Int, nanosecond: Int): PlatformLocalTime
    }

    fun withHour(hour: Int): PlatformLocalTime
    fun withMinute(minute: Int): PlatformLocalTime
    fun toAM(): PlatformLocalTime
    fun toPM(): PlatformLocalTime
    fun noSeconds(): PlatformLocalTime
}