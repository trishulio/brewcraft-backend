package io.company.brewcraft;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

import io.company.brewcraft.security.session.ContextHolder;

@TestConfiguration
public class TestGlobalConfiguration {
    @MockBean
    private ContextHolder contextHolderMock;

}
