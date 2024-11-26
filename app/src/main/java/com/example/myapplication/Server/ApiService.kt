import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// 데이터 모델 (로그인 및 회원가입)
data class LoginRequest(val email: String, val password: String)
data class RegisterRequest(val nickname: String, val email: String, val password: String)
data class TempResponse(val car_device_number: String, val module_number: Int,
                        val module_temp: Float, val created_at: String)

data class TempRequest(val car_device_number: String, val module_number: Int)


data class chargeRequest(val longitude: Float, val latitude: Float)
data class chargeResponse(val name: String, val address: String, val latitude: Float, val longitude: Float)

interface ApiService {
    @POST("/cars/batteryTemp/app")
    suspend fun temperature(@Body tempRequest: TempRequest): TempResponse

    @POST("/login")
    fun login(@Body loginRequest: LoginRequest): Call<Void>  // 서버로 로그인 요청

    @POST("/register")
    fun register(@Body registerRequest: RegisterRequest): Call<Void>  // 회원가입 요청

    @POST("/find-charger")
    suspend fun find_charger(@Body chargeRequest: chargeRequest): chargeResponse
}



