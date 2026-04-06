import { ChevronDown, FilterX } from 'lucide-react';
import type { Model, SortOption } from '../types';
import { ModelCard } from './ModelCard';

interface ModelListProps {
  models: Model[];
  totalModels: number;
  sortBy: SortOption;
  isLoading: boolean;
  errorMessage: string | null;
  page: number;
  totalPages: number;
  onSortChange: (sort: SortOption) => void;
  onPageChange: (page: number) => void;
}

const sortOptions: { value: SortOption; label: string }[] = [
  { value: 'publishedAt', label: '发布时间' },
  { value: 'inputPrice', label: '输入价格' },
  { value: 'outputPrice', label: '输出价格' },
];

export function ModelList({
  models,
  totalModels,
  sortBy,
  isLoading,
  errorMessage,
  page,
  totalPages,
  onSortChange,
  onPageChange,
}: ModelListProps) {
  return (
    <div style={{ backgroundColor: '#ffffff' }}>
      <div style={{ maxWidth: '1200px', margin: '0 auto', padding: '24px 16px' }}>
        <div
          style={{
            display: 'flex',
            flexDirection: 'row',
            alignItems: 'center',
            justifyContent: 'space-between',
            marginBottom: '24px',
            flexWrap: 'wrap',
            gap: '16px',
          }}
        >
          <div>
            <h2
              style={{
                fontSize: '16px',
                color: '#64748b',
                fontWeight: 400,
              }}
            >
              <span style={{ color: '#2563eb', fontWeight: 600, fontSize: '18px' }}>
                {totalModels}
              </span>
              <span style={{ marginLeft: '4px' }}>个模型符合条件</span>
            </h2>
          </div>

          <div style={{ display: 'flex', alignItems: 'center' }}>
            {sortOptions.map((option, index) => (
              <button
                key={option.value}
                onClick={() => onSortChange(option.value)}
                style={{
                  display: 'flex',
                  alignItems: 'center',
                  padding: '6px 12px',
                  fontSize: '14px',
                  fontWeight: sortBy === option.value ? 500 : 400,
                  color: sortBy === option.value ? '#2563eb' : '#64748b',
                  backgroundColor: 'transparent',
                  border: 'none',
                  cursor: 'pointer',
                  transition: 'all 0.2s ease',
                  borderRight:
                    index !== sortOptions.length - 1 ? '1px solid #e2e8f0' : 'none',
                }}
                onMouseEnter={(event) => {
                  if (sortBy !== option.value) {
                    event.currentTarget.style.color = '#475569';
                  }
                }}
                onMouseLeave={(event) => {
                  if (sortBy !== option.value) {
                    event.currentTarget.style.color = '#64748b';
                  }
                }}
              >
                {option.label}
                {sortBy === option.value ? (
                  <ChevronDown style={{ marginLeft: '4px', width: '14px', height: '14px' }} />
                ) : null}
              </button>
            ))}
          </div>
        </div>

        {errorMessage ? (
          <div style={{ textAlign: 'center', padding: '64px 0' }}>
            <div
              style={{
                display: 'inline-flex',
                alignItems: 'center',
                justifyContent: 'center',
                width: '64px',
                height: '64px',
                borderRadius: '50%',
                backgroundColor: '#fef2f2',
                marginBottom: '16px',
              }}
            >
              <FilterX style={{ width: '32px', height: '32px', color: '#dc2626' }} />
            </div>
            <h3
              style={{
                fontSize: '18px',
                fontWeight: 500,
                color: '#0f172a',
                marginBottom: '8px',
              }}
            >
              模型数据加载失败
            </h3>
            <p
              style={{
                fontSize: '14px',
                color: '#64748b',
                maxWidth: '400px',
                margin: '0 auto',
              }}
            >
              {errorMessage}
            </p>
          </div>
        ) : isLoading ? (
          <div
            style={{
              textAlign: 'center',
              padding: '64px 0',
              color: '#64748b',
              fontSize: '14px',
            }}
          >
            模型数据加载中...
          </div>
        ) : models.length > 0 ? (
          <div
            style={{
              display: 'grid',
              gridTemplateColumns: 'repeat(auto-fill, minmax(380px, 1fr))',
              gap: '20px',
            }}
          >
            {models.map((model) => (
              <ModelCard key={model.id} model={model} />
            ))}
          </div>
        ) : (
          <div style={{ textAlign: 'center', padding: '64px 0' }}>
            <div
              style={{
                display: 'inline-flex',
                alignItems: 'center',
                justifyContent: 'center',
                width: '64px',
                height: '64px',
                borderRadius: '50%',
                backgroundColor: '#f1f5f9',
                marginBottom: '16px',
              }}
            >
              <FilterX style={{ width: '32px', height: '32px', color: '#94a3b8' }} />
            </div>
            <h3
              style={{
                fontSize: '18px',
                fontWeight: 500,
                color: '#0f172a',
                marginBottom: '8px',
              }}
            >
              没有找到符合条件的模型
            </h3>
            <p
              style={{
                fontSize: '14px',
                color: '#64748b',
                maxWidth: '400px',
                margin: '0 auto',
              }}
            >
              尝试调整筛选条件或搜索关键词，看看其他模型
            </p>
          </div>
        )}

        {!isLoading && !errorMessage && totalPages > 1 ? (
          <div
            style={{
              display: 'flex',
              justifyContent: 'center',
              alignItems: 'center',
              gap: '12px',
              marginTop: '28px',
            }}
          >
            <button
              onClick={() => onPageChange(page - 1)}
              disabled={page === 0}
              style={{
                padding: '8px 14px',
                fontSize: '14px',
                color: page === 0 ? '#94a3b8' : '#2563eb',
                backgroundColor: '#ffffff',
                border: '1px solid #cbd5e1',
                borderRadius: '6px',
                cursor: page === 0 ? 'not-allowed' : 'pointer',
              }}
            >
              上一页
            </button>
            <span style={{ fontSize: '14px', color: '#64748b' }}>
              第 {page + 1} / {totalPages} 页
            </span>
            <button
              onClick={() => onPageChange(page + 1)}
              disabled={page >= totalPages - 1}
              style={{
                padding: '8px 14px',
                fontSize: '14px',
                color: page >= totalPages - 1 ? '#94a3b8' : '#2563eb',
                backgroundColor: '#ffffff',
                border: '1px solid #cbd5e1',
                borderRadius: '6px',
                cursor: page >= totalPages - 1 ? 'not-allowed' : 'pointer',
              }}
            >
              下一页
            </button>
          </div>
        ) : null}
      </div>
    </div>
  );
}
