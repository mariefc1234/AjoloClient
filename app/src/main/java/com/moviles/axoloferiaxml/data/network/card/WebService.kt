import com.moviles.axoloferiaxml.data.model.Card
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST

object AppConstants {
    const val BASE_URL = "https://ajoloback-production.up.railway.app/api/"
}

interface WebService{
    @POST("payment/update")
    suspend fun updateCard(
        @Header("authtoken") token: String?
    ): Response<Card>
}