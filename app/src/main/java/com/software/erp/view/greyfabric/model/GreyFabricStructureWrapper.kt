package com.software.erp.view.greyfabric.model

import androidx.room.Embedded
import androidx.room.Relation
import com.software.erp.view.greyfabric.GreyFabricDia
import com.software.erp.view.greyfabric.GreyFabricStructurePO

data class GreyFabricStructureWrapper(
    @Embedded val fabricStructurePO: GreyFabricStructurePO ,
    @Relation(
        parentColumn = "id" ,
        entityColumn = "fabricStructureId"
    )
    val fabricDiaList: MutableList<GreyFabricDia>?
)
