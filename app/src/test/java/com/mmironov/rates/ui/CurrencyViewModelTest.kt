package com.mmironov.rates.ui

import androidx.lifecycle.MutableLiveData
import com.mmironov.rates.data.db.Currency
import com.mmironov.rates.data.repository.CurrencyRepository
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class CurrenciesViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    lateinit var repository: CurrencyRepository

    lateinit var viewModel: CurrenciesViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = CurrenciesViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun getRates_positiveResponse() = runBlockingTest {

        whenever(repository.getRates()).thenReturn(MutableLiveData(listOf(Currency("EUR", 1.0, 0))))

        val currencies = viewModel.currencyRates.await().value

        assertNotNull(currencies)
        assertNotSame(0, currencies!!.size)
        assertEquals("EUR", currencies[0].code)
    }
}