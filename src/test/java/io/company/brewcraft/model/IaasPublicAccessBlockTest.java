package io.company.brewcraft.model;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import com.amazonaws.services.s3.model.PublicAccessBlockConfiguration;

public class IaasPublicAccessBlockTest {
    private IaasPublicAccessBlock iaasPublicAccessBlock;

    @BeforeEach
    public void init() {
        iaasPublicAccessBlock = new IaasPublicAccessBlock();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(iaasPublicAccessBlock.getBucketName());
        assertNull(iaasPublicAccessBlock.getPublicAccessBlockConfig());
        assertNull(iaasPublicAccessBlock.getId());
        assertNull(iaasPublicAccessBlock.getVersion());
    }

    @Test
    public void testAllArgConstructor() {
        iaasPublicAccessBlock = new IaasPublicAccessBlock("BUCKET_1", new PublicAccessBlockConfiguration());

        assertEquals("BUCKET_1", iaasPublicAccessBlock.getBucketName());
        assertTrue(new ReflectionEquals(new PublicAccessBlockConfiguration()).matches(iaasPublicAccessBlock.getPublicAccessBlockConfig()));
    }

    @Test
    public void testGetSetId() {
        iaasPublicAccessBlock.setId("BUCKET_1");
        assertEquals("BUCKET_1", iaasPublicAccessBlock.getId());
    }

    @Test
    public void testGetSetBucketCrossOriginConfiguration() {
        iaasPublicAccessBlock.setPublicAccessBlockConfig(new PublicAccessBlockConfiguration());

        assertTrue(new ReflectionEquals(new PublicAccessBlockConfiguration()).matches(iaasPublicAccessBlock.getPublicAccessBlockConfig()));
    }

    @Test
    public void testGetVersion() {
        assertNull(iaasPublicAccessBlock.getVersion());
    }
}
