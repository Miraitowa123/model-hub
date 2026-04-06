import { Clock, Building2 } from 'lucide-react';
import type { Model } from '../types';

interface ModelCardProps {
  model: Model;
}

function getProviderIcon(provider: string): string {
  const iconMap: Record<string, string> = {
    'OpenAI': 'O',
    'Anthropic': 'A',
    'Google': 'G',
    '阿里云': '阿里',
    '百度': '百度',
    'Stability AI': 'S',
    'Salesforce': 'SF',
  };
  return iconMap[provider] || provider.charAt(0);
}

function getProviderColor(provider: string): string {
  const colorMap: Record<string, string> = {
    'OpenAI': '#22c55e',
    'Anthropic': '#f97316',
    'Google': '#3b82f6',
    '阿里云': '#ef4444',
    '百度': '#2563eb',
    'Stability AI': '#a855f7',
    'Salesforce': '#60a5fa',
  };
  return colorMap[provider] || '#64748b';
}

function formatRelativeTime(date: Date): string {
  const now = new Date();
  const diffInDays = Math.floor((now.getTime() - date.getTime()) / (1000 * 60 * 60 * 24));

  if (diffInDays === 0) return '今天';
  if (diffInDays === 1) return '昨天';
  if (diffInDays < 7) return `${diffInDays}天前`;
  if (diffInDays < 30) return `${Math.floor(diffInDays / 7)}周前`;
  if (diffInDays < 365) return `${Math.floor(diffInDays / 30)}个月前`;
  return `${Math.floor(diffInDays / 365)}年前`;
}

export function ModelCard({ model }: ModelCardProps) {
  const providerColor = getProviderColor(model.provider);

  return (
    <div
      style={{
        backgroundColor: '#ffffff',
        border: '1px solid #e2e8f0',
        borderRadius: '8px',
        padding: '20px',
        transition: 'all 0.2s ease',
        cursor: 'pointer',
      }}
      onMouseEnter={(e) => {
        e.currentTarget.style.boxShadow = '0 4px 12px rgba(0,0,0,0.08)';
        e.currentTarget.style.transform = 'translateY(-2px)';
      }}
      onMouseLeave={(e) => {
        e.currentTarget.style.boxShadow = 'none';
        e.currentTarget.style.transform = 'translateY(0)';
      }}
    >
      {/* Header: Icon and Name */}
      <div style={{ display: 'flex', alignItems: 'center', marginBottom: '12px' }}>
        <div
          style={{
            width: '24px',
            height: '24px',
            borderRadius: '4px',
            backgroundColor: providerColor,
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            marginRight: '8px',
            flexShrink: 0,
          }}
        >
          <span style={{ color: '#ffffff', fontSize: '12px', fontWeight: 600 }}>
            {getProviderIcon(model.provider)}
          </span>
        </div>
        <h3
          style={{
            fontSize: '16px',
            fontWeight: 600,
            color: '#0f172a',
            margin: 0,
            overflow: 'hidden',
            textOverflow: 'ellipsis',
            whiteSpace: 'nowrap',
          }}
        >
          {model.name}
        </h3>
      </div>

      {/* Tags */}
      <div style={{ display: 'flex', flexWrap: 'wrap', gap: '6px', marginBottom: '12px' }}>
        <span
          style={{
            padding: '2px 8px',
            fontSize: '12px',
            fontWeight: 500,
            color: '#2563eb',
            backgroundColor: '#eff6ff',
            borderRadius: '4px',
          }}
        >
          {model.category}
        </span>
        {model.tags.slice(0, 2).map((tag) => (
          <span
            key={tag}
            style={{
              padding: '2px 8px',
              fontSize: '12px',
              fontWeight: 500,
              color: '#2563eb',
              backgroundColor: '#eff6ff',
              borderRadius: '4px',
            }}
          >
            {tag}
          </span>
        ))}
      </div>

      {/* Description */}
      <p
        style={{
          fontSize: '14px',
          color: '#475569',
          lineHeight: 1.6,
          marginBottom: '16px',
          display: '-webkit-box',
          WebkitLineClamp: 2,
          WebkitBoxOrient: 'vertical',
          overflow: 'hidden',
        }}
      >
        {model.description}
      </p>

      {/* Footer: Provider and Update Time */}
      <div
        style={{
          display: 'flex',
          justifyContent: 'space-between',
          alignItems: 'center',
          fontSize: '14px',
          color: '#64748b',
        }}
      >
        <div style={{ display: 'flex', alignItems: 'center' }}>
          <Building2 style={{ width: '16px', height: '16px', marginRight: '6px' }} />
          <span>{model.provider}</span>
        </div>
        <div style={{ display: 'flex', alignItems: 'center' }}>
          <Clock style={{ width: '14px', height: '14px', marginRight: '4px' }} />
          <span style={{ fontSize: '12px' }}>更新于 {formatRelativeTime(model.updatedAt)}</span>
        </div>
      </div>
    </div>
  );
}
