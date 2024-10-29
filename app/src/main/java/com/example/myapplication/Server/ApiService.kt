import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

// 데이터 모델 (로그인 및 회원가입)
data class LoginRequest(val email: String, val password: String)
data class RegisterRequest(val nickname: String, val email: String, val password: String)
data class BatteryRequest(
    val auxiliaryBatteryVoltage: Int, val batteryPower: Float,
    val stateOfChargeDisplay: Float, val hvChargingStatus: Boolean,
    val batteryCurrent: Float, val createdAt: String
    )

interface ApiService {

    @POST("/login")
    fun login(@Body loginRequest: LoginRequest): Call<Void>  // 서버로 로그인 요청

    @POST("/register")
    fun register(@Body registerRequest: RegisterRequest): Call<Void>  // 회원가입 요청

    @GET("/cars/batteryStatus")
    suspend fun batteryInfo():BatteryRequest
}
