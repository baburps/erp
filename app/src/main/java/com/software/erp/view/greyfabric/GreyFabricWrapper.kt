package com.software.erp.view.greyfabric

import androidx.room.Embedded
import androidx.room.Relation
import com.software.erp.view.knitting.KnittingProgramPO

data class GreyFabricWrapper(
    @Embedded val knittingProgramPO: KnittingProgramPO ,
    @Relation(
        parentColumn = "srkwDCNo" ,
        entityColumn = "knittingProgramSRKWDCNo"
    )
    val greyFabricList: List<GreyFabricDetailsPO>
)
