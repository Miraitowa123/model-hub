import type { FilterOptions, FilterState } from '../types';

interface FilterBarProps {
  filters: FilterState;
  filterOptions: FilterOptions;
  isLoading: boolean;
  errorMessage: string | null;
  onFilterChange: (filters: FilterState) => void;
}

export function FilterBar({
  filters,
  filterOptions,
  isLoading,
  errorMessage,
  onFilterChange,
}: FilterBarProps) {
  const filterConfig = [
    {
      key: 'providers' as const,
      label: '服务商',
      options: filterOptions.providers,
    },
    {
      key: 'categories' as const,
      label: '分类',
      options: filterOptions.categories,
    },
    {
      key: 'capabilities' as const,
      label: '能力',
      options: filterOptions.capabilities,
    },
    {
      key: 'contextLengths' as const,
      label: '上下文长度',
      options: filterOptions.contextLengths,
    },
  ];

  const handleFilterToggle = (category: keyof FilterState, value: string) => {
    if (value === '') {
      onFilterChange({
        ...filters,
        [category]: [],
      });
      return;
    }

    const currentValues = filters[category];
    const newValues = currentValues.includes(value)
      ? currentValues.filter((item) => item !== value)
      : [...currentValues, value];

    onFilterChange({
      ...filters,
      [category]: newValues,
    });
  };

  return (
    <div style={{ backgroundColor: '#ffffff', padding: '16px 0 24px' }}>
      <div style={{ maxWidth: '1200px', margin: '0 auto', padding: '0 16px' }}>
        <div
          style={{
            backgroundColor: '#ffffff',
            border: '1px solid #e2e8f0',
            borderRadius: '8px',
            padding: '16px',
          }}
        >
          {errorMessage ? (
            <div
              style={{
                marginBottom: '12px',
                padding: '10px 12px',
                borderRadius: '6px',
                backgroundColor: '#fef2f2',
                color: '#b91c1c',
                fontSize: '13px',
              }}
            >
              {errorMessage}
            </div>
          ) : null}

          {filterConfig.map((section, index) => (
            <div
              key={section.key}
              style={{
                display: 'flex',
                alignItems: 'center',
                gap: '16px',
                marginBottom: index !== filterConfig.length - 1 ? '12px' : 0,
                paddingBottom: index !== filterConfig.length - 1 ? '12px' : 0,
                borderBottom:
                  index !== filterConfig.length - 1 ? '1px solid #f1f5f9' : 'none',
              }}
            >
              <div style={{ flexShrink: 0, width: '80px' }}>
                <span style={{ fontSize: '14px', color: '#64748b' }}>{section.label}</span>
              </div>
              <div style={{ flex: 1, display: 'flex', flexWrap: 'wrap', gap: '8px' }}>
                {[{ value: '', label: '不限' }, ...section.options].map((option) => {
                  const isActive =
                    option.value === ''
                      ? filters[section.key].length === 0
                      : filters[section.key].includes(option.value);

                  return (
                    <button
                      key={`${section.key}-${option.value || 'all'}`}
                      onClick={() => handleFilterToggle(section.key, option.value)}
                      disabled={isLoading && option.value !== ''}
                      style={{
                        padding: '6px 12px',
                        fontSize: '14px',
                        borderRadius: '6px',
                        cursor: isLoading && option.value !== '' ? 'not-allowed' : 'pointer',
                        transition: 'all 0.2s ease',
                        opacity: isLoading && option.value !== '' ? 0.6 : 1,
                        ...(isActive
                          ? {
                              backgroundColor: '#eff6ff',
                              color: '#2563eb',
                              border: '1px solid #2563eb',
                            }
                          : {
                              backgroundColor: 'transparent',
                              color: '#475569',
                              border: '1px solid transparent',
                            }),
                      }}
                      onMouseEnter={(event) => {
                        if (!isActive && !(isLoading && option.value !== '')) {
                          event.currentTarget.style.backgroundColor = '#f1f5f9';
                        }
                      }}
                      onMouseLeave={(event) => {
                        if (!isActive) {
                          event.currentTarget.style.backgroundColor = 'transparent';
                        }
                      }}
                    >
                      {option.label}
                    </button>
                  );
                })}
                {section.options.length === 0 && isLoading ? (
                  <span style={{ fontSize: '14px', color: '#94a3b8' }}>加载中...</span>
                ) : null}
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
