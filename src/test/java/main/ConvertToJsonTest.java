package main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.models.Person;
import org.services.Processing;

public class ConvertToJsonTest {

	@Test
	public void simple_conversion_sucess_expected() throws Exception {
		Person p = new Person();
		p.setAddress("rua tal");
		p.setAge("27");
		p.setFirstName("RUAN");
		p.setLastName("Candido");

		Processing processing = new Processing();

		String result = processing.convertToJson(p);
		assertEquals("{\"firstName\":\"RUAN\",\"lastName\":\"Candido\",\"age\":\"27\"}", result);

	}

	@Test
	public void trying_to_convert_a_null_object_throw_expcted() throws Exception {

		Processing processing = new Processing();

		assertThrows(Exception.class, new Executable() {

			@Override
			public void execute() throws Throwable {
				try {
					processing.convertToJson(null);
				} catch (Exception e) {
					assertEquals("The object to serialize can not be null", e.getMessage());
					throw e;
				}

			}
		});

	}

	@Test
	public void trying_to_convert_a_non_annotated_object_throw_expcted() throws Exception {

		Processing processing = new Processing();

		assertThrows(Exception.class, new Executable() {

			@Override
			public void execute() throws Throwable {
				try {
					processing.convertToJson(new Object());
				} catch (Exception e) {
					assertEquals("The class " + Object.class.getSimpleName() + " is not annotated with JsonSerializable",
							e.getMessage());
					throw e;
				}

			}
		});

	}
	
	@Test
	public void a_non_annotated_attribute_should_not_be_converted() throws Exception {
		Person p = new Person();
		//this attribute is not marked with JSONElement annotation
		p.setAddress("rua tal");
		

		Processing processing = new Processing();

		String result = processing.convertToJson(p);
		assertEquals("{\"firstName\":\"\",\"lastName\":\"\",\"age\":\"null\"}", result);

	}
}
