package io.company.brewcraft.service;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.DeletePublicAccessBlockRequest;
import com.amazonaws.services.s3.model.GetPublicAccessBlockRequest;
import com.amazonaws.services.s3.model.GetPublicAccessBlockResult;
import com.amazonaws.services.s3.model.PublicAccessBlockConfiguration;
import com.amazonaws.services.s3.model.SetPublicAccessBlockRequest;

import io.company.brewcraft.model.IaasPublicAccessBlock;

public class AwsPublicAccessBlockClientTest {
    private AwsPublicAccessBlockClient client;

    private AmazonS3 mAwsClient;

    @BeforeEach
    public void init() {
        mAwsClient = mock(AmazonS3.class);

        client = new AwsPublicAccessBlockClient(mAwsClient);
    }

    @Test
    public void testGet_ReturnsIaasPublicAccessBlock() {
        doReturn(new GetPublicAccessBlockResult().withPublicAccessBlockConfiguration(new PublicAccessBlockConfiguration())).when(mAwsClient).getPublicAccessBlock(any(GetPublicAccessBlockRequest.class));

        IaasPublicAccessBlock config = client.get("BUCKET_1");

        assertEquals("BUCKET_1", config.getBucketName());
        assertTrue(new ReflectionEquals(new PublicAccessBlockConfiguration()).matches(config.getPublicAccessBlockConfig()));
    }

    @Test
    public void testGet_ThrowsException_WhenCacheThrowsException() {
        doThrow(AmazonS3Exception.class).when(mAwsClient).getPublicAccessBlock(any(GetPublicAccessBlockRequest.class));

        assertThrows(AmazonS3Exception.class, () -> client.get("BUCKET_1"));
    }

    @Test
    public void testAdd_ReturnsAddedPublicAccessBlock() {
        client = spy(client);
        doReturn(new IaasPublicAccessBlock("BUCKET_1", new PublicAccessBlockConfiguration())).when(client).get("BUCKET_1");

        IaasPublicAccessBlock config = client.add(new IaasPublicAccessBlock("BUCKET_1", new PublicAccessBlockConfiguration()));

        assertEquals("BUCKET_1", config.getBucketName());
        assertTrue(new ReflectionEquals(new PublicAccessBlockConfiguration()).matches(config.getPublicAccessBlockConfig()));
        ArgumentCaptor<SetPublicAccessBlockRequest> argument = ArgumentCaptor.forClass(SetPublicAccessBlockRequest.class);
        verify(mAwsClient).setPublicAccessBlock(argument.capture());
        assertEquals("BUCKET_1", argument.getValue().getBucketName());
        assertTrue(new ReflectionEquals(new PublicAccessBlockConfiguration()).matches(argument.getValue().getPublicAccessBlockConfiguration()));
    }

    @Test
    public void testPut_ReturnsEntity_WhenGetReturnsEntity() {
        client = spy(client);
        doReturn(new IaasPublicAccessBlock("BUCKET_1", new PublicAccessBlockConfiguration())).when(client).get("BUCKET_1");

        IaasPublicAccessBlock config = client.put(new IaasPublicAccessBlock("BUCKET_1", new PublicAccessBlockConfiguration()));

        assertEquals("BUCKET_1", config.getBucketName());
        assertTrue(new ReflectionEquals(new PublicAccessBlockConfiguration()).matches(config.getPublicAccessBlockConfig()));
    }

    @Test
    public void testExists_ReturnsFalse_WhenGetReturnsNull() {
        client = spy(client);
        doReturn(null).when(client).get("BUCKET_1");

        assertFalse(client.exists("BUCKET_1"));
    }

    @Test
    public void testExists_ReturnsTrue_WhenGetReturnsEntity() {
        client = spy(client);
        doReturn(new IaasPublicAccessBlock("BUCKET_1", new PublicAccessBlockConfiguration())).when(client).get("BUCKET_1");

        assertTrue(client.exists("BUCKET_1"));
    }

    @Test
    public void testDelete_ReturnsTrue_WhenDeleteIsSuccessful() {
        doReturn(null).when(mAwsClient).deletePublicAccessBlock(new DeletePublicAccessBlockRequest().withBucketName("BUCKET_1"));

        assertTrue(client.delete("BUCKET_1"));

        verify(mAwsClient, times(1)).deletePublicAccessBlock(new DeletePublicAccessBlockRequest().withBucketName("BUCKET_1"));
    }

    @Test
    public void testDelete_ReturnsFalse_WhenEntityDoesNotExist() {
        doThrow(AmazonS3Exception.class).when(mAwsClient).deletePublicAccessBlock(new DeletePublicAccessBlockRequest().withBucketName("BUCKET_1"));
        assertFalse(client.delete("BUCKET_1"));
    }
}
