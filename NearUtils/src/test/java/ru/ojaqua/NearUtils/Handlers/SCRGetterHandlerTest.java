package ru.ojaqua.NearUtils.Handlers;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.ojaqua.NearUtils.Common.ClipboardWorker;
import ru.ojaqua.NearUtils.Handlers.SCRGetterHandler.SCRGetterHandler;

public class SCRGetterHandlerTest {

	private SCRGetterHandler hndl;

	private ClipboardWorker clipboard;

	@BeforeEach
	void init() {
		clipboard = mock(ClipboardWorker.class);
		hndl = new SCRGetterHandler(clipboard);
	}

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

	@Test
	public void short_number_string() {
		when(clipboard.getText()).thenReturn("32302");
		hndl.run();
		verify(clipboard).setText(eq(""));
	}

	@Test
	public void right_not_numbers() {

		when(clipboard.getText()).thenReturn("452202ssdal;ks;lkd");
		hndl.run();
		verify(clipboard).setText(eq("452202"));
	}

	@Test
	public void right_left_not_numbers() {

		when(clipboard.getText()).thenReturn("452202ssdal;ks;lkd123456");
		hndl.run();
		verify(clipboard).setText(eq("123456, 452202"));

	}

	@Test
	public void left_not_numbers() {

		when(clipboard.getText()).thenReturn("wq	qssa 452202");
		hndl.run();
		verify(clipboard).setText(eq("452202"));
	}

	@Test
	public void many_numbers() {

		when(clipboard.getText()).thenReturn("wq	qssa 45454452202sadqw");
		hndl.run();
		verify(clipboard).setText(eq(""));
	}

	@Test
	public void many_scr_in_one_string() {

		when(clipboard.getText()).thenReturn("wq	qssa 654321sadqw1234567c123456q	saq	");
		hndl.run();
		verify(clipboard).setText(eq("123456, 654321"));

	}
}
