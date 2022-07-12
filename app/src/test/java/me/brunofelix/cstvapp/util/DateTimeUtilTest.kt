package me.brunofelix.cstvapp.util

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit
import kotlin.math.abs

class DateTimeUtilTest {

    @Test
    fun `test is date format from api is converted to date with the correct timezone`() {
        // Given
        val dateFormatFromApi = "2022-07-12T14:00:00Z"

        // When
        val dateConverted = convertDate(dateFormatFromApi, PATTERN_EEE_HH_MM)

        // Then
        assertThat(dateConverted).isEqualTo("ter., 11:00")
    }

    @Test
    fun `test is date format from api is converted to timestamp with the correct timezone`() {
        // Given
        val dateFormatFromApi = "2022-07-12T14:00:00Z"

        // When
        val dateConverted = convertDate(dateFormatFromApi, PATTERN_DD_MM_YYYY_HH_MM)
        val timestamp = convertToTimestamp(dateConverted, PATTERN_DD_MM_YYYY_HH_MM)

        // Then
        assertThat(timestamp).isEqualTo(1657623600000)
    }

    @Test
    fun `test is timestamp is converted to date with the correct timezone`() {
        // Given
        val timestamp: Long = 1657623600000

        // When
        val dateFormatted = convertFromTimestamp(timestamp, PATTERN_DD_MM_YYYY_HH_MM, true)

        // Then
        assertThat(dateFormatted).isEqualTo("12/07/2022 11:00")
    }

    @Test
    fun `test is duration in minutes between timestamps is correct`() {
        // Given
        val timestamp: Long = 1657636200000
        val currentTimeMillis: Long = 1657646196213

        val dateFormatted = convertFromTimestamp(timestamp, PATTERN_DD_MM_YYYY_HH_MM, true)
        val dateFormatted2 = convertFromTimestamp(currentTimeMillis, PATTERN_DD_MM_YYYY_HH_MM, false)
        val t1 = convertToTimestamp(dateFormatted, PATTERN_DD_MM_YYYY_HH_MM)
        val t2 = convertToTimestamp(dateFormatted2, PATTERN_DD_MM_YYYY_HH_MM)

        // When
        val diff = abs(t1 - t2)
        val duration = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS)

        // Then
        assertThat(duration).isEqualTo(14)
    }
}
