import com.google.gson.GsonBuilder
import com.moviles.axoloferiaxml.data.model.Card
import com.moviles.axoloferiaxml.data.model.Shop
import com.moviles.axoloferiaxml.data.network.user_employee.AppConstantes
import com.moviles.axoloferiaxml.data.network.user_employee.WebService
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

object AppConstants {
    const val BASE_URL = "https://ajoloback-production.up.railway.app/api/"
}

interface WebServiceCard{
    @POST("payment/update")
    suspend fun updateCard(
        @Header("authtoken") token: String?,
        @Body card: CardAdd
    ): Response<Card>

    @POST("shoppings/card")
    suspend fun CoinsCardBuy(
        @Header("authtoken") token: String?,
        @Body shopCard: ShopCard
    ): Response<Shop>
}

object RetrofitClientCard {
    val webServiceCard: WebServiceCard by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstantes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebServiceCard::class.java)
    }
}