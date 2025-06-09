package com.gopay.banking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gopay.banking.adapter.in.web.RegisterBankAccountRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegisterBankAccoutControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper mapper;


    @Test
    public void testRegisterMembership() throws Exception {

        RegisterBankAccountRequest request = new RegisterBankAccountRequest("1", "토스", "302-1234-3333", true);


        mockMvc.perform(MockMvcRequestBuilders.post("/banking/account/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
//                .andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(expect)));


    }
}

