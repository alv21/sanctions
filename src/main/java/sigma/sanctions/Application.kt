package sigma.sanctions

import com.samskivert.mustache.Mustache
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.mustache.MustacheEnvironmentCollector
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment

fun main(args: Array<String>) {
	SpringApplication.run(Application::class.java, *args)
}

@SpringBootApplication
class Application {

	@Bean
	fun mustacheCompiler(mustacheTemplateLoader: Mustache.TemplateLoader, environment: Environment): Mustache.Compiler {
		val collector = MustacheEnvironmentCollector()
		collector.setEnvironment(environment)
		return Mustache.compiler().defaultValue("")
				.withLoader(mustacheTemplateLoader)
				.withCollector(collector)
	}
}