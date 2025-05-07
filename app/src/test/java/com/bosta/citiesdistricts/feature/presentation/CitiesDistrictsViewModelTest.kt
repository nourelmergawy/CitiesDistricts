import app.cash.turbine.test
import com.bosta.citiesdistricts.common.data.models.exception.BostaException
import com.bosta.citiesdistricts.common.data.models.state.Resource
import com.bosta.citiesdistricts.feature.citiesDistricts.domain.models.City
import com.bosta.citiesdistricts.feature.citiesDistricts.domain.models.District
import com.bosta.citiesdistricts.feature.citiesDistricts.domain.usecases.GetCitiesDistrictsFromRemoteUC
import com.bosta.citiesdistricts.feature.citiesDistricts.presentation.CitiesDistrictsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class CitiesDistrictsViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val getCitiesDistrictsFromRemoteUC: GetCitiesDistrictsFromRemoteUC = mockk()
    private lateinit var viewModel: CitiesDistrictsViewModel

    private val mockCities = listOf(
        City(
            cityCode = "CAI",
            cityId = "1",
            cityName = "Cairo",
            cityOtherName = "القاهرة",
            districts = listOf(
                District(
                    "BOSTA",
                    "101",
                    "Nasr City",
                    "",
                    true,
                    false,
                    false,
                    true,
                    "Z1",
                    "Zone 1",
                    "Z1"
                ),
                District(
                    "OTHER",
                    "102",
                    "Maadi",
                    "",
                    true,
                    false,
                    false,
                    true,
                    "Z2",
                    "Zone 2",
                    "Z2"
                )
            ),
            dropOffAvailability = true,
            pickupAvailability = true
        ),
        City(
            cityCode = "ALX",
            cityId = "2",
            cityName = "Alexandria",
            cityOtherName = "الإسكندرية",
            districts = emptyList(),
            dropOffAvailability = true,
            pickupAvailability = false
        )
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchCitiesAndDistricts emits loading then success`() = runTest {
        coEvery { getCitiesDistrictsFromRemoteUC() } returns flow {
            emit(Resource.Loading())
            delay(100)
            emit(Resource.Success(mockCities))
        }

        viewModel = CitiesDistrictsViewModel(getCitiesDistrictsFromRemoteUC)

        viewModel.state.test {
            val loadingState = awaitItem()
            println("Received state: $loadingState")
            assertTrue(
                "Expected loading state, but got: $loadingState",
                loadingState is Resource.Loading
            )

            val successState = awaitItem()
            println("Received state: $successState")
            assertTrue(
                "Expected success state, but got: $successState",
                successState is Resource.Success
            )
            assertEquals(
                "Success data should match",
                mockCities,
                (successState as Resource.Success).data
            )

            cancelAndIgnoreRemainingEvents()
        }

        viewModel.filteredCities.test {
            val filteredCities = awaitItem()
            assertEquals("Filtered cities should match mock data", mockCities, filteredCities)
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `fetchCitiesAndDistricts emits loading then failure`() = runTest {
        val exception = mockk<BostaException>(relaxed = true)
        val error = Resource.Failure(exception)

        coEvery { getCitiesDistrictsFromRemoteUC() } returns flow {
            emit(Resource.Loading())
            delay(50)
            emit(error)
        }

        viewModel = CitiesDistrictsViewModel(getCitiesDistrictsFromRemoteUC)

        viewModel.state.test {
            val loadingState = awaitItem()
            assertTrue(
                "Expected loading state, but got: $loadingState",
                loadingState is Resource.Loading
            )

            val failureState = awaitItem()
            assertTrue(
                "Expected failure state, but got: $failureState",
                failureState is Resource.Failure
            )
            assertEquals(
                "Exception should match",
                exception,
                (failureState as Resource.Failure).exception
            )

            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `setSearchQuery filters city list case-insensitively`() = runTest {
        coEvery { getCitiesDistrictsFromRemoteUC() } returns flow {
            emit(Resource.Success(mockCities))
        }

        viewModel = CitiesDistrictsViewModel(getCitiesDistrictsFromRemoteUC)

        viewModel.filteredCities.test {
            awaitItem() // Initial full list
            viewModel.setSearchQuery("alex")
            val result = awaitItem()
            assertEquals(1, result.size)
            assertTrue(result[0].cityName.contains("alex", ignoreCase = true))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `filteredCities emits all cities when search query is blank`() = runTest {
        coEvery { getCitiesDistrictsFromRemoteUC() } returns flow {
            emit(Resource.Success(mockCities))
        }

        viewModel = CitiesDistrictsViewModel(getCitiesDistrictsFromRemoteUC)

        viewModel.filteredCities.test {
            assertEquals(mockCities, awaitItem()) // First and only value
            cancelAndIgnoreRemainingEvents()
        }
    }

}
