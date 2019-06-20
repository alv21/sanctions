package sigma.sanctions

import org.springframework.stereotype.Component
import java.io.File
import java.net.URL
import java.nio.file.Files
import java.nio.file.StandardCopyOption

interface DataService {
	fun retrieveData(): File
}

@Component
class UrlDataService : DataService {
	private val XML_URL = "https://webgate.ec.europa.eu/europeaid/fsd/fsf/public/files/xmlFullSanctionsList_1_1/content?token=n002qso2"

	override fun retrieveData(): File =  File.createTempFile("sanctions", ".tmp")
			.also { it.deleteOnExit() }
			.also { Files.copy(URL(XML_URL).openStream(), it.toPath(), StandardCopyOption.REPLACE_EXISTING) }
}