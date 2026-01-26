package com.EloServ.EloServ;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/centros")
@CrossOrigin
public class CentrosController {

	@GetMapping
	public ResponseEntity<String> getCentros() {

		String json = null;

		try {

			File file = new File("EuskadiLatLon.json");

			json = Files.readString(file.toPath());

		} catch (IOException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		return ResponseEntity.ok(json);
}
}
