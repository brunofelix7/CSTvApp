package me.brunofelix.cstvapp.util

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

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
        val dateFormatted = convertFromTimestamp(timestamp, PATTERN_DD_MM_YYYY_HH_MM)

        // Then
        assertThat(dateFormatted).isEqualTo("12/07/2022 11:00")
    }
}
