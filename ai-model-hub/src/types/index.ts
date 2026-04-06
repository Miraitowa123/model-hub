export interface Model {
  id: string;
  modelId: string;
  name: string;
  provider: string;
  category: string;
  capabilities: string[];
  contextLength: string;
  description: string;
  icon: string;
  updatedAt: Date;
  inputPrice?: number;
  outputPrice?: number;
  tags: string[];
}

export interface FilterState {
  providers: string[];
  categories: string[];
  capabilities: string[];
  contextLengths: string[];
}

export type SortOption = 'publishedAt' | 'inputPrice' | 'outputPrice';

export interface FilterOptionItem {
  value: string;
  label: string;
}

export interface FilterOptions {
  providers: FilterOptionItem[];
  categories: FilterOptionItem[];
  capabilities: FilterOptionItem[];
  contextLengths: FilterOptionItem[];
}

export interface ApiResponse<T> {
  success: boolean;
  message?: string;
  data: T;
  timestamp: string;
  code: number;
}

export interface PagedResponse<T> {
  content: T[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
  first: boolean;
  last: boolean;
  hasNext: boolean;
  hasPrevious: boolean;
}

export interface ProviderDto {
  id: number;
  name: string;
  displayName: string;
  description?: string;
  logoUrl?: string;
  websiteUrl?: string;
  modelCount?: number;
}

export interface CategoryDto {
  id: number;
  name: string;
  displayName: string;
  description?: string;
  icon?: string;
  modelCount?: number;
}

export interface CapabilityDto {
  id: number;
  name: string;
  displayName: string;
  description?: string;
  icon?: string;
  modelCount?: number;
}

export interface ModelDto {
  id: number;
  modelId: string;
  name: string;
  description: string;
  contextLength: number;
  provider: ProviderDto;
  categories: string[];
  capabilities: string[];
  inputPrice?: number | null;
  outputPrice?: number | null;
  priceUnit?: string | null;
  releaseDate?: string | null;
  popularityScore?: number | null;
  updatedAt?: string | null;
}
