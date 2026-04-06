import { Scale, Calculator, Swords, GitBranch } from 'lucide-react';

export function ActionButtons() {
  const tools = [
    { icon: Scale, label: '选型对比', color: '#2563eb', bgColor: '#eff6ff' },
    { icon: Calculator, label: '费用计算器', color: '#7c3aed', bgColor: '#f3e8ff' },
    { icon: Swords, label: '竞技场', color: '#ea580c', bgColor: '#fff7ed' },
    { icon: GitBranch, label: '开源模型', color: '#16a34a', bgColor: '#f0fdf4' },
  ];

  return (
    <div style={{ backgroundColor: '#ffffff', padding: '24px 0' }}>
      <div style={{ maxWidth: '1200px', margin: '0 auto', padding: '0 16px' }}>
        <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', flexWrap: 'wrap', gap: '8px' }}>
          <span style={{ fontSize: '14px', color: '#64748b', marginRight: '8px' }}>大模型工具：</span>
          {tools.map((tool) => (
            <button
              key={tool.label}
              style={{
                display: 'inline-flex',
                alignItems: 'center',
                padding: '8px 16px',
                fontSize: '14px',
                fontWeight: 500,
                color: '#2563eb',
                backgroundColor: '#ffffff',
                border: '1px solid #2563eb',
                borderRadius: '6px',
                cursor: 'pointer',
                transition: 'all 0.2s ease',
                marginRight: '12px'
              }}
              onMouseEnter={(e) => {
                e.currentTarget.style.backgroundColor = '#eff6ff';
              }}
              onMouseLeave={(e) => {
                e.currentTarget.style.backgroundColor = '#ffffff';
              }}
              onMouseDown={(e) => {
                e.currentTarget.style.transform = 'scale(0.98)';
              }}
              onMouseUp={(e) => {
                e.currentTarget.style.transform = 'scale(1)';
              }}
            >
              <div
                style={{
                  width: '20px',
                  height: '20px',
                  borderRadius: '4px',
                  backgroundColor: tool.bgColor,
                  display: 'flex',
                  alignItems: 'center',
                  justifyContent: 'center',
                  marginRight: '8px'
                }}
              >
                <tool.icon style={{ width: '12px', height: '12px', color: tool.color }} />
              </div>
              {tool.label}
            </button>
          ))}
        </div>
      </div>
    </div>
  );
}
