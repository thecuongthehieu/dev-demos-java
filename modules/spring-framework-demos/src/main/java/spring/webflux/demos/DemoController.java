package spring.webflux.demos;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<String>> helloWorld() {
		JsonObject jsonRes = new JsonObject();
		jsonRes.add("text", new JsonPrimitive("Hello World!"));
		return Mono.just(ResponseEntity.status(200).body(jsonRes.toString()));
	}
}
