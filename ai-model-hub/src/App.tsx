import { useDeferredValue, useEffect, useState } from 'react';
import { PAGE_SIZE, fetchFilterOptions, fetchModels } from './api/modelHub';
import { Navbar } from './components/Navbar';
import { HeroSection } from './components/HeroSection';
import { ActionButtons } from './components/ActionButtons';
import { FilterBar } from './components/FilterBar';
import { ModelList } from './components/ModelList';
import type { FilterOptions, FilterState, Model, SortOption } from './types';

const initialFilters: FilterState = {
  providers: [],
  categories: [],
  capabilities: [],
  contextLengths: [],
};

const emptyFilterOptions: FilterOptions = {
  providers: [],
  categories: [],
  capabilities: [],
  contextLengths: [],
};

function App() {
  const [searchQuery, setSearchQuery] = useState('');
  const deferredSearchQuery = useDeferredValue(searchQuery);
  const [filters, setFilters] = useState<FilterState>(initialFilters);
  const [sortBy, setSortBy] = useState<SortOption>('publishedAt');
  const [models, setModels] = useState<Model[]>([]);
  const [filterOptions, setFilterOptions] = useState<FilterOptions>(emptyFilterOptions);
  const [page, setPage] = useState(0);
  const [totalModels, setTotalModels] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [isFiltersLoading, setIsFiltersLoading] = useState(true);
  const [isModelsLoading, setIsModelsLoading] = useState(true);
  const [filtersError, setFiltersError] = useState<string | null>(null);
  const [modelsError, setModelsError] = useState<string | null>(null);

  useEffect(() => {
    const abortController = new AbortController();

    setIsFiltersLoading(true);
    setFiltersError(null);

    fetchFilterOptions(abortController.signal)
      .then((options) => {
        setFilterOptions(options);
      })
      .catch((error: unknown) => {
        if (isAbortError(error)) {
          return;
        }

        setFiltersError('筛选项加载失败，请检查后端接口是否正常启动。');
      })
      .finally(() => {
        setIsFiltersLoading(false);
      });

    return () => {
      abortController.abort();
    };
  }, []);

  useEffect(() => {
    const abortController = new AbortController();

    setIsModelsLoading(true);
    setModelsError(null);

    fetchModels({
      filters,
      search: deferredSearchQuery,
      sortBy,
      page,
      size: PAGE_SIZE,
      signal: abortController.signal,
    })
      .then((response) => {
        setModels(response.content);
        setTotalModels(response.totalElements);
        setTotalPages(response.totalPages);
      })
      .catch((error: unknown) => {
        if (isAbortError(error)) {
          return;
        }

        setModels([]);
        setTotalModels(0);
        setTotalPages(0);
        setModelsError('模型列表加载失败，请检查后端接口或数据库连接。');
      })
      .finally(() => {
        setIsModelsLoading(false);
      });

    return () => {
      abortController.abort();
    };
  }, [deferredSearchQuery, filters, page, sortBy]);

  const handleSearchChange = (value: string) => {
    setSearchQuery(value);
    setPage(0);
  };

  const handleFilterChange = (value: FilterState) => {
    setFilters(value);
    setPage(0);
  };

  const handleSortChange = (value: SortOption) => {
    setSortBy(value);
    setPage(0);
  };

  return (
    <div className="min-h-screen bg-gray-50">
      <Navbar searchQuery={searchQuery} onSearchChange={handleSearchChange} />
      <HeroSection searchQuery={searchQuery} onSearchChange={handleSearchChange} />
      <ActionButtons />
      <FilterBar
        filters={filters}
        filterOptions={filterOptions}
        isLoading={isFiltersLoading}
        errorMessage={filtersError}
        onFilterChange={handleFilterChange}
      />
      <ModelList
        models={models}
        totalModels={totalModels}
        sortBy={sortBy}
        isLoading={isModelsLoading}
        errorMessage={modelsError}
        page={page}
        totalPages={totalPages}
        onSortChange={handleSortChange}
        onPageChange={setPage}
      />
    </div>
  );
}

function isAbortError(error: unknown): boolean {
  return error instanceof DOMException && error.name === 'AbortError';
}

export default App;
