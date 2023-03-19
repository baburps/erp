package com.software.erp.view.greyfabric.model

import androidx.room.Embedded
import androidx.room.Relation
import com.software.erp.view.knitting.model.FabricDia
import com.software.erp.view.knitting.model.FabricStructurePO

data class GreyFabricStructureWrapper(
    @Embedded val fabricStructurePO: FabricStructurePO ,
    @Relation(
        parentColumn = "id" ,
        entityColumn = "fabricStructureId"
    )
    val fabricDiaList: MutableList<FabricDia>?
)
