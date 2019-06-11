package sigma.sanctions

import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.core.io.ResourceLoader
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import java.io.File

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SearchUtilsKtTest {

	@MockBean
	private lateinit var initData: InitData
	@Autowired
	private lateinit var dataService: DataService
	@Autowired
	private lateinit var resourceLoader: ResourceLoader

	@Test
	fun smokeTest() {
		val sanctionList = parseAndMapToResult(FileDataService(resourceLoader).retrieveData())

		assertTrue(search("Robert Gabriel Mugabe", sanctionList).first().relevance == 1F)
		assertTrue(search("Abou Ali", sanctionList).first().relevance == 1F)
		assertTrue(search("οργάνωση Al-Qaida στην Αραβική Χερσόνησο", sanctionList).first().relevance == 1F)
		assertTrue(search("Université Malek Ashtar", sanctionList).first().relevance == 1F)
		assertTrue(search("KCST", sanctionList).first().relevance == 1F)
		assertTrue(search("Iran Modern Devices", sanctionList).first().relevance!! >= 0.8)
		assertTrue(search("Pop Credit Bank", sanctionList).first().relevance!! >= 0.8)
		assertTrue(search("Bank of Syria", sanctionList).first().relevance!! >= 0.8)
	}

	fun search(searchString: String, sanctionList: List<Result>): List<Result> = searchSanctions(searchString, sanctionList)
}

class FileDataService(private val resourceLoader: ResourceLoader) : DataService {
	override fun retrieveData(): File = resourceLoader.getResource("classpath:20190529-FULL-1_1(xsd).xml").file
}
