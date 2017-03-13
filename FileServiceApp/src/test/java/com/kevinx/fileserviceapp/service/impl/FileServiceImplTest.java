package com.kevinx.fileserviceapp.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kevinx.fileserviceapp.service.FileService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class FileServiceImplTest {
    @Autowired
    private FileService fileService;
	
	@Test
	public void testReadFileFromRepo(){
		fileService.readFileFromRepo("Test.txt");
	}
    
    @Test
	public void testFindFilesByKeyword(){
		fileService.getFileListByKeyword("es");
	}
    
    @Test
    public void testEmailSendingSimulator(){
		fileService.emailSendingSimulator();
    }
}
