package io.company.brewcraft.model;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IaasRolePolicyAttachmentTest {
    private IaasRolePolicyAttachment attachment;

    @BeforeEach
    public void init() {
        attachment = new IaasRolePolicyAttachment();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(attachment.getId());
        assertNull(attachment.getIaasRole());
        assertNull(attachment.getIaasPolicy());
        assertNull(attachment.getVersion());
    }

    @Test
    public void testIdConstructor() {
        attachment = new IaasRolePolicyAttachment(new IaasRolePolicyAttachmentId("ROLE", "POLICY"));

        assertEquals(new IaasRolePolicyAttachmentId("ROLE", "POLICY"), attachment.getId());
    }

    @Test
    public void testAllArgConstructor() {
        attachment = new IaasRolePolicyAttachment(new IaasRole("ROLE"), new IaasPolicy("POLICY"));

        assertEquals(new IaasRole("ROLE"), attachment.getIaasRole());
        assertEquals(new IaasPolicy("POLICY"), attachment.getIaasPolicy());
    }

    @Test
    public void testGetSetId() {
        attachment.setId(new IaasRolePolicyAttachmentId("ROLE", "POLICY"));
        assertEquals(new IaasRolePolicyAttachmentId("ROLE", "POLICY"), attachment.getId());
    }

    @Test
    public void testGetSetIaasRole() {
        attachment.setIaasRole(new IaasRole("ROLE"));

        assertEquals(new IaasRole("ROLE"), attachment.getIaasRole());
    }

    @Test
    public void testGetSetIaasPolicy() {
        attachment.setIaasPolicy(new IaasPolicy("POLICY"));

        assertEquals(new IaasPolicy("POLICY"), attachment.getIaasPolicy());
    }
}
