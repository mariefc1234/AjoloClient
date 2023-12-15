import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ShopCard (
    @Expose
    @SerializedName("cost")
    var cost: Int
)