export interface PageResponse<T> {
  items: T[];
  pageNumber: number;
  pageSize: number;
  totalSize: number;
}
