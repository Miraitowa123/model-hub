CREATE DATABASE IF NOT EXISTS modelhubdb
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE modelhubdb;

CREATE TABLE IF NOT EXISTS providers (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  display_name VARCHAR(255) DEFAULT NULL,
  description VARCHAR(255) DEFAULT NULL,
  logo_url VARCHAR(255) DEFAULT NULL,
  website_url VARCHAR(255) DEFAULT NULL,
  created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
  updated_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (id),
  UNIQUE KEY uk_providers_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS categories (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  display_name VARCHAR(255) DEFAULT NULL,
  description VARCHAR(255) DEFAULT NULL,
  icon VARCHAR(255) DEFAULT NULL,
  created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
  updated_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (id),
  UNIQUE KEY uk_categories_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS capabilities (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  display_name VARCHAR(255) DEFAULT NULL,
  description VARCHAR(255) DEFAULT NULL,
  icon VARCHAR(255) DEFAULT NULL,
  created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
  updated_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (id),
  UNIQUE KEY uk_capabilities_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS models (
  id BIGINT NOT NULL AUTO_INCREMENT,
  model_id VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(2000) DEFAULT NULL,
  context_length INT DEFAULT NULL,
  provider_id BIGINT DEFAULT NULL,
  input_price DECIMAL(10, 6) DEFAULT NULL,
  output_price DECIMAL(10, 6) DEFAULT NULL,
  price_unit VARCHAR(32) DEFAULT NULL,
  release_date DATE DEFAULT NULL,
  is_active BIT(1) DEFAULT b'1',
  popularity_score DOUBLE DEFAULT NULL,
  created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
  updated_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (id),
  UNIQUE KEY uk_models_model_id (model_id),
  KEY idx_models_provider_id (provider_id),
  CONSTRAINT fk_models_provider
    FOREIGN KEY (provider_id) REFERENCES providers (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS model_categories (
  model_id BIGINT NOT NULL,
  category_id BIGINT NOT NULL,
  PRIMARY KEY (model_id, category_id),
  KEY idx_model_categories_category_id (category_id),
  CONSTRAINT fk_model_categories_model
    FOREIGN KEY (model_id) REFERENCES models (id) ON DELETE CASCADE,
  CONSTRAINT fk_model_categories_category
    FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS model_capabilities (
  model_id BIGINT NOT NULL,
  capability_id BIGINT NOT NULL,
  PRIMARY KEY (model_id, capability_id),
  KEY idx_model_capabilities_capability_id (capability_id),
  CONSTRAINT fk_model_capabilities_model
    FOREIGN KEY (model_id) REFERENCES models (id) ON DELETE CASCADE,
  CONSTRAINT fk_model_capabilities_capability
    FOREIGN KEY (capability_id) REFERENCES capabilities (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO providers (name, display_name, description, website_url)
VALUES
  ('OpenAI', 'OpenAI', '人工智能研究实验室，开发了GPT系列模型', 'https://openai.com'),
  ('Anthropic', 'Anthropic', 'AI安全公司，开发了Claude系列模型', 'https://anthropic.com'),
  ('Google', 'Google', 'Google DeepMind开发的Gemini系列模型', 'https://deepmind.google'),
  ('Meta', 'Meta', 'Meta AI开发的Llama系列开源模型', 'https://ai.meta.com'),
  ('Mistral', 'Mistral AI', '法国AI公司，开发了Mixtral等模型', 'https://mistral.ai'),
  ('Cohere', 'Cohere', '专注于企业级NLP的AI公司', 'https://cohere.com')
AS new
ON DUPLICATE KEY UPDATE
  display_name = new.display_name,
  description = new.description,
  website_url = new.website_url;

INSERT INTO categories (name, display_name, description, icon)
VALUES
  ('text-generation', '文本生成', '通用文本生成模型', 'MessageSquare'),
  ('code-generation', '代码生成', '专门用于代码生成和理解的模型', 'Code'),
  ('vision', '视觉理解', '支持图像理解和分析的多模态模型', 'Eye'),
  ('embedding', '文本嵌入', '用于生成文本向量表示的模型', 'Hash'),
  ('reasoning', '推理模型', '具有强逻辑推理能力的模型', 'Brain')
AS new
ON DUPLICATE KEY UPDATE
  display_name = new.display_name,
  description = new.description,
  icon = new.icon;

INSERT INTO capabilities (name, display_name, description, icon)
VALUES
  ('function-calling', '函数调用', '支持调用外部函数和工具', 'Wrench'),
  ('vision', '视觉能力', '支持图像理解和分析', 'Eye'),
  ('json-mode', 'JSON模式', '支持结构化JSON输出', 'FileJson'),
  ('streaming', '流式输出', '支持流式响应', 'Zap'),
  ('system-prompt', '系统提示词', '支持系统级提示词', 'MessageSquare')
AS new
ON DUPLICATE KEY UPDATE
  display_name = new.display_name,
  description = new.description,
  icon = new.icon;

INSERT INTO models (
  model_id,
  name,
  description,
  context_length,
  provider_id,
  input_price,
  output_price,
  price_unit,
  release_date,
  is_active,
  popularity_score
)
VALUES
  ('gpt-4o', 'GPT-4o', 'OpenAI最强大的多模态模型', 128000, (SELECT id FROM providers WHERE name = 'OpenAI'), 2.500000, 10.000000, '1M tokens', '2024-05-13', b'1', 95.0),
  ('gpt-4o-mini', 'GPT-4o Mini', 'OpenAI经济高效的快速模型', 128000, (SELECT id FROM providers WHERE name = 'OpenAI'), 0.150000, 0.600000, '1M tokens', '2024-07-18', b'1', 88.0),
  ('gpt-4-turbo', 'GPT-4 Turbo', 'OpenAI高性能文本模型', 128000, (SELECT id FROM providers WHERE name = 'OpenAI'), 10.000000, 30.000000, '1M tokens', '2024-04-09', b'1', 92.0),
  ('text-embedding-3-large', 'Text Embedding 3 Large', 'OpenAI强大的嵌入模型', 8192, (SELECT id FROM providers WHERE name = 'OpenAI'), 0.130000, 0.000000, '1M tokens', '2024-01-25', b'1', 85.0),
  ('claude-3-5-sonnet-20241022', 'Claude 3.5 Sonnet', 'Anthropic最智能的模型', 200000, (SELECT id FROM providers WHERE name = 'Anthropic'), 3.000000, 15.000000, '1M tokens', '2024-10-22', b'1', 94.0),
  ('claude-3-opus-20240229', 'Claude 3 Opus', 'Anthropic强大的高性能模型', 200000, (SELECT id FROM providers WHERE name = 'Anthropic'), 15.000000, 75.000000, '1M tokens', '2024-02-29', b'1', 93.0),
  ('claude-3-haiku-20240307', 'Claude 3 Haiku', 'Anthropic快速经济的模型', 200000, (SELECT id FROM providers WHERE name = 'Anthropic'), 0.250000, 1.250000, '1M tokens', '2024-03-07', b'1', 86.0),
  ('gemini-1.5-pro', 'Gemini 1.5 Pro', 'Google最强大的AI模型', 2000000, (SELECT id FROM providers WHERE name = 'Google'), 3.500000, 10.500000, '1M tokens', '2024-05-15', b'1', 93.0),
  ('gemini-1.5-flash', 'Gemini 1.5 Flash', 'Google快速高效的模型', 1000000, (SELECT id FROM providers WHERE name = 'Google'), 0.350000, 1.050000, '1M tokens', '2024-05-21', b'1', 87.0)
AS new
ON DUPLICATE KEY UPDATE
  name = new.name,
  description = new.description,
  context_length = new.context_length,
  provider_id = new.provider_id,
  input_price = new.input_price,
  output_price = new.output_price,
  price_unit = new.price_unit,
  release_date = new.release_date,
  is_active = new.is_active,
  popularity_score = new.popularity_score;

INSERT IGNORE INTO model_categories (model_id, category_id)
SELECT m.id, c.id FROM models m JOIN categories c
WHERE m.model_id = 'gpt-4o' AND c.name = 'text-generation';
INSERT IGNORE INTO model_categories (model_id, category_id)
SELECT m.id, c.id FROM models m JOIN categories c
WHERE m.model_id = 'gpt-4o' AND c.name = 'vision';
INSERT IGNORE INTO model_categories (model_id, category_id)
SELECT m.id, c.id FROM models m JOIN categories c
WHERE m.model_id = 'gpt-4o' AND c.name = 'reasoning';
INSERT IGNORE INTO model_categories (model_id, category_id)
SELECT m.id, c.id FROM models m JOIN categories c
WHERE m.model_id = 'gpt-4o-mini' AND c.name = 'text-generation';
INSERT IGNORE INTO model_categories (model_id, category_id)
SELECT m.id, c.id FROM models m JOIN categories c
WHERE m.model_id = 'gpt-4o-mini' AND c.name = 'vision';
INSERT IGNORE INTO model_categories (model_id, category_id)
SELECT m.id, c.id FROM models m JOIN categories c
WHERE m.model_id = 'gpt-4-turbo' AND c.name = 'text-generation';
INSERT IGNORE INTO model_categories (model_id, category_id)
SELECT m.id, c.id FROM models m JOIN categories c
WHERE m.model_id = 'gpt-4-turbo' AND c.name = 'code-generation';
INSERT IGNORE INTO model_categories (model_id, category_id)
SELECT m.id, c.id FROM models m JOIN categories c
WHERE m.model_id = 'gpt-4-turbo' AND c.name = 'reasoning';
INSERT IGNORE INTO model_categories (model_id, category_id)
SELECT m.id, c.id FROM models m JOIN categories c
WHERE m.model_id = 'text-embedding-3-large' AND c.name = 'embedding';
INSERT IGNORE INTO model_categories (model_id, category_id)
SELECT m.id, c.id FROM models m JOIN categories c
WHERE m.model_id = 'claude-3-5-sonnet-20241022' AND c.name = 'text-generation';
INSERT IGNORE INTO model_categories (model_id, category_id)
SELECT m.id, c.id FROM models m JOIN categories c
WHERE m.model_id = 'claude-3-5-sonnet-20241022' AND c.name = 'vision';
INSERT IGNORE INTO model_categories (model_id, category_id)
SELECT m.id, c.id FROM models m JOIN categories c
WHERE m.model_id = 'claude-3-5-sonnet-20241022' AND c.name = 'reasoning';
INSERT IGNORE INTO model_categories (model_id, category_id)
SELECT m.id, c.id FROM models m JOIN categories c
WHERE m.model_id = 'claude-3-opus-20240229' AND c.name = 'text-generation';
INSERT IGNORE INTO model_categories (model_id, category_id)
SELECT m.id, c.id FROM models m JOIN categories c
WHERE m.model_id = 'claude-3-opus-20240229' AND c.name = 'vision';
INSERT IGNORE INTO model_categories (model_id, category_id)
SELECT m.id, c.id FROM models m JOIN categories c
WHERE m.model_id = 'claude-3-opus-20240229' AND c.name = 'code-generation';
INSERT IGNORE INTO model_categories (model_id, category_id)
SELECT m.id, c.id FROM models m JOIN categories c
WHERE m.model_id = 'claude-3-opus-20240229' AND c.name = 'reasoning';
INSERT IGNORE INTO model_categories (model_id, category_id)
SELECT m.id, c.id FROM models m JOIN categories c
WHERE m.model_id = 'claude-3-haiku-20240307' AND c.name = 'text-generation';
INSERT IGNORE INTO model_categories (model_id, category_id)
SELECT m.id, c.id FROM models m JOIN categories c
WHERE m.model_id = 'claude-3-haiku-20240307' AND c.name = 'vision';
INSERT IGNORE INTO model_categories (model_id, category_id)
SELECT m.id, c.id FROM models m JOIN categories c
WHERE m.model_id = 'gemini-1.5-pro' AND c.name = 'text-generation';
INSERT IGNORE INTO model_categories (model_id, category_id)
SELECT m.id, c.id FROM models m JOIN categories c
WHERE m.model_id = 'gemini-1.5-pro' AND c.name = 'vision';
INSERT IGNORE INTO model_categories (model_id, category_id)
SELECT m.id, c.id FROM models m JOIN categories c
WHERE m.model_id = 'gemini-1.5-pro' AND c.name = 'code-generation';
INSERT IGNORE INTO model_categories (model_id, category_id)
SELECT m.id, c.id FROM models m JOIN categories c
WHERE m.model_id = 'gemini-1.5-pro' AND c.name = 'reasoning';
INSERT IGNORE INTO model_categories (model_id, category_id)
SELECT m.id, c.id FROM models m JOIN categories c
WHERE m.model_id = 'gemini-1.5-flash' AND c.name = 'text-generation';
INSERT IGNORE INTO model_categories (model_id, category_id)
SELECT m.id, c.id FROM models m JOIN categories c
WHERE m.model_id = 'gemini-1.5-flash' AND c.name = 'vision';

INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'gpt-4o' AND c.name = 'function-calling';
INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'gpt-4o' AND c.name = 'vision';
INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'gpt-4o' AND c.name = 'json-mode';
INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'gpt-4o' AND c.name = 'streaming';
INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'gpt-4o' AND c.name = 'system-prompt';
INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'gpt-4o-mini' AND c.name = 'function-calling';
INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'gpt-4o-mini' AND c.name = 'vision';
INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'gpt-4o-mini' AND c.name = 'json-mode';
INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'gpt-4o-mini' AND c.name = 'streaming';
INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'gpt-4o-mini' AND c.name = 'system-prompt';
INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'gpt-4-turbo' AND c.name = 'function-calling';
INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'gpt-4-turbo' AND c.name = 'json-mode';
INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'gpt-4-turbo' AND c.name = 'streaming';
INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'gpt-4-turbo' AND c.name = 'system-prompt';
INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'claude-3-5-sonnet-20241022' AND c.name = 'function-calling';
INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'claude-3-5-sonnet-20241022' AND c.name = 'vision';
INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'claude-3-5-sonnet-20241022' AND c.name = 'streaming';
INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'claude-3-5-sonnet-20241022' AND c.name = 'system-prompt';
INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'claude-3-opus-20240229' AND c.name = 'function-calling';
INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'claude-3-opus-20240229' AND c.name = 'vision';
INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'claude-3-opus-20240229' AND c.name = 'streaming';
INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'claude-3-opus-20240229' AND c.name = 'system-prompt';
INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'claude-3-haiku-20240307' AND c.name = 'vision';
INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'claude-3-haiku-20240307' AND c.name = 'streaming';
INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'gemini-1.5-pro' AND c.name = 'function-calling';
INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'gemini-1.5-pro' AND c.name = 'vision';
INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'gemini-1.5-pro' AND c.name = 'json-mode';
INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'gemini-1.5-pro' AND c.name = 'streaming';
INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'gemini-1.5-pro' AND c.name = 'system-prompt';
INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'gemini-1.5-flash' AND c.name = 'function-calling';
INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'gemini-1.5-flash' AND c.name = 'vision';
INSERT IGNORE INTO model_capabilities (model_id, capability_id)
SELECT m.id, c.id FROM models m JOIN capabilities c
WHERE m.model_id = 'gemini-1.5-flash' AND c.name = 'streaming';
