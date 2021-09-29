package ru.ojaqua.NearUtils.Param;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import ru.ojaqua.NearUtils.Common.UError;

public class NearUtilsParamReader {

	private NearUtilsParam prm;

	public NearUtilsParamReader(Path filePrmPath) {

		try (InputStream inputStream = Files.newInputStream(filePrmPath)) {

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

	}

	public NearUtilsParam getPrm() {
		return prm;
	}

}
