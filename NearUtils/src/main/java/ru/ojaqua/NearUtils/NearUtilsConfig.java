package ru.ojaqua.NearUtils;

import java.awt.SystemTray;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.ojaqua.NearUtils.Common.UError;
import ru.ojaqua.NearUtils.Param.NearUtilsParam;

@Configuration
public class NearUtilsConfig {

	private final String path = "/NearUtilsParam.xml";

	@Bean
	public NearUtilsParam createNearUtilsParam() {
		Path templStringParmPath = null;
		try {
			URI uri = NearUtilsConfig.class.getResource(path).toURI();
			templStringParmPath = Paths.get(uri).toAbsolutePath();
		} catch (URISyntaxException e) {
			throw new UError("Прблемы при парсинге пути до настроек", e);
		}

		NearUtilsParam prm;

		try (InputStream inputStream = Files.newInputStream(templStringParmPath)) {

			try {
				JAXBContext context = JAXBContext.newInstance(NearUtilsParam.class);
				Unmarshaller unmarshaller = context.createUnmarshaller();

				prm = (NearUtilsParam) unmarshaller.unmarshal(inputStream);

			} catch (JAXBException e) {
				throw new UError("Ошибка при парсинге настроек", e);
			}
		} catch (IOException e1) {
			throw new UError("Ошибка при чтнеии файла настроек", e1);
		}

		return prm;

	}

	@Bean
	SystemTray getSystemTray() {
		return SystemTray.getSystemTray();
	}

}
