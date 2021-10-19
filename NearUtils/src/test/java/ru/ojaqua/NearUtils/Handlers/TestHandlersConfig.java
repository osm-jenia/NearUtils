package ru.ojaqua.NearUtils.Handlers;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import ru.ojaqua.NearUtils.Common.ClipboardWorker;
import ru.ojaqua.NearUtils.Handlers.SCRGetterHandler.SCRGetterHandler;

@Configuration
@ComponentScan(basePackageClasses = SCRGetterHandler.class)
public class TestHandlersConfig {

	@Bean
	public ClipboardWorker clipboardWorker() {
		return mock(ClipboardWorker.class);
	}

}
