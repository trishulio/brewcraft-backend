package io.company.brewcraft.model;

import java.util.Optional;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AwsDocumentTemplates {
    private static final String POLICY_DOC_TENANT_BUCKET = "{\n"
            + "    \"Version\": \"2012-10-17\",\n"
            + "    \"Statement\": [\n"
            + "        {\n"
            + "            \"Sid\": \"VisualEditor0\",\n"
            + "            \"Effect\": \"Allow\",\n"
            + "            \"Action\": [\n"
            + "                \"s3:DeleteObjectTagging\",\n"
            + "                \"s3:ListBucketMultipartUploads\",\n"
            + "                \"s3:GetJobTagging\",\n"
            + "                \"s3:DeleteObjectVersion\",\n"
            + "                \"s3:RestoreObject\",\n"
            + "                \"s3:PutJobTagging\",\n"
            + "                \"s3:ListBucket\",\n"
            + "                \"s3:ListMultipartUploadParts\",\n"
            + "                \"s3:PutObject\",\n"
            + "                \"s3:GetObject\",\n"
            + "                \"s3:DeleteJobTagging\",\n"
            + "                \"s3:DescribeJob\",\n"
            + "                \"s3:GetObjectTagging\",\n"
            + "                \"s3:PutObjectTagging\",\n"
            + "                \"s3:DeleteObject\",\n"
            + "                \"s3:GetBucketLocation\"\n"
            + "            ],\n"
            + "            \"Resource\": \"arn:aws:s3:::%s\"\n"
            + "        }\n"
            + "    ]\n"
            + "}";
    
    private static final String POLICY_DOC_COGNITO_ID_ASSUME_ROLE = "{\n"
            + "  \"Version\": \"2012-10-17\",\n"
            + "  \"Statement\": [\n"
            + "    {\n"
            + "      \"Effect\": \"Allow\",\n"
            + "      \"Principal\": {\n"
            + "        \"Federated\": \"cognito-identity.amazonaws.com\"\n"
            + "      },\n"
            + "      \"Action\": \"sts:AssumeRoleWithWebIdentity\",\n"
            + "      \"Condition\": {\n"
            + "        \"StringEquals\": {\n"
            + "          \"cognito-identity.amazonaws.com:aud\": \"%s\"\n"
            + "        }\n"
            + "      }\n"
            + "    }\n"
            + "  ]\n"
            + "}";

    private static final String BUCKET_NAME_TENANT_VFS = "t-%s-vfs";

    private static final String ROLE_NAME_TENANT_IAAS = "t-%s-iaas";

    private static final String ROLE_DESCRIPTION_TENANT_VFS = "Role assumed by tenant-users to gain access to the Iaas resources: %s";

    private static final String POLICY_NAME_TENANT_VFS = "t-%s-vfs";

    private static final String POLICY_DESCRIPTION_TENANT_VFS = "File storage for tenant: %s";
    
    private String cognitoIdPoolId;
    
    public AwsDocumentTemplates(String cognitoIdPoolId) {
        this.cognitoIdPoolId = cognitoIdPoolId;
    }

    public String getTenantBucketPolicyDoc(String bucketName) {
        return String.format(POLICY_DOC_TENANT_BUCKET, bucketName);
    }

    public String getCognitoIdAssumeRolePolicyDoc() {
        return String.format(POLICY_DOC_COGNITO_ID_ASSUME_ROLE, cognitoIdPoolId);
    }

    public String getTenantVfsBucketName(String tenantId) {
        return String.format(BUCKET_NAME_TENANT_VFS, tenantId);
    }
    
    public String getTenantIdFromTenantVfsBucketName(String bucketName) {
        return reverseFormat(BUCKET_NAME_TENANT_VFS, bucketName);
    }

    public String getTenantIaasRoleName(String tenantId) {
        return String.format(ROLE_NAME_TENANT_IAAS, tenantId);
    }
    
    public String getTenantIdFromTenantIaasRoleName(String roleName) {
        return reverseFormat(ROLE_NAME_TENANT_IAAS, roleName);        
    }

    public String getTenantVfsPolicyName(String tenantId) {
        return String.format(POLICY_NAME_TENANT_VFS, tenantId);
    }
    
    public String getTenantIdFromTenantVfsPolicyName(String policyName) {
        return reverseFormat(POLICY_NAME_TENANT_VFS, policyName);
    }

    public String getTenantVfsPolicyDescription(String tenantId) {
        return String.format(POLICY_DESCRIPTION_TENANT_VFS, tenantId);
    }

    public String getTenantIaasRoleDescription(String tenantId) {
        return String.format(ROLE_DESCRIPTION_TENANT_VFS, tenantId);
    }
    
    private String reverseFormat(String singlePlaceHolderTemplate, String formatted) {
        String tenantId = null;
        Pattern pattern = Pattern.compile(String.format(singlePlaceHolderTemplate, "(.*)"));
        
        Matcher matcher = pattern.matcher(formatted);
        Optional<MatchResult> matched = matcher.results().findFirst();

        
        if (matched.isPresent()) {
            tenantId = formatted.substring(matched.get().start(), matched.get().end());            
        }
        
        if (tenantId == null) {
            throw new IllegalArgumentException("The bucketName doesn't follow the VFS tenant-bucket name pattern");
        }
        
        return tenantId;
    }
}