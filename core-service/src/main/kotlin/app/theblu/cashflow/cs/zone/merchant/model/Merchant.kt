package app.theblu.cashflow.cs.zone.merchant.model

data class Merchant(
    val merchantId: String,
    val preferredName: String?,
    val suggestedNames: Map<String, Long>,
    val preferredCategory: String?,
    val suggestedCategories: Map<String, Long> // CategoryID, Votes
) {
    companion object {
        fun fromFid(accountId: String): Merchant {
            return Merchant(accountId, null, mutableMapOf(), null, mutableMapOf());
        }
    }
}