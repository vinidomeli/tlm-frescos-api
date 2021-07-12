package com.mercadolibre.dambetan01.controller;

import com.newrelic.api.agent.NewRelic;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Ping Debugger")
public class PingController {

	@GetMapping("/ping")
	public String ping() {
		NewRelic.ignoreTransaction();
		return "pong";
	}
}
