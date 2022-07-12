package me.brunofelix.cstvapp.data.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.LargeTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@LargeTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ApiServiceTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_api")
    lateinit var api: ApiService

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun test_isFetchMatchesRequestIsSuccessfully_returnsTrue() = runTest {
        val response = api.fetchMatches()

        assertThat(response.isSuccessful).isTrue()
    }

    @Test
    fun test_isGetTeamRequestIsSuccessfully_returnsTrue() = runTest {
        val response = api.getTeam(131086)

        assertThat(response.isSuccessful).isTrue()
    }
}
