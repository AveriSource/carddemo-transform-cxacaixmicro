package com.mycompany.cxacaixpkg.cucumber;

import com.mycompany.cxacaixpkg.CxacaixmicroApp;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = CxacaixmicroApp.class)
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
