package io.company.brewcraft.service;

import com.amazonaws.arn.Arn;

public class AwsArnMapper {
    public static final String AWS_SERVICE_IAM_POLICY = "policy";

    private String accountId;
    private String partition;
    private String iamRegion;

    public AwsArnMapper(String accountId, String partition, String iamRegion) {
        this.accountId = accountId;
        this.partition = partition;
        this.iamRegion = iamRegion;
    }

    public String getPolicyArn(String policyName) {
        return getServiceArn(AWS_SERVICE_IAM_POLICY, policyName, this.iamRegion);
    }

    private String getServiceArn(String serviceName, String resourceName, String region) {
        String arn = Arn.builder()
                        .withAccountId(this.accountId)
                        .withPartition(this.partition)
                        .withRegion(region)
                        .withService(serviceName)
                        .withResource(resourceName)
                        .build()
                        .toString();

        return arn.toLowerCase();
    }
}
