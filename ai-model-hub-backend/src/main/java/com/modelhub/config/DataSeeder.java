package com.modelhub.config;

import com.modelhub.entity.*;
import com.modelhub.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Component
@ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true")
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final ProviderRepository providerRepository;
    private final CategoryRepository categoryRepository;
    private final CapabilityRepository capabilityRepository;
    private final ModelRepository modelRepository;

    @Override
    @Transactional
    public void run(String... args) {
        log.info("Starting data seeding...");

        // Seed providers
        List<Provider> providers = seedProviders();

        // Seed categories
        List<Category> categories = seedCategories();

        // Seed capabilities
        List<Capability> capabilities = seedCapabilities();

        // Seed models
        seedModels(providers, categories, capabilities);

        log.info("Data seeding completed successfully!");
    }

    private List<Provider> seedProviders() {
        log.info("Seeding providers...");

        List<Provider> providers = Arrays.asList(
                Provider.builder()
                        .name("OpenAI")
                        .displayName("OpenAI")
                        .description("人工智能研究实验室，开发了GPT系列模型")
                        .websiteUrl("https://openai.com")
                        .build(),
                Provider.builder()
                        .name("Anthropic")
                        .displayName("Anthropic")
                        .description("AI安全公司，开发了Claude系列模型")
                        .websiteUrl("https://anthropic.com")
                        .build(),
                Provider.builder()
                        .name("Google")
                        .displayName("Google")
                        .description("Google DeepMind开发的Gemini系列模型")
                        .websiteUrl("https://deepmind.google")
                        .build(),
                Provider.builder()
                        .name("Meta")
                        .displayName("Meta")
                        .description("Meta AI开发的Llama系列开源模型")
                        .websiteUrl("https://ai.meta.com")
                        .build(),
                Provider.builder()
                        .name("Mistral")
                        .displayName("Mistral AI")
                        .description("法国AI公司，开发了Mixtral等模型")
                        .websiteUrl("https://mistral.ai")
                        .build(),
                Provider.builder()
                        .name("Cohere")
                        .displayName("Cohere")
                        .description("专注于企业级NLP的AI公司")
                        .websiteUrl("https://cohere.com")
                        .build()
        );

        return providers.stream()
                .map(this::saveProviderIfMissing)
                .toList();
    }

    private List<Category> seedCategories() {
        log.info("Seeding categories...");

        List<Category> categories = Arrays.asList(
                Category.builder()
                        .name("text-generation")
                        .displayName("文本生成")
                        .description("通用文本生成模型")
                        .icon("MessageSquare")
                        .build(),
                Category.builder()
                        .name("code-generation")
                        .displayName("代码生成")
                        .description("专门用于代码生成和理解的模型")
                        .icon("Code")
                        .build(),
                Category.builder()
                        .name("vision")
                        .displayName("视觉理解")
                        .description("支持图像理解和分析的多模态模型")
                        .icon("Eye")
                        .build(),
                Category.builder()
                        .name("embedding")
                        .displayName("文本嵌入")
                        .description("用于生成文本向量表示的模型")
                        .icon("Hash")
                        .build(),
                Category.builder()
                        .name("reasoning")
                        .displayName("推理模型")
                        .description("具有强逻辑推理能力的模型")
                        .icon("Brain")
                        .build()
        );

        return categories.stream()
                .map(this::saveCategoryIfMissing)
                .toList();
    }

    private List<Capability> seedCapabilities() {
        log.info("Seeding capabilities...");

        List<Capability> capabilities = Arrays.asList(
                Capability.builder()
                        .name("function-calling")
                        .displayName("函数调用")
                        .description("支持调用外部函数和工具")
                        .icon("Wrench")
                        .build(),
                Capability.builder()
                        .name("vision")
                        .displayName("视觉能力")
                        .description("支持图像理解和分析")
                        .icon("Eye")
                        .build(),
                Capability.builder()
                        .name("json-mode")
                        .displayName("JSON模式")
                        .description("支持结构化JSON输出")
                        .icon("FileJson")
                        .build(),
                Capability.builder()
                        .name("streaming")
                        .displayName("流式输出")
                        .description("支持流式响应")
                        .icon("Zap")
                        .build(),
                Capability.builder()
                        .name("system-prompt")
                        .displayName("系统提示词")
                        .description("支持系统级提示词")
                        .icon("MessageSquare")
                        .build()
        );

        return capabilities.stream()
                .map(this::saveCapabilityIfMissing)
                .toList();
    }

    private void seedModels(List<Provider> providers, List<Category> categories, List<Capability> capabilities) {
        log.info("Seeding models...");

        Map<String, Provider> providerMap = providers.stream()
                .collect(java.util.stream.Collectors.toMap(Provider::getName, p -> p));

        Map<String, Category> categoryMap = categories.stream()
                .collect(java.util.stream.Collectors.toMap(Category::getName, c -> c));

        Map<String, Capability> capabilityMap = capabilities.stream()
                .collect(java.util.stream.Collectors.toMap(Capability::getName, c -> c));

        List<Model> models = Arrays.asList(
                // OpenAI Models
                createModel("gpt-4o", "GPT-4o", "OpenAI最强大的多模态模型", providerMap.get("OpenAI"),
                        new String[]{"text-generation", "vision", "reasoning"},
                        new String[]{"function-calling", "vision", "json-mode", "streaming", "system-prompt"},
                        128000, new BigDecimal("2.50"), new BigDecimal("10.00"), "2024-05-13", 95.0, categoryMap, capabilityMap),

                createModel("gpt-4o-mini", "GPT-4o Mini", "OpenAI经济高效的快速模型", providerMap.get("OpenAI"),
                        new String[]{"text-generation", "vision"},
                        new String[]{"function-calling", "vision", "json-mode", "streaming", "system-prompt"},
                        128000, new BigDecimal("0.15"), new BigDecimal("0.60"), "2024-07-18", 88.0, categoryMap, capabilityMap),

                createModel("gpt-4-turbo", "GPT-4 Turbo", "OpenAI高性能文本模型", providerMap.get("OpenAI"),
                        new String[]{"text-generation", "code-generation", "reasoning"},
                        new String[]{"function-calling", "json-mode", "streaming", "system-prompt"},
                        128000, new BigDecimal("10.00"), new BigDecimal("30.00"), "2024-04-09", 92.0, categoryMap, capabilityMap),

                createModel("text-embedding-3-large", "Text Embedding 3 Large", "OpenAI强大的嵌入模型", providerMap.get("OpenAI"),
                        new String[]{"embedding"},
                        new String[]{},
                        8192, new BigDecimal("0.13"), new BigDecimal("0.00"), "2024-01-25", 85.0, categoryMap, capabilityMap),

                // Anthropic Models
                createModel("claude-3-5-sonnet-20241022", "Claude 3.5 Sonnet", "Anthropic最智能的模型", providerMap.get("Anthropic"),
                        new String[]{"text-generation", "vision", "reasoning"},
                        new String[]{"function-calling", "vision", "streaming", "system-prompt"},
                        200000, new BigDecimal("3.00"), new BigDecimal("15.00"), "2024-10-22", 94.0, categoryMap, capabilityMap),

                createModel("claude-3-opus-20240229", "Claude 3 Opus", "Anthropic强大的高性能模型", providerMap.get("Anthropic"),
                        new String[]{"text-generation", "vision", "code-generation", "reasoning"},
                        new String[]{"function-calling", "vision", "streaming", "system-prompt"},
                        200000, new BigDecimal("15.00"), new BigDecimal("75.00"), "2024-02-29", 93.0, categoryMap, capabilityMap),

                createModel("claude-3-haiku-20240307", "Claude 3 Haiku", "Anthropic快速经济的模型", providerMap.get("Anthropic"),
                        new String[]{"text-generation", "vision"},
                        new String[]{"vision", "streaming"},
                        200000, new BigDecimal("0.25"), new BigDecimal("1.25"), "2024-03-07", 86.0, categoryMap, capabilityMap),

                // Google Models
                createModel("gemini-1.5-pro", "Gemini 1.5 Pro", "Google最强大的AI模型", providerMap.get("Google"),
                        new String[]{"text-generation", "vision", "code-generation", "reasoning"},
                        new String[]{"function-calling", "vision", "json-mode", "streaming", "system-prompt"},
                        2000000, new BigDecimal("3.50"), new BigDecimal("10.50"), "2024-05-15", 93.0, categoryMap, capabilityMap),

                createModel("gemini-1.5-flash", "Gemini 1.5 Flash", "Google快速高效的模型", providerMap.get("Google"),
                        new String[]{"text-generation", "vision"},
                        new String[]{"function-calling", "vision", "streaming"},
                        1000000, new BigDecimal("0.35"), new BigDecimal("1.05"), "2024-05-21", 87.0, categoryMap, capabilityMap)
        );

        long ensuredModelCount = models.stream()
                .map(this::saveModelIfMissing)
                .count();

        log.info("Ensured {} models exist in database", ensuredModelCount);
    }

    private Model createModel(String modelId, String name, String description, Provider provider,
                              String[] categoryNames, String[] capabilityNames,
                              int contextLength, BigDecimal inputPrice, BigDecimal outputPrice,
                              String releaseDate, Double popularityScore,
                              Map<String, Category> categoryMap, Map<String, Capability> capabilityMap) {
        Model model = Model.builder()
                .modelId(modelId)
                .name(name)
                .description(description)
                .provider(provider)
                .contextLength(contextLength)
                .pricing(ModelPricing.perMillionTokens(inputPrice, outputPrice))
                .releaseDate(LocalDate.parse(releaseDate))
                .popularityScore(popularityScore)
                .isActive(true)
                .build();

        // Add categories
        for (String catName : categoryNames) {
            Category cat = categoryMap.get(catName);
            if (cat != null) {
                model.addCategory(cat);
            }
        }

        // Add capabilities
        for (String capName : capabilityNames) {
            Capability cap = capabilityMap.get(capName);
            if (cap != null) {
                model.addCapability(cap);
            }
        }

        return model;
    }

    private Provider saveProviderIfMissing(Provider provider) {
        return providerRepository.findByName(provider.getName())
                .orElseGet(() -> providerRepository.save(provider));
    }

    private Category saveCategoryIfMissing(Category category) {
        return categoryRepository.findByName(category.getName())
                .orElseGet(() -> categoryRepository.save(category));
    }

    private Capability saveCapabilityIfMissing(Capability capability) {
        return capabilityRepository.findByName(capability.getName())
                .orElseGet(() -> capabilityRepository.save(capability));
    }

    private Model saveModelIfMissing(Model model) {
        return modelRepository.findByModelId(model.getModelId())
                .orElseGet(() -> modelRepository.save(model));
    }

}
