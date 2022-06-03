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
import com.amazonaws.services.s3.model.BucketCrossOriginConfiguration;
import com.amazonaws.services.s3.model.DeleteBucketCrossOriginConfigurationRequest;
import com.amazonaws.services.s3.model.GetBucketCrossOriginConfigurationRequest;
import com.amazonaws.services.s3.model.SetBucketCrossOriginConfigurationRequest;

import io.company.brewcraft.model.IaasBucketCorsConfiguration;

public class AwsCorsConfigClientTest {
    private AwsCorsConfigClient client;

    private AmazonS3 mAwsClient;

    @BeforeEach
    public void init() {
        mAwsClient = mock(AmazonS3.class);

        client = new AwsCorsConfigClient(mAwsClient);
    }

    @Test
    public void testGet_ReturnsIaasBucketCrossOriginConfiguration() {
        doReturn(new BucketCrossOriginConfiguration()).when(mAwsClient).getBucketCrossOriginConfiguration(any(GetBucketCrossOriginConfigurationRequest.class));

        IaasBucketCorsConfiguration config = client.get("BUCKET_1");


        assertEquals("BUCKET_1", config.getBucketName());
        assertTrue(new ReflectionEquals(new BucketCrossOriginConfiguration()).matches(config.getBucketCrossOriginConfiguration()));
    }

    @Test
    public void testGet_ThrowsException_WhenCacheThrowsException() {
        doThrow(AmazonS3Exception.class).when(mAwsClient).getBucketCrossOriginConfiguration(any(GetBucketCrossOriginConfigurationRequest.class));

        assertThrows(AmazonS3Exception.class, () -> client.get("BUCKET_1"));
    }

    @Test
    public void testAdd_ReturnsAddedAttachement() {
        client = spy(client);
        doReturn(new IaasBucketCorsConfiguration("BUCKET_1", new BucketCrossOriginConfiguration())).when(client).get("BUCKET_1");

        IaasBucketCorsConfiguration config = client.add(new IaasBucketCorsConfiguration("BUCKET_1", new BucketCrossOriginConfiguration()));

        assertEquals("BUCKET_1", config.getBucketName());
        assertTrue(new ReflectionEquals(new BucketCrossOriginConfiguration()).matches(config.getBucketCrossOriginConfiguration()));
        ArgumentCaptor<SetBucketCrossOriginConfigurationRequest> argument = ArgumentCaptor.forClass(SetBucketCrossOriginConfigurationRequest.class);
        verify(mAwsClient).setBucketCrossOriginConfiguration(argument.capture());
        assertEquals("BUCKET_1", argument.getValue().getBucketName());
        assertTrue(new ReflectionEquals(new BucketCrossOriginConfiguration()).matches(argument.getValue().getCrossOriginConfiguration()));
    }

    @Test
    public void testPut_ReturnsEntity_WhenGetReturnsEntity() {
        client = spy(client);
        doReturn(new IaasBucketCorsConfiguration("BUCKET_1", new BucketCrossOriginConfiguration())).when(client).get("BUCKET_1");

        IaasBucketCorsConfiguration config = client.put(new IaasBucketCorsConfiguration("BUCKET_1", new BucketCrossOriginConfiguration()));

        assertEquals("BUCKET_1", config.getBucketName());
        assertTrue(new ReflectionEquals(new BucketCrossOriginConfiguration()).matches(config.getBucketCrossOriginConfiguration()));
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
        doReturn(new IaasBucketCorsConfiguration("BUCKET_1", new BucketCrossOriginConfiguration())).when(client).get("BUCKET_1");

        assertTrue(client.exists("BUCKET_1"));
    }

    @Test
    public void testDelete_ReturnsTrue_WhenDeleteIsSuccessful() {
        doNothing().when(mAwsClient).deleteBucketCrossOriginConfiguration(new DeleteBucketCrossOriginConfigurationRequest("BUCKET_1"));

        assertTrue(client.delete("BUCKET_1"));

        ArgumentCaptor<DeleteBucketCrossOriginConfigurationRequest> argument = ArgumentCaptor.forClass(DeleteBucketCrossOriginConfigurationRequest.class);
        verify(mAwsClient, times(1)).deleteBucketCrossOriginConfiguration(argument.capture());
        assertEquals("BUCKET_1", argument.getValue().getBucketName());
    }

    @Test
    public void testDelete_ReturnsFalse_WhenEntityDoesNotExist() {
        doThrow(AmazonS3Exception.class).when(mAwsClient).deleteBucketCrossOriginConfiguration(any(DeleteBucketCrossOriginConfigurationRequest.class));
        assertFalse(client.delete("BUCKET_1"));
    }
}
