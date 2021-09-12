package ru.ojaqua.NearUtils.Handlers;

import static org.junit.Assert.*;

import org.junit.Test;

public class SCRGetterTest {

	
	@Test
	public void empty_string() {

		SCRGetter getter = new SCRGetter(() -> "", (str) -> {
			assertEquals(str, "");
		});
		
		getter.run();
		
	}

	@Test
	public void null_string() {

		SCRGetter getter = new SCRGetter(() ->null, (str) -> {
			assertEquals(str, "");
		});
		
		getter.run();
		
	}
	
	@Test
	public void only_scr_string() {

		SCRGetter getter = new SCRGetter(() -> "452202", (str) -> {
			assertEquals(str, "452202");
		});
		
		getter.run();
		
	}

	@Test
	public void short_number_string() {

		SCRGetter getter = new SCRGetter(() -> "32302", (str) -> {
			assertEquals(str, "");
		});
		
		getter.run();
		
	}

	@Test
	public void right_not_numbers() {

		SCRGetter getter = new SCRGetter(() -> "452202ssdal;ks;lkd", (str) -> {
			assertEquals(str, "452202");
		});
		
		getter.run();
		
	}

	@Test
	public void right_left_not_numbers() {

		SCRGetter getter = new SCRGetter(() -> "452202ssdal;ks;lkd123456", (str) -> {
			assertEquals(str, "123456, 452202");
		});
		
		getter.run();
		
	}
	
	@Test
	public void left_not_numbers() {

		SCRGetter getter = new SCRGetter(() -> "wq	qssa 452202", (str) -> {
			assertEquals(str, "452202");
		});
		
		getter.run();
		
	}
	
	@Test
	public void many_numbers() {

		SCRGetter getter = new SCRGetter(() -> "wq	qssa 45454452202sadqw", (str) -> {
			assertEquals(str, "");
		});
		
		getter.run();
		
	}

	@Test
	public void many_scr_in_one_string() {

		SCRGetter getter = new SCRGetter(() -> "wq	qssa 654321sadqw1234567c123456q	saq	", (str) -> {
			assertEquals(str, "123456, 654321");
		});
		
		getter.run();
		
	}
}
