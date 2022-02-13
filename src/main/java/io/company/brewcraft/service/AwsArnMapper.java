package io.company.brewcraft.service;

import com.amazonaws.arn.Arn;

public class AwsArnMapper {
    public static final String AWS_SERVICE_IAM_POLICY = "policy";

    private String accountId;
    private String partition;
    private String region;

    public AwsArnMapper(String accountId, String partition, String region) {
        this.accountId = accountId;
        this.partition = partition;
        this.region = region;
    }

    public String getPolicyArn(String policyName) {
        return getServiceArn(AWS_SERVICE_IAM_POLICY, policyName);
    }

    private String getServiceArn(String serviceName, String resourceName) {
        String arn = Arn.builder()
                        .withAccountId(this.accountId)
                        .withPartition(this.partition)
                        .withRegion(this.region)
                        .withService(serviceName)
                        .withResource(resourceName)
                        .build()
                        .toString();

        return arn.toLowerCase();
    }
}
