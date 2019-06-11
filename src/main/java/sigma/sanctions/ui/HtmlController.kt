package sigma.sanctions.ui

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import sigma.sanctions.InitData
import sigma.sanctions.searchSanctions

@Controller
class HtmlController(val initData: InitData) {

	@GetMapping("/")
	fun formGet(model: Model): String {
		model["title"] = "Sanctions"
		return "sanctions"
	}

	@PostMapping("/")
	fun formPost(name: String, model: Model): String {
		model["title"] = "Sanctions"
		model["searchResults"] = searchSanctions(name, initData.getSanctionList())
		return "sanctions"
	}

}