package io.company.brewcraft.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.IaasAuthorization;
import io.company.brewcraft.model.IaasAuthorizationCredentials;
import io.company.brewcraft.security.session.ContextHolder;
import io.company.brewcraft.security.session.PrincipalContext;

public class TenantContextIaasAuthorizationFetchTest {
    private TenantContextIaasAuthorizationFetch ctxFetcher;

    private IaasAuthorizationFetch mFetcher;
    private ContextHolder mCtxHolder;

    @BeforeEach
    public void init() {
        mFetcher = mock(IaasAuthorizationFetch.class);
        mCtxHolder = mock(ContextHolder.class);

        ctxFetcher = new TenantContextIaasAuthorizationFetch(mFetcher, mCtxHolder);
    }

    @Test
    public void testFetch_ReturnsIaasAuthorization_WhenContextReturnsIaasLogin() {
        PrincipalContext mCtx = mock(PrincipalContext.class);
        doReturn(new IaasAuthorizationCredentials("TOKEN")).when(mCtx).getIaasLogin();
        doReturn(mCtx).when(mCtxHolder).getPrincipalContext();

        IaasAuthorization ret = new IaasAuthorization("ACCESS_KEY_ID", null, null, null);
        doReturn(ret).when(mFetcher).fetch(new IaasAuthorizationCredentials("TOKEN"));

        IaasAuthorization auth = ctxFetcher.fetch();

        assertEquals(new IaasAuthorization("ACCESS_KEY_ID", null, null, null), auth);
    }
}
