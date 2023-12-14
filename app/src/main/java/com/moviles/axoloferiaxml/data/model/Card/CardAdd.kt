import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CardAdd (
    @Expose
    @SerializedName("card_number")
    var cardNumber: String,

    @Expose
    @SerializedName("card_holder")
    var cardHolder: String,

    @Expose
    @SerializedName("card_expiration_month")
    var cardExpirationMonth: Int,

    @Expose
    @SerializedName("card_expiration_year")
    var cardExpirationYear: Int
)