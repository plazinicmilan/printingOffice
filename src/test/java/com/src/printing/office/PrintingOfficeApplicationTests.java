package com.src.printing.office;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.src.printing.office.start.PrintingOfficeApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PrintingOfficeApplication.class)
public class PrintingOfficeApplicationTests {

	@Test
	public void initializationWorks() {
		assertTrue(true);
	}
}
