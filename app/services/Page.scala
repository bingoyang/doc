package services

/**
 * @author yangliubing
 */
class Page[T](
  datas: Option[List[T]] = None,
  total: Int,
  pageNo: Int,
  pageSize: Int,
  totalPage: Int)