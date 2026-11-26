package com.palette.bff.observability;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CorrelationIdFilterTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void preservesValidSuppliedCorrelationId() throws Exception {
        mockMvc.perform(get("/api/hello")
                        .header(CorrelationIdFilter.HEADER_NAME, "trace-123"))
                .andExpect(status().isOk())
                .andExpect(header().string(CorrelationIdFilter.HEADER_NAME, "trace-123"));
    }

    @Test
    void generatesCorrelationIdWhenHeaderIsInvalid() throws Exception {
        mockMvc.perform(get("/api/hello")
                        .header(CorrelationIdFilter.HEADER_NAME, "unsafe id !!!"))
                .andExpect(status().isOk())
                .andExpect(header().exists(CorrelationIdFilter.HEADER_NAME));
    }
}
