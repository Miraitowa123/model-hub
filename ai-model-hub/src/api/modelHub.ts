import type {
  ApiResponse,
  CapabilityDto,
  CategoryDto,
  FilterOptions,
  FilterState,
  Model,
  ModelDto,
  PagedResponse,
  ProviderDto,
  SortOption,
} from '../types';

const API_BASE = '/api/v1';

export const PAGE_SIZE = 12;

const contextLengthOptions = [
  { value: '0-16K', label: '0-16K' },
  { value: '16K-32K', label: '16K-32K' },
  { value: '32K-128K', label: '32K-128K' },
  { value: '128K-500K', label: '128K-500K' },
  { value: '500K-5000K', label: '500K+' },
] satisfies FilterOptions['contextLengths'];

interface FetchModelsParams {
  filters: FilterState;
  search: string;
  sortBy: SortOption;
  page: number;
  size?: number;
  signal?: AbortSignal;
}

export async function fetchFilterOptions(signal?: AbortSignal): Promise<FilterOptions> {
  const [providers, categories, capabilities] = await Promise.all([
    request<ProviderDto[]>('/providers', undefined, signal),
    request<CategoryDto[]>('/categories', undefined, signal),
    request<CapabilityDto[]>('/capabilities', undefined, signal),
  ]);

  return {
    providers: providers.map((provider) => ({
      value: provider.name,
      label: provider.displayName,
    })),
    categories: categories.map((category) => ({
      value: category.name,
      label: category.displayName,
    })),
    capabilities: capabilities.map((capability) => ({
      value: capability.name,
      label: capability.displayName,
    })),
    contextLengths: contextLengthOptions,
  };
}

export async function fetchModels({
  filters,
  search,
  sortBy,
  page,
  size = PAGE_SIZE,
  signal,
}: FetchModelsParams): Promise<PagedResponse<Model>> {
  const params = new URLSearchParams();

  appendArrayParams(params, 'providers', filters.providers);
  appendArrayParams(params, 'categories', filters.categories);
  appendArrayParams(params, 'capabilities', filters.capabilities);
  appendArrayParams(params, 'contextLengths', filters.contextLengths);

  if (search.trim()) {
    params.set('search', search.trim());
  }

  const sortConfig = mapSortOption(sortBy);
  params.set('sort', sortConfig.sort);
  params.set('order', sortConfig.order);
  params.set('page', String(page));
  params.set('size', String(size));

  const response = await request<PagedResponse<ModelDto>>('/models', params, signal);

  return {
    ...response,
    content: response.content.map(mapModelDtoToModel),
  };
}

async function request<T>(
  path: string,
  params?: URLSearchParams,
  signal?: AbortSignal
): Promise<T> {
  const queryString = params?.toString();
  const url = queryString ? `${API_BASE}${path}?${queryString}` : `${API_BASE}${path}`;

  const response = await fetch(url, {
    method: 'GET',
    headers: {
      Accept: 'application/json',
    },
    signal,
  });

  if (!response.ok) {
    throw new Error(`Request failed with status ${response.status}`);
  }

  const payload = (await response.json()) as ApiResponse<T>;

  if (!payload.success) {
    throw new Error(payload.message || 'Request failed');
  }

  return payload.data;
}

function appendArrayParams(
  params: URLSearchParams,
  key: keyof FilterState,
  values: string[]
) {
  values.forEach((value) => params.append(key, value));
}

function mapSortOption(sortBy: SortOption) {
  switch (sortBy) {
    case 'inputPrice':
      return { sort: 'inputPrice', order: 'asc' as const };
    case 'outputPrice':
      return { sort: 'outputPrice', order: 'asc' as const };
    case 'publishedAt':
    default:
      return { sort: 'publishedAt', order: 'desc' as const };
  }
}

function mapModelDtoToModel(model: ModelDto): Model {
  const categories = normalizeStringArray(model.categories);
  const capabilities = normalizeStringArray(model.capabilities);
  const providerName = model.provider?.displayName || model.provider?.name || '未知厂商';

  return {
    id: String(model.id),
    modelId: model.modelId,
    name: model.name,
    provider: providerName,
    category: categories[0] || '未分类',
    capabilities,
    contextLength: formatContextLength(model.contextLength),
    description: model.description,
    icon: model.provider?.logoUrl || model.provider?.name || providerName,
    updatedAt: parseApiDate(model.updatedAt),
    inputPrice: model.inputPrice ?? undefined,
    outputPrice: model.outputPrice ?? undefined,
    tags: capabilities.slice(0, 3),
  };
}

function normalizeStringArray(values: string[] | undefined): string[] {
  return Array.isArray(values) ? values : [];
}

function formatContextLength(value: number | null | undefined): string {
  if (!value || value <= 0) {
    return 'N/A';
  }

  return `${Math.round(value / 1000)}K`;
}

function parseApiDate(value: string | null | undefined): Date {
  if (!value) {
    return new Date(0);
  }

  const normalizedValue = value.includes('T') ? value : value.replace(' ', 'T');
  const parsedDate = new Date(normalizedValue);

  return Number.isNaN(parsedDate.getTime()) ? new Date(0) : parsedDate;
}
