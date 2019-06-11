package sigma.sanctions

import org.apache.logging.log4j.kotlin.Logging
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class InitData(val dataService: DataService) : Logging {

	private lateinit var sanctionList: List<Result>

	@PostConstruct
	private fun init() {
		logger.info("Starting initialization: downloading, parsing and mapping")
		sanctionList = parseAndMapToResult(dataService.retrieveData())
		logger.debug("Initialization done.")
	}

	fun getSanctionList() = sanctionList

}