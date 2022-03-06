package io.company.brewcraft.service;

import com.amazonaws.arn.Arn;

public class AwsArnMapper {
    public static final String AWS_SERVICE_IAM = "iam";
    public static final String AWS_SERVICE_PREFIX_POLICY = "policy/";

    private String accountId;
    private String partition;

    public AwsArnMapper(String accountId, String partition) {
        this.accountId = accountId;
        this.partition = partition;
    }

    public String getPolicyArn(String policyName) {
        return getServiceArn("", AWS_SERVICE_IAM, AWS_SERVICE_PREFIX_POLICY, policyName);
    }

    private String getServiceArn(String region, String serviceName, String resourcePrefix, String resourceName) {
        String arn = Arn.builder()
            .withAccountId(this.accountId)
            .withPartition(this.partition)
            .withService(serviceName)
            .withRegion(region)
            .withResource(resourcePrefix + resourceName)
            .build()
            .toString();

        return arn.toLowerCase();
    }
}
