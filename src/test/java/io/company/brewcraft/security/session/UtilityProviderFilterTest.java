package io.company.brewcraft.security.session;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import io.company.brewcraft.util.UtilityProvider;
import io.company.brewcraft.util.validator.Validator;

public class UtilityProviderFilterTest {
    private UtilityProviderFilter filter;

    private ServletRequest mReq;
    private ServletResponse mRes;
    private FilterChain mChain;

    private UtilityProvider mProvider;

    @BeforeEach
    public void init() {
        mProvider = mock(UtilityProvider.class);

        mReq = mock(ServletRequest.class);
        mRes = mock(ServletResponse.class);
        mChain = mock(FilterChain.class);

        filter = new UtilityProviderFilter(mProvider);
    }

    @Test
    public void testDoFilter_SetsNewValidatorOnTheProvider() throws IOException, ServletException {
        filter.doFilter(mReq, mRes, mChain);

        InOrder order = inOrder(mProvider, mChain);
        order.verify(mProvider, times(1)).setValidator(any(Validator.class));
        order.verify(mChain).doFilter(mReq, mRes);
    }
}
