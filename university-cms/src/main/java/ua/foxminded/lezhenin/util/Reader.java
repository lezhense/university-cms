package ua.foxminded.lezhenin.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Reader {

	public List<String> getListFromFile(String fileName) {
		List<String> list = new ArrayList<>();
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			list = stream.toList();
		} catch (IOException e) {
			log.error("Failed to load file.", e);
		}
		return list;
	}

}
