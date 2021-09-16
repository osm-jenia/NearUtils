package ru.ojaqua.NearUtils.Handlers;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import ru.ojaqua.NearUtils.Common.UError;

public class QueryGetterHandlerTest {

	@Test
	public void result_regular() {

		assertTrue("Result= 0                               12345".substring(0).matches("^Result=\\s\\d+\\s{5}.*"));
	}
	
	@Test
	public void empty_string() {
		QueryGetterHandler getter = new QueryGetterHandler(() -> "", (str) -> {
			assertEquals(str, "");
		});

		getter.run();
	}

	@Test
	public void null_string() {

		QueryGetterHandler getter = new QueryGetterHandler(() -> null, (str) -> {
			assertEquals(str, "");
		});

		getter.run();

	}

	@Test
	public void scrol_query() throws IOException {

		Path path = Paths.get("src/test/resource/QueryGetterTest/inTrace.txt");

		String strQuery;
		strQuery = new String(Files.readAllBytes(path));

		QueryGetterHandler getter = new QueryGetterHandler(() -> strQuery, (outString) -> {
			Path outFilePath = Paths.get("src/test/resource/QueryGetterTest/outTrace.txt");

			if (Files.exists(outFilePath)) {
				try {
					Files.delete(outFilePath);
				} catch (IOException e) {
					throw new UError("Исключение при попытке удаления файла вывода для теста", e);
				}
			}

			try {
				Files.writeString(outFilePath, outString, StandardCharsets.UTF_8);
			} catch (IOException e) {
				throw new UError("Неудачно записал в файл вывода теста", e);
			}
			// assertEquals(outString, "");
		});

		getter.run();
	}

	@Test
	public void get_space_position_without_result_center() {

		String[] lines = { "123456   12345678901234567890   12345", 
				           "123456   12345678901234567890",
				           "123456   1234567890123", };

		int endPosition = new QueryGetterHandler(null, null).getEndPositionInLine(lines, 0, 9);

		assertEquals(endPosition, 29);

	}

	@Test
	public void get_space_position_without_result_left() {

		String[] lines = { "12345678901234567890   12345", 
				           "12345678901234567890",
				           "12345678901234", };

		int endPosition = new QueryGetterHandler(null, null).getEndPositionInLine(lines, 0, 0);

		assertEquals(endPosition, 20);

	}

	@Test
	public void get_space_position_without_result_right() {

		String[] lines = { "123456   12345678901234567890", 
				           "123456   12345678901234567890",
				           "123456   1234567890123", };

		int endPosition = new QueryGetterHandler(null, null).getEndPositionInLine(lines, 0, 10);

		assertEquals( 29, endPosition );

	}

	@Test
	public void get_space_position_without_result_alone() {

		String[] lines = { "12345678901234567890", 
				           "12345678901234567890",
				           "1234567890123", };

		int endPosition = new QueryGetterHandler(null, null).getEndPositionInLine(lines, 0, 0);

		assertEquals(20, endPosition);

	}

	@Test
	public void get_space_position_center() {

		String[] lines = { "123456   12345678901234567890   12345", 
				           "123456   12345678901234567890",
				           "123456   1234567890123",
				           "123456   Result=0               12345",
				           "123456   12345678901234567890   12345",
				           
		};

		int endPosition = new QueryGetterHandler(null, null).getEndPositionInLine(lines, 0, 9);

		assertEquals(endPosition, 29);

	}

	@Test
	public void get_space_position_left() {

		String[] lines = { "12345678901234567890   12345", 
				           "12345678901234567890",
				           "12345678901234", 
				           "Result=0               12345",
				           "12345678901234567890   12345",
				           
		};

		int endPosition = new QueryGetterHandler(null, null).getEndPositionInLine(lines, 0, 0);

		assertEquals(endPosition, 20);

	}

	@Test
	public void get_space_position_right() {

		String[] lines = { "123456   12345678901234567890", 
				           "123456   12345678901234567890",
				           "123456   1234567890123", 
				           "123456   Result=0",
				           "123456   12345678901234567890",
				           
		};

		int endPosition = new QueryGetterHandler(null, null).getEndPositionInLine(lines, 0, 10);

		assertEquals(endPosition, 29);

	}

	@Test
	public void get_space_position_alone() {

		String[] lines = { "12345678901234567890", 
				           "12345678901234567890",
				           "1234567890123", 
				           "Result=0",
				           "12345678901234567890",
				           
		};

		int endPosition = new QueryGetterHandler(null, null).getEndPositionInLine(lines, 0, 0);

		assertEquals( 20, endPosition );

	}
	
	
	@Test
	public void get_space_position_center_with_param() {

		String[] lines = { "123456   #1, type RSDSHORT, value: 0      12345", 
				           "123456   #2, type RSDSHORT, value: 0      12345",
				           "123456   123456789012345678901234567890   12345", 
				           "123456   123456789012345678901234567890",
				           "123456   1234567890123",
				           "123456   Result=0                         12345",
				           "123456   12345678901234567890             12345",
				           
		};

		int endPosition = new QueryGetterHandler(null, null).getEndPositionInLine(lines, 2, 9);

		assertEquals(endPosition, 39);

	}
	
	@Test
	public void get_space_position_left_with_param() {

		String[] lines = { "#1, type RSDSHORT, value: 0      12345", 
				           "#2, type RSDSHORT, value: 0      12345",
				           "123456789012345678901234567890   12345", 
				           "123456789012345678901234567890",
				           "1234567890123",
				           "Result=0                         12345",
				           "12345678901234567890             12345",
				           
		};

		int endPosition = new QueryGetterHandler(null, null).getEndPositionInLine(lines, 2, 9);

		assertEquals(endPosition, 30);

	}

	@Test
	public void get_space_position_rght_with_param() {

		String[] lines = { 
				   "123456   #1, type RSDSHORT, value: 0", 
		           "123456   #2, type RSDSHORT, value: 0",
		           "123456   123456789012345678901234567890", 
		           "123456   123456789012345678901234567890",
		           "123456   1234567890123",
		           "123456   Result=0",
		           "123456   12345678901234567890",
		           
        };

		int endPosition = new QueryGetterHandler(null, null).getEndPositionInLine(lines, 2, 9);

		assertEquals(endPosition, 39);

	}
	

	@Test
	public void get_space_shaffle_position_left_with_param() {

		String[] lines = { "#1, type RSDSHORT, value: 0            12345", 
				           "#2, type RSDSHORT, value: 0            12345",
				           "12345678901234   567890123456789   0   12345", 
				           "1234567890123   4567890123456   7890",
				           "1234567   890123",
				           "Result=0                               12345",
				           "1234567   89012345   67890             12345",
				           
		};

		int endPosition = new QueryGetterHandler(null, null).getEndPositionInLine(lines, 2, 0);

		assertEquals(endPosition, 36);

	}
	
	@Test
	public void get_space_position_center_with_param_start_space() {

		String[] lines = { "123456   #1, type RSDSHORT, value: 0      12345", 
				           "123456   #2, type RSDSHORT, value: 0      12345",
				           "123456   12345678901234567890123456789    12345", 
				           "123456   123456789012345678901234567890",
				           "123456   1234567890123",
				           "123456   Result=0                         12345",
				           "123456   12345678901234567890             12345",
				           
		};

		int endPosition = new QueryGetterHandler(null, null).getEndPositionInLine(lines, 2, 9);

		assertEquals(endPosition, 39);

	}
	
	
	@Test
	public void get_value_str_for_query() {

		QueryGetterHandler oueryGetter = new QueryGetterHandler(null, null);
		assertEquals(oueryGetter.getValueStr("#1, type RSDSHORT, value: 0 "), "0");
		assertEquals(oueryGetter.getValueStr("#3, type RSDDATE, value: 0.0.0 "), "to_date('1.1.1', 'dd.mm.yyyy')");
		assertEquals(oueryGetter.getValueStr("#4, type RSDDATE, value: 31.12.9999 "), "to_date('31.12.9999', 'dd.mm.yyyy')");
		assertEquals(oueryGetter.getValueStr("#9, type RSDTIME, value: 0:0:0 "), "to_date('01.01.0001 0:0:0', 'DD.MM.YYYY HH24:MI:SS')");
		assertEquals(oueryGetter.getValueStr("#10, type RSDTIME, value: 23:59:59 "), "to_date('01.01.0001 23:59:59', 'DD.MM.YYYY HH24:MI:SS')");
		assertEquals(oueryGetter.getValueStr("#11, type RSDLONG, value: 1 "), "1");
		assertEquals(oueryGetter.getValueStr("#23, type RSDCHAR, value: 88 "), "chr(88)");
		assertEquals(oueryGetter.getValueStr("#23, type RSDLPSTR, value: что-то "), "'что-то'");
		assertEquals(oueryGetter.getValueStr("#24, type RSDVERYLONG, value: что-то "), "что-то");
		assertEquals(oueryGetter.getValueStr("#23, type RSDLPSTR, value: "), "chr(1)");
		assertEquals(oueryGetter.getValueStr("#39, type RSDPT_NUMERIC, value: 11.000000000000 "), "11.000000000000");

	}
	
	@Test
	public void full_query_position_center_with_param() {

		String lines =  "123456   KeyFind: using search SQL        12345\n"+
				        "123456   #1, type RSDSHORT, value: 0      12345\n"+
				        "123456   #2, type RSDSHORT, value: 0      12345\n"+
				        "123456   123456789012_?_678901234567e     12345\n"+ 
				        "123456   123456789012_?_67890123456789e\n" +
				        "123456   1234567890123e\n" +
				        "123456   Result= 0                        12345\n";

		new QueryGetterHandler(()->lines, (str)->{
			assertEquals("123456789012_0_678901234567e  123456789012_0_67890123456789e1234567890123e", str);
		}).run();
	}
	
	
	@Test
	public void full_packege_position_center_with_param() {

		String lines =  "123456   KeyFind: using search SQL                               12345\n"+
				        "123456   #1, type RSDSHORT, value: 0                             12345\n"+
				        "123456   #2, type RSDSHORT, value: 0                             12345\n"+
				        "123456   BEGIN ? := RSB_Common.RSI_GetRegValueParam(?); END;     12345\n"+ 
				        "123456   Result= 0                                               12345\n";

		new QueryGetterHandler(()->lines, (str)->{
			assertEquals("BEGIN 0 := RSB_Common.RSI_GetRegValueParam(0); END;", str);
		}).run();
	}
	
	
}