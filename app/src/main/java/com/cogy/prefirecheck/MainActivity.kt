package com.cogy.prefirecheck

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import org.json.JSONArray
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    // ---------- ตั้งค่า ----------
    private val JMS_API_URL = "https://jmsgw.jtexpress.co.th/esscustomer/firstPackage/page"
    private val DEFAULT_JMS_TOKEN = "1372b668f9cf43d6b68bf05abda40eb6"

    private val CHUTE_MAP: Map<String, String> = mapOf(
    "1" to "A1", "2" to "A1", "3" to "A1", "4" to "A1", "5" to "A1", "6" to "A1",
    "7" to "H1", "8" to "H1", "9" to "H1", "10" to "H1", "11" to "H1", "12" to "H1",
    "13" to "V1", "14" to "V1", "15" to "V2", "16" to "V2", "17" to "Z1", "18" to "Z1",
    "19" to "B1", "20" to "B1", "21" to "B1", "22" to "B2", "23" to "B2", "24" to "B2",
    "25" to "P1", "26" to "P1", "27" to "P2", "28" to "P2", "29" to "P3", "30" to "P3",
    "31" to "S1", "32" to "S1", "33" to "S3", "34" to "S3", "35" to "L1", "36" to "L1",
    "37" to "D1", "38" to "D1", "39" to "J1", "40" to "J1", "41" to "F51-01", "42" to "F51-02",
    "43" to "F51-03", "44" to "N51-05", "45" to "F51-06", "46" to "F51-07", "47" to "F51-07", "48" to "F51-08",
    "49" to "F51-09", "50" to "F51-10", "51" to "F51-11", "52" to "F51-12", "53" to "F51-13", "54" to "F51-14",
    "55" to "F51-16", "56" to "F51-21", "57" to "N51-26", "58" to "F51-27", "59" to "F51-31", "60" to "N51-36",
    "61" to "F51-41", "62" to "F51-46", "63" to "G43-01", "64" to "G43-02", "65" to "G43-06", "66" to "G43-11",
    "67" to "G43-16", "68" to "G43-21", "69" to "G43-26", "70" to "G43-31", "71" to "G43-36", "72" to "G44-01",
    "73" to "G44-01", "74" to "G44-02", "75" to "G44-03", "76" to "G44-06", "77" to "G44-11", "78" to "G44-16",
    "79" to "G44-21", "80" to "G44-26", "81" to "G44-31", "82" to "G44-36", "83" to "G44-41", "84" to "G44-46",
    "85" to "G44-51", "86" to "G44-56", "87" to "G44-61", "88" to "G44-66", "90" to "G42-01", "91" to "G42-01",
    "92" to "G42-02", "93" to "G42-02", "94" to "G42-03", "95" to "G42-04", "96" to "G42-05", "97" to "N42-11",
    "98" to "N42-16", "99" to "N42-17", "100" to "N42-21", "101" to "N42-22", "102" to "N42-26", "103" to "N42-31",
    "104" to "N42-36", "105" to "N42-41", "106" to "G52-01", "107" to "G52-01", "108" to "N52-02", "109" to "N52-02",
    "110" to "N52-03", "111" to "G52-06", "112" to "G52-06", "113" to "G52-11", "114" to "G52-32", "115" to "N52-16",
    "116" to "N52-21", "117" to "N52-26", "118" to "G52-31", "119" to "N52-36", "120" to "N52-41", "121" to "T53-01",
    "122" to "G53-02", "123" to "G53-02", "124" to "T53-03", "125" to "T53-03", "126" to "T53-04", "127" to "T53-05",
    "128" to "T53-05", "129" to "T53-06", "130" to "N53-09", "131" to "T53-10", "132" to "T53-10", "133" to "N53-11",
    "134" to "N53-12", "136" to "N53-14", "137" to "F53-15", "138" to "N53-16", "139" to "N53-21", "140" to "N53-26",
    "141" to "F53-31", "142" to "N53-36", "143" to "N53-41", "144" to "N53-46", "145" to "G54-01", "146" to "N54-04",
    "147" to "N54-05", "148" to "G54-07", "149" to "N54-08", "150" to "G54-09", "151" to "N54-16", "152" to "N54-31",
    "153" to "N54-46", "154" to "F55-01", "155" to "F55-02", "156" to "F55-03", "157" to "F55-04", "158" to "F55-05",
    "159" to "F55-06", "160" to "F55-07", "161" to "F55-11", "162" to "F55-16", "163" to "F55-16", "164" to "F55-21",
    "165" to "F55-26", "166" to "F55-32", "167" to "F55-36", "168" to "F55-41", "169" to "F55-46", "170" to "F55-51",
    "171" to "F55-56", "172" to "F55-56", "173" to "F55-57", "177" to "G54-01", "178" to "G54-01", "179" to "N54-04",
    "180" to "N54-05", "181" to "G54-07", "182" to "N54-08", "183" to "G54-09", "184" to "N54-16", "185" to "N54-31",
    "186" to "N54-46", "187" to "F55-01", "188" to "F55-02", "189" to "F55-03", "190" to "F55-04", "191" to "F55-04",
    "192" to "F55-05", "193" to "F55-06", "194" to "F55-07", "195" to "F55-11", "196" to "F55-16", "197" to "F55-16",
    "198" to "F55-21", "199" to "F55-21", "200" to "F55-26", "201" to "F55-32", "202" to "F55-32", "203" to "F55-36",
    "204" to "F55-36", "205" to "F55-41", "206" to "F55-41", "207" to "F55-46", "208" to "F55-51", "209" to "F55-56",
    "210" to "F55-56", "211" to "F55-57", "212" to "F55-57", "213" to "T53-01", "214" to "T53-01", "215" to "G53-02",
    "216" to "G53-02", "217" to "T53-03", "218" to "T53-03", "219" to "T53-04", "220" to "T53-04", "221" to "T53-05",
    "222" to "T53-05", "223" to "T53-06", "224" to "T53-06", "225" to "N53-09", "226" to "T53-10", "227" to "T53-10",
    "228" to "N53-11", "229" to "N53-12", "231" to "N53-14", "232" to "F53-15", "233" to "N53-16", "234" to "N53-16",
    "235" to "N53-21", "236" to "N53-26", "237" to "F53-31", "238" to "N53-36", "239" to "N53-41", "240" to "N53-41",
    "241" to "N53-46", "242" to "F51-08", "243" to "F51-08", "244" to "F51-10", "245" to "F51-11", "246" to "F51-12",
    "247" to "F51-13", "248" to "F51-14", "249" to "F51-16", "250" to "F51-21", "251" to "F51-31", "252" to "F51-46",
    "253" to "N53-09", "254" to "N53-11", "255" to "F53-15", "256" to "N53-21", "257" to "N53-26", "258" to "N53-46",
    "259" to "F55-41", "260" to "F55-56", "261" to "F55-57", "266" to "G43-01", "267" to "G43-02", "268" to "G43-02",
    "269" to "G43-06", "270" to "G43-11", "271" to "G43-16", "272" to "G43-21", "273" to "G43-26", "274" to "G43-31",
    "275" to "G43-36", "276" to "G43-36", "277" to "G44-01", "278" to "G44-01", "279" to "G44-02", "280" to "G44-03",
    "281" to "G44-06", "282" to "G44-11", "283" to "G44-16", "284" to "G44-21", "285" to "G44-26", "286" to "G44-31",
    "287" to "G44-36", "288" to "G44-41", "289" to "G44-46", "290" to "G44-51", "291" to "G44-56", "292" to "G44-61",
    "293" to "G44-66", "294" to "G42-01", "295" to "G42-01", "296" to "G42-02", "297" to "G42-03", "298" to "G42-04",
    "299" to "G42-05", "300" to "N42-11", "301" to "N42-16", "302" to "N42-17", "303" to "N42-21", "304" to "N42-22",
    "305" to "N42-26", "306" to "N42-31", "307" to "N42-36", "308" to "N42-41", "309" to "G52-01", "310" to "G52-01",
    "311" to "N52-02", "312" to "N52-02", "313" to "N52-03", "314" to "G52-06", "315" to "G52-06", "316" to "G52-11",
    "317" to "G52-11", "318" to "N52-16", "319" to "N52-21", "320" to "N52-26", "321" to "G52-31", "322" to "G52-32",
    "323" to "N52-36", "324" to "N52-41", "325" to "F51-01", "326" to "F51-02", "327" to "F51-03", "328" to "F51-03",
    "329" to "N51-05", "330" to "F51-06", "331" to "F51-06", "332" to "F51-07", "333" to "F51-07", "334" to "F51-08",
    "335" to "F51-08", "336" to "F51-09", "337" to "F51-10", "338" to "F51-11", "339" to "F51-12", "340" to "F51-13",
    "341" to "F51-14", "342" to "F51-16", "343" to "F51-21", "344" to "N51-26", "345" to "N51-26", "346" to "F51-27",
    "347" to "F51-31", "348" to "N51-36", "349" to "F51-41", "350" to "F51-41", "351" to "F51-46"
)

    // ---------- UI ----------
    private lateinit var tokenInput: EditText
    private lateinit var timeoutInput: EditText
    private lateinit var chuteInput: EditText
    private lateinit var packageInput: EditText
    private lateinit var testBtn: Button
    private lateinit var bannerLayout: LinearLayout
    private lateinit var decisionText: TextView
    private lateinit var decisionDetail: TextView
    private lateinit var decisionLatency: TextView
    private lateinit var statTotal: TextView
    private lateinit var statPass: TextView
    private lateinit var statBlock: TextView
    private lateinit var statAvgLatency: TextView
    private lateinit var statMaxLatency: TextView
    private lateinit var statFitWindow: TextView
    private lateinit var logContainer: LinearLayout

    data class TestEntry(
        val chuteNo: String, val packageNumber: String, val expected: String,
        val actual: String, val decision: String, val reason: String,
        val latencyMs: Long, val time: String
    )

    private val testLog = mutableListOf<TestEntry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tokenInput = findViewById(R.id.tokenInput)
        timeoutInput = findViewById(R.id.timeoutInput)
        chuteInput = findViewById(R.id.chuteInput)
        packageInput = findViewById(R.id.packageInput)
        testBtn = findViewById(R.id.testBtn)
        bannerLayout = findViewById(R.id.bannerLayout)
        decisionText = findViewById(R.id.decisionText)
        decisionDetail = findViewById(R.id.decisionDetail)
        decisionLatency = findViewById(R.id.decisionLatency)
        statTotal = findViewById(R.id.statTotal)
        statPass = findViewById(R.id.statPass)
        statBlock = findViewById(R.id.statBlock)
        statAvgLatency = findViewById(R.id.statAvgLatency)
        statMaxLatency = findViewById(R.id.statMaxLatency)
        statFitWindow = findViewById(R.id.statFitWindow)
        logContainer = findViewById(R.id.logContainer)

        tokenInput.setText(DEFAULT_JMS_TOKEN)
        timeoutInput.setText("300")

        testBtn.setOnClickListener { runTestClicked() }
    }

    private fun runTestClicked() {
        val chuteNo = chuteInput.text.toString().trim()
        val packageNumber = packageInput.text.toString().trim()
        if (chuteNo.isEmpty() || packageNumber.isEmpty()) {
            Toast.makeText(this, "กรอกทั้งเลขช่องและเลขพัสดุก่อน", Toast.LENGTH_SHORT).show()
            return
        }
        testBtn.isEnabled = false
        testBtn.text = "⏳ กำลังเช็ค..."

        Thread {
            val entry = runTest(chuteNo, packageNumber)
            runOnUiThread {
                testLog.add(0, entry)
                renderBanner(entry)
                renderLog()
                renderStats()
                testBtn.isEnabled = true
                testBtn.text = "🔍 ทดสอบยิง"
            }
        }.start()
    }

    private fun runTest(chuteNo: String, packageNumber: String): TestEntry {
        val jmsToken = tokenInput.text.toString().trim().ifEmpty { DEFAULT_JMS_TOKEN }
        val expected = CHUTE_MAP[chuteNo]

        val t0 = System.currentTimeMillis()
        var actual: String? = null
        var errorMsg: String? = null
        try {
            actual = checkPackageAgainstJms(packageNumber, jmsToken)
        } catch (e: Exception) {
            errorMsg = e.message ?: e.toString()
        }
        val t1 = System.currentTimeMillis()
        val latencyMs = t1 - t0

        val decision: String
        val reason: String
        when {
            errorMsg != null -> {
                decision = "block"
                reason = "เช็คไม่สำเร็จ ($errorMsg) — ถือว่าห้ามยิงไว้ก่อนเพื่อความปลอดภัย"
            }
            expected == null -> {
                decision = "block"
                reason = "ไม่พบช่อง $chuteNo ใน CHUTE_MAP — ไม่รู้ว่าควรเป็นรหัสอะไร"
            }
            actual == null -> {
                decision = "block"
                reason = "ไม่พบเลขพัสดุนี้ใน JMS เลย"
            }
            actual != expected -> {
                decision = "block"
                reason = "ช่อง $chuteNo ควรเป็น $expected แต่ JMS บอกว่า $actual"
            }
            else -> {
                decision = "pass"
                reason = "ช่อง $chuteNo ตรงกับ $expected ตามที่ JMS ยืนยัน"
            }
        }

        val timeStr = SimpleDateFormat("HH:mm:ss", Locale("th", "TH")).format(Date())
        return TestEntry(chuteNo, packageNumber, expected ?: "-", actual ?: "-", decision, reason, latencyMs, timeStr)
    }

    private fun checkPackageAgainstJms(packageNumber: String, jmsToken: String): String? {
        val now = Calendar.getInstance()
        val past5Days = Calendar.getInstance()
        past5Days.add(Calendar.DAY_OF_MONTH, -5)

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val startTimeApi = sdf.format(past5Days.time) + " 00:00:00"
        val endTimeApi = sdf.format(now.time) + " 23:59:59"

        val body = JSONObject()
        body.put("current", 1)
        body.put("size", 20)
        body.put("startTime", startTimeApi)
        body.put("endTime", endTimeApi)
        body.put("packNetworkCode", "999008")
        body.put("packageNumbers", JSONArray(listOf(packageNumber)))
        body.put("countryId", "1")

        val url = URL(JMS_API_URL)
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "POST"
        conn.doOutput = true
        conn.connectTimeout = 8000
        conn.readTimeout = 8000
        conn.setRequestProperty("Accept", "application/json, text/plain, */*")
        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8")
        conn.setRequestProperty("authToken", jmsToken)
        conn.setRequestProperty("lang", "TH")
        conn.setRequestProperty("langType", "TH")
        conn.setRequestProperty("timezone", "GMT+0700")

        OutputStreamWriter(conn.outputStream, "UTF-8").use { it.write(body.toString()) }

        val responseCode = conn.responseCode
        if (responseCode !in 200..299) {
            throw Exception("JMS HTTP $responseCode")
        }

        val responseText = conn.inputStream.bufferedReader(Charsets.UTF_8).use { it.readText() }
        val json = JSONObject(responseText)
        val dataObj = json.optJSONObject("data")

        val records: JSONArray = when {
            dataObj?.optJSONArray("records") != null -> dataObj.getJSONArray("records")
            dataObj?.optJSONArray("list") != null -> dataObj.getJSONArray("list")
            json.optJSONArray("data") != null -> json.getJSONArray("data")
            else -> JSONArray()
        }

        for (i in 0 until records.length()) {
            val item = records.getJSONObject(i)
            val packNo = item.optString("packageNumber", item.optString("billCode", item.optString("waybillNo", "")))
            if (packNo.trim() == packageNumber.trim()) {
                val firstCode = item.optString("firstCode", "")
                val jmssss = item.optString("jmssss", "")
                return if (firstCode.isNotEmpty()) firstCode else if (jmssss.isNotEmpty()) jmssss else null
            }
        }
        return null
    }

    private fun renderBanner(entry: TestEntry) {
        bannerLayout.visibility = View.VISIBLE
        if (entry.decision == "pass") {
            bannerLayout.setBackgroundColor(0xFFE6F7EE.toInt())
            decisionText.setTextColor(0xFF0F9D58.toInt())
            decisionText.text = "✅ ยิงได้ (PASS)"
        } else {
            bannerLayout.setBackgroundColor(0xFFFDECEB.toInt())
            decisionText.setTextColor(0xFFE5484D.toInt())
            decisionText.text = "🛑 ห้ามยิง (BLOCK)"
        }
        decisionDetail.text = entry.reason
        decisionLatency.text = "ใช้เวลาเช็ค ${entry.latencyMs} ms"
    }

    private fun renderLog() {
        logContainer.removeAllViews()
        for (e in testLog) {
            val row = TextView(this)
            val icon = if (e.decision == "pass") "✅" else "🛑"
            row.text = "$icon [${e.time}] ช่อง ${e.chuteNo} | ควรเป็น ${e.expected} | JMS: ${e.actual} | ${e.packageNumber} | ${e.latencyMs}ms\n${e.reason}"
            row.setPadding(12, 12, 12, 12)
            row.textSize = 12.5f
            row.setBackgroundColor(if (e.decision == "pass") 0xFFE6F7EE.toInt() else 0xFFFDECEB.toInt())
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 0, 0, 8)
            row.layoutParams = params
            logContainer.addView(row)
        }
    }

    private fun renderStats() {
        val total = testLog.size
        val passCount = testLog.count { it.decision == "pass" }
        val blockCount = total - passCount
        val latencies = testLog.map { it.latencyMs }
        val avgLatency = if (latencies.isNotEmpty()) latencies.average().toLong() else 0
        val maxLatency = latencies.maxOrNull() ?: 0
        val windowMs = timeoutInput.text.toString().toLongOrNull() ?: 300

        statTotal.text = total.toString()
        statPass.text = passCount.toString()
        statBlock.text = blockCount.toString()
        statAvgLatency.text = if (latencies.isNotEmpty()) "$avgLatency" else "-"
        statMaxLatency.text = if (latencies.isNotEmpty()) "$maxLatency" else "-"

        if (latencies.isEmpty()) {
            statFitWindow.text = "-"
        } else if (maxLatency <= windowMs) {
            statFitWindow.text = "✅ เข้า"
            statFitWindow.setTextColor(0xFF0F9D58.toInt())
        } else {
            statFitWindow.text = "❌ ไม่เข้า"
            statFitWindow.setTextColor(0xFFE5484D.toInt())
        }
    }
}
