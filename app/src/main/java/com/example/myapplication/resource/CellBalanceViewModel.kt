import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.myapplication.network.RetrofitInstance

class CellBalanceViewModel : ViewModel() {
    private val _cells = MutableStateFlow<List<CellData>>(emptyList())
    val cells: StateFlow<List<CellData>> get() = _cells

    fun fetchCellData(deviceNumber: String, tenNumRange: IntRange) {
        viewModelScope.launch {
            try {
                val cellDataList = mutableListOf<CellData>()
                for (tenNum in tenNumRange) {
                    val request = CellRequest(device_number = deviceNumber, ten_num = tenNum)
                    val response = RetrofitInstance.api.cellvoltage(request)
                    val voltageData = response.toCellData(tenNum)
                    cellDataList.addAll(voltageData)
                }
                _cells.value = cellDataList.take(98)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

// Response 확장 함수: 서버 데이터를 CellData로 변환
fun CellResponse.toCellData(tenNum: Int): List<CellData> {
    val cells = listOf(
        CellData(index = tenNum * 10 + 1, voltageDeviation = cell_00),
        CellData(index = tenNum * 10 + 2, voltageDeviation = cell_01),
        CellData(index = tenNum * 10 + 3, voltageDeviation = cell_02),
        CellData(index = tenNum * 10 + 4, voltageDeviation = cell_03),
        CellData(index = tenNum * 10 + 5, voltageDeviation = cell_04),
        CellData(index = tenNum * 10 + 6, voltageDeviation = cell_05),
        CellData(index = tenNum * 10 + 7, voltageDeviation = cell_06),
        CellData(index = tenNum * 10 + 8, voltageDeviation = cell_07),
        CellData(index = tenNum * 10 + 9, voltageDeviation = cell_08),
        CellData(index = tenNum * 10 + 10, voltageDeviation = cell_09)
    )
    return cells
}