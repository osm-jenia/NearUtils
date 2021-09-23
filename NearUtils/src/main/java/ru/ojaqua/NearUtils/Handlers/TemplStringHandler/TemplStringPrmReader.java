package ru.ojaqua.NearUtils.Handlers.TemplStringHandler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import ru.ojaqua.NearUtils.Common.UError;

public class TemplStringPrmReader {

	private TemplStringPrm prm;

	public TemplStringPrmReader(String filePrmPath) {

		Path path = Paths.get(filePrmPath);

		try (InputStream inputStream = Files.newInputStream(path)) {

			try {
				JAXBContext context = JAXBContext.newInstance(TemplStringPrm.class);
				Unmarshaller unmarshaller = context.createUnmarshaller();

				prm = (TemplStringPrm) unmarshaller.unmarshal(inputStream);

			} catch (JAXBException e) {
				throw new UError("Ошибка при парсинге настроек меню шаблонов строк", e);
			}
		} catch (IOException e1) {
			throw new UError("Ошибка при чтнеии файла настроек меню шаблонов строк", e1);
		}

	}

	public TemplStringPrm getPrm() {
		return prm;
	}

}
