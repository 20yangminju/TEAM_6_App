import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// 데이터 모델 (로그인 및 회원가입)
data class LoginRequest(val email: String, val password: String)
data class RegisterRequest(val nickname: String, val email: String, val password: String)

interface ApiService {

    @POST("/login")
    fun login(@Body loginRequest: LoginRequest): Call<Void>  // 서버로 로그인 요청

    @POST("/register")
    fun register(@Body registerRequest: RegisterRequest): Call<Void>  // 회원가입 요청
}
