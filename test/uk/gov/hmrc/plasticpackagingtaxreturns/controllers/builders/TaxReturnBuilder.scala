/*
 * Copyright 2021 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.plasticpackagingtaxreturns.controllers.builders

import uk.gov.hmrc.plasticpackagingtaxreturns.models.{
  ExportedPlasticWeight,
  HumanMedicinesPlasticWeight,
  ImportedPlasticWeight,
  ManufacturedPlasticWeight,
  MetaData,
  TaxReturn
}
import uk.gov.hmrc.plasticpackagingtaxreturns.models.{
  ConvertedPackagingCredit,
  ImportedPlasticWeight,
  ManufacturedPlasticWeight,
  TaxReturn
}

//noinspection ScalaStyle
trait TaxReturnBuilder {

  private type TaxReturnModifier = TaxReturn => TaxReturn

  def aTaxReturn(modifiers: TaxReturnModifier*): TaxReturn =
    modifiers.foldLeft(modelWithDefaults)((current, modifier) => modifier(current))

  private def modelWithDefaults: TaxReturn =
    TaxReturn(id = "id")

  def withId(id: String): TaxReturnModifier = _.copy(id = id)

  def withManufacturedPlasticWeight(totalKg: Long, totalKgBelowThreshold: Long): TaxReturnModifier =
    _.copy(manufacturedPlasticWeight =
      Some(ManufacturedPlasticWeight(totalKg = totalKg, totalKgBelowThreshold = totalKgBelowThreshold))
    )

  def withImportedPlasticWeight(totalKg: Long, totalKgBelowThreshold: Long): TaxReturnModifier =
    _.copy(importedPlasticWeight =
      Some(ImportedPlasticWeight(totalKg = totalKg, totalKgBelowThreshold = totalKgBelowThreshold))
    )

  def withHumanMedicinesPlasticWeight(totalKg: Long): TaxReturnModifier =
    _.copy(humanMedicinesPlasticWeight =
      Some(HumanMedicinesPlasticWeight(totalKg = totalKg))
    )

  def withDirectExportDetails(totalKg: Long, totalValueForCreditInPence: Long): TaxReturnModifier =
    _.copy(exportedPlasticWeight =
      Some(ExportedPlasticWeight(totalKg = totalKg, totalValueForCreditInPence = totalValueForCreditInPence))
    )

  def withConvertedPlasticPackagingCredit(totalPence: Long): TaxReturnModifier =
    _.copy(convertedPackagingCredit =
      Some(ConvertedPackagingCredit(totalPence))
    )

  def withMetadata(returnCompleted: Boolean): TaxReturnModifier =
    _.copy(metaData = MetaData(returnCompleted = returnCompleted))

}
