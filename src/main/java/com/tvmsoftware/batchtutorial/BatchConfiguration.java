package com.tvmsoftware.batchtutorial;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    private final JobBuilderFactory jobBuilder;
    private final StepBuilderFactory stepBuilder;

    @Value("${file.input}")
    private String fileInput;

    protected BatchConfiguration(JobBuilderFactory jobBuilder, StepBuilderFactory stepBuilder) {
        super();
        this.jobBuilder = jobBuilder;
        this.stepBuilder = stepBuilder;
    }

    @Bean
    public FlatFileItemReader<Coffee> reader() {
        return new FlatFileItemReaderBuilder<Coffee>().name("coffeeItemReader")
                .resource(new ClassPathResource(fileInput))
                .delimited()
                .names("brand", "origin", "characteristics")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(Coffee.class);
                }})
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<Coffee> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Coffee>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO coffee (brand, origin, characteristics) VALUES (:brand, :origin, :characteristics)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilder.get("importCoffeeJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<Coffee> writer) {
        return stepBuilder.get("importCoffeeStep")
                .<Coffee, Coffee>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }

    @Bean
    public CoffeeItemProcessor processor() {
        return new CoffeeItemProcessor();
    }
}
