package io.company.brewcraft.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.company.brewcraft.repository.InvoiceRepository;
import io.company.brewcraft.service.InvoiceService;
import io.company.brewcraft.util.controller.AttributeFilter;

@Configuration
@EnableJpaRepositories(basePackages = { "io.company.brewcraft.repository" })
@EntityScan(basePackages = { "io.company.brewcraft.model" })
public class InvoiceConfiguration {

    @Bean
    @ConditionalOnMissingBean(InvoiceService.class)
    public InvoiceService invoiceService(InvoiceRepository invoiceRepo) {
        return new InvoiceService(invoiceRepo);
    }

    @Bean
    @ConditionalOnMissingBean(AttributeFilter.class)
    public AttributeFilter attributeFilter() {
        return new AttributeFilter();
    }
}
