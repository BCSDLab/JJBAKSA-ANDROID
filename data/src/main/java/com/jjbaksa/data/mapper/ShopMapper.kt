package com.jjbaksa.data.mapper

import com.jjbaksa.data.model.shop.ShopsResp
import com.jjbaksa.domain.base.ErrorType
import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.resp.shop.ShopsResult
import com.jjbaksa.domain.resp.shop.ShopsResultContent
import com.jjbaksa.domain.resp.shop.ShopsResultPageable
import com.jjbaksa.domain.resp.shop.ShopsResultSort

object ShopMapper {
    fun ShopsResp.mapShopToResult(): RespResult<ShopsResult> {
        return if (this.code == 0) {
            val shopsResultContentList = mutableListOf<ShopsResultContent>()

            for (i in this.content) {
                shopsResultContentList.add(
                    ShopsResultContent(
                        i.shopId,
                        i.placeId,
                        i.placeName,
                        i.address,
                        i.x,
                        i.y,
                        i.dist,
                        i.score
                    )
                )
            }

            val shopsResultSortInPageable = ShopsResultSort(
                this.pageable.sort.empty, this.pageable.sort.sorted, this.pageable.sort.unsorted
            )

            val shopsResultPageable = ShopsResultPageable(
                shopsResultSortInPageable,
                this.pageable.offset,
                this.pageable.pageNumber,
                this.pageable.pageSize,
                this.pageable.paged,
                this.pageable.unpaged
            )

            val shopsResultSortInRoot = ShopsResultSort(
                this.sort.empty, this.sort.sorted, this.sort.unsorted
            )

            val shopsResult = ShopsResult(
                shopsResultContentList,
                shopsResultPageable,
                this.last,
                this.totalPages,
                this.totalElements,
                this.size,
                this.number,
                shopsResultSortInRoot,
                this.first,
                this.numberOfElements,
                this.empty
            )

            RespResult.Success(shopsResult)
        } else {
            RespResult.Error(ErrorType(this.errorMessage, this.code))
        }
    }
}
