package com.continentalbakingco.balancesheet20.constants

object  Constants {
    private const val PROTOCOL_HTTP = "http://"
    private const val LOCATOR_GLOBAL = "208.163.58.212"
    private const val WEB_PORT = ":880"
    private const val API_PATH = "/BalanceSheetApi/"
    const val BASE_URL = "$PROTOCOL_HTTP$LOCATOR_GLOBAL$WEB_PORT$API_PATH"
}
