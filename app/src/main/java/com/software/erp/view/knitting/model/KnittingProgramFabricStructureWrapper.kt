package com.software.erp.view.knitting.model

import androidx.room.Embedded
import androidx.room.Relation
import com.software.erp.view.knitting.FabricDia
import com.software.erp.view.knitting.FabricStructurePO

data class KnittingProgramFabricStructureWrapper(
    @Embedded val fabricStructurePO: FabricStructurePO ,
    @Relation(
        parentColumn = "id" ,
        entityColumn = "fabricStructureId"
    )
    val fabricDiaList: List<FabricDia>?
)
