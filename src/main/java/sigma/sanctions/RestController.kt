package sigma.sanctions

import com.google.gson.Gson
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
class RestController(val initData: InitData) {

	@GetMapping("/status")
	fun status(): HttpStatus = HttpStatus.OK

	@GetMapping("/search")
	fun search(@RequestParam(value = "name") name: String): String = Gson().toJson(searchSanctions(name, initData.getSanctionList()))

}



