package com.china.hcg.db;

import com.china.hcg.ApplicationConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Map;

/**
 * @description:
 * @author: lyq
 * @time: 2023-11-28 028 11:07
 **/

@RunWith(SpringRunner.class)
@SpringBootTest(classes={ApplicationConfiguration.class})
@AutoConfigureMockMvc
@Slf4j
public class ESTest {
    @Autowired
    private MongoDB mongoDB;

    @Test
    public void syncOrder() throws Exception {
         mongoDB.getSyncMemberFindAndUpdate("memberAccount","19ba4739e4b0b970e7cc63b71","accountAmount" ,1l,1,false);
        System.err.println(12);
    }
}
