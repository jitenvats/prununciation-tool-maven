package com.wellsfargo.hackathon.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.cloud.texttospeech.v1.TextToSpeechClient;
import com.wellsfargo.hackathon.exception.BadRequestException;
import com.wellsfargo.hackathon.exception.ExternalSystemException;
import com.wellsfargo.hackathon.model.EmployeeEntity;
import com.wellsfargo.hackathon.model.EmployeeResponse;
import com.wellsfargo.hackathon.model.repository.EmployeeRepository;
import com.wellsfargo.hackathon.util.PronunciationType;

@SpringBootTest
public class EmployeeServiceTest {

	private Logger log = LoggerFactory.getLogger(EmployeeServiceTest.class);

	
	@InjectMocks
	EmployeeServiceImpl empoImpl;
	
	@Mock
	EmployeeRepository employeeRepository;
	
	@Mock
	private TranslationService translationService; 
	
	@Mock
	TextToSpeechClient textToSpeechClient;

	@Test
	public void testSaveEmployee() throws ExternalSystemException {
		
		EmployeeEntity emp = new EmployeeEntity();
		emp.setEmployeeId("abcd0sdsadas");
		emp.setEmployeeName("Hello World");
		emp.setLanguage("en-US");
		emp.setPhonetic("Hello World");
		
		Mockito.when(employeeRepository.save(Mockito.any(EmployeeEntity.class))).thenReturn(emp);
		
		EmployeeResponse actual = empoImpl.saveEmployee(emp, PronunciationType.MALE, emp.getLanguage(), false, 1);
		
		assertEquals(actual.getEmployeeName(), "Hello World");
	}
	
	@Test
	public void testgetEmployeeDetails() throws ExternalSystemException, BadRequestException {
		
		EmployeeEntity emp = new EmployeeEntity();
		emp.setEmployeeId("abcd0sdsadas");
		emp.setEmployeeName("Hello World");
		emp.setLanguage("en-US");
		emp.setPhonetic("Hello World");
		
		Mockito.when(employeeRepository.findById(Mockito.anyString())).thenReturn( Optional.of(emp));
		
		EmployeeEntity actual = empoImpl.getEmployeeDetails("12345");
				
		assertEquals(actual.getEmployeeName(), "Hello World");
	}
	
	
	@Test
	public void testgetEmployeeDetailsWilNull() throws ExternalSystemException, BadRequestException {
		
		EmployeeEntity emp = new EmployeeEntity();
		emp.setEmployeeId("abcd0sdsadas");
		emp.setEmployeeName("Hello World");
		emp.setLanguage("en-US");
		emp.setPhonetic("Hello World");
		
		Mockito.when(employeeRepository.findById(Mockito.anyString())).thenReturn( Optional.of(emp));
		
		EmployeeEntity actual = empoImpl.getEmployeeDetailsWilNull("12344");
				
		assertEquals(actual.getEmployeeName(), "Hello World");
	}
	
	
	@Test
	public void testdeleteEmployee() throws ExternalSystemException, BadRequestException {
		
		EmployeeEntity emp = new EmployeeEntity();
		emp.setEmployeeId("abcd0sdsadas");
		emp.setEmployeeName("Hello World");
		emp.setLanguage("en-US");
		emp.setPhonetic("Hello World");
		
		Mockito.when(employeeRepository.findById(Mockito.anyString())).thenReturn( Optional.of(emp));
		
		empoImpl.deleteEmployee("131213");
				
	}
	
}
