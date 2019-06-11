package sigma.sanctions

import ec.europa.eu.fpi.fsd.export.ExportType
import ec.europa.eu.fpi.fsd.export.ObjectFactory
import ec.europa.eu.fpi.fsd.export.SanctionEntityType
import info.debatty.java.stringsimilarity.NormalizedLevenshtein
import org.apache.commons.lang3.StringUtils
import java.io.File
import java.io.StringReader
import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBElement

fun searchSanctions(name: String, sanctionList: List<Result>): List<Result> {
	return sanctionList
			.asSequence()
			.mapNotNull { res -> Result(res.name, res.aliases, relevance = res.aliases.plus(res.name).map { it.toLowerCase().matchScore(name) }.max()) }
			.filter { it.relevance ?: 0F > 0 }
			.sortedByDescending { it.relevance }
			.toList()
}

fun parseAndMapToResult(file: File): List<Result> = (
		JAXBContext
				.newInstance(ObjectFactory::class.java)
				.createUnmarshaller()
				.unmarshal(StringReader(file.readText(charset = Charsets.UTF_8))) as JAXBElement<ExportType>
		).value
		.sanctionEntity
		.asSequence()
		.mapNotNull { it.mapToResult() }
		.toList()

private fun String.matchScore(name: String): Float {
	val name = name.toLowerCase()

	if (equals(name))
		return 1F
	if (contains(name) && name.length > 5)
		return 0.9F

	val distance = NormalizedLevenshtein().distance(name, this).toFloat()
	if (distance < 0.5)
		return Math.round((1F - distance) * 10) / 10F

	return 0F
}

private fun SanctionEntityType.mapToResult(): Result? {
	val aliases = nameAlias
			.map {
				if (StringUtils.isNotEmpty(it.wholeName)) it.wholeName
				else "${it.firstName ?: ""}${it.middleName ?: ""}${it.lastName ?: ""}"
			}

	return if (aliases.isEmpty()) null
	else Result(aliases[0], if (aliases.size > 1) aliases.subList(1, aliases.size) else emptyList(), relevance = null)
}


class Result(val name: String,
			 val aliases: List<String>,
			 val sanctioned: Boolean = true,
			 val list: String = "european_sanctions_list",
			 val relevance: Float?)