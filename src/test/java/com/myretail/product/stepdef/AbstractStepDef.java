package com.myretail.product.stepdef;

import com.myretail.product.MyretailProductApiApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = MyretailProductApiApplication.class, loader = SpringBootContextLoader.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("ci")
@Slf4j
public class AbstractStepDef {
}
