package ru.ojaqua.NearUtils.Handlers;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import ru.ojaqua.NearUtils.Common.ClipboardWorker;
import ru.ojaqua.NearUtils.Handlers.SCRGetterHandler.SCRGetterHandler;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestHandlersConfig.class, loader = AnnotationConfigContextLoader.class)
public class SCRGetterHandlerTest {

	@Autowired
	private SCRGetterHandler hndl;

	// mocked dependencies
	@Autowired
	private ClipboardWorker clipboard;

	@Test
	public void empty_string() {

		when(clipboard.getText()).thenReturn("");
		hndl.run();
		verify(clipboard).setText(eq(""));
	}

	@Test
	public void null_string() {

		when(clipboard.getText()).thenReturn(null);
		hndl.run();
		verify(clipboard).setText(eq(""));
	}

	@Test
	public void only_scr_string() {

		when(clipboard.getText()).thenReturn("452202");
		hndl.run();
		verify(clipboard).setText(eq("452202"));
	}

//	@Test
//	public void short_number_string() {
//
//		SCRGetterHandler getter = new SCRGetterHandler(() -> "32302", (str) -> {
//			assertEquals(str, "");
//		});
//
//		getter.run();
//
//	}
//
//	@Test
//	public void right_not_numbers() {
//
//		SCRGetterHandler getter = new SCRGetterHandler(() -> "452202ssdal;ks;lkd", (str) -> {
//			assertEquals(str, "452202");
//		});
//
//		getter.run();
//
//	}
//
//	@Test
//	public void right_left_not_numbers() {
//
//		SCRGetterHandler getter = new SCRGetterHandler(() -> "452202ssdal;ks;lkd123456", (str) -> {
//			assertEquals(str, "123456, 452202");
//		});
//
//		getter.run();
//
//	}
//
//	@Test
//	public void left_not_numbers() {
//
//		SCRGetterHandler getter = new SCRGetterHandler(() -> "wq	qssa 452202", (str) -> {
//			assertEquals(str, "452202");
//		});
//
//		getter.run();
//
//	}
//
//	@Test
//	public void many_numbers() {
//
//		SCRGetterHandler getter = new SCRGetterHandler(() -> "wq	qssa 45454452202sadqw", (str) -> {
//			assertEquals(str, "");
//		});
//
//		getter.run();
//
//	}
//
//	@Test
//	public void many_scr_in_one_string() {
//
//		SCRGetterHandler getter = new SCRGetterHandler(() -> "wq	qssa 654321sadqw1234567c123456q	saq	", (str) -> {
//			assertEquals(str, "123456, 654321");
//		});
//
//		getter.run();
//
//	}
}
