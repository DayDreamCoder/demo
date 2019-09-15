package com.minliu.demo.controller;

import com.minliu.demo.DemoApplication;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.*;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DemoApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UploadControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(UploadControllerTest.class);

    private MockMvc mockMvc;

    private MockHttpServletRequest request;
    private MockHttpSession session;
    private MockMultipartHttpServletRequest multipartHttpServletRequest;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private UploadController uploadController;

    @BeforeClass
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @BeforeMethod
    public void beforeMethod() {
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        multipartHttpServletRequest = new MockMultipartHttpServletRequest();
        new MockHttpServletResponse();
    }

    @Test
    public void testUpload() {
        File file = new File("C:\\Users\\liumin\\Desktop\\student.xlsx");
        try {
            MockMultipartFile uploadFile = new MockMultipartFile("file", "student.xlsx", MediaType.TEXT_PLAIN_VALUE, new FileInputStream(file));
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload")
                    .file(uploadFile)
                    .header("Content-type", "multipart/form-data")
                    .contentType(MediaType.MULTIPART_FORM_DATA))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print()).andReturn();
            logger.info(mvcResult.getResponse().getContentAsString());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }
}