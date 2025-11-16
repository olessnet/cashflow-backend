package app.theblu.cashflow.cs.zone.merchant.model

data class MerchantNameVote(
    val merchantId: String,
    val userId: String,
    val name: String,
)

data class MerchantCategoryVote(
    val merchantId: String,
    val userId: String,
    val categoryId: Int,
)