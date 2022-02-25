package com.wakaztahir.datetime

import android.graphics.Paint
import android.graphics.Rect
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.*
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

// Screen Configuration

@Composable
internal actual fun rememberScreenConfiguration(): ScreenConfiguration {
    val config = LocalConfiguration.current
    return remember(config.screenWidthDp, config.screenHeightDp) {
        ScreenConfiguration(
            screenWidthDp = config.screenWidthDp,
            screenHeightDp = config.screenHeightDp
        )
    }
}

@Composable
internal actual fun isSmallDevice(): Boolean {
    return LocalConfiguration.current.screenWidthDp <= 360
}

@Composable
internal actual fun isLargeDevice(): Boolean {
    return LocalConfiguration.current.screenWidthDp <= 600
}

// Cavnas

internal actual fun Canvas.drawText(
    text: String,
    x: Float,
    y: Float,
    color: Color,
    textSize: Float,
    angle: Float,
    radius: Float,
    isCenter: Boolean?,
    alpha: Int,
) {
    val outerText = Paint()
    outerText.color = color.toArgb()
    outerText.textSize = textSize
    outerText.textAlign =
        if (isCenter == true) Paint.Align.CENTER else if (isCenter == false) Paint.Align.LEFT else Paint.Align.RIGHT
    outerText.alpha = maxOf(0, minOf(alpha * 255, 255))

    val r = Rect()
    outerText.getTextBounds(text, 0, text.length, r)

    nativeCanvas.drawText(
        text,
        x + (radius * cos(angle)),
        y + (radius * sin(angle)) + (abs(r.height())) / 2,
        outerText
    )
}

internal fun Color.toAndroidColor(): Int {
    val x = this.toArgb()
    return android.graphics.Color.argb(
        android.graphics.Color.red(x),
        android.graphics.Color.green(x),
        android.graphics.Color.blue(x),
        android.graphics.Color.alpha(x),
    )
}

// Platform LocalDate And LocalTime

actual class PlatformLocalDate(val date: LocalDate) {
    actual val year: Int
        get() = date.year
    actual val month: Int
        get() = date.month.value
    actual val dayOfMonth: Int
        get() = date.dayOfMonth
    actual val monthValue: Int
        get() = date.monthValue
    actual val dayOfYear : Int
        get() = date.dayOfYear
    actual val dayOfWeekValue : Int
        get() = date.dayOfWeek.value
    actual val isLeapYear : Boolean
        get() = date.isLeapYear

    actual fun withDayOfMonth(dayOfMonth: Int): PlatformLocalDate {
        return com.wakaztahir.datetime.PlatformLocalDate(date.withDayOfMonth(dayOfMonth))
    }

    actual fun getMonthShortLocalName(): String {
        return date.month.shortLocalName
    }

    actual fun getMonthDisplayName(): String {
        return date.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    }

    actual fun getDayOfWeekShortLocalName(): String {
        return date.dayOfWeek.shortLocalName
    }

    actual companion object {

        actual val MIN: PlatformLocalDate = com.wakaztahir.datetime.PlatformLocalDate(LocalDate.MIN)
        actual val MAX: PlatformLocalDate = com.wakaztahir.datetime.PlatformLocalDate(LocalDate.MAX)

        actual fun now(): PlatformLocalDate {
            return com.wakaztahir.datetime.PlatformLocalDate(LocalDate.now())
        }

        actual fun of(year: Int, month: Int, dayOfMonth: Int): PlatformLocalDate {
            return com.wakaztahir.datetime.PlatformLocalDate(LocalDate.of(year, month, dayOfMonth))
        }
    }

    internal actual fun getFirstDayOfMonth(): Int {
        return date.withDayOfMonth(1).dayOfWeek.value % 7
    }

    internal actual fun getNumDays(): Int {
        return date.month.length(date.isLeapYear)
    }

    fun toLocalDate() : LocalDate = date
}

actual class PlatformLocalTime(var time: LocalTime) : Comparable<PlatformLocalTime> {
    override fun compareTo(other: PlatformLocalTime): Int {
        return time.compareTo(other.time)
    }

    actual val isAM: Boolean
        get() = time.isAM
    actual val hour: Int
        get() = time.hour
    actual val minute: Int
        get() = time.minute
    actual val second : Int
        get() = time.second
    actual val simpleHour: Int
        get() = time.simpleHour
    actual val nano: Int
        get() = time.nano

    actual companion object {

        actual val MIN: PlatformLocalTime = com.wakaztahir.datetime.PlatformLocalTime(LocalTime.MIN)
        actual val MAX: PlatformLocalTime = com.wakaztahir.datetime.PlatformLocalTime(LocalTime.MAX)

        actual fun now(): PlatformLocalTime {
            return com.wakaztahir.datetime.PlatformLocalTime(LocalTime.now())
        }

        actual fun of(hour: Int, minute: Int): PlatformLocalTime {
            return com.wakaztahir.datetime.PlatformLocalTime(LocalTime.of(hour, minute))
        }

        actual fun of(hour: Int, minute: Int, second: Int): PlatformLocalTime {
            return com.wakaztahir.datetime.PlatformLocalTime(LocalTime.of(hour, minute, second))
        }

        actual fun of(hour: Int, minute: Int, second: Int, nanosecond: Int): PlatformLocalTime {
            return com.wakaztahir.datetime.PlatformLocalTime(LocalTime.of(hour, minute, second, nanosecond))
        }

    }

    actual fun withHour(hour: Int): PlatformLocalTime {
        return com.wakaztahir.datetime.PlatformLocalTime(time.withHour(hour))
    }

    actual fun withMinute(minute: Int): PlatformLocalTime {
        return com.wakaztahir.datetime.PlatformLocalTime(time.withMinute(minute))
    }

    actual fun noSeconds(): PlatformLocalTime {
        return com.wakaztahir.datetime.PlatformLocalTime(time.noSeconds())
    }

    actual fun toPM(): PlatformLocalTime {
        return com.wakaztahir.datetime.PlatformLocalTime(time.toPM())
    }

    actual fun toAM(): PlatformLocalTime {
        return com.wakaztahir.datetime.PlatformLocalTime(time.toAM())
    }

    fun toLocalTime() : LocalTime = time
}