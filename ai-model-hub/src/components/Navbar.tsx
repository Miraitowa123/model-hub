import { Search, ChevronDown } from 'lucide-react';

interface NavbarProps {
  searchQuery: string;
  onSearchChange: (query: string) => void;
}

const navItems = [
  { name: '首页', href: '#', active: true },
  { name: 'AI资讯', href: '#', hasDropdown: true },
  { name: 'AI产品库', href: '#', hasDropdown: true },
  { name: '模型广场', href: '#', hasDropdown: true },
  { name: 'MCP服务', href: '#', hasDropdown: true },
  { name: 'AI服务', href: '#', hasDropdown: true },
  { name: '算力市场', href: '#', hasDropdown: true },
  { name: 'AI应用指南', href: '#' },
];

export function Navbar({ searchQuery, onSearchChange }: NavbarProps) {
  return (
    <nav
      style={{
        position: 'sticky',
        top: 0,
        zIndex: 100,
        backgroundColor: '#ffffff',
        borderBottom: '1px solid #e2e8f0',
        height: '64px',
      }}
    >
      <div
        style={{
          maxWidth: '1200px',
          margin: '0 auto',
          padding: '0 16px',
          height: '100%',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'space-between',
        }}
      >
        {/* Logo */}
        <div style={{ display: 'flex', alignItems: 'center', flexShrink: 0 }}>
          <div style={{ display: 'flex', alignItems: 'center', gap: '4px' }}>
            <div
              style={{
                width: '32px',
                height: '32px',
                borderRadius: '8px',
                background: 'linear-gradient(135deg, #3b82f6 0%, #2563eb 100%)',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
              }}
            >
              <span style={{ color: '#ffffff', fontWeight: 700, fontSize: '14px' }}>
                AI
              </span>
            </div>
            <span
              style={{
                fontSize: '20px',
                fontWeight: 700,
                color: '#2563eb',
              }}
            >
              AIBASE
            </span>
          </div>
        </div>

        {/* Desktop Navigation */}
        <div
          style={{
            display: 'flex',
            alignItems: 'center',
            marginLeft: '24px',
            flex: 1,
            justifyContent: 'center',
          }}
        >
          {navItems.map((item) => (
            <a
              key={item.name}
              href={item.href}
              style={{
                display: 'flex',
                alignItems: 'center',
                padding: '0 16px',
                height: '64px',
                fontSize: '14px',
                fontWeight: 500,
                color: item.active ? '#2563eb' : '#475569',
                textDecoration: 'none',
                transition: 'color 0.2s ease',
              }}
              onMouseEnter={(e) => {
                e.currentTarget.style.color = '#2563eb';
              }}
              onMouseLeave={(e) => {
                e.currentTarget.style.color = item.active ? '#2563eb' : '#475569';
              }}
            >
              {item.name}
              {item.hasDropdown && (
                <ChevronDown
                  style={{
                    width: '14px',
                    height: '14px',
                    marginLeft: '2px',
                    color: '#94a3b8',
                  }}
                />
              )}
            </a>
          ))}
        </div>

        {/* Right Side: Search + Auth */}
        <div
          style={{
            display: 'flex',
            alignItems: 'center',
            gap: '16px',
            flexShrink: 0,
          }}
        >
          {/* Search Box */}
          <div style={{ position: 'relative' }}>
            <input
              type="text"
              placeholder="搜索"
              value={searchQuery}
              onChange={(e) => onSearchChange(e.target.value)}
              style={{
                width: '160px',
                height: '32px',
                padding: '0 32px 0 12px',
                fontSize: '14px',
                color: '#0f172a',
                backgroundColor: '#ffffff',
                border: '1px solid #e2e8f0',
                borderRadius: '6px',
                outline: 'none',
                transition: 'border-color 0.2s ease, box-shadow 0.2s ease',
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
            <Search
              style={{
                position: 'absolute',
                right: '10px',
                top: '50%',
                transform: 'translateY(-50%)',
                width: '16px',
                height: '16px',
                color: '#94a3b8',
                pointerEvents: 'none',
              }}
            />
          </div>

          {/* Auth Buttons */}
          <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
            <button
              style={{
                padding: '0 12px',
                height: '32px',
                fontSize: '14px',
                fontWeight: 500,
                color: '#2563eb',
                backgroundColor: 'transparent',
                border: 'none',
                borderRadius: '6px',
                cursor: 'pointer',
                transition: 'color 0.2s ease',
              }}
              onMouseEnter={(e) => {
                e.currentTarget.style.color = '#1d4ed8';
              }}
              onMouseLeave={(e) => {
                e.currentTarget.style.color = '#2563eb';
              }}
            >
              登录
            </button>
            <button
              style={{
                padding: '0 12px',
                height: '32px',
                fontSize: '14px',
                fontWeight: 500,
                color: '#2563eb',
                backgroundColor: 'transparent',
                border: 'none',
                borderRadius: '6px',
                cursor: 'pointer',
                transition: 'color 0.2s ease',
              }}
              onMouseEnter={(e) => {
                e.currentTarget.style.color = '#1d4ed8';
              }}
              onMouseLeave={(e) => {
                e.currentTarget.style.color = '#2563eb';
              }}
            >
              注册
            </button>
          </div>
        </div>
      </div>
    </nav>
  );
}
