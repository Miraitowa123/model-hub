import { Search } from 'lucide-react';

interface HeroSectionProps {
  searchQuery: string;
  onSearchChange: (query: string) => void;
}

export function HeroSection({ searchQuery, onSearchChange }: HeroSectionProps) {
  return (
    <div style={{
      backgroundColor: '#ffffff',
      padding: '48px 0'
    }}>
      <div style={{
        maxWidth: '1200px',
        margin: '0 auto',
        padding: '0 16px',
        textAlign: 'center'
      }}>
        {/* Main Title */}
        <h1 style={{
          fontSize: '32px',
          fontWeight: 700,
          color: '#0f172a',
          lineHeight: 1.3,
          marginBottom: '16px'
        }}>
          聚合全球优质<span style={{ color: '#2563eb' }}>AI模型</span>
          <br />
          赋能下一代应用开发
        </h1>

        {/* Subtitle */}
        <p style={{
          fontSize: '18px',
          fontWeight: 400,
          color: '#475569',
          marginBottom: '24px'
        }}>
          调用全球AI模型，从通用到垂直，应有尽有
        </p>

        {/* Search Input */}
        <div style={{
          maxWidth: '600px',
          margin: '0 auto'
        }}>
          <div style={{
            position: 'relative',
            width: '100%'
          }}>
            <input
              type="text"
              placeholder="输入你想搜索的AI模型"
              value={searchQuery}
              onChange={(e) => onSearchChange(e.target.value)}
              style={{
                display: 'block',
                width: '100%',
                padding: '12px 48px 12px 16px',
                fontSize: '14px',
                lineHeight: 1.5,
                color: '#0f172a',
                backgroundColor: '#ffffff',
                border: '1px solid #e2e8f0',
                borderRadius: '8px',
                outline: 'none',
                transition: 'all 0.2s ease'
              }}
              onFocus={(e) => {
                e.currentTarget.style.borderColor = '#2563eb';
                e.currentTarget.style.boxShadow = '0 0 0 3px rgba(37, 99, 235, 0.1)';
              }}
              onBlur={(e) => {
                e.currentTarget.style.borderColor = '#e2e8f0';
                e.currentTarget.style.boxShadow = 'none';
              }}
            />
            <div style={{
              position: 'absolute',
              top: 0,
              bottom: 0,
              right: 0,
              padding: '0 12px',
              display: 'flex',
              alignItems: 'center',
              pointerEvents: 'none'
            }}>
              <Search style={{ width: '20px', height: '20px', color: '#94a3b8' }} />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
