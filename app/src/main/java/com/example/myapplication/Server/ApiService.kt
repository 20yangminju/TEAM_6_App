import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// 데이터 모델 (로그인 및 회원가입)
data class LoginRequest(val email: String, val password: String)
data class RegisterRequest(val nickname: String, val email: String, val password: String)
data class TempResponse(val device_number: String, val module_number: Int,
                        val module_temp: Float, val created_at: String)

data class TempRequest(val device_number: String, val module_number: Int)

data class CellRequest(val device_number: String, val ten_num: Int)
data class CellResponse(val cell_00: Int, val cell_01: Int, val cell_02: Int, val cell_03: Int, val cell_04: Int,
                        val cell_05: Int, val cell_06: Int, val cell_07: Int, val cell_08: Int, val cell_09: Int,)

data class chargeRequest(val longitude: Float, val latitude: Float)
data class chargeResponse(val name: String, val address: String, val latitude: Float, val longitude: Float)



interface ApiService {
    @POST("cars/batteryTemp/app")
    suspend fun temperature(@Body tempRequest: TempRequest): TempResponse

    @POST("cars/cellVoltage/app")
    suspend fun cellvoltage(@Body CellRequest: CellRequest): CellResponse

    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Call<Void>  // 서버로 로그인 요청

    @POST("register")
    fun register(@Body registerRequest: RegisterRequest): Call<Void>  // 회원가입 요청

    @POST("find-charger")
    suspend fun find_charger(@Body chargeRequest: chargeRequest): chargeResponse
}



